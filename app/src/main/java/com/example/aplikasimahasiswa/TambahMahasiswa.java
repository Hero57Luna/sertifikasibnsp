package com.example.aplikasimahasiswa;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;

public class TambahMahasiswa extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    ImageView preview_foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_mahasiswa);

        EditText edit_nim = findViewById(R.id.input_nim);
        EditText edit_nama = findViewById(R.id.input_nama);
        EditText edit_alamat = findViewById(R.id.input_alamat);
        radioGroup = findViewById(R.id.input_kelamin);
        Button pilih_foto = findViewById(R.id.upload_foto);
        preview_foto = findViewById(R.id.foto_preview);


        DBHelper dbHelper = new DBHelper();

        Button btn = findViewById(R.id.button_simpan);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();

                radioButton = (RadioButton) findViewById(selectedId);

                Mahasiswa mahasiswa = new Mahasiswa(edit_nim.getText().toString(), edit_nama.getText().toString(), edit_alamat.getText().toString(), radioButton.getText().toString());
                dbHelper.uploadImage(filePath);

                dbHelper.InsertData(mahasiswa).addOnSuccessListener(suc -> {
                    Toast.makeText(getBaseContext(), "Data berhasil dimasukkan!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(err -> {
                    Toast.makeText(getBaseContext(), "Terjadi error: " + err.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

        pilih_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            preview_foto.setVisibility(View.VISIBLE);
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                preview_foto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
