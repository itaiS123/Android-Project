package il.co.shivhit.androidproject.ACTIVITIES;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import ADAPTERS.OutfitAdapter;
import il.co.shivhit.androidproject.R;
import il.co.shivhit.model.AppUser;
import il.co.shivhit.model.Cloths;
import il.co.shivhit.model.Outfit;
import il.co.shivhit.viewmodel.OutfitViewModel;

public class View_Outfits_activity extends BaseActivity {
    private Button returnBack_btn;
    private RecyclerView listOfOutfits_rv;
    private OutfitAdapter adapter;
    private ArrayList<String> outfits_names;
    private OutfitViewModel viewModel;
    private AppUser loggedUser;
    private Cloths cloths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_outfits);
        initializeViews();
        loadData();
        setObservers();
        setRecyclerView();
    }

    private void loadData() {
        viewModel.getAll(loggedUser);
    }

    @Override
    protected void initializeViews() {
        returnBack_btn = findViewById(R.id.returnBack_btn);
        listOfOutfits_rv = findViewById(R.id.listOfOutfits_rv);

        outfits_names = new ArrayList<String>();
        viewModel = new ViewModelProvider(this).get(OutfitViewModel.class);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        loggedUser = (AppUser) getIntent().getSerializableExtra("loggedUser");

        cloths = new Cloths();

        setListeners();
    }
    private void setObservers() {
        viewModel.getOutfitsLiveDataNames().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                outfits_names = strings;
                adapter.setOutfits_names(outfits_names);
                adapter.notifyDataSetChanged();
            }
        });

        viewModel.getCloths().observe(this, new Observer<Cloths>() {
            @Override
            public void onChanged(Cloths cloths) {

            }
        });
    }

    private void setRecyclerView() {
        OutfitAdapter.OnItemLongClickListener longClickListener = new OutfitAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(String outfit_name) {
                Intent intent = new Intent(View_Outfits_activity.this, View_Outfit.class);
                intent.putExtra("outfit_name", outfit_name);


                startActivity(intent);
                return true;
            }
        };

        adapter = new OutfitAdapter(this, outfits_names, R.layout.single_outfit_name, longClickListener);
        listOfOutfits_rv.setAdapter(adapter);
        listOfOutfits_rv.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void setListeners() {
        returnBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish();}
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