package news_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/8/20 0020.
 */

public class Loginopenhelper extends SQLiteOpenHelper {
    private SQLiteDatabase getDatabaseWrit() {
        return this.getWritableDatabase();
    }
    public Loginopenhelper(Context context) {
        super(context,"admindata.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table admins(_id integer primary key autoincrement,admin varchar(20),password varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("create table admins(_id integer primary key autoincrement,admin varchar(20),password varchar(20))");
    }
}
