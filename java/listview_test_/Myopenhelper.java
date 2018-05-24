package listview_test_;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/7/19 0019.
 */


public class Myopenhelper extends SQLiteOpenHelper {
    private SQLiteDatabase getDatabaseWrit() {
        return this.getWritableDatabase();
    }
    public void delete(long position) {
        SQLiteDatabase db = getDatabaseWrit();
        db.delete("xingcheng", "_id="+position,null);//数据的删除
        db.close();
    }
    public Myopenhelper(Context context) {
            super(context,"city.db",null,1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table xingcheng(_id integer primary key autoincrement,name varchar(20),year integer(10),month integer(10),day integer(10))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("create table xingcheng(_id integer primary key autoincrement,name varchar(20),year integer(10),month integer(10),day integer(10))");
        }
}

