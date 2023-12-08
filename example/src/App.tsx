import * as React from 'react';

import { FoldingFeatureProvider } from '@logicwind/react-native-fold-detection';
import SampleScreen from './SampleScreen';

export default function App() {
  return (
    <FoldingFeatureProvider>
      <SampleScreen />
    </FoldingFeatureProvider>
  );
}
