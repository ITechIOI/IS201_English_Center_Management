package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.example.app.activity.Activity_Notifications;
import com.example.app.model.NotificationDTO;
import com.example.app.model.OfficialStudentDTO;

import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
    public static NotificationDAO instance;
    private NotificationDAO(Context context) {}
    public static synchronized NotificationDAO getInstance(Context context) {
        if (instance == null) {
            instance = new NotificationDAO(context);
        }
        return instance;
    }
    public int InsertNotification(Context context, NotificationDTO notification) {
        int rowEffect = -1;
        ContentValues values = new ContentValues();
        int maxId = DataProvider.getInstance(context).getMaxId("NOTIFICATION", "ID_NOTIFICATION");

        values.put("ID_NOTIFICATION", "NOT" + String.valueOf(maxId + 1));
        values.put("TITLE", notification.getTitle());
        values.put("ID_ACCOUNT", notification.getPoster());
        values.put("CONTENT", notification.getDescription());
        values.put("STATUS", 0);

        try {
            rowEffect = DataProvider.getInstance(context).insertData("NOTIFICATION", values);
            if (rowEffect > 0 ) {
                Log.d("Insert Notification: ", "success");
            } else {
                Log.d("Insert Notification: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert Notification Error: ", e.getMessage());
        }

        return rowEffect;
    }

    public int DeleteNotification(Context context, String whereClause, String[] whereArgs) {
        int rowEffect = -1;

        try {
            rowEffect = DataProvider.getInstance(context).deleteData("NOTIFICATION",whereClause, whereArgs);
            if (rowEffect > 0) {
                Log.d("Delete Notification: ", "success");
            } else {
                Log.d("Delete Notification: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert Notification: ", e.getMessage());
        }

        return rowEffect;
    }

    public int UpdateNotification(Context context, NotificationDTO notification, String whereClause, String[] whereArgs)  {
        int rowEffect = -1;

        ContentValues values = new ContentValues();
        values.put("ID_NOTIFICATION", notification.getIdNotification());
        values.put("ID_ACCOUNT", notification.getPoster());
        values.put("TITLE", notification.getTitle());
        values.put("CONTENT", notification.getDescription());

        try {
            int rowsUpdated = DataProvider.getInstance(context).updateData("NOTIFICATION", values, whereClause, whereArgs);
            return rowsUpdated;
        } catch (SQLException e) {
            Log.e("Update Notification: ", e.getMessage());
        }

        return rowEffect;
    }
    public List<NotificationDTO> SelectNotification (Context context, String whereClause, String[] whereArgs) {
        Cursor cursor = null;
        List<NotificationDTO> listNotification = new ArrayList<>();

        try {
            cursor = DataProvider.getInstance(context).selectData("NOTIFICATION", new String[]{"*"},  whereClause, whereArgs, null);
        } catch(SQLException e) {
            Log.d("Select Notification: ", e.getMessage());
        }
        String fullName = "";
        String title = "";
        String content = "";
        String idNotification = "";


        if (cursor.moveToFirst()) {
            do {
                int idNotificationIndex = cursor.getColumnIndex("ID_NOTIFICATION");
                if (idNotificationIndex!= -1) {
                    idNotification = cursor.getString(idNotificationIndex);
                }

                String idAccount = "";
                int idAccountIndex = cursor.getColumnIndex("ID_ACCOUNT");
                if (idAccountIndex!= -1) {
                    idAccount = cursor.getString(idAccountIndex);
                }
                String idUser = "";
                String whereClauseFindUser = "ID_ACCOUNT = ?";
                String[] whereArgsFindUser = new String[] {idAccount};
                Cursor fullRow = AccountDAO.getInstance(context).selectAccount(context, whereClauseFindUser, whereArgsFindUser);

                Log.d("ID_ACCOUNT: ", idAccount);

                if (fullRow!= null && fullRow.moveToFirst()) {
                    do {
                        int idUserIndex = fullRow.getColumnIndex("ID_USER");
                        if (idUserIndex!= -1) {
                            idUser = fullRow.getString(idUserIndex);
                            Log.d("idUser: ", idUser);
                        }
                    } while (fullRow.moveToNext());
                } else {
                    // Handle case where no user is found
                    Log.w("NotificationProcessor", "No user found for account ID: " + idAccount);
                }
                String whereClauseFindFullName = "ID_STAFF = ?";
                String[] whereArgsFindFullName = new String[] {idUser};
                Cursor fullRowFullName = StaffDAO.getInstance(context).SelectStaff(context, whereClauseFindFullName, whereArgsFindFullName);

                if (fullRowFullName.moveToFirst()) {
                    do {
                        int fullNameIndex = fullRowFullName.getColumnIndex("FULLNAME");
                        if (fullNameIndex!= -1) {
                            fullName = fullRowFullName.getString(fullNameIndex);
                        }
                    } while (fullRowFullName.moveToNext());
                }

                int titleIndex = cursor.getColumnIndex("TITLE");
                if (titleIndex!= -1) {
                    title = cursor.getString(titleIndex);
                }

                int contentIndex = cursor.getColumnIndex("CONTENT");
                if (contentIndex!= -1) {
                    content = cursor.getString(contentIndex);
                }

                listNotification.add(new NotificationDTO(idNotification, fullName, title, content));

               // dataArrayList.add(new NotificationDTO(idNotification, fullName, title, content));

            } while (cursor.moveToNext());
        }

        return listNotification;
    }
}
