package com.example.aplikasimahasiswa;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class TambahMahasiswa extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    ProgressBar progressBar;
    ImageView preview_foto;
    Button kirim_foto;
    Button btn;
    DBHelper dbHelper;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private int STORAGE_PERMISSION_CODE = 1;
    private String imgUrl;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_mahasiswa);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        progressBar = findViewById(R.id.progressBar);
        EditText edit_nim = findViewById(R.id.input_nim);
        EditText edit_nama = findViewById(R.id.input_nama);
        EditText edit_alamat = findViewById(R.id.input_alamat);
        radioGroup = findViewById(R.id.input_kelamin);
        Button pilih_foto = findViewById(R.id.upload_foto);
        kirim_foto = findViewById(R.id.kirim_foto);
        preview_foto = findViewById(R.id.foto_preview);
        btn = findViewById(R.id.button_simpan);

        dbHelper = new DBHelper();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String imageURL = dbHelper.getDownloadUrl();
                int selectedId = radioGroup.getCheckedRadioButtonId();

                radioButton = (RadioButton) findViewById(selectedId);

                Mahasiswa mahasiswa = new Mahasiswa(edit_nim.getText().toString(), edit_nama.getText().toString(), edit_alamat.getText().toString(), radioButton.getText().toString(), imgUrl);

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
                if (ContextCompat.checkSelfPermission(TambahMahasiswa.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    chooseImage();
                }
                else {
                    requestStoragePermission();
                }
            }
        });

        kirim_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Mengirim...", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.VISIBLE);
                uploadImage(filePath);
            }
        });
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Ijinkan akses")
                    .setMessage("Ijinkan akses ke media penyimpanan untuk menggunakan aplikasi")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(TambahMahasiswa.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        }
        else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseImage();
            }
            else {
                Toast.makeText(getBaseContext(), "Ijinkan akses", Toast.LENGTH_SHORT).show();
            }
        }
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
            kirim_foto.setEnabled(true);
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

    public void uploadImage(Uri filePath) {
        if(filePath != null)
        {
            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            UploadTask uploadTask;
            uploadTask = (UploadTask) ref.putFile(filePath).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    progressBar.setProgress((int) progress);
                }
            });

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        imgUrl = downloadUri.toString();
                        Toast.makeText(getBaseContext(), "Terkirim", Toast.LENGTH_SHORT).show();
                        btn.setEnabled(true);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TambahMahasiswa.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
