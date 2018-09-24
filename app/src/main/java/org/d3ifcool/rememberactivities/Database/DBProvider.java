package org.d3ifcool.rememberactivities.Database;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.d3ifcool.rememberactivities.Message;
/**
 * Created by Yoga Wahyu Yuwono on 20/09/2018.
 */

public class DBProvider extends ContentProvider {
        DBHelper myDBHelper;
        private static final int RA = 100;
        private static final int RA_ID =101;
        private static final int RAAA=102;
        private static final int RAAA_ID=103;

        private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        static {
            sUriMatcher.addURI(RememberActivitiesContract.CONTENT_AUTHORITY,RememberActivitiesContract.MY_PATH,RA);
            sUriMatcher.addURI(RememberActivitiesContract.CONTENT_AUTHORITY,RememberActivitiesContract.MY_PATH+"/#", RA_ID);
            sUriMatcher.addURI(RememberActivitiesContract.CONTENT_AUTHORITY,RememberActivitiesContract.PATH_PENCAPAIAN,RAAA);
            sUriMatcher.addURI(RememberActivitiesContract.CONTENT_AUTHORITY,RememberActivitiesContract.PATH_PENCAPAIAN+"/#",RAAA_ID);
        }
        public static final String LOG_TAG=DBProvider.class.getSimpleName();
        @Override
        public boolean onCreate() {
            myDBHelper=new DBHelper(getContext());

            return true;
        }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = myDBHelper.getReadableDatabase();

        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case RA:
                cursor = db.query(RememberActivitiesContract.myContractEntry.Table_Name,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case RA_ID:
                selection = RememberActivitiesContract.myContractEntry.UID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor=db.query(RememberActivitiesContract.myContractEntry.Table_Name,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case RAAA:
                cursor=db.query(RememberActivitiesContract.pencapaianEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case RAAA_ID:
                selection = RememberActivitiesContract.pencapaianEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor=db.query(RememberActivitiesContract.pencapaianEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match=sUriMatcher.match(uri);
        switch (match){
            case RA:
                return RememberActivitiesContract.myContractEntry.CONTENT_LIST_TYPE;
            case RA_ID:
                return RememberActivitiesContract.myContractEntry.CONTENT_ITEM_TYPE;
            case RAAA:
                return RememberActivitiesContract.pencapaianEntry.CONTENT_LIST_TYPE_ACCOUNT;
            case RAAA_ID:
                return RememberActivitiesContract.pencapaianEntry.CONTENT_ITEM_TYPE_ACCOUNT;
            default:
                throw new IllegalArgumentException("unknown URI"+uri + "with match "+match);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match=sUriMatcher.match(uri);
        switch (match){
            case RA:
                return insertData(uri,contentValues);
            case RAAA:
                return insertDataPencapaian(uri,contentValues);
            default:
                throw new IllegalArgumentException("insertion failed"+uri);
        }

    }

    private Uri insertDataPencapaian(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db=myDBHelper.getWritableDatabase();
        long id=db.insert(RememberActivitiesContract.pencapaianEntry.TABLE_NAME,null,contentValues);
        if (id==-1){
            Message.message(getContext(),"Gagal memasukan data");
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,id);
    }

    private Uri insertData(Uri uri,ContentValues values) {

        SQLiteDatabase db=myDBHelper.getWritableDatabase();
        long id=db.insert(RememberActivitiesContract.myContractEntry.Table_Name,null,values);
        if (id==-1){
            Message.message(getContext(),"gagal memasukan data");
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,id);
    }



    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db=myDBHelper.getWritableDatabase();
        int rowsDeleted;
        final int match=sUriMatcher.match(uri);
        switch (match){
            case RA:
                rowsDeleted=db.delete(RememberActivitiesContract.myContractEntry.Table_Name,selection,selectionArgs);
                break;
            case RA_ID:
                selection= RememberActivitiesContract.myContractEntry.UID + "=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted=db.delete(RememberActivitiesContract.myContractEntry.Table_Name,selection,selectionArgs);
                break;
            case RAAA:
                rowsDeleted=db.delete(RememberActivitiesContract.pencapaianEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case RAAA_ID:
                selection= RememberActivitiesContract.pencapaianEntry._ID + "=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted=db.delete(RememberActivitiesContract.pencapaianEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("error while deleted row"+uri);
        }
        if (rowsDeleted!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsDeleted;
    }
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match=sUriMatcher.match(uri);
        switch (match){
            case RA:
                return updateData(uri,contentValues,selection,selectionArgs);
            case RA_ID:
                selection=RememberActivitiesContract.myContractEntry.UID+ "=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateData(uri,contentValues,selection,selectionArgs);
            case RAAA:
                return updateDataPencapaian(uri,contentValues,selection,selectionArgs);
            case RAAA_ID:
                selection=RememberActivitiesContract.pencapaianEntry._ID+ "=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateDataPencapaian(uri,contentValues,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("error while update data"+uri);
        }

    }

    private int updateDataPencapaian(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        if (contentValues.size()==0){
            return 0;
        }
        SQLiteDatabase db=myDBHelper.getWritableDatabase();
        int rowsUpdated=db.update(RememberActivitiesContract.pencapaianEntry.TABLE_NAME,contentValues,selection,selectionArgs);
        if (rowsUpdated!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }

    private int updateData(Uri uri,ContentValues values,String selection,String[] selectionArgs){
        if (values.size()==0){
            return 0;
        }
        SQLiteDatabase db=myDBHelper.getWritableDatabase();
        int rowsUpdated=db.update(RememberActivitiesContract.myContractEntry.Table_Name,values,selection,selectionArgs);
        if (rowsUpdated!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }
}
