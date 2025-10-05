import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:prbd_tuto/favorites_provider.dart';
import 'package:prbd_tuto/data_error_widget.dart';
import 'package:prbd_tuto/word_pair.dart';

class FavoritesPage extends ConsumerWidget {
  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final asyncState = ref.watch(favoritesProvider);
    final notifier = ref.read(favoritesProvider.notifier);

    return asyncState.when(
      data: (state) => data(state, notifier),
      error: (err, _) => DataErrorWidget(
          error: err, stackTrace: StackTrace.current, notifier: notifier),
      loading: () => data(asyncState.value ?? [], notifier, isLoading: true),
    );
  }

  Widget data(
    List<WordPair> favorites,
    FavoritesNotifier notifier, {
    isLoading = false,
  }) {
    return favorites.isEmpty
        ? Center(child: Text('No favorites yet.'))
        : Stack(
            children: [
              Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Padding(
                    padding: const EdgeInsets.all(20),
                    child: Text(
                      'You have '
                      '${favorites.length} favorites:',
                      style: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                  Expanded(
                    child: ListView(
                      children: [
                        for (var pair in favorites)
                          ListTile(
                            leading: IconButton(
                              icon: Icon(Icons.favorite),
                              onPressed: isLoading
                                  ? null
                                  : () {
                                      notifier.removeFavorite(pair);
                                    },
                            ),
                            title: Text(pair.asLowerCase),
                          ),
                      ],
                    ),
                  ),
                ],
              ),
              if (isLoading) loading(),
            ],
          );
  }

  Widget loading() {
    return Center(
      child: CircularProgressIndicator(
        color: Colors.black,
      ),
    );
  }
}
