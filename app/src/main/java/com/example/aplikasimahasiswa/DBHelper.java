package com.example.aplikasimahasiswa;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBHelper {

    private DatabaseReference databaseReference;

    public DBHelper() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Mahasiswa.class.getSimpleName());
    }

    public Task<Void> InsertData(Mahasiswa mhs) {
        return databaseReference.push().setValue(mhs);
    }
}