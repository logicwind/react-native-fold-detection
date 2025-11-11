import { useEffect, useState } from 'react';
import { NativeEventEmitter, NativeModules, Platform } from 'react-native';

import type { FoldData } from './types';

export * from './types';

const LINKING_ERROR =
  `The package 'react-native-fold-detection' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const FoldDetection = NativeModules.FoldDetection
  ? NativeModules.FoldDetection
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export const useFoldingFeature = (): FoldData => {
  const [data, setData] = useState<FoldData>({
    layoutInfo: {
      state: 'FLAT',
      orientation: 'VERTICAL',
      occlusionType: 'NONE',
      isSeparating: false,
      bounds: { top: 0, bottom: 0, left: 0, right: 0 },
      isFoldSupported: false,
    },
    isTableTop: false,
    isBook: false,
    isFlat: true,
  });

  useEffect(() => {
    if (Platform.OS !== 'android' || !FoldDetection) {
      return;
    }

    const foldEmitter = new NativeEventEmitter(FoldDetection);

    FoldDetection.startListening();

    const sub = foldEmitter.addListener('onFoldChange', (event) => {
      setData(event);
    });

    FoldDetection.getLayoutInfo().then(setData).catch(console.warn);

    return () => {
      FoldDetection.stopListening();
      sub.remove();
    };
  }, []);

  return data;
};
