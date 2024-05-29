package com.example.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapter.ExamScoreDAO;
import com.example.app.adapter.NotificationDAO;
import com.example.app.model.ExamScoreDTO;
import com.example.app.model.NotificationDTO;

import java.util.List;

public class Activity_Add_Exam_Score extends AppCompatActivity {
    EditText speak, write, listen, read;
    Button exitBtn, doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam_score);

        speak = findViewById(R.id.speaking_score);
        write = findViewById(R.id.writing_score);
        listen = findViewById(R.id.listening_score);
        read = findViewById(R.id.reading_score);
        String message = getIntent().getStringExtra("idStudent");

        Log.d("Exam score found: ", message );

        exitBtn = findViewById(R.id.exit_btn);
        doneBtn = findViewById(R.id.done_btn);
       if (!message.equals("")) {

           List<ExamScoreDTO> listExamScore = ExamScoreDAO.getInstance(Activity_Add_Exam_Score.this)
                           .SelectExamScore(Activity_Add_Exam_Score.this,
                                   "ID_EXAM_SCORE = ? AND STATUS = ?",
                                   new String[] {message, "0"});

           Log.d("Exam score found: ", listExamScore.toString());

           speak.setText(listExamScore.get(0).getSpeaking());
           write.setText(listExamScore.get(0).getWriting());
           listen.setText(listExamScore.get(0).getListening());
           read.setText(listExamScore.get(0).getReading());

           exitBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Exam_Score.this);
                   builder.setTitle("Xác nhận")
                           .setMessage("Bạn có chắc chắn muốn thoát?");
                   builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {
                           finish();
                       }
                   });
                   builder.setNegativeButton("Hủy", null);
                   builder.show();
               }
           });
           doneBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (speak.getText().toString().equals("") || write.getText().toString().equals("")
                           || listen.getText().toString().equals("") || read.getText().toString().equals("")) {
                       Toast.makeText(Activity_Add_Exam_Score.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                   } else {
                       AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Exam_Score.this);
                       builder.setTitle("Xác nhận")
                               .setMessage("Bạn có chắc chắn muốn thêm thông báo không?");
                       builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int which) {

                               ExamScoreDTO examScore = new ExamScoreDTO(null, null,null,
                                       speak.getText().toString(), write.getText().toString(),
                                       listen.getText().toString(), read.getText().toString());
                               try {
                                   int rowEffect = ExamScoreDAO.getInstance(Activity_Add_Exam_Score.this)
                                           .UpdateExamScore(Activity_Add_Exam_Score.this,
                                                   examScore, "ID_EXAM_SCORE = ? AND STATUS = ?",
                                                   new String[] {message, "0"});
                                   if (rowEffect > 0) {
                                       Toast.makeText(Activity_Add_Exam_Score.this,
                                               "Sửa điểm học viên thành công", Toast.LENGTH_SHORT).show();
                                   } else {
                                       Toast.makeText(Activity_Add_Exam_Score.this,
                                               "Sửa điểm học viên thất bại", Toast.LENGTH_SHORT).show();
                                   }
                               } catch (Exception e) {
                                   Log.d("Update exam score of student", "failed");
                               }

                           }
                       });
                       builder.setNegativeButton("Hủy", null);
                       builder.show();
                   }
               }
           });
       }
    }
}