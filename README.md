# React Native Simple Notifications

## Android

### Setup Firebase

If you haven't already, [set up Firebase](https://firebase.google.com/docs/android/setup) in your project.

No SDK's will have to be added (you can skip Step 4: Add Firebase SDKs to your app)

## iOS

### Add Capabilities : Background Mode - Remote Notifications

Go into your MyReactProject/ios dir and open MyProject.xcworkspace workspace.
Select the top project "MyProject" and select the "Signing & Capabilities" tab.
Add a 2 new Capabilities using "+" button:

- `Background Mode` capability and tick `Remote Notifications`.
- `Push Notifications` capability

### AppDelegate.m(m)

In AppDelegate implementation, add

```objc
- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{
 NSMutableString *hexString = [NSMutableString string];
  NSUInteger deviceTokenLength = deviceToken.length;
  const unsigned char *bytes = (unsigned char *)deviceToken.bytes;
  for (NSUInteger i = 0; i < deviceTokenLength; i++) {
    [hexString appendFormat:@"%02x", bytes[i]];
  }
  [[NSNotificationCenter defaultCenter] postNotificationName:@"RemoteNotificationsRegistered"
                                                      object:self
                                                    userInfo:@{@"token" : [hexString copy]}];
}

- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error
{
 [[NSNotificationCenter defaultCenter] postNotificationName:@"RemoteNotificationsFailedToRegister"
                                                      object:self
                                                    userInfo:@{@"error": error}];
}
```

## Usage
### getToken
```ts
import SimpleNotifications from 'react-native-simple-notifications';

try {
  const token = await SimpleNotifications.getToken();
  // send token to push notification server
} catch (error) {
  console.log('getToken error', error);
}
```

### createNotificationChannel (Android only)
Notification channels are required since Android 8. FCM automatically creates a default channel if none is specified, but when androidSdkTarget < 33, creating a notification channel is what requests notification permissions on Android >= 13, so it's recommended to manually create one.
It's safe to call this function repeatedly.
If you create it manually, you must tell FCM to use the channel you created by adding the following in your AndroidManifest.xml:
```xml
<meta-data
    android:name="com.google.firebase.messaging.default_notification_channel_id"
    android:value="uniqueChannelId" />
```
```ts
import SimpleNotifications from 'react-native-simple-notifications';

try {
  await SimpleNotifications.createNotificationChannel("uniqueChannelId", "uniqueChannelName");
  // send token to push notification server
} catch (error) {
  console.log('createNotificationChannel error', error);
}
```
**Note: When launching the Android app through the react-native cli, there's currently a weird bug that prevents the permission dialog from showing immediately after the creation of a notification channel. Instead the dialog is shown when the app is re-opened. This is not the case when launched through Android Studio. We are yet to determine if there's any issues in published versions.**

## Credits

Parts of this project are inspired/taken from https://github.com/react-native-push-notification/ios
