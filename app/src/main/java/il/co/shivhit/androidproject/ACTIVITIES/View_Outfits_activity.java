package il.co.shivhit.androidproject.ACTIVITIES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import il.co.shivhit.androidproject.R;

public class View_Outfits_activity extends BaseActivity {
    private Button returnBack_btn;
    private RecyclerView listOfOutfits_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_outfits);
        initializeViews();
    }

    @Override
    protected void initializeViews() {
        returnBack_btn = findViewById(R.id.returnBack_btn);
        listOfOutfits_rv = findViewById(R.id.listOfOutfits_rv);
        setListeners();
    }

    @Override
    protected void setListeners() {
        returnBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish();}
        });
    }
}