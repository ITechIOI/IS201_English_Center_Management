package com.example.app.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.app.R;
import com.example.app.activity.Activity_Add_Account;
import com.example.app.activity.Activity_Add_Class;
import com.example.app.activity.Activity_Add_Exam_Score;
import com.example.app.activity.Activity_Add_Notification;
import com.example.app.activity.Activity_Add_Official_Student;
import com.example.app.activity.Activity_Add_Potential_Student;
import com.example.app.activity.Activity_Add_Program;
import com.example.app.activity.Activity_Add_Schedule;
import com.example.app.activity.Activity_Add_Staff;
import com.example.app.activity.Activity_Notifications_Second_Layer;
import com.example.app.activity.Activity_Notifications_ToolBars_Second_Layer;
import com.example.app.adapter.ClassDAO;
import com.example.app.adapter.ClassroomDAO;
import com.example.app.adapter.PotentialStudentDAO;

import java.util.ArrayList;
import java.util.List;

public class List_Adapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<Object> arrayDataList;
    int idLayout;

    public List_Adapter(@NonNull Context context,int idLayout, ArrayList<Object> arrayDataList) {
        super(context, idLayout, arrayDataList);
        mContext = context;
        this.arrayDataList = arrayDataList;
        this.idLayout = idLayout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Object item = arrayDataList.get(position);
        convertView = LayoutInflater.from(getContext()).inflate(idLayout, parent, false);
        if (item instanceof List_Information)
            List_Information_View(convertView, position);
        else if (item instanceof NotificationDTO)
            List_Notifications_View(convertView, position);
        else if (item instanceof ExamScoreDTO)
            List_Score_View(convertView, position);
        else if (item instanceof ProgramDTO)
            List_Education_Program_View(convertView, position);
        else if (item instanceof ClassDTO)
            List_Class_View(convertView, position);
        else if (item instanceof PotentialStudentDTO)
            PotentialStudentDTO_View(convertView, position);
        else if (item instanceof OfficialStudentDTO)
            OfficialStudentDTO_View(convertView, position);
        else if (item instanceof ScheduleDTO)
            Schedule_View(convertView, position);
        else if (item instanceof AccountDTO)
            Account_View(convertView, position);
        else if (item instanceof  CertificateDTO)
            Certificate_View(convertView, position);
        else if (item instanceof ClassroomDTO)
            Classroom_View(convertView, position);
        else
            throw new IllegalArgumentException("Unknown data type: " + item.getClass().getName());

        return convertView;
    }

    private void List_Information_View (@Nullable View convertView, int position) {
        ImageView listImage = convertView.findViewById(R.id.listImage);
        TextView listName = convertView.findViewById(R.id.listName);

        List_Information listData = (List_Information) arrayDataList.get(position);

        listImage.setImageResource(listData.getImg());
        listName.setText(listData.getName());
    }
    private void List_Notifications_View (@Nullable View convertView, int position) {
        TextView title, poster, description;
        title = convertView.findViewById(R.id.title);
        poster = convertView.findViewById(R.id.poster);
        description = convertView.findViewById(R.id.content);

        NotificationDTO listNotifications = (NotificationDTO) arrayDataList.get(position);

        title.setText(listNotifications.getTitle());
        poster.setText(listNotifications.getPoster());
        description.setText(listNotifications.getDescription());

        if (convertView.findViewById(R.id.edit_notification) != null) {
            Button remove = convertView.findViewById(R.id.remove_notification);
            remove.setTag(position);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                    // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int position = (int) v.getTag();
                            arrayDataList.remove(position);
                            notifyDataSetChanged();
                        }
                    });

                    // Nút "Hủy": Không làm gì cả, đóng dialog
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    // Tạo và hiển thị AlertDialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
            Button edit = convertView.findViewById(R.id.edit_notification);
            edit.setTag(position);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Activity_Add_Notification.class);
                    intent.putExtra("idNotification", "1");
                    mContext.startActivity(intent);
                }
            });
        }
    }
    private void List_Score_View (@Nullable View convertView, int position) {
        TextView speak, write, listen, read;
        ExamScoreDTO listScore = (ExamScoreDTO) arrayDataList.get(position);

        speak = convertView.findViewById(R.id.speaking_score);
        speak.setText(listScore.getSpeaking());
        write = convertView.findViewById(R.id.writing_score);
        write.setText(listScore.getWriting());
        listen = convertView.findViewById(R.id.listening_score);
        listen.setText(listScore.getListening());
        read = convertView.findViewById(R.id.reading_score);
        read.setText(listScore.getReading());

        if (convertView.findViewById(R.id.edit_score) != null) {
            //Giao diện nhân viên
            TextView studentName, idStudent;
            idStudent = convertView.findViewById(R.id.idStudent);
            idStudent.setText(listScore.getIdStudent());
            studentName = convertView.findViewById(R.id.studentName);
            studentName.setText("Haha");
            Button editScore = convertView.findViewById(R.id.edit_score);
            editScore.setTag(position);
            editScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Activity_Add_Exam_Score.class);
                    intent.putExtra("idStudent", "1");
                    mContext.startActivity(intent);
                }
            });
        } else {
            //Giao diện học viên
            TextView courseID;
            courseID = convertView.findViewById(R.id.courseID);
            courseID.setText(listScore.getIdExam());
        }


