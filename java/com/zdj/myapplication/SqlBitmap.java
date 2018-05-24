package com.zdj.myapplication;

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
    private final static String DB_NAME = "city_db";
    /**表名**/
    private String TABLE_IMAGE = "city_data";
    public String T_ID = "id";//字段名
    public String T_BLOB = "name";//字段名
    /**表名**/
    private String TABLE_IMAGE_suixin = "recycler";
    public String T_ID_xin = "_id";//字段名
    public String T_BLOB_xin = "T_BLOB";//字段名
    public String CITY="city";//字段名
    public String TIME="time"; //字段名
    /**
     * 表名
     */
    private String TABLE_MINSU="minsu";
    public String ID_minsu="_id";//字段名
    public String PRICE_minsu="price";//字段名
    public String SHAP_minsu="shap";//字段名
    public String DIS_minsu="dis";//字段名
    public String PHONE_minsu="phone";//字段名
    public String PHOTO_minsu="photo";//字段名
    public String PHOTO_minsu1="photo1";//字段名
    public String NAME_minsu="name";//字段名
    /**
     * 表名
     */
    private String TABLE_JINDIAN="jindian";
    private String ID_jindian="_id";//字段名
    private String NAME_jindian="name";//字段名
    /**
     * 创建表SQL 字符串
     */
    private String TABLE_JINDIAN_CREATE="Create table "+TABLE_JINDIAN+"("+ID_jindian
            +" INTEGER PRIMARY KEY AUTOINCREMENT ,"+NAME_jindian+" VARCHAR );";
    /**
     * 创建表SQL 字符串
     */
    private String TABLE_MINSU_CREATE="Create table "+TABLE_MINSU+"("+ID_minsu
            +" INTEGER PRIMARY KEY AUTOINCREMENT ,"
            +PHOTO_minsu+" BLOB ,"+PHOTO_minsu1+" BLOB ,"+PRICE_minsu+" VARCHAR ,"+SHAP_minsu+" VARCHAR ,"+DIS_minsu+" VARCHAR ,"+PHONE_minsu+" VARCHAR ,"
            +NAME_minsu+" VARCHAR );";
    /**创建表SQL 字符串**/
    private String TABLE_IMAGE_CREATE = "Create table " + TABLE_IMAGE + "(" + T_ID
            + " VARCHAR ,"
            + T_BLOB + " VARCHAR );";
    /**创建表SQL 字符串**/
    private String TABLE_IMAGE_CREATE_XIN = "Create table " + TABLE_IMAGE_suixin + "(" + T_ID_xin
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + T_BLOB_xin + " BLOB ,"+CITY+" VARCHAR ,"+TIME+" VARCHAR );";

    public SqlBitmap(Context context){
        super(context, DB_NAME, null, DATABASE_VERSION);
    }
    public void insertData(String id,String name){
        ContentValues initValues = new ContentValues();
        initValues.put(T_ID,id);
        initValues.put(T_BLOB,name);
        SQLiteDatabase db=getDatabaseWrit();
        db.insert(TABLE_IMAGE,null,initValues);
        db.close();
    }
    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> list;
        SQLiteDatabase db = getDatabaseWrit();
        Cursor cursor = db.query(TABLE_IMAGE, null, null, null, null, null, null);//数据的查询
        HashMap<String, Object> bindData;
        list = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            bindData = new HashMap<>();
            bindData.put(T_ID, cursor.getString(0));
            bindData.put(T_BLOB, cursor.getString(1));
            list.add(bindData);
        }
        cursor.close();
        db.close();
        return list;
    }
    public int getSize(){
        SQLiteDatabase db = getDatabaseWrit();
        Cursor cursor = db.query(TABLE_IMAGE, null, null, null, null, null, null);//数据的查询
        return cursor.getCount();
    }
    //查询获取id
    public String queryID(String name){
        SQLiteDatabase db=getDatabaseWrit();
        String sql="select *from "+TABLE_IMAGE+" where "+T_BLOB+"=?";
        Cursor cursor;
        cursor=db.rawQuery(sql,new String[]{name});
        if (cursor.getCount()==0)
            return "";
        cursor.moveToNext();
        String id=cursor.getString(0);
        cursor.close();
        db.close();
        return id;
    }
    //获取SQLiteDatabase
    private SQLiteDatabase getDatabaseRead() {
        return this.getReadableDatabase();
    }
    private SQLiteDatabase getDatabaseWrit() {
        return this.getWritableDatabase();
    }

    /**创建民宿数据**/
    public Long create_minsu(Bitmap photo,Bitmap photo1, String price, String shap,String dis,String phone,String name) {
        ContentValues initValues = new ContentValues();
        Long id;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        if (photo==null)
            initValues.put(PHOTO_minsu,"");
        else{
            photo.compress(Bitmap.CompressFormat.PNG, 100, os);
            initValues.put(PHOTO_minsu, os.toByteArray());//以字节形式保存
        }
        ByteArrayOutputStream os1 = new ByteArrayOutputStream();
        if (photo1==null)
            initValues.put(PHOTO_minsu1,"");
        else{
            photo1.compress(Bitmap.CompressFormat.PNG, 100, os1);
            initValues.put(PHOTO_minsu1, os1.toByteArray());//以字节形式保存
        }
        initValues.put(PRICE_minsu,price);
        initValues.put(SHAP_minsu,shap);
        initValues.put(DIS_minsu,dis);
        initValues.put(PHONE_minsu,phone);
        initValues.put(NAME_minsu,name);
        SQLiteDatabase db =getDatabaseWrit();
        id = db.insert(TABLE_MINSU, null, initValues);//保存数据
        db.close();
        return id;
    }
    /**创建便签本数据**/
    public Long createData_xin(Bitmap bmp, String city, String time) {
        ContentValues initValues = new ContentValues();
        Long id;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        if (bmp==null)
            initValues.put(T_BLOB_xin,"");
        else{
            bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
            initValues.put(T_BLOB_xin, os.toByteArray());//以字节形式保存
        }
        initValues.put(CITY,city);
        initValues.put(TIME,time);
        SQLiteDatabase db =getDatabaseWrit();
        id = db.insert(TABLE_IMAGE_suixin, null, initValues);//保存数据
        db.close();
        return id;
    }
    /**
     * 获取民宿的所有数据
     */
    public List<Map<String, Object>> getDate_minsu(){
        List<Map<String, Object>> list = null;
        SQLiteDatabase db = getDatabaseRead();
        Cursor cursor = db.query(TABLE_MINSU, null, null, null, null, null, null);//数据的查询
        HashMap<String, Object> bindData;
        list = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            bindData = new HashMap<>();
            bindData.put(ID_minsu, cursor.getLong(0));
            byte[] in = cursor.getBlob(1);
            if (in.equals(""))
                bindData.put(PHOTO_minsu, null);
            else {
                Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
                bindData.put(PHOTO_minsu, bmpout);
            }

            byte[] in1 = cursor.getBlob(2);
            if (in1.equals(""))
                bindData.put(PHOTO_minsu1, null);
            else {
                Bitmap bmpout1 = BitmapFactory.decodeByteArray(in1, 0, in1.length);
                bindData.put(PHOTO_minsu1, bmpout1);
            }
            bindData.put(PRICE_minsu,cursor.getString(3));
            bindData.put(SHAP_minsu,cursor.getString(4));
            bindData.put(DIS_minsu,cursor.getString(5));
            bindData.put(PHONE_minsu,cursor.getString(6));
            bindData.put(NAME_minsu,cursor.getString(7));
            list.add(bindData);
        }
        cursor.close();
        db.close();
        return list;
    }
    /**
     * 获取便签本的所有数据
     * @return
     */
    public List<Map<String, Object>> getData_xin() {
        List<Map<String, Object>> list = null;
        SQLiteDatabase db = getDatabaseRead();
        Cursor cursor = db.query(TABLE_IMAGE_suixin, null, null, null, null, null, null);//数据的查询
        HashMap<String, Object> bindData;
        list = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            bindData = new HashMap<>();
            bindData.put(T_ID_xin, cursor.getLong(0));
            byte[] in = cursor.getBlob(1);
            if (in.equals(""))
                bindData.put(T_BLOB_xin, null);
            else {
                Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
                bindData.put(T_BLOB_xin, bmpout);
            }
            bindData.put(CITY,cursor.getString(2));
            bindData.put(TIME,cursor.getString(3));
            list.add(bindData);
        }
        cursor.close();
        db.close();
        return list;
    }
    /**
     * 获取对应id的民宿item的所有数据
     */
    public HashMap<String,Object> getItem_minsu(long id){
        HashMap<String,Object> hashMap;
        hashMap=new HashMap<>();
        SQLiteDatabase db = getDatabaseWrit();
        Cursor cursor=db.query(TABLE_MINSU,null,"_id="+id,null,null,null,null);
        cursor.moveToFirst();
        hashMap.put(ID_minsu, cursor.getLong(0));
        byte[] in = cursor.getBlob(1);
        if (in.equals(""))
            hashMap.put(PHOTO_minsu, null);
        else {
            Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
            hashMap.put(PHOTO_minsu, bmpout);
        }
        hashMap.put(PRICE_minsu,cursor.getString(2));
        hashMap.put(SHAP_minsu,cursor.getString(3));
        hashMap.put(DIS_minsu,cursor.getString(4));
        hashMap.put(PHONE_minsu,cursor.getString(5));
        hashMap.put(NAME_minsu,cursor.getString(6));
        return hashMap;
    }
    /**
     * 返回对应id的便签本item的所有数据
     * @param id
     * @return
     */
    public HashMap<String,Object> getItem_xin(long id){
        HashMap<String,Object> hashMap;
        hashMap=new HashMap<>();
        SQLiteDatabase db = getDatabaseWrit();
        Cursor cursor=db.query(TABLE_IMAGE_suixin,null,"_id="+id,null,null,null,null);
        cursor.moveToFirst();
        hashMap.put(T_ID_xin, cursor.getLong(0));
        byte[] in = cursor.getBlob(1);
        if (in.equals(""))
            hashMap.put(T_BLOB_xin, null);
        else {
            Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
            hashMap.put(T_BLOB_xin, bmpout);
        }
        hashMap.put(CITY,cursor.getString(2));
        hashMap.put(TIME,cursor.getString(3));
        return hashMap;
    }
    //删除民宿的数据
    public void delete_minsu(long position) {
        SQLiteDatabase db = getDatabaseWrit();
        db.delete(TABLE_MINSU, "_id="+position, null);//数据的删除
        db.close();
    }
    //对随心记数据的删除
    public void delete_xin(long position) {
        SQLiteDatabase db = getDatabaseWrit();
        db.delete(TABLE_IMAGE_suixin, "_id="+position, null);//数据的删除
        db.close();
    }

    /**
     * 存入搜索景点的数据
     */
    public Long insert_city(String city){
        ContentValues initValues=new ContentValues();
        Long id;
        initValues.put(NAME_jindian,city);
        SQLiteDatabase db =getDatabaseWrit();
        id = db.insert(TABLE_JINDIAN, null, initValues);//保存数据
        db.close();
        return id;
    }

    /**
     * 返回景点城市的数据
     */
    public String[] send_city(){
        String[] str =null;
        int i=0;
        SQLiteDatabase db = getDatabaseWrit();
        Cursor cursor = db.query(TABLE_JINDIAN, null, null, null, null, null, null);//数据的查询
        if (cursor.getCount()<5 && cursor.getCount()>0){
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                str=new String[cursor.getCount()];
                str[i++]=cursor.getString(1);
            }
        }
        else if (cursor.getCount()>5){
            str=new String[5];
            for (int j=cursor.getCount();j>cursor.getCount()-5;j--){
                Cursor cursor1=db.query(TABLE_JINDIAN,null,"_id="+j,null,null,null,null);
                cursor1.moveToFirst();
                str[i++]=cursor1.getString(1);
            }
        }
        db.close();
        return str;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_IMAGE_CREATE);
        sqLiteDatabase.execSQL(TABLE_IMAGE_CREATE_XIN);
        sqLiteDatabase.execSQL(TABLE_MINSU_CREATE);
        sqLiteDatabase.execSQL(TABLE_JINDIAN_CREATE);
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
        String sqlxin = " drop table " + TABLE_IMAGE_CREATE_XIN;
        String sqlminsu=" drop table "+TABLE_MINSU_CREATE;
        String sqljindian=" drop table "+TABLE_JINDIAN_CREATE;
        sqLiteDatabase.execSQL(sqljindian);
        sqLiteDatabase.execSQL(sqlminsu);
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sqlxin);
        onCreate(sqLiteDatabase);
    }
}
