#import "TFMSimpleNotificationsModule.h"
#import <React/RCTConvert.h>

@implementation TFMSimpleNotificationsModule

RCT_EXPORT_MODULE()

- (dispatch_queue_t)methodQueue {
    return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(getToken:(RCTPromiseResolveBlock) resolve
                  rejecter:(RCTPromiseRejectBlock) reject)
{
    NSNotificationCenter * __weak center = [NSNotificationCenter defaultCenter];
    id __block registeredObserver, failedToRegisterObserver;
    
    registeredObserver = [center addObserverForName:@"RemoteNotificationsRegistered"
                                             object:nil
                                              queue:[NSOperationQueue mainQueue]
                                         usingBlock:^(NSNotification *notification) {
        [center removeObserver:registeredObserver];
        [center removeObserver:failedToRegisterObserver];
        
        resolve(notification.userInfo[@"token"]);
    }];
    
    failedToRegisterObserver = [center addObserverForName:@"RemoteNotificationsFailedToRegister"
                                                   object:nil
                                                    queue:[NSOperationQueue mainQueue]
                                               usingBlock:^(NSNotification *notification) {
        [center removeObserver:failedToRegisterObserver];
        [center removeObserver:registeredObserver];
        
        reject(@"Failed to get token", ((NSError*)notification.userInfo[@"error"]).localizedDescription, nil);
    }];
    
    [RCTSharedApplication() registerForRemoteNotifications];
}

@end
