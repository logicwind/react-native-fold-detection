import { Text, View, StyleSheet } from 'react-native';
import { useFoldingFeature } from '@logicwind/react-native-fold-detection';

const App = () => {
  const { layoutInfo, isTableTop, isBook, isFlat } = useFoldingFeature();

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Device Folding Info</Text>

      <Text>Tabletop: {isTableTop ? 'Yes' : 'No'}</Text>
      <Text>Book: {isBook ? 'Yes' : 'No'}</Text>
      <Text>Flat: {isFlat ? 'Yes' : 'No'}</Text>

      <Text style={styles.subTitle}>Layout Info:</Text>
      <Text style={styles.json}>{JSON.stringify(layoutInfo, null, 2)}</Text>
    </View>
  );
};

export default App;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  title: {
    fontSize: 18,
    marginBottom: 12,
    fontWeight: '600',
  },
  subTitle: {
    fontSize: 16,
    marginTop: 16,
    fontWeight: '500',
  },
  json: {
    marginTop: 8,
    fontSize: 12,
    fontFamily: 'monospace',
  },
});
