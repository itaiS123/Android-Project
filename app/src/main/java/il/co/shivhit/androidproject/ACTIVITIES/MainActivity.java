package il.co.shivhit.androidproject.ACTIVITIES;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import il.co.shivhit.androidproject.R;
import il.co.shivhit.model.AppUser;
import il.co.shivhit.viewmodel.AppUserViewModel;
import il.co.shivhit.viewmodel.GenericViewModelFactory;



public class MainActivity extends BaseActivity {
    private Button register_btn;
    private Button logIn_btn;
    private EditText username_et;
    private EditText password_et;
    private AppUserViewModel appUserViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
    }

    @Override
    protected void initializeViews() {
        logIn_btn = findViewById(R.id.logIn_btn);
        register_btn = findViewById(R.id.register_btn);

        password_et = findViewById(R.id.password_et);
        username_et = findViewById(R.id.username_et);

        GenericViewModelFactory<AppUserViewModel> factory = new GenericViewModelFactory<>(getApplication(), AppUserViewModel::new);
        appUserViewModel = new ViewModelProvider(this, factory).get(AppUserViewModel.class);

        SharedPreferences preferences = getSharedPreferences("Android-Project", MODE_PRIVATE);
        if (preferences.getBoolean("remember", false)){
            Intent intent = new Intent(MainActivity.this, Main_Page_Activity.class);
            startActivity(intent);
        }

        setObservers();
        setListeners();
    }

    @Override
    protected void setListeners() {
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUser appUser = new AppUser(username_et.getText().toString(), password_et.getText().toString());
                appUserViewModel.add(appUser);
            }
        });

        logIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Log_In_Activity.class);
                startActivity(intent);;
            }
        });
    }

    private void setObservers(){
        appUserViewModel.getSuccessOperation().observe(MainActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess) {
                    Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to save user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
