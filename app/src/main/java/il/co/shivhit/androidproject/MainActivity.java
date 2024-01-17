package il.co.shivhit.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button wardrobe_btn;
    private Button outfits_btn;
    private Intent outfits_intent;
    //make a wardrobe activity and xml file
    private Intent wardrobe_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitViews();
    }

    private void InitViews() {
        outfits_btn = findViewById(R.id.outfits_btn);
        wardrobe_btn = findViewById(R.id.wardrobe_btn);


        outfits_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent outfits_intent = new Intent(MainActivity.this, Outfits_activity.class);
                startActivityForResult(outfits_intent, 1);

            }
        });

        wardrobe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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