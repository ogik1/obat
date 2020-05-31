package com.aa183.mahendra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

public class TampilActivity extends AppCompatActivity {

    private ImageView imgObat;
    private TextView tvnama, tvtanggal, tvdeskripsi, tvindikasi, tvpabrik, tvkemasan;
    private String linkobat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        imgObat = findViewById(R.id.iv_obat);
        tvnama = findViewById(R.id.tv_nama);
        tvtanggal = findViewById(R.id.tv_tgl);
        tvdeskripsi = findViewById(R.id.tv_deskripsi);
        tvindikasi = findViewById(R.id.tv_indikasi);
        tvpabrik = findViewById(R.id.tv_pabrik);
        tvkemasan = findViewById(R.id.tv_kemasan);

        Intent terimaData = getIntent();
        tvnama.setText(terimaData.getStringExtra("JUDUL"));
        tvtanggal.setText(terimaData.getStringExtra("TANGGAL"));
        tvdeskripsi.setText(terimaData.getStringExtra("DESKRIPSI"));
        tvindikasi.setText(terimaData.getStringExtra("INDIKASI"));
        tvpabrik.setText(terimaData.getStringExtra("PABRIK"));
        tvkemasan.setText(terimaData.getStringExtra("KEMASAN"));
        imgObat.setImageResource(getResources().getIdentifier(terimaData.getStringExtra("GAMBAR"), "drawable", "com.aa183.mahendra"));
        linkobat = terimaData.getStringExtra("LINK");
        String imgLocation = terimaData.getStringExtra("GAMBAR");

        try {
            File file = new File(imgLocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            imgObat.setImageBitmap(bitmap);
            imgObat.setContentDescription(imgLocation);
        }catch (FileNotFoundException er){
            er.printStackTrace();
            Toast.makeText(this, "Gagal mengambil dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }

        linkobat = terimaData.getStringExtra("LINK");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tampil_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.item_bagikan){
            Intent bagiObat = new Intent(Intent.ACTION_SEND);
            bagiObat.putExtra(Intent.EXTRA_SUBJECT, tvnama.getText().toString());
            bagiObat.putExtra(Intent.EXTRA_TEXT, linkobat);
            bagiObat.setType("text/plain");
            startActivity(Intent.createChooser(bagiObat, "bagikan Obat"));
        }
        return super.onOptionsItemSelected(item);

    }
}
