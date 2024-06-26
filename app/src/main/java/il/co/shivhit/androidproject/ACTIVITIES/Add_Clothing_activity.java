package il.co.shivhit.androidproject.ACTIVITIES;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

import il.co.shivhit.androidproject.R;
import il.co.shivhit.model.AppUser;
import il.co.shivhit.model.Cloth;
import il.co.shivhit.viewmodel.ClothViewModel;
import il.co.shivhit.viewmodel.GenericViewModelFactory;

public class Add_Clothing_activity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    private ImageView shirt_imgView;
    private ImageButton take_picture_imgBtn;
    private Button goBack_btn;
    private Button addToWardrobe_btn;
    private Spinner category_spinner;
    private Spinner color_spinner;
    private ClothViewModel clothViewModel;
    private TextToSpeech textToSpeech;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private AppUser loggedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothing);
        initializeViews();
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
    @Override
    protected void initializeViews() {
        shirt_imgView = findViewById(R.id.shirt_imgView);
        take_picture_imgBtn = findViewById(R.id.take_picture_imgBtn);

        goBack_btn = findViewById(R.id.goBack_btn);
        addToWardrobe_btn = findViewById(R.id.addToWardrobe_btn);

        color_spinner = findViewById(R.id.color_spinner);
        ArrayAdapter<CharSequence> adapter_colors = ArrayAdapter.createFromResource(this, R.array.Colors, android.R.layout.simple_spinner_dropdown_item);
        adapter_colors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        color_spinner.setAdapter(adapter_colors);
        color_spinner.setOnItemSelectedListener(this);


        category_spinner = findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> adapter_category = ArrayAdapter.createFromResource(this, R.array.Category, android.R.layout.simple_spinner_dropdown_item);
        adapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(adapter_category);
        category_spinner.setOnItemSelectedListener(this);

        loggedUser = (AppUser) getIntent().getSerializableExtra("loggedUser");

        setListeners();
        setObservers();
    }

    private void setObservers() {
        GenericViewModelFactory<ClothViewModel> factory = new GenericViewModelFactory<>(getApplication(), ClothViewModel::new);
        clothViewModel = new ViewModelProvider(this, factory).get(ClothViewModel.class);

        clothViewModel.getSuccessOperation().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    Toast.makeText(Add_Clothing_activity.this, "Cloth successfully saved", Toast.LENGTH_SHORT).show();
                    speakText("Cloth successfully saved");
                    // Handler to delay finish
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1500); // Adjust delay time (in milliseconds) as needed

                }
                else {
                    Toast.makeText(Add_Clothing_activity.this, "Error!!!", Toast.LENGTH_SHORT).show();
                    speakText("Error with saving the photo");
                }
            }
        });
    }
    @Override
    protected void setListeners() {
        goBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        take_picture_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Add_Clothing_activity.this);
                builder.setTitle("Choose an option");
                builder.setItems(new CharSequence[]{"Take Photo", "Choose from Gallery"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                           takePicture();
                        } else {
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY);
                        }
                    }
                });
                builder.show();
            }
        });


        addToWardrobe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedColor = color_spinner.getSelectedItem().toString();
                String selectedCategory = category_spinner.getSelectedItem().toString();

                // Get the image from ImageView
                Bitmap clothImage = ((BitmapDrawable) shirt_imgView.getDrawable()).getBitmap();

                String idfsUser = loggedUser.getIdfs();

                Cloth newCloth = new Cloth(selectedCategory, selectedColor, bitmapToString(clothImage), idfsUser);
                clothViewModel.add(newCloth);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                if (imageBitmap != null) {
                    shirt_imgView.setImageBitmap(imageBitmap);
                }
            }
        }
    }

    //method that must be implemented because of Interface
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }
    //method that must be implemented because of Interface
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // Method to convert Bitmap to String
    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    // Method to convert String to Bitmap
    public static Bitmap stringToBitmap(String encodedString) {
        byte[] decodedByteArray = Base64.decode(encodedString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.music_menu, menu);
        return true;
    }

    @Override
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

    public void takePicture() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            // Permission is granted, start the camera intent
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
