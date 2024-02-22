package il.co.shivhit.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import il.co.shivhit.model.Cloth;

public class ClothRepository {
    private FirebaseFirestore db;
    private CollectionReference collection;

    public ClothRepository(){
        db = FirebaseFirestore.getInstance();
        collection = db.collection("Cloth");
    }

    public ClothRepository(Context context){
        try {
            db = FirebaseFirestore.getInstance();
        }
        catch (Exception e){
            FirebaseInstance instance = FirebaseInstance.instance(context);
            db = FirebaseFirestore.getInstance(FirebaseInstance.app);
        }
        collection = db.collection("Cloths");
    }

    public Task<Boolean> add (Cloth cloth){
        TaskCompletionSource<Boolean> taskCompletion = new TaskCompletionSource<Boolean>();
        DocumentReference document = collection.document();
        cloth.setIdfs(document.getId());
        document.set(cloth).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            taskCompletion.setResult(true);}}).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                taskCompletion.setResult(false);}});

        return taskCompletion.getTask();
    }
}
