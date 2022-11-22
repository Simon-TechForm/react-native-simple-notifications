import {NativeModules} from 'react-native';
const {TFMSimpleNotificationsModule} = NativeModules;

interface Spec {
  getToken(): Promise<string>;
  createNotificationChannel(id: string, name: string): void;
}

export default TFMSimpleNotificationsModule as Spec;
