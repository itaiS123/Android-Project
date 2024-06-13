package il.co.shivhit.androidproject.ACTIVITIES;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

import il.co.shivhit.androidproject.R;

public abstract class BaseActivity extends AppCompatActivity {
    protected static MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    protected abstract void initializeViews();
    protected abstract void setListeners();
}
