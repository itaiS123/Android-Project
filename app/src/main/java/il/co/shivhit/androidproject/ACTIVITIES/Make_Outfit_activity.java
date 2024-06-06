package il.co.shivhit.androidproject.ACTIVITIES;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ADAPTERS.ClothAdapter;
import il.co.shivhit.androidproject.R;
import il.co.shivhit.model.Cloth;
import il.co.shivhit.model.Cloths;
import il.co.shivhit.model.Outfit;
import il.co.shivhit.viewmodel.ClothViewModel;
import il.co.shivhit.viewmodel.OutfitViewModel;

public class Make_Outfit_activity extends BaseActivity {
    private EditText name_et;
    private EditText description_et;
    private Button saveOutfit_btn;
    private Button returnBack_btn;

    private ClothViewModel clothViewModel_shirt;
    private Cloths cloths_shirt;
    private RecyclerView shirt_rv;
    private ClothAdapter clothAdapter_shirt;

    private ClothViewModel clothViewModel_pants;
    private Cloths cloths_pants;
    private RecyclerView pants_rv;
    private ClothAdapter clothAdapter_pants;

    private ClothViewModel clothViewModel_shoes;
    private Cloths cloths_shoes;
    private RecyclerView shoes_rv;
    private ClothAdapter clothAdapter_shoes;

    private OutfitViewModel outfitViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_outfit);
        initializeViews();
        setObservers();
        setRecyclerView();
    }

    @Override
    protected void initializeViews() {
        name_et = findViewById(R.id.name_et);
        description_et = findViewById(R.id.description_et);

        saveOutfit_btn = findViewById(R.id.saveOutfit_btn);
        returnBack_btn = findViewById(R.id.returnBack_btn);

        shirt_rv = findViewById(R.id.shirt_rv);
        cloths_shirt = new Cloths();
        clothViewModel_shirt = new ViewModelProvider(this).get(ClothViewModel.class);

        pants_rv = findViewById(R.id.pants_rv);
        cloths_pants = new Cloths();
        clothViewModel_pants = new ViewModelProvider(this).get(ClothViewModel.class);

        shoes_rv = findViewById(R.id.shoes_rv);
        cloths_shoes = new Cloths();
        clothViewModel_shoes = new ViewModelProvider(this).get(ClothViewModel.class);

        outfitViewModel = new ViewModelProvider(this).get(OutfitViewModel.class);
        setListeners();
    }
    private void setObservers(){
        clothViewModel_pants.getPantsLiveData().observe(this, new Observer<Cloths>() {
            @Override
            public void onChanged(Cloths cloths) {
                Log.d("qqq", "Observe Pants: " + String.valueOf(cloths.size()));
                clothAdapter_pants.setCloths(cloths);
            }
        });

        clothViewModel_shoes.getShowsLiveData().observe(this, new Observer<Cloths>() {
            @Override
            public void onChanged(Cloths cloths) {
                Log.d("qqq", "Observe shows: " + String.valueOf(cloths.size()));
                clothAdapter_shoes.setCloths(cloths);
            }
        });

        clothViewModel_shirt.getShitsLiveData().observe(this, new Observer<Cloths>() {
            @Override
            public void onChanged(Cloths cloths) {
                Log.d("qqq", "Observe Shits: " + String.valueOf(cloths.size()));
                clothAdapter_shirt.setCloths(cloths);
            }
        });

        outfitViewModel.getSuccessOperation().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Toast.makeText(getApplicationContext(), "good", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "bad", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setRecyclerView() {
        ClothAdapter.OnItemClickListener listener =
                new ClothAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClicked(Cloth cloth) {
                        Toast.makeText(Make_Outfit_activity.this, "Click", Toast.LENGTH_SHORT).show();
                    }};

        ClothAdapter.OnItemLongClickListener longListener =
                new ClothAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(Cloth cloth) {
                        Toast.makeText(Make_Outfit_activity.this, " long Click", Toast.LENGTH_SHORT).show();
                        return true;
                    }};

        clothAdapter_shirt = new ClothAdapter(this, cloths_shirt, R.layout.single_cloth_filter, listener, longListener);
        shirt_rv.setAdapter(clothAdapter_shirt);
        shirt_rv.setLayoutManager(new LinearLayoutManager(this));

        clothAdapter_pants = new ClothAdapter(this, cloths_pants, R.layout.single_cloth_filter, listener, longListener);
        pants_rv.setAdapter(clothAdapter_pants);
        pants_rv.setLayoutManager(new LinearLayoutManager(this));

        clothAdapter_shoes = new ClothAdapter(this, cloths_shoes, R.layout.single_cloth_filter, listener, longListener);
        shoes_rv.setAdapter(clothAdapter_shoes);
        shoes_rv.setLayoutManager(new LinearLayoutManager(this));


        // ------------ filter not working because, run the app with and without the filter and look at the changes ------------
        Log.d("qqq", "Pants shirts");
        clothViewModel_shirt.filter("Sweater & Shirt");
        Log.d("qqq", "Pants filter");
        clothViewModel_pants.filter("Pants & Shorts");
        Log.d("qqq", "Pants shows");
        clothViewModel_shoes.filter("Shoes & Boots & Flip-Flops");
    }

    @Override
    protected void setListeners() {
        returnBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveOutfit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!description_et.getText().toString().isEmpty() && !name_et.getText().toString().isEmpty()){
                    LinearLayoutManager layoutManager_shirt = (LinearLayoutManager)shirt_rv.getLayoutManager();
                    int firstVisibleItemPosition_shirt = layoutManager_shirt.findFirstVisibleItemPosition();
                    Cloth shirt = clothAdapter_shirt.getClothByPosition(firstVisibleItemPosition_shirt);

                    LinearLayoutManager layoutManager_pants = (LinearLayoutManager)pants_rv.getLayoutManager();
                    int firstVisibleItemPosition_pants = layoutManager_pants.findFirstVisibleItemPosition();
                    Cloth pants = clothAdapter_shirt.getClothByPosition(firstVisibleItemPosition_pants);

                    LinearLayoutManager layoutManager_shoes = (LinearLayoutManager)shoes_rv.getLayoutManager();
                    int firstVisibleItemPosition_shoes = layoutManager_shoes.findFirstVisibleItemPosition();
                    Cloth shoes = clothAdapter_shoes.getClothByPosition(firstVisibleItemPosition_shoes);

                    Cloths cloths = new Cloths();
                    cloths.add(shirt);
                    cloths.add(pants);
                    cloths.add(shoes);

                    Outfit outfit = new Outfit(cloths, name_et.getText().toString(), description_et.getText().toString());
                    outfitViewModel.add(outfit);

                    //Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please insert valid values", Toast.LENGTH_SHORT).show();
                }
           }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.music_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.enable_Music) {
            Toast.makeText(this, "Enable Music:", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.disable_Music) {
            Toast.makeText(this, "Disable Music", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}