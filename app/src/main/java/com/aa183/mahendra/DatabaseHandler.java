package com.aa183.mahendra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_obat";
    private final static String TABLE_OBAT = "t_obat";
    private final static String KEY_ID_OBAT = "idObat";
    private final static String KEY_NAMA_OBAT = "namaObat";
    private final static String KEY_TANGGAL = "tanggal";
    private final static String KEY_GAMBAR = "gambar";
    private final static String KEY_DESKRIPSI = "deskripsi";
    private final static String KEY_INDIKASI = "indikasi";
    private final static String KEY_PABRIK = "pabrik";
    private final static String KEY_KEMASAN = "kemasan";
    private final static String KEY_LINK = "link";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
    private  Context context;

    public DatabaseHandler(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;


    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_OBAT = "CREATE TABLE " + TABLE_OBAT
                + "(" + KEY_ID_OBAT + "INTERGER PRIMARY KEY,"
                + KEY_NAMA_OBAT + " TEXT," + KEY_TANGGAL + " DATE," + KEY_GAMBAR + " TEXT,"
                + KEY_DESKRIPSI + " TEXT," + KEY_INDIKASI + " TEXT," + KEY_PABRIK + " TEXT,"
                + KEY_KEMASAN + " TEXT," + KEY_LINK + " TEXT);";
        Log.d("Create Table", CREATE_TABLE_OBAT);

        db.execSQL(CREATE_TABLE_OBAT);
        inisialisasiObatawal(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_OBAT;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahObat(Obat dataObat){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_ID_OBAT, dataObat.getIdObat());
        cv.put(KEY_NAMA_OBAT, dataObat.getNamaObat());
        cv.put(KEY_TANGGAL, sdFormat.format(dataObat.getTanggal()));
        cv.put(KEY_GAMBAR, dataObat.getGambar());
        cv.put(KEY_DESKRIPSI, dataObat.getDeskripsi());
        cv.put(KEY_INDIKASI, dataObat.getIndikasi());
        cv.put(KEY_PABRIK, dataObat.getPabrik());
        cv.put(KEY_KEMASAN, dataObat.getKemasan());
        cv.put(KEY_LINK, dataObat.getLink());
        db.insert(TABLE_OBAT, null, cv);
        db.close();
    }

    public void tambahObat(Obat dataObat, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(KEY_ID_OBAT, dataObat.getIdObat());
        cv.put(KEY_NAMA_OBAT, dataObat.getNamaObat());
        cv.put(KEY_TANGGAL, sdFormat.format(dataObat.getTanggal()));
        cv.put(KEY_GAMBAR, dataObat.getGambar());
        cv.put(KEY_DESKRIPSI, dataObat.getDeskripsi());
        cv.put(KEY_INDIKASI, dataObat.getIndikasi());
        cv.put(KEY_PABRIK, dataObat.getPabrik());
        cv.put(KEY_KEMASAN, dataObat.getKemasan());
        cv.put(KEY_LINK, dataObat.getLink());

        db.insert(TABLE_OBAT, null, cv);
    }

    public void editObat(Obat dataObat){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_ID_OBAT, dataObat.getIdObat());
        cv.put(KEY_NAMA_OBAT, dataObat.getNamaObat());
        cv.put(KEY_TANGGAL, sdFormat.format(dataObat.getTanggal()));
        cv.put(KEY_GAMBAR, dataObat.getGambar());
        cv.put(KEY_DESKRIPSI, dataObat.getDeskripsi());
        cv.put(KEY_INDIKASI, dataObat.getIndikasi());
        cv.put(KEY_PABRIK, dataObat.getPabrik());
        cv.put(KEY_KEMASAN, dataObat.getKemasan());
        cv.put(KEY_LINK, dataObat.getLink());

        db.update(TABLE_OBAT, cv, KEY_ID_OBAT + "=?", new String[]{String.valueOf(dataObat.getIdObat())});
        db.close();
    }

    public void hapusObat(int idObat){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_OBAT, KEY_ID_OBAT + "=?", new String[]{String.valueOf(idObat)});
        db.close();;
    }

    public ArrayList<Obat>getAllObat(){
        ArrayList<Obat> dataObat = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_OBAT;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if (csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                }catch (ParseException er){
                    er.printStackTrace();
                }

                Obat tempObat= new Obat(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(2),
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7)
                );

                dataObat.add(tempObat);
            }while (csr.moveToNext());
        }
        return  dataObat;
    }

    private String storeImageFile(int id){
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    public void inisialisasiObatawal(SQLiteDatabase db){
        int idObat = 0;
        Date tempDate = new Date();

    }
}
