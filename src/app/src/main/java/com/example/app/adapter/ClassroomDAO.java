package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import com.example.app.model.ClassroomDTO;
import com.example.app.model.NotificationDTO;

public class ClassroomDAO {
    public static ClassroomDAO instance;
    private ClassroomDAO(Context context) {}
    public static synchronized ClassroomDAO getInstance(Context context) {
        if (instance == null) {
            instance = new ClassroomDAO(context);
        }
        return instance;
    }
    public int InsertNewClassroom(Context context, ClassroomDTO classroom)  {
        int rowEffect = -1;

        ContentValues values = new ContentValues();

        int maxId = DataProvider.getInstance(context).getMaxId("CLASSROOM", "ID_CLASSROOM");

        values.put("ID_CLASSROOM", "CLA" + String.valueOf(maxId + 1));
        values.put("NAME", classroom.getName());

        try {
            rowEffect = DataProvider.getInstance(context).insertData("CLASSROOM", values);
            if (rowEffect > 0 ) {
                Log.d("Insert New Classroom: ", "success");
            } else {
                Log.d("Insert New Classroom: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert New Classroom: ", e.getMessage());
        }

        return rowEffect;
    }

    public int UpdateClassroom(Context context, ClassroomDTO classroom, String whereClauses, String[] whereArgs) {
        int rowEffect = -1;

        ContentValues values = new ContentValues();
        values.put("ID_CLASSROOM", classroom.getIdRoom());
        values.put("NAME", classroom.getName());

        try {
            rowEffect = DataProvider.getInstance(context).updateData("CLASSROOM", values, whereClauses, whereArgs);
            return rowEffect;
        } catch (SQLException e) {
            Log.e("Update Classroom: ", e.getMessage());
        }

        return rowEffect;
    }
}
