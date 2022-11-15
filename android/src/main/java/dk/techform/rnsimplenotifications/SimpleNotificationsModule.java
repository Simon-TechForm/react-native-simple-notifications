package dk.techform.rnsimplenotifications;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.firebase.messaging.FirebaseMessaging;

public class SimpleNotificationsModule extends ReactContextBaseJavaModule {
    SimpleNotificationsModule(ReactApplicationContext context) {
        super(context);
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
}