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
  State: FoldingFeatureState;
  OcclusionType: FoldingFeatureOcclusionType;
  Orientation: FoldingFeatureOrientation;
  IsSeparating: boolean;
  bounds?: {
    top: number;
    bottom: number;
    left: number;
    right: number;
  };
};
