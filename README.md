# @logicwind/react-native-fold-detection

`@logicwind/react-native-fold-detection` is a lightweight React Native library that helps developers easily detect the fold state of foldable devices.

## Installation

Using npm:

```sh md title="Terminal"
npm install @logicwind/react-native-fold-detection
```

or using yarn:

```sh md title="Terminal"
yarn add @logicwind/react-native-fold-detection
```

### Expo Setup

If you're working with this Expo project, make sure to run:

```sh md title="Terminal"
npx expo prebuild
```

## Usage

The `useFoldingFeature()` hook provides information about the device’s folding state — useful when developing apps for foldable devices (like the Samsung Galaxy Fold or Surface Duo).
It helps you adapt your UI layout based on how the device is folded.

```tsx
import { useFoldingFeature } from 'react-native-folding-feature';

const MyComponent = () => {
  const { layoutInfo, isTableTop, isBook, isFlat } = useFoldingFeature();

  return (
    <View>
      {isTableTop && <Text>Device is in tabletop mode</Text>}
      {isBook && <Text>Device is in book mode</Text>}
      {isFlat && <Text>Device is fully flat</Text>}
    </View>
  );
};
```

## Returned Values

| Property     | Type         | Description                                                                                                                                              |
| ------------ | ------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `layoutInfo` | `LayoutInfo` | Provides detailed layout data about the folding feature — such as hinge bounds, orientation, and separation state. Useful for custom layout adjustments. |
| `isTableTop` | `boolean`    | Returns `true` when the device is partially folded in **tabletop mode** (like a laptop).                                                                 |
| `isBook`     | `boolean`    | Returns `true` when the device is folded.                                                                                                                |
| `isFlat`     | `boolean`    | Returns `true` when the device is **completely unfolded (flat)**. Ideal for fullscreen layouts.                                                          |

### `LayoutInfo` Type

| Property          | Type                         | Description                                                                   |
| ----------------- | ---------------------------- | ----------------------------------------------------------------------------- |
| `state`           | `'FLAT' \| 'HALF_OPENED'`    | Current folding state of the device.                                          |
| `orientation`     | `'VERTICAL' \| 'HORIZONTAL'` | Orientation of the folding hinge.                                             |
| `occlusionType`   | `'NONE' \| 'FULL'`           | Indicates whether the hinge area occludes (covers) part of the display.       |
| `isSeparating`    | `boolean`                    | `true` if the hinge divides the display area into two separate regions.       |
| `isFoldSupported` | `boolean`                    | `true` if the device supports folding features.                               |
| `bounds`          | `Bounds`                     | The screen coordinates that describe the position and size of the hinge area. |

### `Bounds` Type

| Property | Type     | Description                                                           |
| -------- | -------- | --------------------------------------------------------------------- |
| `top`    | `number` | Distance (in pixels) from the top edge of the screen to the hinge.    |
| `bottom` | `number` | Distance (in pixels) from the bottom edge of the screen to the hinge. |
| `left`   | `number` | Distance (in pixels) from the left edge of the screen to the hinge.   |
| `right`  | `number` | Distance (in pixels) from the right edge of the screen to the hinge.  |

## react-native-fold-detection is crafted mindfully at [Logicwind](https://www.logicwind.com?utm_source=github&utm_medium=github.com-logicwind&utm_campaign=react-native-fold-detection)

We are a 130+ people company developing and designing multiplatform applications using the Lean & Agile methodology. To get more information on the solutions that would suit your needs, feel free to get in touch by [email](mailto:sales@logicwind.com) or through or [contact form](https://www.logicwind.com/contact-us?utm_source=github&utm_medium=github.com-logicwind&utm_campaign=react-native-fold-detection)!

We will always answer you with pleasure 😁

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
