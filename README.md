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

```ts
import SimpleNotifications from 'react-native-simple-notifications';

try {
  const token = await SimpleNotifications.getToken();
  // send token to push notification server
} catch (error) {
  console.log('getToken error', error);
}
```

## Credits

Parts of this project are inspired/taken from https://github.com/react-native-push-notification/ios
