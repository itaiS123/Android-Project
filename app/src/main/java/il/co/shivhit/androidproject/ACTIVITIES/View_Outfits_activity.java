package il.co.shivhit.androidproject.ACTIVITIES;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import il.co.shivhit.androidproject.R;

public class View_Outfits_activity extends AppCompatActivity {
    private Button returnBack_btn;
    private ImageButton trashBin_imgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_outfits);
        InitViews();
    }

    private void InitViews() {
        returnBack_btn = findViewById(R.id.returnBack_btn);
        trashBin_imgBtn = findViewById(R.id.trashBin_imgBtn);
        returnBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish();}
        });

        trashBin_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}