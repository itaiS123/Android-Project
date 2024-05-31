package il.co.shivhit.androidproject.ACTIVITIES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import il.co.shivhit.androidproject.R;

public class Outfits_activity extends BaseActivity {
    private Button viewOutfits_btn;
    private Button makeAnOutfit_btn;
    private Button returnBack_Btn;
    private Intent goBack_intent;
    private Intent makeOtfit_intent;
    private Intent viewOutfits_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfits);
        initializeViews();

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initializeViews() {
        returnBack_Btn = findViewById(R.id.returnBack_Btn);
        viewOutfits_btn = findViewById(R.id.viewOutfits_btn);
        makeAnOutfit_btn = findViewById(R.id.makeAnOutfit_btn);

        setListeners();
    }

    @Override
    protected void setListeners() {
        returnBack_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        makeAnOutfit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent makeOtfit_intent = new Intent(Outfits_activity.this, Make_Outfit_activity.class);
                startActivityForResult(makeOtfit_intent, 1);
            }
        });

        viewOutfits_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewOutfits_intent = new Intent(Outfits_activity.this, View_Outfits_activity.class);
                startActivityForResult(viewOutfits_intent, 2);
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

        if (item.getItemId() == R.id.enable_Music) {
            Toast.makeText(this, "Enable Music:", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.disable_Music) {
            Toast.makeText(this, "Disable Music", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}