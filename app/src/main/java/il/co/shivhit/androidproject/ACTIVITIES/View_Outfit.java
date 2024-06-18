package il.co.shivhit.androidproject.ACTIVITIES;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import il.co.shivhit.androidproject.R;

public class View_Outfit extends BaseActivity {
    private TextView outfitName_tv;
    private TextView description_tv;
    private ImageView shirt_imgView;
    private ImageView pants_imgView;
    private ImageView shoes_imgView;
    private Button returnBack_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_outfit);
        initializeViews();
    }

    @Override
    protected void initializeViews() {
        outfitName_tv = findViewById(R.id.outfitName_tv);
        description_tv = findViewById(R.id.description_tv);

        shirt_imgView = findViewById(R.id.shirt_imgView);
        pants_imgView = findViewById(R.id.pants_imgView);
        shoes_imgView = findViewById(R.id.shoes_imgView);

        returnBack_Btn = findViewById(R.id.returnBack_Btn);

        setListeners();
    }

    @Override
    protected void setListeners() {
        returnBack_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });
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
}