/*        courseID.setText(listScore.courseID);
        speak.setText(listScore.speak);
        write.setText(listScore.write);
        listen.setText(listScore.listen);
        read.setText(listScore.read);*/
    }
    private void List_Education_Program_View (@Nullable View convertView, int position) {
        TextView idProgram, programName, inputScore, outputScore, content, speak, write, read, listen, tuitionFees, certificate, studyPeriod;
        idProgram = convertView.findViewById(R.id.idProgram);
        programName = convertView.findViewById(R.id.program_name);
        inputScore = convertView.findViewById(R.id.inputScore);
        outputScore = convertView.findViewById(R.id.outputScore);
        content = convertView.findViewById(R.id.content);
        speak = convertView.findViewById(R.id.speaking);
        write = convertView.findViewById(R.id.writing);
        listen = convertView.findViewById(R.id.listening);
        read = convertView.findViewById(R.id.reading);
        studyPeriod = convertView.findViewById(R.id.studyPeriod);
        tuitionFees = convertView.findViewById(R.id.tuitionFees);
        certificate = convertView.findViewById(R.id.certificate);

        ProgramDTO listEducationProgram = (ProgramDTO) arrayDataList.get(position);

        idProgram.setText(listEducationProgram.getIdProgram());
        programName.setText(listEducationProgram.getNameProgram());
        inputScore.setText(listEducationProgram.getInputScore());
        outputScore.setText(listEducationProgram.getOutputScore());
        content.setText(listEducationProgram.getContent());
        speak.setText(listEducationProgram.getSpeakingScore());
        write.setText(listEducationProgram.getWritingScore());
        listen.setText(listEducationProgram.getListeningScore());
        read.setText(listEducationProgram.getReadingScore());
        studyPeriod.setText(listEducationProgram.getStudy_period());
        tuitionFees.setText(String.valueOf(listEducationProgram.getTuitionFees()));
        certificate.setText(listEducationProgram.getIdCertificate());

        Button editProgram, removeProgram;
        if (convertView.findViewById(R.id.edit_program) != null) {
            removeProgram = convertView.findViewById(R.id.remove_program);
            removeProgram.setTag(position);
            removeProgram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                    // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int position = (int) v.getTag();
                            arrayDataList.remove(position);
                            notifyDataSetChanged();
                        }
                    });

                    // Nút "Hủy": Không làm gì cả, đóng dialog
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    // Tạo và hiển thị AlertDialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

            editProgram = convertView.findViewById(R.id.edit_program);
            editProgram.setTag(position);
            editProgram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editProgram = new Intent(getContext(), Activity_Add_Program.class);
                    editProgram.putExtra("idProgram", idProgram.getText());
                    mContext.startActivity(editProgram);
                }
            });
        }
    }
    private void List_Class_View (@Nullable View convertView, int position) {
        TextView classID, className, startDate, endDate, programID, teacherName;
        ClassDTO listClass = (ClassDTO) arrayDataList.get(position);

        classID = convertView.findViewById(R.id.classID);
        className = convertView.findViewById(R.id.class_name);
        startDate = convertView.findViewById(R.id.start_date);
        endDate = convertView.findViewById(R.id.end_date);
        programID = convertView.findViewById(R.id.programID);
        teacherName = convertView.findViewById(R.id.teacher_name);

        classID.setText(listClass.getClassID());
        className.setText(listClass.getClassName());
        startDate.setText(listClass.getStartDate());
        endDate.setText(listClass.getEndDate());
        programID.setText(listClass.getIdProgram());
        teacherName.setText(listClass.getIdTeacher());
        if (convertView.findViewById(R.id.edit_class) != null) {
            //Tính năng thêm/xóa lớp của nhân viên ghi danh
            Button editClass = convertView.findViewById(R.id.edit_class);
            editClass.setTag(position);
            editClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Activity_Add_Class.class);
                    intent.putExtra("classID", "1");
                    mContext.startActivity(intent);
                }
            });
            Button removeClass = convertView.findViewById(R.id.remove_class);
            removeClass.setTag(position);
            removeClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                    // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int position = (int) v.getTag();
                            arrayDataList.remove(position);
                            notifyDataSetChanged();
                        }
                    });

                    // Nút "Hủy": Không làm gì cả, đóng dialog
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    // Tạo và hiển thị AlertDialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }

        if (convertView.findViewById(R.id.detailBtn) != null) {
            TextView staffID = convertView.findViewById(R.id.staffID);
            staffID.setText(listClass.getIdStaff());
            Button detailBtn = convertView.findViewById(R.id.detailBtn);
            detailBtn.setTag(position);
            detailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if (convertView.findViewById(R.id.edit_class) != null) {
                        //Nhân viên ghi danh
                        intent = new Intent(getContext(), Activity_Notifications_ToolBars_Second_Layer.class);
                        intent.putExtra("classID", "1");
                    } else {
                        //Nhân viên học vụ
                        intent = new Intent(getContext(), Activity_Notifications_Second_Layer.class);
                        intent.putExtra("classID", "1");
                        intent.putExtra("idCertificate", "");
                    }
                    mContext.startActivity(intent);
                }
            });
        }
    }
    /*private void List_Class_Manage_View (@Nullable View convertView, int position) {
        TextView classID, className, startDate, endDate, programID, teacherName, staffID;
        ClassDTO_Manage listClass = (ClassDTO_Manage) arrayDataList.get(position);

        classID = convertView.findViewById(R.id.classID);
        className = convertView.findViewById(R.id.class_name);
        startDate = convertView.findViewById(R.id.start_date);
        endDate = convertView.findViewById(R.id.end_date);
        programID = convertView.findViewById(R.id.programID);
        teacherName = convertView.findViewById(R.id.teacher_name);
        staffID = convertView.findViewById(R.id.staffID);

        classID.setText(listClass.getClassID());
        className.setText(listClass.getClassName());
        startDate.setText(listClass.getStartDate());
        endDate.setText(listClass.getEndDate());
        programID.setText(listClass.getIdProgram());
        teacherName.setText(listClass.getIdTeacher());
        teacherName.setText(listClass.getIdTeacher());
        staffID.setText(listClass.getIdStaff());

        Button removeClass, editClass;
        removeClass = convertView.findViewById(R.id.remove_class);
        removeClass.setTag(position);
        removeClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = (int) v.getTag();
                        arrayDataList.remove(position);
                        notifyDataSetChanged();
                    }
                });

                // Nút "Hủy": Không làm gì cả, đóng dialog
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Tạo và hiển thị AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        editClass = convertView.findViewById(R.id.edit_class);
        editClass.setTag(position);
        editClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editClass = new Intent(getContext(), Activity_Add_Class.class);
                editClass.putExtra("classID", classID.getText());
                mContext.startActivity(editClass);
            }
        });

        Button detailBtn = convertView.findViewById(R.id.detailBtn);
        detailBtn.setTag(position);
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_Notifications_ToolBars_Second_Layer.class);
                intent.putExtra("classID", classID.getText());
                intent.putExtra("idCertificate", "");
                mContext.startActivity(intent);
            }
        });
    }*/
    private void PotentialStudentDTO_View (@Nullable View convertView, int position) {
        TextView studentName, phoneNumber, gender, address, level, appointmentNumber;
        studentName = convertView.findViewById(R.id.student_name);
        phoneNumber = convertView.findViewById(R.id.phone_number);
        gender = convertView.findViewById(R.id.gender);
        level = convertView.findViewById(R.id.level);
        address = convertView.findViewById(R.id.address);
        appointmentNumber = convertView.findViewById(R.id.appointment_number);

        PotentialStudentDTO listTalentedStudent = (PotentialStudentDTO) arrayDataList.get(position);

        studentName.setText(listTalentedStudent.getStudentName());
        phoneNumber.setText(listTalentedStudent.getPhoneNumber());
        gender.setText(listTalentedStudent.getGender());
        level.setText(listTalentedStudent.getLevel());
        address.setText(listTalentedStudent.getAddress());
        appointmentNumber.setText(listTalentedStudent.getAppointmentNumber());

        Button removePotentialStudent, editPotentialStudent;

        removePotentialStudent = convertView.findViewById(R.id.remove_student);
        removePotentialStudent.setTag(position);
        removePotentialStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = (int) v.getTag();
                        arrayDataList.remove(position);

                        try {
                            int rowEffect = PotentialStudentDAO.getInstance(mContext).deletePotentialStudent(
                                    mContext, listTalentedStudent, "ID_STUDENT = ?",
                                    new String[] {listTalentedStudent.getStudentID()});
                            Log.d("Delete Potential Student Error: ", String.valueOf(rowEffect));
                        } catch (Exception e) {
                            Log.d("Delete Potential Student Error: ", e.getMessage());
                        }

                        notifyDataSetChanged();
                    }
                });

                // Nút "Hủy": Không làm gì cả, đóng dialog
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Tạo và hiển thị AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        editPotentialStudent = convertView.findViewById(R.id.edit_student);
        editPotentialStudent.setTag(position);
        editPotentialStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPotential = new Intent(getContext(), Activity_Add_Potential_Student.class);
                addPotential.putExtra("studentID", listTalentedStudent.getStudentID());
                mContext.startActivity(addPotential);
            }
        });
    }
    private void OfficialStudentDTO_View (@Nullable View convertView, int position) {
        TextView studentName, phoneNumber, gender, address, birthday;
        studentName = convertView.findViewById(R.id.student_name);
        phoneNumber = convertView.findViewById(R.id.phone_number);
        gender = convertView.findViewById(R.id.gender);
        address = convertView.findViewById(R.id.address);
        birthday = convertView.findViewById(R.id.birthday);

        OfficialStudentDTO officialStudentDTO = (OfficialStudentDTO) arrayDataList.get(position);

        studentName.setText(officialStudentDTO.getFullName());
        phoneNumber.setText(officialStudentDTO.getPhoneNumber());
        gender.setText(officialStudentDTO.getGender());
        address.setText(officialStudentDTO.getAddress());
        birthday.setText(officialStudentDTO.getBirthday());

        Button removeOfficialStudent, editOfficialStudent;

        removeOfficialStudent = convertView.findViewById(R.id.remove_student);
        removeOfficialStudent.setTag(position);
        removeOfficialStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = (int) v.getTag();
                        arrayDataList.remove(position);
                        notifyDataSetChanged();
                    }
                });

                // Nút "Hủy": Không làm gì cả, đóng dialog
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Tạo và hiển thị AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        editOfficialStudent = convertView.findViewById(R.id.edit_student);
        editOfficialStudent.setTag(position);
        editOfficialStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPotential = new Intent(getContext(), Activity_Add_Official_Student.class);
                addPotential.putExtra("studentID", "");
                mContext.startActivity(addPotential);
            }
        });
    }
    private void Schedule_View (@Nullable View convertView, int position) {
        TextView dayOfWeek, startTime, endTime, idClass, idClassroom;
        dayOfWeek = convertView.findViewById(R.id.day_of_week);
        startTime = convertView.findViewById(R.id.start_time);
        endTime = convertView.findViewById(R.id.end_time);
        idClass = convertView.findViewById(R.id.idClass);
        idClassroom = convertView.findViewById(R.id.id_classroom);

        ScheduleDTO listSchedule = (ScheduleDTO) arrayDataList.get(position);

        dayOfWeek.setText(listSchedule.getDayOfWeek());
        startTime.setText(listSchedule.getStartTime());
        endTime.setText(listSchedule.getEndTime());

        List<ClassDTO> listClass = ClassDAO.getInstance(getContext()).selectClass(getContext(),
                "ID_CLASS= ?", new String[] {listSchedule.getIdClass()});
        List<ClassroomDTO> listClassroom = ClassroomDAO.getInstance(getContext()).SelectClassroom(
                getContext(), "ID_CLASSROOM = ?", new String[] {listSchedule.idClassroom});

        idClass.setText(listClass.get(0).getClassName());
        idClassroom.setText(listClassroom.get(0).getName());

        Button editSchedule, removeSchedule;
        if (convertView.findViewById(R.id.edit_schedule) != null) {
            editSchedule = convertView.findViewById(R.id.edit_schedule);
            editSchedule.setTag(position);
            editSchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Activity_Add_Schedule.class);
                    intent.putExtra("idSchedule", "1");
                    mContext.startActivity(intent);
                }
            });

            removeSchedule = convertView.findViewById(R.id.remove_schedule);
            removeSchedule.setTag(position);
            removeSchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                    // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int position = (int) v.getTag();
                            arrayDataList.remove(position);
                            notifyDataSetChanged();
                        }
                    });

                    // Nút "Hủy": Không làm gì cả, đóng dialog
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    // Tạo và hiển thị AlertDialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }
    }
    private void Certificate_View (@Nullable View convertView, int position) {
        TextView idCertificate, name, content;
        CertificateDTO listCertificate = (CertificateDTO) arrayDataList.get(position);

        idCertificate = convertView.findViewById(R.id.idCertificate);
        name = convertView.findViewById(R.id.name);
        content = convertView.findViewById(R.id.content);

        //  idCertificate.setText(listCertificate.getIdCertificate());
        name.setText(listCertificate.getName());
        content.setText(listCertificate.getContent());

        Button detailBtn = convertView.findViewById(R.id.detailBtn);
        detailBtn.setTag(position);
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_Notifications_Second_Layer.class);
                intent.putExtra("idCertificate", "1");
                intent.putExtra("classID", "");
                mContext.startActivity(intent);
            }
        });

    }
    private void Account_View (@Nullable View convertView, int position) {
        TextView idAccount, idUser, username, password;
        AccountDTO listAccount = (AccountDTO) arrayDataList.get(position);

        idAccount = convertView.findViewById(R.id.idAccount);
        idUser = convertView.findViewById(R.id.idUser);
        username = convertView.findViewById(R.id.username);
        password = convertView.findViewById(R.id.password);

        idAccount.setText(listAccount.getIdAccount());
        idUser.setText(listAccount.getIdUser());
        username.setText(listAccount.getUserName());
        password.setText(listAccount.getPassWord());

        Button remove = convertView.findViewById(R.id.remove_account);
        remove.setTag(position);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = (int) v.getTag();
                        arrayDataList.remove(position);
                        notifyDataSetChanged();
                    }
                });

                // Nút "Hủy": Không làm gì cả, đóng dialog
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Tạo và hiển thị AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        Button edit = convertView.findViewById(R.id.edit_account);
        edit.setTag(position);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_Add_Account.class);
                intent.putExtra("idAccount", "1");
                mContext.startActivity(intent);
            }
        });
    }
    private void Classroom_View (@Nullable View convertView, int position) {
        ClassroomDTO listClassromm = (ClassroomDTO) arrayDataList.get(position);
        TextView idClassroom, nameClassroom, name;
        idClassroom = convertView.findViewById(R.id.idClassroom);
        nameClassroom = convertView.findViewById(R.id.nameRoom);
        name = convertView.findViewById(R.id.name);

        idClassroom.setText(listClassromm.getIdRoom());
        nameClassroom.setText(listClassromm.getName());

        LinearLayout layout;
        layout = convertView.findViewById(R.id.linear_layout);
        if (true) {
            name.setText("Hê hê");
            layout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.red_border));
        } else layout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.blue_border));
    }

    private void Staff_View (@Nullable View convertView, int position) {
        StaffDTO listStaff = (StaffDTO) arrayDataList.get(position);
        TextView idStaff, fullName, address, phoneNumber, gender, birthday, type;
        idStaff = convertView.findViewById(R.id.idStaff);
        idStaff.setText(listStaff.getIdStaff());

        fullName = convertView.findViewById(R.id.fullName);
        fullName.setText(listStaff.getFullName());

        address = convertView.findViewById(R.id.address);
        address.setText(listStaff.getAddress());

        phoneNumber = convertView.findViewById(R.id.phoneNumber);
        phoneNumber.setText(listStaff.getPhoneNumber());

        gender = convertView.findViewById(R.id.gender);
        gender.setText(listStaff.getGender());

        birthday = convertView.findViewById(R.id.birthday);
        birthday.setText(listStaff.getBirthday());

        type = convertView.findViewById(R.id.type);
        type.setText("Nhân viên ghi danh");

        Button removeStaff = convertView.findViewById(R.id.remove_staff);
        removeStaff.setTag(position);
        removeStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = (int) v.getTag();
                        arrayDataList.remove(position);
                        notifyDataSetChanged();
                    }
                });

                // Nút "Hủy": Không làm gì cả, đóng dialog
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Tạo và hiển thị AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        Button editStaff = convertView.findViewById(R.id.edit_staff);
        editStaff.setTag(position);
        editStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_Add_Staff.class);
                intent.putExtra("idStaff", listStaff.getIdStaff());
                intent.putExtra("idTeacher", "");
                mContext.startActivity(intent);
            }
        });
    }

    private void Teacher_View (@Nullable View convertView, int position) {
        TeacherDTO listStaff = (TeacherDTO) arrayDataList.get(position);
        TextView idStaff, fullName, address, phoneNumber, gender, birthday, type;
        idStaff = convertView.findViewById(R.id.idStaff);
        idStaff.setText(listStaff.getIdTeacher());

        fullName = convertView.findViewById(R.id.fullName);
        fullName.setText(listStaff.getFullName());

        address = convertView.findViewById(R.id.address);
        address.setText(listStaff.getAddress());

        phoneNumber = convertView.findViewById(R.id.phoneNumber);
        phoneNumber.setText(listStaff.getPhoneNumber());

        gender = convertView.findViewById(R.id.gender);
        gender.setText(listStaff.getGender());

        birthday = convertView.findViewById(R.id.birthday);
        birthday.setText(listStaff.getBirthday());

        type = convertView.findViewById(R.id.type);
        type.setText("Giáo viên");

        Button removeStaff = convertView.findViewById(R.id.remove_staff);
        removeStaff.setTag(position);
        removeStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = (int) v.getTag();
                        arrayDataList.remove(position);
                        notifyDataSetChanged();
                    }
                });

                // Nút "Hủy": Không làm gì cả, đóng dialog
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Tạo và hiển thị AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        Button editStaff = convertView.findViewById(R.id.edit_staff);
        editStaff.setTag(position);
        editStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_Add_Staff.class);
                intent.putExtra("idStaff", "");
                intent.putExtra("idTeacher", listStaff.getIdTeacher());
                mContext.startActivity(intent);
            }
        });
    }

}
