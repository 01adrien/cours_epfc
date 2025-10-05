import 'package:prbd_tuto/word_pair.dart';

class MyAppState {
  final WordPair? current;
  final List<WordPair> favorites;

  MyAppState({
    this.current,
    List<WordPair> favorites = const [],
  }) : favorites = List.unmodifiable(favorites);

  MyAppState copyWith({
    WordPair? current,
    List<WordPair>? favorites,
  }) {
    return MyAppState(
      current: current ?? this.current,
      favorites:
      favorites != null ? List.unmodifiable(favorites) : this.favorites,
    );
  }

  bool isFavorite(WordPair pair) {
    return favorites.contains(pair);
  }
}