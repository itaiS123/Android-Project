package il.co.shivhit.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import il.co.shivhit.model.Cloth;
import il.co.shivhit.repository.ClothRepository;

public class ClothViewModel extends ViewModel{
    private MutableLiveData<Boolean> successOperation;
    private Context context;
    private ClothRepository repository;

    public ClothViewModel(Context context){
        successOperation = new MutableLiveData<>();
        this.context = context;
        repository = new ClothRepository(context);
    }

    public void add(Cloth cloth){
        repository.add(cloth)
                .addOnSuccessListener(aBoolean ->
                {successOperation.setValue(true);})
                .addOnFailureListener(e ->
                {successOperation.setValue(false);});
    }

    public LiveData<Boolean> getSuccessOperation(){
        return successOperation;
    }
}
