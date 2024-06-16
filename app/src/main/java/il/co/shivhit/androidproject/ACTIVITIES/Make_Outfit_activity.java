package il.co.shivhit.androidproject.ACTIVITIES;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

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

    private Cloth shirt;
    private Cloth pants;
    private Cloth shoes;

    private TextToSpeech textToSpeech;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_outfit);

        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    // Set language (optional)
                    textToSpeech.setLanguage(Locale.US); // Change Locale for different languages
                }
            }
        });

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

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        setListeners();
    }
    private void setObservers(){
        clothViewModel_pants.getPantsLiveData().observe(this, new Observer<Cloths>() {
            @Override
            public void onChanged(Cloths cloths) {
                clothAdapter_pants.setCloths(cloths);
            }
        });

        clothViewModel_shoes.getShowsLiveData().observe(this, new Observer<Cloths>() {
            @Override
            public void onChanged(Cloths cloths) {
                clothAdapter_shoes.setCloths(cloths);
            }
        });

        clothViewModel_shirt.getShirtsLiveData().observe(this, new Observer<Cloths>() {
            @Override
            public void onChanged(Cloths cloths) {
                clothAdapter_shirt.setCloths(cloths);
            }
        });

        outfitViewModel.getSuccessOperation().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Toast.makeText(getApplicationContext(), "Outfit saved", Toast.LENGTH_SHORT).show();
                    // make textToSpeech
                    finish();
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

        ClothAdapter.OnItemLongClickListener longListener_shirt =
                new ClothAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(Cloth cloth) {
                        shirt = cloth;
                        Toast.makeText(Make_Outfit_activity.this, " Cloth saved (shirt)", Toast.LENGTH_SHORT).show();
                        return true;
                    }};

        ClothAdapter.OnItemLongClickListener longListener_pants =
                new ClothAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(Cloth cloth) {
                        pants = cloth;
                        Toast.makeText(Make_Outfit_activity.this, " Cloth saved (pants)", Toast.LENGTH_SHORT).show();
                        return true;
                    }};

        ClothAdapter.OnItemLongClickListener longListener_shoes =
                new ClothAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(Cloth cloth) {
                        shoes = cloth;
                        Toast.makeText(Make_Outfit_activity.this, " Cloth saved (shoes)", Toast.LENGTH_SHORT).show();
                        return true;
                    }};


        clothAdapter_shirt = new ClothAdapter(this, cloths_shirt, R.layout.single_cloth_filter, listener, longListener_shirt);
        shirt_rv.setAdapter(clothAdapter_shirt);
        shirt_rv.setLayoutManager(new LinearLayoutManager(this));

        clothAdapter_pants = new ClothAdapter(this, cloths_pants, R.layout.single_cloth_filter, listener, longListener_pants);
        pants_rv.setAdapter(clothAdapter_pants);
        pants_rv.setLayoutManager(new LinearLayoutManager(this));

        clothAdapter_shoes = new ClothAdapter(this, cloths_shoes, R.layout.single_cloth_filter, listener, longListener_shoes);
        shoes_rv.setAdapter(clothAdapter_shoes);
        shoes_rv.setLayoutManager(new LinearLayoutManager(this));


        clothViewModel_shirt.filter("Sweater & Shirt");
        clothViewModel_pants.filter("Pants & Shorts");
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
                    Cloths cloths = new Cloths();
                    cloths.add(shirt);
                    cloths.add(pants);
                    cloths.add(shoes);

                    Outfit outfit = new Outfit(cloths, name_et.getText().toString(), description_et.getText().toString());
                    outfitViewModel.add(outfit);

                    speakText("Cloth successfully saved");

                    // Handler to delay finish
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1500);
                }

                else {
                    Toast.makeText(getApplicationContext(), "Please insert valid values", Toast.LENGTH_SHORT).show();
                }
           }
        });
    }

    public void speakText(String text) {
        if (textToSpeech != null && textToSpeech.isSpeaking() == false) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null); // Speak the text
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.shutdown();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.music_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (Main_Page_Activity.player != null) {
            if (item.getItemId() == R.id.enable_Music) {
                Main_Page_Activity.player.start();
            } else if (item.getItemId() == R.id.disable_Music) {
                Main_Page_Activity.player.pause();
            }
        }

        if (item.getItemId() == R.id.logOut_item) {
            SharedPreferences preferences = getSharedPreferences("Android-Project", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("remember", false);
            editor.apply();
            finish();
        }
        return true;
    }
}