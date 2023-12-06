
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNFoldDetectionSpec.h"

@interface FoldDetection : NSObject <NativeFoldDetectionSpec>
#else
#import <React/RCTBridgeModule.h>

@interface FoldDetection : NSObject <RCTBridgeModule>
#endif

@end
