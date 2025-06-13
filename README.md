# react-native-fold-detection

The purpose of the package is to provide details regarding the Android folding capability.

## Installation

```sh
npm install @logicwind/react-native-fold-detection
```


## iOS

You'll need to disable auto-linking for this package.
To do so, create react-native.config.js in the root of your project with this content:
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

## In App.js Wrap your app with FoldingFeatureProvider

```js
import * as React from "react";

import { FoldingFeatureProvider } from "@logicwind/react-native-fold-detection";
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
import { useFoldingFeature } from "@logicwind/react-native-fold-detection";

const { layoutInfo, isTableTop, isBook, isFlat } = useFoldingFeature();
```


### useFoldingFeature Props

| Prop       | Type       | Default | Description                                                                                                              |
| ---------- | ---------- | ------- | ------------------------------------------------------------------------------------------------------------------------ |
| layoutInfo | LayoutInfo |         | Folding Feature from [android doc](https://developer.android.com/reference/kotlin/androidx/window/layout/FoldingFeature) |
| isTableTop | boolean    | false   | HALF_OPENED & HORIZONTAL                                                                                                 |
| isBook     | boolean    | false   | HALF_OPENED & VERTICAL                                                                                                   |
| isFlat     | boolean    | true    |                                                                                                                          |

## react-native-fold-detection is crafted mindfully at [Logicwind](https://www.logicwind.com?utm_source=github&utm_medium=github.com-logicwind&utm_campaign=react-native-fold-detection)

We are a 130+ people company developing and designing multiplatform applications using the Lean & Agile methodology. To get more information on the solutions that would suit your needs, feel free to get in touch by [email](mailto:sales@logicwind.com) or through or [contact form](https://www.logicwind.com/contact-us?utm_source=github&utm_medium=github.com-logicwind&utm_campaign=react-native-fold-detection)!

We will always answer you with pleasure 😁

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details