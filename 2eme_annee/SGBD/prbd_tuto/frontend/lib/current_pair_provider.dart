import 'dart:async';

import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:prbd_tuto/favorites_provider.dart';
import 'package:prbd_tuto/word_pair.dart';
import 'package:prbd_tuto/abstract_async_notifier.dart';
import 'package:prbd_tuto/security_provider.dart';

final currentPairProvider =
    AsyncNotifierProvider<CurrentPairNotifier, WordPair?>(
  () => CurrentPairNotifier(),
);

class CurrentPairNotifier extends AbstractAsyncNotifier<WordPair?> {
  @override
  Future<WordPair?> build() async {
    ref.read(favoritesProvider);
    ref.watch(securityProvider);
    state = AsyncData(null);
    state = AsyncLoading();
    try {
      return await WordPair.getRandom();
    } catch (e) {
      throw "Something went wrong!\nPlease try again uuu later.";
    }
  }

  @override
  Future<void> refresh() async {
    await getNext();
  }

  Future<void> getNext() async {
    state = const AsyncLoading();
    try {
      var pair = await WordPair.getRandom();
      state = AsyncData(pair);
    } catch (e) {
      state = AsyncError("Something went wrong!\nPlease try again ggg later.",
          StackTrace.current);
    }
  }

  Future<void> toggleFavorite() async {
    final favoritesNotifier = ref.read(favoritesProvider.notifier);

    final current = state.asData?.value;
    if (current == null) return;

    state = const AsyncLoading();
    try {
      await favoritesNotifier.toggleFavorite(current);
      state = AsyncData(current);
    } catch (e) {
      state = AsyncError("Something went wrong!\nPlease try again fff later.",
          StackTrace.current);
    }
  }

  bool isFavorite() {
    final favorites = ref.read(favoritesProvider.notifier);
    final current = state.value;
    if (current == null) return false;
    return favorites.isFavorite(current);
  }
}
