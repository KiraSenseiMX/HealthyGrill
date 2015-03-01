package mx.itesm.osamukt.healthygrill;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by OsamuKT on 26/02/2015.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "recipes.sqlite";
    private static final int DB_SCHEME_VERSION = 1;
    public DbHelper (Context context){
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(DataBaseManager.CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
