package il.co.shivhit.androidproject.ACTIVITIES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import il.co.shivhit.androidproject.R;

public class Wardrobe_activity extends BaseActivity { // Inherits from BaseActivity
    private Button goBack_btn;
    private Button addClothing_btn;
    private Button viewWardrobe_btn;
    private Intent addClothing_intent;

    private static MediaPlayer player;
    private final int musicResourceId = R.raw.mp3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);
        initializeViews();

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initializeViews() {
        goBack_btn = findViewById(R.id.goBack_btn);
        addClothing_btn = findViewById(R.id.addClothing_btn);
        viewWardrobe_btn = findViewById(R.id.viewWardrobe_btn);

        player = MediaPlayer.create(this, musicResourceId);
        if (player != null) {
            player.setLooping(true);
        } else {
            Toast.makeText(Wardrobe_activity.this, "Error creating media player", Toast.LENGTH_SHORT).show();
        }

        setListeners();
    }

    @Override
    protected void setListeners() {
        goBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addClothing_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addClothing_intent = new Intent(Wardrobe_activity.this, Add_Clothing_activity.class);
                startActivityForResult(addClothing_intent, 1);
            }
        });

        viewWardrobe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addClothing_intent = new Intent(Wardrobe_activity.this, View_Wardrobe_activity.class);
                startActivityForResult(addClothing_intent, 2);
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
