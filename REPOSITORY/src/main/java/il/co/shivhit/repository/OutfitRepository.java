package il.co.shivhit.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import il.co.shivhit.model.Outfit;
import il.co.shivhit.model.Outfits;

public class OutfitRepository {
    private FirebaseFirestore db;
    private CollectionReference collection;
    private TaskCompletionSource completionSource;
    public OutfitRepository(Context context){
        try {
            db = FirebaseFirestore.getInstance();
        }
        catch (Exception e){
            FirebaseInstance instance = FirebaseInstance.instance(context);
            db = FirebaseFirestore.getInstance(FirebaseInstance.app);
        }
        collection = db.collection("Outfits");
    }
    public Task<Boolean> add(Outfit outfit){
        TaskCompletionSource<Boolean> taskCompletion = new TaskCompletionSource<Boolean>();
        DocumentReference document = collection.document();
        outfit.setIdfs(document.getId());

        document.set(outfit)
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

    public Task<Boolean> delete(Outfit outfit){
        completionSource = new TaskCompletionSource<>();
        DocumentReference document = collection.document(outfit.getIdfs());
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


    public Task<Outfits> getAll() {
        TaskCompletionSource<Outfits> outfitsCompletion = new TaskCompletionSource<>();
        Outfits outfits = new Outfits();
        collection.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            for (DocumentSnapshot document : querySnapshot) {
                                Outfit outfit = document.toObject(Outfit.class);
                                if (outfit != null)
                                    outfits.add(outfit);
                            }
                            outfitsCompletion.setResult(outfits);
                        }
                        else
                            outfitsCompletion.setResult(outfits);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        outfitsCompletion.setResult(null);
                    }
                });

        return outfitsCompletion.getTask();
    }





}
