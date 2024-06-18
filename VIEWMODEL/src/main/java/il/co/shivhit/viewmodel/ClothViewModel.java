package il.co.shivhit.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.widget.ListViewAutoScrollHelper;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import il.co.shivhit.model.AppUser;
import il.co.shivhit.model.Cloth;
import il.co.shivhit.model.Cloths;
import il.co.shivhit.repository.ClothRepository;

public class ClothViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> successOperation;
    private ClothRepository repository;
    private MutableLiveData<Cloths> clothsLiveData;

    private MutableLiveData<Cloths> shirtsLiveData;
    private MutableLiveData<Cloths> pantsLiveData;
    private MutableLiveData<Cloths> showsLiveData;

    public ClothViewModel(@NonNull Application application) {
        super(application);
        repository = new ClothRepository(application);
        successOperation = new MutableLiveData<>();
        clothsLiveData = new MutableLiveData<>();

        shirtsLiveData = new MutableLiveData<>();
        pantsLiveData = new MutableLiveData<>();
        showsLiveData = new MutableLiveData<>();

    }

    public void add(Cloth cloth) {
        repository.add(cloth)
                .addOnSuccessListener(aBoolean -> successOperation.setValue(true))
                .addOnFailureListener(e -> successOperation.setValue(false));
    }
    public void delete(Cloth Cloth){

        repository.delete(Cloth)
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

    public LiveData<Cloths> getAll(AppUser appUser) {
        repository.getAll(appUser)
                .addOnSuccessListener(cloths -> clothsLiveData.setValue(cloths))
                .addOnFailureListener(e -> clothsLiveData.setValue(null));

        return clothsLiveData;
    }

    public LiveData<Cloths> filter(String category, String color){
        repository.filter(category, color)
                .addOnSuccessListener(cloths -> clothsLiveData.setValue(cloths))
                .addOnFailureListener(e -> clothsLiveData.setValue(null));
        return clothsLiveData;
    }

    public LiveData<Cloths> filter(String category){
        //clothsLiveData = new MutableLiveData<>();
        repository.filter(category)
                .addOnSuccessListener(new OnSuccessListener<Cloths>() {
                    @Override
                    public void onSuccess(Cloths cloths) {
                        Log.d("qqq", "ViewModel clothe: " + String.valueOf(cloths.size()));

                        //clothsLiveData.setValue(cloths);

                        if (category.equals("Sweater & Shirt"))
                            shirtsLiveData.setValue(cloths);

                        if (category.equals("Pants & Shorts"))
                            pantsLiveData.setValue(cloths);

                        if (category.equals("Shoes & Boots & Flip-Flops"))
                            showsLiveData.setValue(cloths);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        clothsLiveData.setValue(null);
                    }
                });

        return clothsLiveData;
    }

    public MutableLiveData<Cloths> getClothsLiveData() {
        return clothsLiveData;
    }

    public MutableLiveData<Cloths> getShirtsLiveData(){ return shirtsLiveData; }

    public MutableLiveData<Cloths> getPantsLiveData(){
        return pantsLiveData;
    }

    public MutableLiveData<Cloths> getShowsLiveData(){
        return showsLiveData;
    }
}
