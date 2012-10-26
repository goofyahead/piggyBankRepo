package es.finnapps.piggybank.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";
    public static final String DATABASE_NAME = "piggybank.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME_PIGGYS = "Piggys";
    public static final String TABLE_NAME_AUTHORIZE = "Authorize";

    public static final String PIGGY_ID = "_id";
    public static final String PIGGY_TYPE = "piggyType";
    public static final String PIGGY_NAME = "name";
    public static final String PIGGY_AMOUNT = "amount";
    public static final String PIGGY_EXPIRATION = "expiration";
    public static final String PIGGY_ACCOUNT_NUMBER = "accountNumber";

    public static final String AUTHORIZATION_ID = "_id";
    public static final String AUTHORIZATION_ACCOUNT = "accountNumber";
    public static final String AUTHORIZATION_USER = "userPhoneNumber";

    public static final String CREATE_PIGGYS = "CREATE TABLE "
                                + TABLE_NAME_PIGGYS + " ("
                                + PIGGY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + PIGGY_TYPE + " INTEGER, "
                                + PIGGY_NAME + " TEXT, "
                                + PIGGY_AMOUNT + " REAL, "
                                + PIGGY_EXPIRATION + " TEXT, "
                                + PIGGY_ACCOUNT_NUMBER + " TEXT )";

    public static final String CREATE_AUTHORIZE = "CREATE TABLE "
                                + TABLE_NAME_AUTHORIZE + "( "
                                + AUTHORIZATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + AUTHORIZATION_ACCOUNT + " TEXT, "
                                + AUTHORIZATION_USER + " TEXT)";

    public DbHelper(Context context) {
        super(context, DbHelper.DATABASE_NAME, null, DbHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "creating database");
        db.execSQL(DbHelper.CREATE_AUTHORIZE);
        db.execSQL(DbHelper.CREATE_PIGGYS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database, this will drop tables and recreate");
        db.execSQL("DROP TABLE IF EXISTS " + DbHelper.TABLE_NAME_AUTHORIZE);
        db.execSQL("DROP TABLE IF EXISTS " + DbHelper.TABLE_NAME_PIGGYS);
        onCreate(db);
    }
}
