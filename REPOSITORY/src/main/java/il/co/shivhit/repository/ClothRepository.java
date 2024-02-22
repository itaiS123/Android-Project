package il.co.shivhit.repository;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ClothRepository {
    private FirebaseFirestore db;
    private CollectionReference collection;

    public ClothRepository(){
        db = FirebaseFirestore.getInstance();
        collection = db.collection("Cloth");
    }
}
