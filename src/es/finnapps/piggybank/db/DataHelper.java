package es.finnapps.piggybank.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import es.finnapps.piggybank.model.Piggy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class DataHelper {
    private static final String TAG = DataHelper.class.getName();

    private SQLiteDatabase db;

    public DataHelper(Context context) {
        DbHelper openHelper = new DbHelper(context);
        db = openHelper.getWritableDatabase();
    }

    private void deletePiggy(int id) {
        String where = DbHelper.PIGGY_ID + " = " + id;
        db.delete(DbHelper.TABLE_NAME_PIGGYS, where, new String[] { String.valueOf(id) });
    }

    public void addPiggy(Piggy piggy) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.PIGGY_ACCOUNT_NUMBER, piggy.getNumber());
        values.put(DbHelper.PIGGY_AMOUNT, piggy.getAmount());
        values.put(DbHelper.PIGGY_EXPIRATION, piggy.getExpiration());
        values.put(DbHelper.PIGGY_NAME, piggy.getName());
        values.put(DbHelper.PIGGY_TYPE, piggy.getType());
        long id = db.insert(DbHelper.TABLE_NAME_PIGGYS, null, values);
        piggy.setId((int) id);
        // add shared if necessary to autorization table.
    }

    public Piggy updatePiggy(Piggy piggy) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.PIGGY_ACCOUNT_NUMBER, piggy.getNumber());
        values.put(DbHelper.PIGGY_AMOUNT, piggy.getAmount());
        values.put(DbHelper.PIGGY_EXPIRATION, piggy.getExpiration());
        values.put(DbHelper.PIGGY_NAME, piggy.getName());
        values.put(DbHelper.PIGGY_TYPE, piggy.getType());
        db.update(DbHelper.TABLE_NAME_PIGGYS, values, DbHelper.PIGGY_ID + " = " + piggy.getId(), null);
        return getPiggyById(piggy.getId());
    }

    private Piggy getPiggyById(int id) {
        String where = DbHelper.PIGGY_ID + " = " + id;
        Piggy newPiggy = null;
        Cursor cursor = getPiggysCursor(where, "ASC");
        if (cursor.moveToNext()) {
//            newPiggy = new Piggy(
//                    cursor.getColumnIndex(DbHelper.PIGGY_NAME),
//                    cursor.getColumnIndex(DbHelper.PIGGY_ACCOUNT_NUMBER),
//                    cursor.getColumnIndex(DbHelper.PIGGY_AMOUNT),
//                    new Date(cursor.getColumnIndex(DbHelper.PIGGY_EXPIRATION)),
//                    cursor.getColumnIndex(DbHelper.PIG), 
//                    DbHelper.PIGGY_TYPE, 
                    // FOR SHARED A NEW CURSOR IS NEEDED WITH ACCOUNt NUMBER);
//                    );
        }
        return newPiggy;
    }

    public List<Piggy> getAllPiggys() {
        List<Piggy> allPiggys = new LinkedList <Piggy>();
        Cursor cursor = getPiggysCursor(null, "ASC");
            while (cursor.moveToNext()) {
//                Piggy newPiggy = new Piggy(
//                        cursor.getColumnIndex(DbHelper.PIGGY_NAME),
//                        cursor.getColumnIndex(DbHelper.PIGGY_ACCOUNT_NUMBER),
//                        cursor.getColumnIndex(DbHelper.PIGGY_AMOUNT),
//                        new Date(cursor.getColumnIndex(DbHelper.PIGGY_EXPIRATION)),
//                        cursor.getColumnIndex(DbHelper.PIG), 
//                        DbHelper.PIGGY_TYPE, 
//                        // FOR SHARED A NEW CURSOR IS NEEDED WITH ACCOUNt NUMBER);
//                        );
//                        allPiggys.add(newPiggy);
            }
            cursor.close();
            return allPiggys;
        }

    public Cursor getPiggysCursor(String selection, String order) {
        return db.query(DbHelper.TABLE_NAME_PIGGYS, null, selection, null, null, null, "ASC");
    }
}
