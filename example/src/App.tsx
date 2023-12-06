import * as React from 'react';

import { FoldingFeatureProvider } from 'react-native-fold-detection';
import SampleScreen from './SampleScreen';

export default function App() {
  return (
    <FoldingFeatureProvider>
      <SampleScreen />
    </FoldingFeatureProvider>
  );
}
