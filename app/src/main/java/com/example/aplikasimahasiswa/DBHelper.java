package com.example.aplikasimahasiswa;

import android.app.ProgressDialog;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class DBHelper {

    private DatabaseReference databaseReference;

    public String downloadUrl;
    public double progress;


    public DBHelper() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Mahasiswa.class.getSimpleName());

    }

    public Task<Void> InsertData(Mahasiswa mhs) {
        return databaseReference.push().setValue(mhs);
    }

    public Query get() {
        return databaseReference.orderByKey();
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }
}
