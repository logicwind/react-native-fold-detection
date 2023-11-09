import React from 'react';
import { SafeAreaView, StyleSheet, Text } from 'react-native';
import { useFoldingFeature } from 'react-native-fold-detection';

export default () => {
  const { layoutInfo, isTableTop, isBook, isFlat } = useFoldingFeature();

  return (
    <SafeAreaView style={styles.container}>
      <Text style={styles.header}>Folding Feature Information</Text>

      <Text style={styles.blockText}>state: {layoutInfo.State}</Text>
      <Text style={styles.blockText}>
        orientation: {layoutInfo.Orientation}
      </Text>
      <Text style={styles.blockText}>
        occlusionType: {layoutInfo.OcclusionType}
      </Text>
      <Text style={styles.blockText}>
        isSeparating: {layoutInfo.IsSeparating ? 'true' : 'false'}
      </Text>
      <Text style={styles.blockText}>
        bounds: {`${JSON.stringify(layoutInfo.bounds)}`}
      </Text>
      <Text style={styles.header}>Helpers: </Text>
      <Text style={styles.blockText}>
        isTableTop: {isTableTop ? 'true' : 'false'}
      </Text>
      <Text style={styles.blockText}>isBook: {isBook ? 'true' : 'false'}</Text>
      <Text style={styles.blockText}>isFlat: {isFlat ? 'true' : 'false'}</Text>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 10,
  },
  header: {
    fontSize: 20,
    fontWeight: '700',
  },
  blockText: {
    display: 'flex',
  },
});
