export enum FoldingFeatureState {
  FLAT = 'FLAT',
  HALF_OPENED = 'HALF_OPENED',
}

export enum FoldingFeatureOrientation {
  VERTICAL = 'VERTICAL',
  HORIZONTAL = 'HORIZONTAL',
}

export enum FoldingFeatureOcclusionType {
  NONE = 'NONE',
  FULL = 'FULL',
}

export type LayoutInfo = {
  state: FoldingFeatureState;
  occlusionType: FoldingFeatureOcclusionType;
  orientation: FoldingFeatureOrientation;
  isSeparating: boolean;
  bounds?: {
    top: number;
    bottom: number;
    left: number;
    right: number;
  };
};
