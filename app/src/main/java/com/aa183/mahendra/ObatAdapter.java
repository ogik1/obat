package com.aa183.mahendra;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ObatAdapter extends RecyclerView.Adapter<ObatAdapter.ObatViewHolder> {

    private Context context;
    private ArrayList<Obat> dataObat;
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    public ObatAdapter(Context context, ArrayList<Obat> dataObat) {
        this.context = context;
        this.dataObat = dataObat;
    }

    @NonNull
    @Override
    public ObatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_obat, parent, false);
        return new ObatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObatViewHolder holder, int position) {
        Obat tempObat = dataObat.get(position);
        holder.idObat = tempObat.getIdObat();
        holder.tv_nama.setText(tempObat.getNamaObat());
        holder.tv_headline.setText(tempObat.getPabrik());
        holder.tanggal = sdFormat.format(tempObat.getTanggal());
        holder.gambar = tempObat.getGambar();
        holder.deskripsi = tempObat.getDeskripsi();
        holder.indikasi = tempObat.getIndikasi();
        holder.kemasan = tempObat.getKemasan();
        holder.link = tempObat.getLink();

        try {
            File file = new File(holder.gambar);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            holder.imgObat.setImageBitmap(bitmap);
            holder.imgObat.setContentDescription(holder.gambar);
        }catch (FileNotFoundException er){
            er.printStackTrace();
            Toast.makeText(context, "Gagal mengambil dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return dataObat.size();
    }

    public class ObatViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnLongClickListener{

        private ImageView imgObat;
        private TextView tv_nama, tv_headline;
        private int idObat;
        private String tanggal, gambar, deskripsi, indikasi, pabrik, kemasan, link;

        public ObatViewHolder(@NonNull View itemView) {
            super(itemView);

            imgObat = itemView.findViewById(R.id.iv_obat);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_headline = itemView.findViewById(R.id.tv_pabrik);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent bukaObat = new Intent(context, TampilActivity.class);
            bukaObat.putExtra("ID", idObat);
            bukaObat.putExtra("JUDUL", tv_nama.getText().toString());
            bukaObat.putExtra("TANGGAL", tanggal);
            bukaObat.putExtra("GAMBAR", gambar);
            bukaObat.putExtra("DESKRIPSI", deskripsi);
            bukaObat.putExtra("INDIKASI", indikasi);
            bukaObat.putExtra("PABRIK", pabrik);
            bukaObat.putExtra("KEMASAN", kemasan);
            bukaObat.putExtra("LINK", link);
            context.startActivity(bukaObat);
        }

        @Override
        public boolean onLongClick(View v) {

            Intent bukaInput = new Intent(context, InputActivity.class);
            bukaInput.putExtra("OPERASI", "update");
            bukaInput.putExtra("ID", idObat);
            bukaInput.putExtra("NAMA", tv_nama.getText());
            bukaInput.putExtra("TANGGAL", tanggal);
            bukaInput.putExtra("GAMBAR", gambar);
            bukaInput.putExtra("DESKRIPSI", deskripsi);
            bukaInput.putExtra("INDIKASI", indikasi);
            bukaInput.putExtra("PABRIK", pabrik);
            bukaInput.putExtra("KEMASAN", kemasan);
            bukaInput.putExtra("LINK", link);
            context.startActivity(bukaInput);
            return true;
        }
    }
}
