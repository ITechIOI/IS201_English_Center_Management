package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.example.app.model.ClassDTO;
import com.example.app.model.CollectionTuitionFeesDTO;

import java.util.ArrayList;
import java.util.List;

public class CollectionTuitionFeesDAO {
    public static CollectionTuitionFeesDAO instance;
    private CollectionTuitionFeesDAO(Context context) {}
    public static synchronized CollectionTuitionFeesDAO getInstance(Context context) {
        if (instance == null) {
            instance = new CollectionTuitionFeesDAO(context);
        }
        return instance;
    }
/*    "ID_BILL TEXT PRIMARY KEY , " +
            "ID_STUDENT TEXT, " +
            "COLLECTION_DATE TEXT, " +
            "TOTAL_MONEY INTEGER, " +
            "STATUS INTEGER," +*/

    public int InsertCollection_Tuition_Fees(Context context, CollectionTuitionFeesDTO collection) {
        int rowEffect = -1;

        ContentValues values = new ContentValues();
        int maxId = DataProvider.getInstance(context).getMaxId("COLLECTING_TUITION_FEES", "ID_BILL");

        values.put("ID_BILL", "CTF" + String.valueOf(maxId + 1));
        values.put("ID_STUDENT", collection.getIdStudent());
        values.put("COLLECTION_DATE", collection.getCollectionDate());
        values.put("TOTAL_MONEY", collection.getMoney());
        values.put("STATUS", 0);

        try {
            rowEffect = DataProvider.getInstance(context).insertData("COLLECTING_TUITION_FEES", values);
            if (rowEffect > 0 ) {
                Log.d("Insert Collecting Tuition Fees: ", "success");
            } else {
                Log.d("Insert Collecting Tuition Fees: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert Collecting Tuition Fees: ", e.getMessage());
        }

        return rowEffect;
    }

    public int UpdateCollection_Tuition_Fees(Context context, CollectionTuitionFeesDTO collection,
                                             String whereClause, String[] whereArg) {
        int rowEffect = -1;
        ContentValues values = new ContentValues();

        values.put("ID_BILL", "CTF" + collection.getIdBill());
        values.put("ID_STUDENT", collection.getIdStudent());
        values.put("COLLECTION_DATE", collection.getCollectionDate());
        values.put("TOTAL_MONEY", collection.getMoney());
        values.put("STATUS", 0);

        try {
            rowEffect = DataProvider.getInstance(context).updateData("COLLECTING_TUITION_FEES",
                    values, whereClause, whereArg);
            if (rowEffect > 0 ) {
                Log.d("Update Collecting Tuition Fees: ", "success");
            } else {
                Log.d("Update Collecting Tuition Fees: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Update Collecting Tuition Fees: ", e.getMessage());
        }

        return rowEffect;
    }

    public List<CollectionTuitionFeesDTO> SelectCollectionTuitionFees(Context context, String whereClause, String[] whereArg) {
        List<CollectionTuitionFeesDTO> listCollection = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataProvider.getInstance(context).selectData("COLLECTING_TUITION_FEES",
                    new String[]{"*"},  whereClause, whereArg, null);
        } catch(SQLException e) {
            Log.d("Select Collection Tuition Fees: ", e.getMessage());
        }

        String idBill = "", idStudent = "", collectionDate = "", money = "";
        if (cursor.moveToFirst()) {
            do {

                int idBillIndex = cursor.getColumnIndex("ID_BILL");
                if (idBillIndex != -1) {
                    idBill = cursor.getString(idBillIndex);
                }
                int idStudentIndex = cursor.getColumnIndex("ID_STUDENT");
                if (idStudentIndex!= -1) {
                    idStudent = cursor.getString(idStudentIndex);
                }
                int collectionDateIndex = cursor.getColumnIndex("COLLECTION_DATE");
                if (collectionDateIndex != -1) {
                    collectionDate = cursor.getString(collectionDateIndex);
                }
                int moneyIndex = cursor.getColumnIndex("TOTAL_MONEY");
                if (moneyIndex != -1) {
                    money = cursor.getString(moneyIndex);
                }
                listCollection.add(new CollectionTuitionFeesDTO(idBill, idStudent, collectionDate, money));

            } while (cursor.moveToNext());
        }

        return listCollection;
    }

}
