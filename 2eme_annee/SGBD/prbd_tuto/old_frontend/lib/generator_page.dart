import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:prbd_tuto/big_card.dart';
import 'package:prbd_tuto/my_app_state.dart';
import 'package:prbd_tuto/my_app_provider.dart';

class GeneratorPage extends ConsumerWidget {
  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final asyncState = ref.watch(myAppProvider);
    final notifier = ref.read(myAppProvider.notifier);

    return asyncState.when(
      data: (state) => data(state, notifier),
      error: (err, stackTrace) => error(err, stackTrace, notifier),
      loading: () => data(asyncState.value, notifier, isLoading: true),
    );
  }

  Widget data(
    MyAppState? state,
    MyAppNotifier notifier, {
    bool isLoading = false,
  }) {
    return Center(
      child: Stack(
        alignment: Alignment.center,
        children: [
          if (state != null && state.current != null)
            Opacity(
              opacity: isLoading ? 0.33 : 1,
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  BigCard(pair: state.current!),
                  SizedBox(height: 10),
                  Row(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      ElevatedButton.icon(
                        onPressed: isLoading
                            ? null
                            : () {
                                notifier.toggleFavorite(state.current!);
                              },
                        icon: Icon(state.isFavorite(state.current!)
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

  Widget error(
    Object error,
    StackTrace stackTrace,
    MyAppNotifier notifier,
  ) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(
            error.toString(),
            textAlign: TextAlign.center,
            style: TextStyle(
              color: Colors.red,
              fontSize: 16,
              fontWeight: FontWeight.bold,
            ),
          ),
          SizedBox(height: 10),
          ElevatedButton(
            onPressed: () {
              notifier.getNext();
            },
            child: Text('Retry'),
          ),
        ],
      ),
    );
  }
}
