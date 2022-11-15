import {NativeModules} from 'react-native';
const {TFMSimpleNotificationsModule} = NativeModules;

interface Spec {
  getToken(): Promise<string>;
}

export default TFMSimpleNotificationsModule as Spec;
