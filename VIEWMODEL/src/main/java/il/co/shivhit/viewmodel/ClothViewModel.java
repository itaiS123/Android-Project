package il.co.shivhit.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import il.co.shivhit.model.Cloth;

public class ClothViewModel extends ViewModel{
    private MutableLiveData<Boolean> successOperation;
    private Context context;
    private ClothRepository repository;

    private MutableLiveData<Cloth> clothLiveData;

    public ClothViewModel(Context context) {

        successOperation = new MutableLiveData<>();
        this.context = context;
        repository = new ClothRepository(context);
        blogPostsLiveData = new MutableLiveData<>();
    }

    public LiveData<Boolean> getSuccessOperation() {
        return successOperation;
    };

    public void add(BlogPost blogPost){
        Log.d("ll", "going to repository");
        successOperation.setValue(true);
        repository.add(blogPost)
                .addOnSuccessListener(aBoolean -> {successOperation.setValue(true);})
                .addOnFailureListener(e -> {successOperation.setValue(false);});
        Log.d("ll", "after repository");
    }

    public LiveData<BlogPosts> getAll() {
        clothLiveData = repository.getAll();
        return clothLiveData;
    }
}
