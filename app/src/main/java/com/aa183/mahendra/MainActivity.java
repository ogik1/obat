package com.aa183.mahendra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Obat> dataObat;
    private RecyclerView rvObat;
    private ObatAdapter obatAdapter;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvObat = findViewById(R.id.rv_tampil_obat);
        dbHandler = new DatabaseHandler(this);
    }
    private void tampilDataObat(){
        dataObat = dbHandler.getAllObat();
        obatAdapter = new ObatAdapter(this, dataObat);
        RecyclerView.LayoutManager layManager = new LinearLayoutManager(MainActivity.this);
        rvObat.setLayoutManager(layManager);
        rvObat.setAdapter(obatAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.item_menu_tambah){
            Intent bukaInput = new Intent(getApplicationContext(), InputActivity.class);
            bukaInput.putExtra("OPERASI", "insert");
            startActivity(bukaInput);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilDataObat();
    }
}
