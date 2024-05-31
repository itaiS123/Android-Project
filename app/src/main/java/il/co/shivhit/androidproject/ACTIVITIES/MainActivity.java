package il.co.shivhit.androidproject.ACTIVITIES;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import il.co.shivhit.androidproject.R;

public class MainActivity extends BaseActivity {
    private Button wardrobe_btn;
    private Button outfits_btn;
    private Intent outfits_intent;
    private Intent wardrobe_intent;

    @Override
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

        if (item.getItemId() == R.id.enable_Music) {
            Intent intent = new Intent(this, BackGraound_service.class);
            startService(intent);
        } else if (item.getItemId() == R.id.disable_Music) {
            Intent intent = new Intent(this, BackGraound_service.class);
            stopService(intent);
        }
        return true;
    }
}
