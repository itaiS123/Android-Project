package il.co.shivhit.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Outfits_activity extends AppCompatActivity {
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
        InitViews();
    }
    private void InitViews() {
        returnBack_Btn = findViewById(R.id.returnBack_Btn);
        viewOutfits_btn = findViewById(R.id.viewOutfits_btn);
        makeAnOutfit_btn = findViewById(R.id.makeAnOutfit_btn);

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
}