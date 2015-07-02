package org.saarang.erp.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

import org.saarang.erp.Objects.ERPPost;
import org.saarang.erp.Objects.ERPWall;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahammad on 15/06/15.
 */
public class DatabaseHelper {

    private static final String DATABASE_NAME = "SaarangERP2016";
    private static final int DATABASE_VERSION = 1;

    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME,null,DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub _id INTEGER PRIMARY KEY
            db.execSQL(ERPPost.ERPNEWSFEED_CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL(" DROP TABLE IF EXISTS " + ERPPost.ERPNEWSFEED_CREATE_TABLE  );
            onCreate(db);
        }

    }

    public DatabaseHelper(Context c){
        ourContext = c;
    }

    public DatabaseHelper open(){
        ourHelper = new DbHelper (ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        ourHelper.close();
    }

    public long addNewsFeed(ContentValues cv) {
        open();
        long id = ourDatabase.insert(ERPPost.TABLE_NAME, null, cv);
        close();
        return id;
    }

    public List<ERPPost> getAllPosts () {
        open();
        String[] columns = ERPPost.columns;
        Cursor c = ourDatabase.query(ERPPost.TABLE_NAME, columns, null, null, null, null,  ERPPost.COLUMN_UPDATED + " DESC ");
        List<ERPPost> arrayList = new ArrayList<>();
        Gson gson = new Gson();
        while ( c.moveToNext() ){
            ERPPost post = new ERPPost(c.getString(2), c.getString(3), c.getString(5), c.getString(6),
                    gson.fromJson(c.getString(4), ERPWall.class), (c.getInt(9) > 0), c.getString(8), c.getString(7),
                    c.getString(10), c.getString(11) );
            arrayList.add(post);
        }
        close();
        return arrayList;
    }

    public void markPostAsUpdated(String postId){
        open();
        ContentValues cv = new ContentValues();
        cv.put(ERPPost.COLUMN_IF_ACKNOWLEDGED, 1);
        ourDatabase.update(ERPPost.TABLE_NAME, cv, ERPPost.COLUMN_POST_ID+" = ?", new String[]{postId});
        close();
    }

    public void updateComment(String postId, String comment){
        open();
        ContentValues cv = new ContentValues();
        cv.put(ERPPost.COLUMN_COMMENTS, comment);
        ourDatabase.update(ERPPost.TABLE_NAME, cv, ERPPost.COLUMN_POST_ID+" = ?", new String[]{postId});
        close();
    }


//    public long addTag(ContentValues cv){
//        long id = ourDatabase.insert(DATABASE_TABLE, null, cv);
//        return id;
//    }
//
//
//    public List<TagObject> getCategory(String category){
//        open();
//        String[] columns = new String[] {KEY_ROWID, TAG_ID, TAG_NAME, TAG_CATEGORY};
//        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, TAG_CATEGORY + "= ?", new String[]{category}, null, null,  KEY_ROWID + " ASC");
//        List<TagObject> arrayList = new ArrayList<>();
//        while ( c.moveToNext() ){
//            TagObject tag = new TagObject(c.getString(2), c.getString(3), c.getInt(1));
//            arrayList.add(tag);
//        }
//        close();
//        return arrayList;
//
//    }


//        public Cursor getCardById (int id) {
//            String[] columns = new String[] {KEY_ROWID, KEY_URL, KEY_TAG, KEY_QCFLAG, KEY_KEYSPACE, KEY_TRUTH, KEY_ANSWERED_OR_NOT, KEY_IMAGE};
//            Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + " = " + id, null, null, null,  KEY_ROWID + " ASC");
//            return c;
//        }
//
//        public Void deleteCardFromQue(int id){
//            int count = ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + " = " + id, null);
//            Log.d("Number Deleted", Integer.toString(count));
//            return null;
//        }
//
//        public int getCountOfCards(){
//            String[] columns = new String[] {KEY_ROWID, KEY_URL, KEY_TAG, KEY_QCFLAG, KEY_KEYSPACE, KEY_TRUTH, KEY_ANSWERED_OR_NOT, KEY_IMAGE};
//            Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ANSWERED_OR_NOT + " = " + 0, null, null, null,  KEY_ROWID + " ASC");
//            int count = c.getCount();
//            return count;
//        }


}

