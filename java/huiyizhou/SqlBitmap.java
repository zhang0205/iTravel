package huiyizhou;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *回忆轴数据库
 */

public class SqlBitmap extends SQLiteOpenHelper{
    /**数据库版本号**/
    private final static int DATABASE_VERSION = 1;
    /**数据库名**/
    private final static String DB_NAME = "city";
    /**表名**/
    private String TABLE_IMAGE = "image_data";
    public String T_ID = "_id";//字段名
    public String T_BLOB = "T_BLOB";//字段名
    public String CITY="city";//字段名
    public String TIME="time"; //字段名
    public String VOICE="voice";
    /**创建表SQL 字符串**/
    private String TABLE_IMAGE_CREATE = "Create table " + TABLE_IMAGE + "(" + T_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + T_BLOB + " BLOB ,"+CITY+" VARCHAR ,"+TIME+" VARCHAR ,"+VOICE+" VARCHAR );";

    public SqlBitmap(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }
    /**创建数据**/
    public Long createData(Bitmap bmp,String city,String time,String voice) {
        ContentValues initValues = new ContentValues();
        Long id = null;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, os);

        initValues.put(T_BLOB, os.toByteArray());//以字节形式保存
        initValues.put(CITY,city);
        initValues.put(TIME,time);
        initValues.put(VOICE,voice);

        SQLiteDatabase db =getDatabaseWrit();
        id = db.insert(TABLE_IMAGE, null, initValues);//保存数据
        db.close();
        return id;
    }
    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = null;
        SQLiteDatabase db = getDatabaseRead();
        Cursor cursor = db.query(TABLE_IMAGE, null, null, null, null, null, null);//数据的查询
        HashMap<String, Object> bindData = null;
        list = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            bindData = new HashMap<>();
            bindData.put(T_ID, cursor.getLong(0));
            byte[] in = cursor.getBlob(1);
            Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
            bindData.put(T_BLOB, bmpout);
            bindData.put(CITY,cursor.getString(2));
            bindData.put(TIME,cursor.getString(3));
            bindData.put(VOICE,cursor.getString(4));
            list.add(bindData);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 返回对应id的item的所有数据
     * @param id
     * @return
     */
    public HashMap<String,Object> getItem(long id){
        HashMap<String,Object> hashMap=null;
        hashMap=new HashMap<>();
        SQLiteDatabase db = getDatabaseWrit();
        Cursor cursor=db.query(TABLE_IMAGE,null,"_id="+id,null,null,null,null);
        cursor.moveToFirst();
        hashMap.put(T_ID, cursor.getLong(0));
        byte[] in = cursor.getBlob(1);
        Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
        hashMap.put(T_BLOB, bmpout);
        hashMap.put(CITY,cursor.getString(2));
        hashMap.put(TIME,cursor.getString(3));
        hashMap.put(VOICE,cursor.getString(4));
        return hashMap;
    }
    //对回忆轴数据的删除
    public void delete(long position) {
        SQLiteDatabase db = getDatabaseWrit();
        db.delete(TABLE_IMAGE, "_id="+position, null);//数据的删除
        db.close();
    }
    //获取SQLiteDatabase
    private SQLiteDatabase getDatabaseRead() {
        return this.getReadableDatabase();
    }
    private SQLiteDatabase getDatabaseWrit() {
        return this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_IMAGE_CREATE);
    }
    /**
     * 当数据库版本号发生变化时调用这个方法
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = " drop table " + TABLE_IMAGE;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
