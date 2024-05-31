package il.co.shivhit.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import il.co.shivhit.model.Cloth;
import il.co.shivhit.model.Cloths;

public class ClothRepository {
    private FirebaseFirestore db;
    private CollectionReference collection;
    private TaskCompletionSource completionSource;

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


    public Task<Boolean> add(Cloth cloth){
        TaskCompletionSource<Boolean> taskCompletion = new TaskCompletionSource<Boolean>();
        DocumentReference document = collection.document();
        cloth.setIdfs(document.getId());

        document.set(cloth)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        taskCompletion.setResult(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        taskCompletion.setResult(false);
                    }
                });

        return  taskCompletion.getTask();
    }

    public Task<Boolean> delete(Cloth cloth){
        completionSource = new TaskCompletionSource<>();
        DocumentReference document = collection.document(cloth.getIdfs());
        document.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        completionSource.setResult(true);}})
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completionSource.setResult(false);
                        completionSource.setException(e);
                    }
                });
        return completionSource.getTask();
    }


    public Task<Cloths> getAll() {
        TaskCompletionSource<Cloths> clothsCompletion = new TaskCompletionSource<>();

        Cloths cloths = new Cloths();
        collection.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            for (DocumentSnapshot document : querySnapshot) {
                                Cloth cloth = document.toObject(Cloth.class);
                                if (cloth != null)
                                    cloths.add(cloth);
                            }
                            clothsCompletion.setResult(cloths);
                        }
                        else
                            clothsCompletion.setResult(cloths);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        clothsCompletion.setResult(null);
                    }
                });

        return clothsCompletion.getTask();
    }

    public Task<Cloths> filter(String category, String color){
        TaskCompletionSource<Cloths> clothsCompletion = new TaskCompletionSource<>();
        Cloths cloths = new Cloths();
        collection.whereEqualTo("category", category).whereEqualTo("color", color).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            for (DocumentSnapshot document : querySnapshot) {
                                Cloth cloth = document.toObject(Cloth.class);
                                if (cloth != null)
                                    cloths.add(cloth);
                            }
                            clothsCompletion.setResult(cloths);
                        }
                        else
                            clothsCompletion.setResult(cloths);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        clothsCompletion.setResult(null);
                    }
                });

        return clothsCompletion.getTask();
    }

    public Task<Cloths> filter(String category){
        TaskCompletionSource<Cloths> clothsCompletion = new TaskCompletionSource<>();
        Cloths cloths = new Cloths();
        collection.whereEqualTo("category", category).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            for (DocumentSnapshot document : querySnapshot) {
                                Cloth cloth = document.toObject(Cloth.class);
                                if (cloth != null)
                                    cloths.add(cloth);
                            }
                            clothsCompletion.setResult(cloths);
                            Log.d("qqq", "Clothes: " + String.valueOf(cloths.size()));
                        }
                        else
                            clothsCompletion.setResult(cloths);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        clothsCompletion.setResult(null);
                    }
                });

        return clothsCompletion.getTask();
    }
}
