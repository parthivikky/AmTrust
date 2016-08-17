package mobello.amtrust.com.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Parthi on 21-Jul-16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static DBHelper getInstance(){
        return new DBHelper(AmTrustApp.getInstance());
    }

    private static final String DB_NAME = "AmTrust";

    private static final int DB_VERSION = 1;

    private static final String QUICK_SCAN_FEATURES = "quick_scan_features";

    private static final String FEATURES = "features";

    private static final String IS_WORKING = "is_working";

    private Context context;

    private static final String QUICK_SCAN_CREAT_QUERY = "CREATE TABLE IF NOT EXISTS " + QUICK_SCAN_FEATURES + "("
            + FEATURES + " TEXT," + IS_WORKING + " INTEGER);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUICK_SCAN_CREAT_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + QUICK_SCAN_CREAT_QUERY);
    }

    public void quickScanFeatures(String feature_name, int is_working){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FEATURES,feature_name);
        values.put(IS_WORKING,is_working);
        database.insert(QUICK_SCAN_FEATURES,null,values);
        database.close();
    }

    public JSONArray getQuickScanFeatures(){
        SQLiteDatabase database = this.getReadableDatabase();
        JSONArray jsonArray = new JSONArray();
        String selectQuery = "SELECT * FROM " + QUICK_SCAN_FEATURES;
        try {
            Cursor cursor = database.rawQuery(selectQuery, null);
            if(cursor.moveToFirst()){
                do{
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name" , cursor.getString(cursor.getColumnIndex(FEATURES)));
                    jsonObject.put("is_working",cursor.getInt(cursor.getColumnIndex(IS_WORKING)));
                    jsonArray.put(jsonObject);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    public void clearQuickScanData(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(QUICK_SCAN_FEATURES,null,null);
        database.close();
    }
}
