import 'dart:async';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:prbd_tuto/word_pair.dart';
import 'package:prbd_tuto/my_app_state.dart';

final myAppProvider = AsyncNotifierProvider<MyAppNotifier, MyAppState>(
      () => MyAppNotifier(),
);

class MyAppNotifier extends AsyncNotifier<MyAppState> {
  @override
  Future<MyAppState> build() async {
    try {
      state = const AsyncValue.loading();
      // permet de lancer plusieurs futures en parallèle
      final result = await Future.wait([
        WordPair.getRandom(),
        WordPair.getFavorites(),
      ]);
      return MyAppState(
        current: result[0] as WordPair,
        favorites: result[1] as List<WordPair>,
      );
    } catch (e) {
      throw "Something went wrong!\nPlease try again later.";
    }
  }

  Future<void> getNext() async {
    final currentState = state.asData?.value ?? MyAppState(favorites: []);
    state = const AsyncValue.loading();
    try {
      var newPair = await WordPair.getRandom();
      state = AsyncValue.data(currentState.copyWith(current: newPair));
    } catch (e) {
      state = AsyncValue.error(
          "Something went wrong!\nPlease try again later.", StackTrace.current);
    }
  }

  Future<void> toggleFavorite(WordPair pair) async {
    final currentState = state.asData?.value;
    if (currentState == null) return;

    state = const AsyncValue.loading();
    try {
      final favorites = List<WordPair>.from(currentState.favorites);
      await pair.toggleFavorite();
      if (favorites.contains(pair)) {
        favorites.remove(pair);
      } else {
        favorites.add(pair);
        favorites.sort((a, b) => a.asLowerCase.compareTo(b.asLowerCase));
      }
      state = AsyncValue.data(currentState.copyWith(favorites: favorites));
    } catch (e) {
      state = AsyncValue.error(
          "Something went wrong!\nPlease try again later.", StackTrace.current);
    }
  }

  Future<void> removeFavorite(WordPair pair) async {
    if (state.value?.isFavorite(pair) ?? false) {
      toggleFavorite(pair);
    }
  }

  Future<void> getFavorites() async {
    state = const AsyncValue.loading();
    try {
      final favorites = await WordPair.getFavorites();
      state = AsyncValue.data(state.asData!.value.copyWith(favorites: favorites));
    } catch (e) {
      state = AsyncValue.error(
          "Something went wrong!\nPlease try again later.", StackTrace.current);
    }
  }
}