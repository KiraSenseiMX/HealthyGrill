package mx.itesm.osamukt.healthygrill;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by OsamuKT on 26/02/2015.
 */
public class DataBaseManager {
    public static final String TABLE_NAME     = "recipes";
    public static final String CN_ID          = "_id";
    public static final String CN_RECIPENAME  = "recipeName";
    public static final String CN_FUNCTION    = "function";
    public static final String CN_TEMPERATURE = "temperature";
    public static final String CN_TOOL        = "tool";
    public static final String CN_INGREDIENTS = "ingredients";
    public static final String CN_PROCESS     = "process";
    public static final String CREATE_TABLE   = "create table "
        + TABLE_NAME     + " ("
        + CN_ID          + " integer primary key autoincrement,"
        + CN_RECIPENAME  + " text not null,"
        + CN_FUNCTION    + " text,"
        + CN_TEMPERATURE + " text,"
        + CN_TOOL        + " text,"
        + CN_INGREDIENTS + " text not null,"
        + CN_PROCESS     + " text not null);";
    private DbHelper helper;
    private SQLiteDatabase db;
    public DataBaseManager(Context context) {
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }
    private ContentValues generarContentValues (String recipeName, String function, String temperature, String tool, String ingredients, String process) {
        ContentValues valores = new ContentValues();
        valores.put(CN_RECIPENAME, recipeName);
        valores.put(CN_FUNCTION, function);
        valores.put(CN_TEMPERATURE, temperature);
        valores.put(CN_TOOL, tool);
        valores.put(CN_INGREDIENTS, ingredients);
        valores.put(CN_PROCESS, process);
        return valores;
    }
    private String[] columnas = new String[]{CN_ID,CN_RECIPENAME,CN_FUNCTION,CN_TEMPERATURE,CN_TOOL,CN_INGREDIENTS,CN_PROCESS};
    public void insertar (String recipeName, String function, String temperature, String tool, String ingredients, String process){
        db.insert(TABLE_NAME, null, generarContentValues(recipeName, function, temperature, tool, ingredients, process));
    }
    public void eliminar (String recipeName) {
        db.delete(TABLE_NAME,CN_RECIPENAME+"=?",new String[]{recipeName});
    }
    public Cursor cargarCursorRecipes () {
        return db.query(TABLE_NAME,columnas,null,null,null,null,null);
    }
    public Cursor buscarReceta (String recipeName) {
        return db.query(TABLE_NAME,columnas,CN_RECIPENAME+"=?",new String[]{recipeName},null,null,null);
    }
}
