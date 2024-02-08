package il.co.shivhit.androidproject.ACTIVITIES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import il.co.shivhit.androidproject.R;

public class Wardrobe_activity extends AppCompatActivity {
    private Button goBack_btn;
    private Button addClothing_btn;
    private Button viewWardrobe_btn;
    private Intent addClothing_intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);
        InitViews();
    }

    private void InitViews() {
        goBack_btn = findViewById(R.id.goBack_btn);
        addClothing_btn = findViewById(R.id.addClothing_btn);
        viewWardrobe_btn = findViewById(R.id.viewWardrobe_btn);

        goBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
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
}