package il.co.shivhit.androidproject.ACTIVITIES;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import il.co.shivhit.androidproject.R;

public class Main_Page_Activity extends BaseActivity {
    private Button wardrobe_btn;
    private Button outfits_btn;

    public static MediaPlayer player;
    private final int musicResourceId = R.raw.mp3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        initializeViews();
    }

    @Override
    protected void initializeViews() {
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        outfits_btn = findViewById(R.id.outfits_btn);
        wardrobe_btn = findViewById(R.id.wardrobe_btn);

        // Create media player (check for null)
        player = MediaPlayer.create(this, musicResourceId);
        if (player != null) {
            player.setLooping(true); // Set looping for continuous playback
        } else {
            Toast.makeText(this, "Error creating media player", LENGTH_SHORT).show();
        }

        setListeners();
    }

    @Override
    protected void setListeners() {
        wardrobe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_Page_Activity.this, Wardrobe_activity.class);
                startActivityForResult(intent, 2);
            }
        });

        outfits_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_Page_Activity.this, Outfits_activity.class);
                startActivityForResult(intent, 1);
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

        if (player != null) {
            if (item.getItemId() == R.id.enable_Music) {
                player.start();
            } else if (item.getItemId() == R.id.disable_Music) {
                player.pause();
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

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null && player.isPlaying()) {
            player.pause(); // Pause music when activity goes in background
        }
    }

    @Override
    protected void onResume() {  // Resume music if player exists and activity is in foreground
        super.onResume();
        if (player != null) {
            player.start();
        }
    }
}
