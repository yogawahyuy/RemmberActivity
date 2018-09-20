package org.d3ifcool.rememberactivities.Database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Yoga Wahyu Yuwono on 20/09/2018.
 */

public class RememberActivitiesContract {
    private RememberActivitiesContract(){}
    public static final String CONTENT_AUTHORITY="org.d3ifcool.rememberactivities";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://" +CONTENT_AUTHORITY);
    public static final String MY_PATH="RAA";
    public static final String PATH_PENCAPAIAN="Pencapaian";
    public static final class myContractEntry implements BaseColumns {
        public static final Uri CONTENT_URI=Uri.withAppendedPath(BASE_CONTENT_URI,MY_PATH);
        public static final String CONTENT_LIST_TYPE= ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +MY_PATH;
        public static final String  CONTENT_ITEM_TYPE= ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" +MY_PATH;
        public static final String DB_NAME = "rememberactivities";
        public static final String Table_Name = "kegiatan";
        public static final int version = 1;
        public static final String UID = "_id";
        public static final String NAME = "nama_kgt";
        public static final String tgl_mulai = "tgl_mulai";
        public static final String jam_mulai = "jam_mulai";
        public static final String tgl_berakhir = "tgl_berakhir";
        public static final String jam_berakhir = "jam_berakhir";
        public static final String catatan = "catatan";
        public static final String status="keterangan";
        public static final String create_table = "CREATE TABLE " + Table_Name + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(50), " +
                tgl_mulai + " TEXT, " + jam_mulai + " TEXT, " + tgl_berakhir + " TEXT, " + jam_berakhir + " TEXT, "  +
                catatan + " TEXT)";
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS" + Table_Name;
    }

    public static final class pencapaianEntry implements BaseColumns{
        public static final Uri CONTENT_URI=Uri.withAppendedPath(BASE_CONTENT_URI,PATH_PENCAPAIAN);
        public static final String CONTENT_LIST_TYPE_ACCOUNT= ContentResolver.CURSOR_DIR_BASE_TYPE+ "/" + CONTENT_AUTHORITY+ "/" +PATH_PENCAPAIAN;
        public static final String CONTENT_ITEM_TYPE_ACCOUNT= ContentResolver.CURSOR_ITEM_BASE_TYPE+ "/" +CONTENT_AUTHORITY+ "/" +PATH_PENCAPAIAN;
        public final static String TABLE_NAME="pencapaian";
        public final static String PENCAPAIAN_ID=BaseColumns._ID;
        public static final  String COLUMN_NAMA="name";
        public static final  String COLUMN_tanggal="tanggal";
        public static final  String COLUMN_catatan="catatan";
        public static final String SQL_CREATE_PENCAPAIAN_TABLE="CREATE TABLE "+TABLE_NAME+ " (" +
                PENCAPAIAN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_NAMA+" TEXT, " + COLUMN_tanggal+" TEXT,"+ COLUMN_catatan+" TEXT)";

    }

}
