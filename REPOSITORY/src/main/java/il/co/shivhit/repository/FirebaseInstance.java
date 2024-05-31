package il.co.shivhit.repository;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirebaseInstance {
    private static volatile FirebaseInstance _instance = null;
    public static FirebaseApp app;
    private FirebaseInstance(Context context) {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("android-project-edad2")		// ApplicationId
                .setApplicationId("1:1054890786483:android:6a5f0c24a0e8501ca9fac6")		// ProjectId
                .setApiKey("AIzaSyCd5C6rfLh8I_cp3vlIYDzdOhQz8rWsyJA")
                .setStorageBucket("android-project-edad2.appspot.com")
                .build();

        app = FirebaseApp.initializeApp(context, options);
    }

    public static FirebaseInstance instance(Context context) {
        if (_instance == null) {  // 1st check
            synchronized (FirebaseInstance.class) {
                if (_instance == null){ // 2nd check
                    _instance = new FirebaseInstance(context);
                }
            }
        }
        return _instance;
    }
}
