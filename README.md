# react-native-fold-detection

The purpose of the package is to provide details regarding the Android folding capability.

## Installation

```sh
npm install react-native-fold-detection
```

## In App.js Wrap your app with FoldingFeatureProvider

```js
import * as React from "react";

import { FoldingFeatureProvider } from "react-native-fold-detection";
import SampleScreen from "./SampleScreen";

export default function App() {
  return (
    <FoldingFeatureProvider>
      <SampleScreen />
    </FoldingFeatureProvider>
  );
}
```

## In other screens

```js
import { useFoldingFeature } from "react-native-fold-detection";

const { layoutInfo, isTableTop, isBook, isFlat } = useFoldingFeature();
```

## In other screens

You'll need to disable autolinking for this package

```js
module.exports = {
  dependencies: {
    "react-native-fold-detection": {
      platforms: {
        ios: null, // this will disable autolinking for this package on iOS
      },
    },
  },
};
```

### useFoldingFeature Props

| Prop       | Type       | Default | Description                                                                                                              |
| ---------- | ---------- | ------- | ------------------------------------------------------------------------------------------------------------------------ |
| layoutInfo | LayoutInfo |         | Folding Feature from [android doc](https://developer.android.com/reference/kotlin/androidx/window/layout/FoldingFeature) |
| isTableTop | boolean    | false   | HALF_OPENED & HORIZONTAL                                                                                                 |
| isBook     | boolean    | false   | HALF_OPENED & VERTICAL                                                                                                   |
| isFlat     | boolean    | true    |                                                                                                                          |

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
