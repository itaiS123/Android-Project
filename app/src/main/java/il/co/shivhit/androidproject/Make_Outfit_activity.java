package il.co.shivhit.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Make_Outfit_activity extends AppCompatActivity {
    private EditText name_et;
    private EditText description_et;
    private ImageView shirt_imgV;
    private ImageView pants_imgV;
    private ImageView shoes_imgV;
    private ImageButton right_arrow1_imgB;
    private ImageButton right_arrow2_imgB;
    private ImageButton right_arrow3_imgB;
    private ImageButton left_arrow1_imgB;
    private ImageButton left_arrow2_imgB;
    private ImageButton left_arrow3_imgB;
    private Button addOutfit_btn;
    private Button returnBack_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_outfit);
        InitViews();
    }

    private void InitViews() {
        name_et = findViewById(R.id.name_et);
        description_et = findViewById(R.id.description_et);

        shirt_imgV = findViewById(R.id.shirt_imgV);
        shirt_imgV = findViewById(R.id.shirt_imgV);
        pants_imgV = findViewById(R.id.pants_imgV);
        shoes_imgV = findViewById(R.id.shoes_imgV);

        left_arrow1_imgB = findViewById(R.id.left_arrow1_imgB);
        left_arrow2_imgB = findViewById(R.id.left_arrow2_imgB);
        left_arrow3_imgB = findViewById(R.id.left_arrow3_imgB);
        right_arrow1_imgB = findViewById(R.id.right_arrow1_imgB);
        right_arrow2_imgB = findViewById(R.id.right_arrow2_imgB);
        right_arrow3_imgB = findViewById(R.id.right_arrow3_imgB);


        addOutfit_btn = findViewById(R.id.addOutfit_btn);
        returnBack_btn = findViewById(R.id.returnBack_btn);

        returnBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}