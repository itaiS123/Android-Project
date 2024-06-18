package il.co.shivhit.androidproject.ACTIVITIES;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import il.co.shivhit.androidproject.R;
import il.co.shivhit.model.AppUser;
import il.co.shivhit.viewmodel.AppUserViewModel;
import il.co.shivhit.viewmodel.GenericViewModelFactory;

public class Log_In_Activity extends BaseActivity {
    private Button logIn_btn;
    private EditText username_et;
    private EditText password_et;
    private AppUserViewModel appUserViewModel;
    private CheckBox remember_checkBox;
    private AppUser loggedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initializeViews();
    }

    @Override
    protected void initializeViews() {
        logIn_btn = findViewById(R.id.logIn_btn);
        password_et = findViewById(R.id.password_et);
        username_et = findViewById(R.id.username_et);
        remember_checkBox = findViewById(R.id.remember_checkBox);

        SharedPreferences preferences = getSharedPreferences("Android-Project", MODE_PRIVATE);
        if (preferences.getBoolean("remember", false)) {
            AppUser appUser = new AppUser("itai", "itai123");
            appUser.setIdfs("z8CORF15F0VBVEILSX01");

            Intent intent = new Intent(Log_In_Activity.this, Main_Page_Activity.class);
            intent.putExtra("loggedUser", appUser);
            startActivity(intent);
        }

        setObservers();
        setListeners();
    }

    @Override
    protected void setListeners() {
        logIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!username_et.getText().toString().equals(null) && !password_et.getText().toString().equals(null)){
                    AppUser appUser = new AppUser(username_et.getText().toString(), password_et.getText().toString());
                    loggedUser = appUser;
                    appUserViewModel.exists(appUser);
                }
                else {
                    Toast.makeText(Log_In_Activity.this, "Enter valid values", Toast.LENGTH_SHORT).show();
                }
            }
        });

        remember_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences preferences = getSharedPreferences("Android-Project", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putBoolean("remember", buttonView.isChecked());
                editor.apply();
            }
        });
    }

    private void setObservers(){
        GenericViewModelFactory<AppUserViewModel> factory = new GenericViewModelFactory<>(getApplication(), AppUserViewModel::new);
        appUserViewModel = new ViewModelProvider(this, factory).get(AppUserViewModel.class);

        appUserViewModel.getExistsUser().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    appUserViewModel.getIdFromUserName(loggedUser.getUserName());
                }
                else{
                    Toast.makeText(Log_In_Activity.this, "username or password is incorrect, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        appUserViewModel.getUserId().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != "ERROR") {
                    loggedUser.setIdfs(s);
                    Intent intent = new Intent(Log_In_Activity.this, Main_Page_Activity.class);
                    intent.putExtra("loggedUser", loggedUser);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Could not find user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}