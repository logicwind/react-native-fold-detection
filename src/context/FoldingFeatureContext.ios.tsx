export const useFoldingFeature = (): any => {
  return {
    layoutInfo: undefined,
    isTableTop: undefined,
    isBook: undefined,
    isFlat: undefined,
  };
};

export const FoldingFeatureProvider = ({ children }: { children: any }) => {
  return children;
};
