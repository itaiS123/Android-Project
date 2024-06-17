package il.co.shivhit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import il.co.shivhit.model.Cloth;
import il.co.shivhit.model.Cloths;
import il.co.shivhit.model.Outfit;
import il.co.shivhit.model.Outfits;
import il.co.shivhit.repository.ClothRepository;
import il.co.shivhit.repository.OutfitRepository;

public class OutfitViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> successOperation;
    private OutfitRepository repository;
    private MutableLiveData<Outfits> outfitsLiveData;

    public OutfitViewModel(@NonNull Application application) {
        super(application);
        repository = new OutfitRepository(application);
        successOperation = new MutableLiveData<>();
        outfitsLiveData = new MutableLiveData<>();
    }

    public void add(Outfit outfit) {
        repository.add(outfit)
                .addOnSuccessListener(aBoolean -> successOperation.setValue(true))
                .addOnFailureListener(e -> successOperation.setValue(false));
    }

    public void delete(Outfit outfit){

        repository.delete(outfit)
                .addOnSuccessListener(new OnSuccessListener<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        if (aBoolean)
                            successOperation.setValue(true);
                        else
                            successOperation.setValue(false);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        successOperation.setValue(false);
                    }
                });
    }
    public LiveData<Boolean> getSuccessOperation() {
        return successOperation;
    }
    public LiveData<Outfits> getOutfitsLiveData() {return outfitsLiveData; }
    public LiveData<Outfits> getAll() {
        repository.getAll()
                .addOnSuccessListener(outfits -> outfitsLiveData.setValue(outfits))
                .addOnFailureListener(e -> outfitsLiveData.setValue(null));

        return outfitsLiveData;
    }
}
