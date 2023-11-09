# react-native-fold-detection

The purpose of the package is to provide details regarding the Android folding capability.

## Installation

```sh
npm install react-native-fold-detection
```

## Usage

```js
//In App.js
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

//In other screens
import { useFoldingFeature } from 'react-native-fold-detection';

const { layoutInfo, isTableTop, isBook, isFlat } = useFoldingFeature();

```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
