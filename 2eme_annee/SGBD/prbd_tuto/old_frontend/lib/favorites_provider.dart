import 'dart:async';

import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:prbd_tuto/current_pair_provider.dart';
import 'package:prbd_tuto/word_pair.dart';
import 'package:prbd_tuto/abstract_async_notifier.dart';
import 'package:prbd_tuto/security_provider.dart';

final favoritesProvider =
    AsyncNotifierProvider<FavoritesNotifier, List<WordPair>>(
  () => FavoritesNotifier(),
);

class FavoritesNotifier extends AbstractAsyncNotifier<List<WordPair>> {
  @override
  Future<List<WordPair>> build() async {
    ref.watch(securityProvider);
    state = AsyncData([]);
    state = AsyncLoading();
    try {
      return await WordPair.getFavorites();
    } catch (e) {
      throw "Something went wrong!\nPlease try again later.";
    }
  }

  @override
  Future<void> refresh() async {
    await getFavorites();
  }

  Future<void> toggleFavorite(WordPair pair) async {
    final current = state.asData?.value;
    if (current == null) return;

    state = const AsyncLoading();
    try {
      final favorites = List<WordPair>.from(current);
      await pair.toggleFavorite();
      if (favorites.contains(pair)) {
        favorites.remove(pair);
      } else {
        favorites.add(pair);
        favorites.sort((a, b) => a.asLowerCase.compareTo(b.asLowerCase));
      }
      state = AsyncData(List<WordPair>.unmodifiable(favorites));
    } catch (e) {
      state = AsyncValue.error(
          "Something went wrong!\nPlease try again later.", StackTrace.current);
    }
  }

  Future<void> removeFavorite(WordPair pair) async {
    if (isFavorite(pair)) {
      await toggleFavorite(pair);
    }
  }

  Future<void> getFavorites() async {
    state = const AsyncValue.loading();
    try {
      final favorites = await WordPair.getFavorites();
      state = AsyncData(favorites);
    } catch (e) {
      state = AsyncValue.error(
          "Something went wrong!\nPlease try again later.", StackTrace.current);
    }
  }

  bool isFavorite(WordPair pair) {
    return state.asData?.value.contains(pair) ?? false;
  }
}
