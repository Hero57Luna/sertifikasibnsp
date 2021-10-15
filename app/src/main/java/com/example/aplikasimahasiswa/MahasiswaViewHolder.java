package com.example.aplikasimahasiswa;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MahasiswaViewHolder extends RecyclerView.ViewHolder {

    public TextView txt_nim, txt_nama, txt_alamat, txt_kelamin;
    public ImageView image;

    public MahasiswaViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_nim = itemView.findViewById(R.id.txt_nim);
        txt_nama = itemView.findViewById(R.id.txt_nama);
        txt_alamat = itemView.findViewById(R.id.txt_alamat);
        txt_kelamin = itemView.findViewById(R.id.txt_kelamin);
        image = itemView.findViewById(R.id.picture);
    }
}
