package il.co.shivhit.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Outfits_activity extends AppCompatActivity {
    private Button viewOutfits_btn;
    private Button makeAnOutfit_btn;
    private Button goBack_btn;
    private Intent goBack_intent;
    private Intent makeOtfit_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfits);
        InitViews();
    }
    private void InitViews() {
        goBack_btn = findViewById(R.id.goBack_btn);
        viewOutfits_btn = findViewById(R.id.viewOutfits_btn);
        makeAnOutfit_btn = findViewById(R.id.makeAnOutfit_btn);

        goBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        makeAnOutfit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent makeOtfit_intent = new Intent(Outfits_activity.this, Outfits_activity.class);
                startActivityForResult(makeOtfit_intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Handle the case when Outfits_activity finishes
            // You may not need to do anything specific in this case
        }
    }
}