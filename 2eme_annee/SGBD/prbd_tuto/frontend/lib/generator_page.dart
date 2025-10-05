import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:prbd_tuto/big_card.dart';
import 'package:prbd_tuto/current_pair_provider.dart';
import 'package:prbd_tuto/favorites_provider.dart';
import 'package:prbd_tuto/data_error_widget.dart';
import 'package:prbd_tuto/word_pair.dart';


class GeneratorPage extends ConsumerWidget {
  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final asyncState = ref.watch(currentPairProvider);
    final notifier = ref.read(currentPairProvider.notifier);
    
    // pour être notifié quand l'état des favoris change (par exemple dans le
    // cas où la paire courante est favorite et est affichée alors que la liste
    // des favoris est encore en cours de chargement)
    ref.watch(favoritesProvider);

    return asyncState.when(
      data: (state) => data(state, notifier),
      error: (err, stackTrace) => DataErrorWidget(error: err, stackTrace: stackTrace, notifier: notifier),
      loading: () => data(asyncState.value, notifier, isLoading: true),
    );
  }

  Widget data(
    WordPair? pair,
    CurrentPairNotifier notifier, {
    bool isLoading = false,
  }) {
    return Center(
      child: Stack(
        alignment: Alignment.center,
        children: [
          if (pair != null)
            Opacity(
              opacity: isLoading ? 0.33 : 1,
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  BigCard(pair: pair),
                  SizedBox(height: 10),
                  Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      ElevatedButton.icon(
                        onPressed: isLoading
                            ? null
                            : () {
                                notifier.toggleFavorite();
                              },
                        icon: Icon(notifier.isFavorite()
                            ? Icons.favorite
                            : Icons.favorite_border),
                        label: Text('Like'),
                      ),
                      SizedBox(width: 10),
                      ElevatedButton(
                        onPressed: isLoading
                            ? null
                            : () {
                                notifier.getNext();
                              },
                        child: Text('Next'),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          if (isLoading)
            CircularProgressIndicator(
              color: Colors.black,
            ),
        ],
      ),
    );
  }


}
