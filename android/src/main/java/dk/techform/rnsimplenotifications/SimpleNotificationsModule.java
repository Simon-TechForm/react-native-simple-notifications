package dk.techform.rnsimplenotifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.firebase.messaging.FirebaseMessaging;

public class SimpleNotificationsModule extends ReactContextBaseJavaModule {
    private final ReactApplicationContext context;
    SimpleNotificationsModule(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "TFMSimpleNotificationsModule";
    }

    @ReactMethod
    public void getToken(Promise promise) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if(!task.isSuccessful()) {
                Exception exception = task.getException();
                if(exception == null) {
                    promise.reject("Failed to get token", "Unknown error");
                    return;
                }
                promise.reject("Failed to get token", exception.getMessage());
                return;
            }
            promise.resolve(task.getResult());
        });
    }

    @ReactMethod
    public void createNotificationChannel(String id, String name) {
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
