package il.co.shivhit.androidproject.ACTIVITIES;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Locale;

import ADAPTERS.ClothAdapter;
import il.co.shivhit.androidproject.R;
import il.co.shivhit.model.Cloth;
import il.co.shivhit.model.Cloths;
import il.co.shivhit.viewmodel.ClothViewModel;

public class View_Wardrobe_activity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    private Spinner categoryFilter_spinner;
    private Spinner colorFilter_spinner;
    private Button apply_btn;
    private Button returnBack_btn;

    private ClothViewModel clothViewModel;
    private Cloths cloths;
    private RecyclerView filter_rv;
    private ClothAdapter clothAdapter;

    private TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wardrobe);
        initializeViews();
        setRecyclerView();
        setObservers();

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

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }

    private void setObservers(){
        clothViewModel.getAll()
                .observe(this, new Observer<Cloths>() {
                    @Override
                    public void onChanged(Cloths observedCloths) {
                        cloths = observedCloths;
                        clothAdapter.setCloths(cloths);
                    }
                });
    }


    private void setRecyclerView() {
        ClothAdapter.OnItemClickListener listener =
                new ClothAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClicked(Cloth cloth) {
                        Toast.makeText(View_Wardrobe_activity.this, "Click", Toast.LENGTH_SHORT).show();
                    }
                };

        ClothAdapter.OnItemLongClickListener longListener =
                new ClothAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(Cloth cloth) {
                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(View_Wardrobe_activity.this)
                                .setTitle("Delete Cloth")
                                .setMessage("Are you sure you want to delete this cloth?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteCloth(cloth);
                                        speakText("Cloth successfully deleted");
                                        Toast.makeText(View_Wardrobe_activity.this, "Cloth deleted", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return true;
                    }
                };

        clothAdapter = new ClothAdapter(this, cloths, R.layout.single_cloth, listener, longListener);
        filter_rv.setAdapter(clothAdapter);
        filter_rv.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void initializeViews() {
        colorFilter_spinner = findViewById(R.id.colorFilter_spinner);
        ArrayAdapter<CharSequence> adapter_colorFilter = ArrayAdapter.createFromResource(this, R.array.Colors, android.R.layout.simple_spinner_dropdown_item);
        adapter_colorFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorFilter_spinner.setAdapter(adapter_colorFilter);
        colorFilter_spinner.setOnItemSelectedListener(this);


        categoryFilter_spinner = findViewById(R.id.categoryFilter_spinner);
        ArrayAdapter<CharSequence> adapter_categoryFilter = ArrayAdapter.createFromResource(this, R.array.Category, android.R.layout.simple_spinner_dropdown_item);
        adapter_categoryFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryFilter_spinner.setAdapter(adapter_categoryFilter);
        categoryFilter_spinner.setOnItemSelectedListener(this);

        apply_btn = findViewById(R.id.apply_btn);
        filter_rv = findViewById(R.id.filter_rv);
        returnBack_btn = findViewById(R.id.returnBack_btn);

        cloths = new Cloths();
        clothViewModel = new ViewModelProvider(this).get(ClothViewModel.class);
        setListeners();
    }

    @Override
    protected void setListeners() {
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                clothViewModel.filter(categoryFilter_spinner.getSelectedItem().toString(), colorFilter_spinner.getSelectedItem().toString());
            }
        });

        returnBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });
    }

    private void deleteCloth(Cloth cloth){
        clothViewModel.delete(cloth);
        cloths.remove(cloth);
        clothAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
}