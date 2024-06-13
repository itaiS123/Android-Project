package il.co.shivhit.androidproject.ACTIVITIES;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import il.co.shivhit.androidproject.R;

public class MainActivity extends BaseActivity {
    private Button wardrobe_btn;
    private Button outfits_btn;
    private Intent outfits_intent;
    private Intent wardrobe_intent;

    private static MediaPlayer player;
    private final int musicResourceId = R.raw.mp3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initializeViews() {
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
        outfits_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outfits_intent = new Intent(MainActivity.this, Outfits_activity.class);
                startActivityForResult(outfits_intent, 1);
            }
        });

        wardrobe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wardrobe_intent = new Intent(MainActivity.this, Wardrobe_activity.class);
                startActivityForResult(wardrobe_intent, 2);
            }
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
        if (player != null) {
            if (item.getItemId() == R.id.enable_Music) {
                player.start();
            } else if (item.getItemId() == R.id.disable_Music) {
                player.pause();
            }
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
    protected void onResume() {
        super.onResume();
        if (player != null) { // Resume music if player exists and activity is in foreground
            player.start();
        }
    }
}
