package com.example.aplikasimahasiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edit_nim = findViewById(R.id.input_nim);
        EditText edit_nama = findViewById(R.id.input_nama);
        EditText edit_alamat = findViewById(R.id.input_alamat);
        EditText edit_kelamin = findViewById(R.id.input_kelamin);

        DBHelper dbHelper = new DBHelper();

        Button btn = findViewById(R.id.button_simpan);
        Button btn_lihat = findViewById(R.id.button_lihat);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mahasiswa mahasiswa = new Mahasiswa(edit_nim.getText().toString(), edit_nama.getText().toString(), edit_alamat.getText().toString(), edit_kelamin.getText().toString());

                dbHelper.InsertData(mahasiswa).addOnSuccessListener(suc -> {
                    Toast.makeText(getBaseContext(), "Data berhasil dimasukkan!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(err -> {
                    Toast.makeText(getBaseContext(), "Terjadi error: "+err.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

        btn_lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                startActivity(intent);
            }
        });
    }
}