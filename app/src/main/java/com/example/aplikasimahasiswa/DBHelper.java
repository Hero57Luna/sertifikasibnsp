package com.example.aplikasimahasiswa;

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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class DBHelper extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    public String downloadUrl;


    public DBHelper() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Mahasiswa.class.getSimpleName());
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    public Task<Void> InsertData(Mahasiswa mhs) {
        return databaseReference.push().setValue(mhs);
    }

    public Query get() {
        return databaseReference.orderByKey();
    }

    public void uploadImage(Uri filePath) {
        if(filePath != null)
        {
            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            UploadTask uploadTask;
            uploadTask = ref.putFile(filePath);

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
                        downloadUrl = downloadUri.toString();
                        System.out.println("Download URL : " + downloadUrl);
                    }
                }
            });

//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    //downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
//                    System.out.println(downloadUrl);
//                }
//            });
        }
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }
}
