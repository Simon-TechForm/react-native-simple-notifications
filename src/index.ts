import {NativeModules} from 'react-native';
const {TFMSimpleNotificationsModule} = NativeModules;

interface Spec {
  getToken(): Promise<string>;
  createNotificationChannel(channelId: string, channelName: string): void;
}

export default TFMSimpleNotificationsModule as Spec;
