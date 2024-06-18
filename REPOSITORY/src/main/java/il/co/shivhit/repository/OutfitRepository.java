package il.co.shivhit.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import il.co.shivhit.model.AppUser;
import il.co.shivhit.model.Cloths;
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


    public Task<ArrayList<String>> getAll(AppUser appUser) {
        TaskCompletionSource<ArrayList<String>> outfitsCompletion = new TaskCompletionSource<>();
        ArrayList<String> outfits_names = new ArrayList<String>();


        collection.whereEqualTo("userIdfs", appUser.getIdfs()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            for (DocumentSnapshot document : querySnapshot) {
                                String outfit_name = document.get("nameOfOutfit").toString();
                                if (!outfit_name.isEmpty())
                                    outfits_names.add(outfit_name);
                            }
                            outfitsCompletion.setResult(outfits_names);
                        } else {
                            Log.d("OutfitRepository", "No outfits found");
                            outfitsCompletion.setResult(null);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("OutfitRepository", "Error getting outfits", e);
                        outfitsCompletion.setResult(null);
                    }
                });

        return outfitsCompletion.getTask();
    }

    public Task<Cloths> getOutfitUsingNameOfOutfit(String nameOfOutfit) {
        TaskCompletionSource<Cloths> outfitCompletion = new TaskCompletionSource<>();
        collection.whereEqualTo("nameOfOutfit", nameOfOutfit).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    for (DocumentSnapshot document : querySnapshot) {
                        String outfit_name = document.getString("nameOfOutfit");
                        if (outfit_name != null && outfit_name.equals(nameOfOutfit)) {
                            Cloths cloths = (Cloths) document.get("cloths");
                            outfitCompletion.setResult(cloths);
                            return;
                        }
                    }
                    Log.d("OutfitRepository", "No matching outfit found");
                    outfitCompletion.setResult(null);
                } else {
                    Log.d("OutfitRepository", "No outfits found");
                    outfitCompletion.setResult(null);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("OutfitRepository", "Error getting outfit", e);
                outfitCompletion.setException(e);
            }
        });

        return outfitCompletion.getTask();
    }
}
