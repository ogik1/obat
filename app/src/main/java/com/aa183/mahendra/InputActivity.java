package com.aa183.mahendra;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class InputActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editNama, editTanggal, editDeskripsi, editIndikasi, editPabrik, editKemasan, editLink;
    private ImageView ivObat;
    private DatabaseHandler dbHandler;
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    private boolean updateDate = false;
    private int idObat = 0;
    private Button btnSimpan;
    private String tanggalobat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        editNama = findViewById(R.id.edit_nama);
        editTanggal = findViewById(R.id.edit_tanggal);
        editDeskripsi = findViewById(R.id.edit_deskripsi);
        editIndikasi = findViewById(R.id.edit_indikasi);
        editPabrik = findViewById(R.id.edit_pabrik);
        editKemasan = findViewById(R.id.edit_kemasan);
        editLink = findViewById(R.id.edit_link);
        ivObat = findViewById(R.id.iv_obat);
        btnSimpan = findViewById(R.id.btn_simpan);

        dbHandler = new DatabaseHandler(this);

        Intent terimaIntent = getIntent();
        Bundle data = terimaIntent.getExtras();
        if (data.getString("OPERASI").equals("insert")){
            updateDate = false;
        }else {
            updateDate = true;
            idObat = data.getInt("ID");
            editNama.setText(data.getString("JUDUL"));
            editTanggal.setText(data.getString("TANGGAL"));
            editDeskripsi.setText(data.getString("DESKRIPSI"));
            editIndikasi.setText(data.getString("INDIKASI"));
            editPabrik.setText(data.getString("PABRIK"));
            editKemasan.setText(data.getString("KEMASAN"));
            editLink.setText(data.getString("LINK"));
            loadImageFromInternalStorage(data.getString("GAMBAR"));
        }

        ivObat.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);
        editTanggal.setOnClickListener(this);
    }

    private  void pickImage(){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(4, 2)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                try {
                    Uri imageUri = result.getUri();
                    InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    String location = saveImageToInternalStorage(selectedImage, getApplicationContext());
                    loadImageFromInternalStorage(location);
                }catch (FileNotFoundException er){
                    er.printStackTrace();
                    Toast.makeText(this, "Ada kesalahan pemilihan gambar", Toast.LENGTH_SHORT).show();
                }
            }
        }else {
            Toast.makeText(this, "anda belum memilih gambar", Toast.LENGTH_LONG).show();
        }
    }

    public static String saveImageToInternalStorage(Bitmap bitmap, Context ctx){
        ContextWrapper ctxWrapper = new ContextWrapper(ctx);
        File file = ctxWrapper.getDir("images", MODE_PRIVATE);
        String uniqueID = UUID.randomUUID().toString();
        file = new File(file, "obat-"+ uniqueID +".jpg");
        try {
            OutputStream stream = null;
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        }catch (IOException er){
            er.printStackTrace();
        }

        Uri savedImage = Uri.parse(file.getAbsolutePath());
        return savedImage.toString();
    }

    private void loadImageFromInternalStorage(String imgLocation){
        try {
            File file = new File(imgLocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            ivObat.setImageBitmap(bitmap);
            ivObat.setContentDescription(imgLocation);
        }catch (FileNotFoundException er){
            er.printStackTrace();
            Toast.makeText(this, "Gagal mengambil dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.item_menu_hapus);

        if(updateDate==true){
            item.setEnabled(true);
            item.getIcon().setAlpha(255);
        }else{
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.input_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_menu_hapus){
            hapusData();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void simpanData(){
        String nama, gambar, deskripsi, indikasi, pabrik, kemasan, link;
        Date tanggal = new Date();
        nama = editNama.getText().toString();
        gambar = ivObat.getContentDescription().toString();
        deskripsi = editDeskripsi.getText().toString();
        indikasi = editIndikasi.getText().toString();
        pabrik = editPabrik.getText().toString();
        kemasan = editKemasan.getText().toString();
        link = editLink.getText().toString();

        try {
            tanggal = sdFormat.parse(editTanggal.getText().toString());
        }catch (ParseException er){
            er.printStackTrace();
        }

        Obat tempObat = new Obat(
                idObat, nama, tanggal, gambar, deskripsi, indikasi, pabrik, kemasan, link
        );

        if (updateDate == true){
            dbHandler.editObat(tempObat);
            Toast.makeText(this, "Data berita diperbaharui", Toast.LENGTH_SHORT).show();
        }else {
            dbHandler.tambahObat(tempObat);
            Toast.makeText(this, "Data berita ditambahkan", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void hapusData(){
        dbHandler.hapusObat(idObat);
        Toast.makeText(this, "Data Obat diHapus", Toast.LENGTH_SHORT);
    }

    public void pilihTanggal(){
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tanggalobat = dayOfMonth + "/" + month + "/" + year;

                pilihWaktu();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        pickerDialog.show();
    }

    public void pilihWaktu(){
        final  Calendar calendar = Calendar.getInstance();
        TimePickerDialog pickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tanggalobat = tanggalobat + " " + hourOfDay + ":" + minute;
                editTanggal.setText(tanggalobat);
            }
        },calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
    }

    @Override
    public void onClick(View v) {
        int idView = v.getId();

        if (idView == R.id.btn_simpan){
            simpanData();
        }else if (idView == R.id.iv_obat){
            pickImage();
        }else if (idView == R.id.edit_tanggal){
            pilihTanggal();
        }
    }
}
