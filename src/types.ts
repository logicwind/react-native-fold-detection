export type FoldState = 'FLAT' | 'HALF_OPENED';

export type Orientation = 'VERTICAL' | 'HORIZONTAL';

export type OcclusionType = 'NONE' | 'FULL';

export type Bounds = {
  top: number;
  bottom: number;
  left: number;
  right: number;
};

export type LayoutInfo = {
  state: FoldState;
  orientation: Orientation;
  occlusionType: OcclusionType;
  isSeparating: boolean;
  isFoldSupported: boolean;
  bounds: Bounds;
};

export type FoldData = {
  layoutInfo: LayoutInfo;
  isTableTop: boolean;
  isBook: boolean;
  isFlat: boolean;
};
