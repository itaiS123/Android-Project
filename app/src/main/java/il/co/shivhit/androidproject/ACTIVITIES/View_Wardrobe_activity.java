package il.co.shivhit.androidproject.ACTIVITIES;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import il.co.shivhit.androidproject.R;

public class View_Wardrobe_activity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    private Spinner categoryFilter_spinner;
    private Spinner colorFilter_spinner;
    private Button apply_btn;
    private Button returnBack_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wardrobe);
        initializeViews();
    }

    @Override
    protected void initializeViews() {
        colorFilter_spinner = findViewById(R.id.colorFilter_spinner);
        ArrayAdapter<CharSequence> adapter_colorFilter = ArrayAdapter.createFromResource(this, R.array.Colors, android.R.layout.simple_spinner_dropdown_item);
        adapter_colorFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorFilter_spinner.setAdapter(adapter_colorFilter);
        colorFilter_spinner.setOnItemSelectedListener(this);


        categoryFilter_spinner = findViewById(R.id.categoryFilter_spinner);
        ArrayAdapter<CharSequence> adapter_categoryFilter = ArrayAdapter.createFromResource(this, R.array.Category, android.R.layout.simple_spinner_dropdown_item);
        adapter_categoryFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryFilter_spinner.setAdapter(adapter_categoryFilter);
        categoryFilter_spinner.setOnItemSelectedListener(this);

        apply_btn = findViewById(R.id.apply_btn);
        returnBack_btn = findViewById(R.id.returnBack_btn);


        setListeners();
    }

    @Override
    protected void setListeners() {
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        returnBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}