package com.example.aplikasimahasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipe);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        dbHelper = new DBHelper();
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Tambah Data");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Tambah Data")) {
            Intent intent = new Intent(MainActivity.this, TambahMahasiswa.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData() {
        dbHelper.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Mahasiswa> mhs = new ArrayList<>();

                for (DataSnapshot data : snapshot.getChildren()) {
                    Mahasiswa mahasiswa = data.getValue(Mahasiswa.class);
                    mhs.add(mahasiswa);
                }

                recyclerViewAdapter.setItems(mhs);
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}