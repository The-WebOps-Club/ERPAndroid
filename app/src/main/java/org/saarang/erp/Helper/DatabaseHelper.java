package org.saarang.erp.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.saarang.erp.Objects.ERPNotification;
import org.saarang.erp.Objects.ERPPost;
import org.saarang.erp.Objects.ERPUser;

import java.util.List;

/**
 * Created by Ahammad on 15/06/15.
 */
public class DatabaseHelper {

    private static final String DATABASE_NAME = "SaarangERP2016";
    private static final int DATABASE_VERSION = 3;

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
            db.execSQL(ERPNotification.CREATE_TABLE);
            db.execSQL(ERPUser.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL(" DROP TABLE IF EXISTS " + ERPPost.TABLE_NAME  );
            db.execSQL(" DROP TABLE IF EXISTS " + ERPNotification.TABLE_NAME  );
            db.execSQL(" DROP TABLE IF EXISTS " + ERPUser.TABLE_NAME  );
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

    public long addNotification(ContentValues cv) {
        open();
        long id = ourDatabase.insert(ERPNotification.TABLE_NAME, null, cv);
        close();
        return id;
    }

    public long addUser(ContentValues cv) {
        open();
        long id = ourDatabase.insert(ERPUser.TABLE_NAME, null, cv);
        close();
        return id;
    }

    public List<ERPPost> getAllPosts () {
        open();
        String[] columns = ERPPost.columns;
        Cursor c = ourDatabase.query(ERPPost.TABLE_NAME, columns, null, null, null, null,  ERPPost.COLUMN_UPDATED + " DESC ");
        List<ERPPost> arrayList = ERPPost.getArrayList(c);
        close();
        return arrayList;
    }

    public List<ERPNotification> getAllNotifications () {
        open();
        String[] columns = ERPNotification.columns;
        Cursor c = ourDatabase.query(ERPNotification.TABLE_NAME, columns, null, null, null, null,  ERPNotification.KEY_ROWID + " DESC ");
        List<ERPNotification> arrayList = ERPNotification.getArrayList(c);
        close();
        return arrayList;
    }

    public List<ERPPost> getPostsFromWall (String wallId) {
        open();
        String[] columns = ERPPost.columns;
        Cursor c = ourDatabase.query(ERPPost.TABLE_NAME, columns, ERPPost.COLUMN_WALL + " LIKE ?", new String[]{ "%" + wallId + "%"},
                null, null,  ERPPost.COLUMN_UPDATED + " DESC ");
        List<ERPPost> arrayList = ERPPost.getArrayList(c);
        close();
        return arrayList;
    }

    public ERPPost getPost (String postId) {
        open();
        String[] columns = ERPPost.columns;
        Cursor c = ourDatabase.query(ERPPost.TABLE_NAME, columns, ERPPost.COLUMN_POST_ID + " LIKE ?", new String[]{ postId },
                null, null,  ERPPost.COLUMN_UPDATED + " DESC ");
        List<ERPPost> arrayList = ERPPost.getArrayList(c);
        close();
        return arrayList.get(0);
    }

    public String getComments(String postId){
        String comment = " ";
        open();
        Cursor c = ourDatabase.query(ERPPost.TABLE_NAME, new String[]{ERPPost.COLUMN_COMMENTS}, ERPPost.COLUMN_POST_ID + " = ?",
                new String[]{postId}, null, null,  ERPPost.COLUMN_UPDATED + " DESC ");
        while (c.moveToNext()){
            comment = c.getString(0);
        }
        close();
        return comment;
    }

    public String getAcknowledgment(String postId){
        String comment = " ";
        open();
        Cursor c = ourDatabase.query(ERPPost.TABLE_NAME, new String[]{ERPPost.COLUMN_ACKNOWLEDGE}, ERPPost.COLUMN_POST_ID + " = ?",
                new String[]{postId}, null, null,  ERPPost.COLUMN_UPDATED + " DESC ");
        while (c.moveToNext()){
            comment = c.getString(0);
        }
        close();
        return comment;
    }

    public ERPUser getUser(String userId) {
        open();
        String[] columns = ERPUser.columns;
        Cursor c = ourDatabase.query(ERPUser.TABLE_NAME, columns, ERPUser.COLUMN_USERID + " LIKE ?",
                new String[]{ userId }, null, null,  null);
        ERPUser user = ERPUser.parseUserFromCV(c);
        close();
        return user;
    }

    public ERPPost getRandomPost(){

        return getAllPosts().get(0);
    }

    public void markPostAsUpdated(String postId, String acknowledgment){
        open();
        ContentValues cv = new ContentValues();
        cv.put(ERPPost.COLUMN_IF_ACKNOWLEDGED, 1);
        cv.put(ERPPost.COLUMN_ACKNOWLEDGE, acknowledgment);
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

