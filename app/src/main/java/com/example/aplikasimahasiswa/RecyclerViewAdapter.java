package com.example.aplikasimahasiswa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    ArrayList<Mahasiswa> list = new ArrayList<>();

    public RecyclerViewAdapter(Context ctx) {
        this.context = ctx;
    }

    public void setItems(ArrayList<Mahasiswa> mhs) {
        list.addAll(mhs);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new MahasiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MahasiswaViewHolder mahasiswaViewHolder = (MahasiswaViewHolder) holder;
        Mahasiswa mhs = list.get(position);
        mahasiswaViewHolder.txt_nim.setText(mhs.getNim());
        mahasiswaViewHolder.txt_nama.setText(mhs.getNama());
        mahasiswaViewHolder.txt_alamat.setText(mhs.getAlamat());
        mahasiswaViewHolder.txt_kelamin.setText(mhs.getKelamin());
        Glide.with(mahasiswaViewHolder.image.getContext()).load(mhs.getImageUrl()).into(mahasiswaViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
