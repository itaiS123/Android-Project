package il.co.shivhit.androidproject.ACTIVITIES;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

public class BackGraound_service extends Service {
    private MediaPlayer player;
    // Tune from the internet
    private static final String Mp3 = "https://file-examples.com/wp-content/uploads/2017/11/file_example_MP3_700KB.mp3";

    @Override
    public IBinder onBind(Intent intent) {
        return null; // Must return
    }

    // The method service that performs the action
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show();

        // Starting a thread
        new Thread(() -> {
            // Loading the file
            player = MediaPlayer.create(this, Uri.parse(Mp3));
            // The melody will repeat itself
            player.setLooping(true);
            // Starting the player
            player.start();
        }).start();

        return START_STICKY;
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
