package il.co.shivhit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import il.co.shivhit.model.AppUser;
import il.co.shivhit.model.AppUsers;
import il.co.shivhit.repository.AppUserRepository;

public class AppUserViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> successOperation;
    private AppUserRepository repository;
    private MutableLiveData<AppUsers> appUsersLiveData;
    private MutableLiveData<Boolean> existsUser;



    public AppUserViewModel(@NonNull Application application) {
        super(application);
        repository = new AppUserRepository(application);
        successOperation = new MutableLiveData<>();
        appUsersLiveData = new MutableLiveData<>();
        existsUser = new MutableLiveData<>();
    }

    public void add(AppUser appUser) {
        repository.add(appUser)
                .addOnSuccessListener(aBoolean -> successOperation.setValue(aBoolean))
                .addOnFailureListener(e -> successOperation.setValue(false));
    }
    public void exists(AppUser user)
    {
        repository.exists(user)
                .addOnSuccessListener(aBoolean -> { existsUser.setValue(aBoolean);})
                .addOnFailureListener(aBoolean -> { existsUser.setValue(false);});
    }
    public LiveData<Boolean> getSuccessOperation() {
        return successOperation;
    }
    public LiveData<Boolean> getExistsUser() { return existsUser; }
}

