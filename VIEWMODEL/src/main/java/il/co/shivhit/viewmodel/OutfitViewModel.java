package il.co.shivhit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import il.co.shivhit.model.AppUser;
import il.co.shivhit.model.Cloths;
import il.co.shivhit.model.Outfit;
import il.co.shivhit.model.Outfits;
import il.co.shivhit.repository.OutfitRepository;

public class OutfitViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> successOperation;
    private OutfitRepository repository;
    private MutableLiveData<ArrayList<String>> outfitsLiveDataNames;
    private MutableLiveData<Cloths> clothsLiveDataNames;

    public OutfitViewModel(@NonNull Application application) {
        super(application);
        repository = new OutfitRepository(application);
        successOperation = new MutableLiveData<>();
        outfitsLiveDataNames = new MutableLiveData<>();
        clothsLiveDataNames = new MutableLiveData<>();
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
    public LiveData<ArrayList<String>> getOutfitsLiveDataNames() {return outfitsLiveDataNames; }
    public LiveData<Cloths> getCloths() {return clothsLiveDataNames; }

    public LiveData<ArrayList<String>> getAll(AppUser appUser) {
        repository.getAll(appUser)
                .addOnSuccessListener(outfits -> outfitsLiveDataNames.setValue(outfits))
                .addOnFailureListener(e -> outfitsLiveDataNames.setValue(null));

        return outfitsLiveDataNames;
    }
    public void getOutfitUsingNameOfOutfit(String outfit_name) {
        repository.getOutfitUsingNameOfOutfit(outfit_name)
                .addOnSuccessListener(cloths -> clothsLiveDataNames.setValue(cloths))
                .addOnFailureListener(e -> clothsLiveDataNames.setValue(null));
    }
}
