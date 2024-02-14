package il.co.shivhit.androidproject.ACTIVITIES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import il.co.shivhit.androidproject.R;
import il.co.shivhit.model.Cloth;

public class Add_Clothing_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ImageView shirt_imgView;
    private ImageButton take_picture_imgBtn;
    private Button goBack_btn;
    private Button addToWardrobe_btn;
    private Spinner category_spinner;
    private Spinner color_spinner;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothing);
        InitViews();
    }

    private void InitViews() {
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

        goBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        take_picture_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Add_Clothing_activity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Add_Clothing_activity.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 1);
                    dispatchTakePictureIntent();
                }
            }
        });

        addToWardrobe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCategory = category_spinner.getSelectedItem().toString();
                String selectedColor = color_spinner.getSelectedItem().toString();

                // Get the image from ImageView
                Bitmap clothImage = ((BitmapDrawable)shirt_imgView.getDrawable()).getBitmap();


                Cloth newCloth = new Cloth(selectedCategory, selectedColor, bitmapToString(clothImage));

                // Now you can use the newCloth object as needed
                // need to save in data base and display it in other activity
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
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
}