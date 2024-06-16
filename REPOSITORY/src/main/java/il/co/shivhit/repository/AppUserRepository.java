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

import il.co.shivhit.model.AppUser;
import il.co.shivhit.model.AppUsers;

public class AppUserRepository {
    private FirebaseFirestore db;
    private CollectionReference collection;

    public AppUserRepository(Context context) {
        try {
            db = FirebaseFirestore.getInstance();
        } catch (Exception e) {
            FirebaseInstance instance = FirebaseInstance.instance(context);
            db = FirebaseFirestore.getInstance(FirebaseInstance.app);
        }
        collection = db.collection("Users");
    }

    public Task<Boolean> add(AppUser user) {
        TaskCompletionSource<Boolean> taskCompletion = new TaskCompletionSource<>();
        DocumentReference document = collection.document();
        user.setIdfs(document.getId());

        document.set(user)
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

        return taskCompletion.getTask();
    }
    public Task<Boolean> exists(AppUser user)
    {
        TaskCompletionSource<Boolean> taskExist = new TaskCompletionSource<>();
        collection.whereEqualTo("userName", user.getUserName()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
            if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                if (document != null) {
                    if(document.get("password").toString().equals(user.getPassword().toString()))
                    {
                        taskExist.setResult(true);
                    }
                    else
                        taskExist.setResult(false);
                }

                else
                    taskExist.setResult(false);
                }
                else
                    taskExist.setResult(false);
                }})
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e) { taskExist.setResult(false); }
                });
        return taskExist.getTask();
    }
}
