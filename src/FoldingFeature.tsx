import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-folding-feature' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const FoldingFeature = NativeModules.FoldingFeature
  ? NativeModules.FoldingFeature
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

interface FoldingFeatureInterface {
  startListening: () => void;
}

export default FoldingFeature as FoldingFeatureInterface;
