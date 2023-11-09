import React, {
  createContext,
  useContext,
  useEffect,
  useMemo,
  useState,
  type PropsWithChildren,
} from 'react';
import { NativeEventEmitter, NativeModules } from 'react-native';

import FoldingFeature from '../FoldingFeature';
import {
  FoldingFeatureOcclusionType,
  FoldingFeatureOrientation,
  FoldingFeatureState,
  type LayoutInfo,
} from '../types';

type FoldingFeatureContextProps = {
  layoutInfo: LayoutInfo;
  isTableTop: boolean;
  isBook: boolean;
  isFlat: boolean;
};

export const FoldingFeatureContext = createContext<FoldingFeatureContextProps>({
  layoutInfo: {
    state: FoldingFeatureState.FLAT,
    occlusionType: FoldingFeatureOcclusionType.NONE,
    orientation: FoldingFeatureOrientation.VERTICAL,
    isSeparating: false,
    isFoldSupported: false,
  },
  // helper state
  isTableTop: false,
  isBook: false,
  isFlat: true,
});

export const useFoldingFeature = () => {
  const context = useContext(FoldingFeatureContext);

  if (context === undefined) {
    throw new Error('useFoldingFeature was used outside of its provider');
  }

  return context;
};

export const FoldingFeatureProvider = ({ children }: PropsWithChildren<{}>) => {
  const value = useProvideFunc();

  return (
    <FoldingFeatureContext.Provider value={value}>
      {children}
    </FoldingFeatureContext.Provider>
  );
};

const useProvideFunc = (): FoldingFeatureContextProps => {
  const [layoutInfo, setLayoutInfo] = useState<LayoutInfo>({
    state: FoldingFeatureState.FLAT,
    occlusionType: FoldingFeatureOcclusionType.NONE,
    orientation: FoldingFeatureOrientation.VERTICAL,
    isSeparating: false,
    isFoldSupported: false,
  });

  const updateLayoutInfo = (event: LayoutInfo) => {
    setLayoutInfo(event);
  };

  const isTableTop = useMemo(() => {
    return (
      layoutInfo.state === FoldingFeatureState.HALF_OPENED &&
      layoutInfo.orientation === FoldingFeatureOrientation.HORIZONTAL
    );
  }, [layoutInfo]);

  const isBook = useMemo(() => {
    return (
      layoutInfo.state === FoldingFeatureState.HALF_OPENED &&
      layoutInfo.orientation === FoldingFeatureOrientation.VERTICAL
    );
  }, [layoutInfo]);

  const isFlat = useMemo(() => {
    return !(isTableTop || isBook);
  }, [isTableTop, isBook]);

  useEffect(() => {
    FoldingFeature.startListening();

    const eventEmitter = new NativeEventEmitter(NativeModules.FoldingFeature);
    const layoutSubscription = eventEmitter.addListener(
      'onLayoutInfoChange',
      (event) => {
        if (event?.displayFeatures) {
          const stringObject = JSON.stringify(event.displayFeatures);
          const displayFeatures = JSON.parse(stringObject);
          if (displayFeatures) {
            // Now you can use these values as needed in your React Native component
            updateLayoutInfo(displayFeatures);
          }
        }
      }
    );

    return () => {
      layoutSubscription.remove();
    };
  }, []);

  return {
    layoutInfo,
    isTableTop,
    isBook,
    isFlat,
  };
};
