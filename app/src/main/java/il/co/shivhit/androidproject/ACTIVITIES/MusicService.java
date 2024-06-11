package il.co.shivhit.androidproject.ACTIVITIES;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

import il.co.shivhit.androidproject.R;

public class MusicService extends Service {

    private MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            if (action.equals("PLAY")) {
                startPlaying();
            } else if (action.equals("STOP")) {
                stopPlaying();
            }
        }

        return START_STICKY; // Service restarts if system kills it
    }

    private void startPlaying() {
        if (player == null) { // Check if player is already created
            player = MediaPlayer.create(this, R.raw.mp3);
            if (player == null) {
                Toast.makeText(this, "Error creating media player", Toast.LENGTH_SHORT).show();
                stopSelf(); // Stop the service if there's an error
                return;
            }
        }

        try {
            player.setLooping(true);
            player.start();
        } catch (Exception e) {
            Toast.makeText(this, "Error starting music: " + e.getMessage(), LENGTH_SHORT).show();
            stopSelf();
        }
    }

    private void stopPlaying() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onDestroy() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }
}



