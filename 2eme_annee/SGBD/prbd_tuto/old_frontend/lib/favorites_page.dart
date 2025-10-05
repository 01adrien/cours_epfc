import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:prbd_tuto/my_app_provider.dart';
import 'package:prbd_tuto/my_app_state.dart';

class FavoritesPage extends ConsumerWidget {
  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final asyncState = ref.watch(myAppProvider);
    final notifier = ref.read(myAppProvider.notifier);

    return asyncState.when(
      data: (state) => data(state, notifier),
      error: (err, _) => error(err, StackTrace.current, notifier),
      loading: () =>
          data(asyncState.value ?? MyAppState(), notifier, isLoading: true),
    );
  }

  Widget data(
    MyAppState state,
    MyAppNotifier notifier, {
    isLoading = false,
  }) {
    return state.favorites.isEmpty
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
                      '${state.favorites.length} favorites:',
                      style: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                  Expanded(
                    child: ListView(
                      children: [
                        for (var pair in state.favorites)
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

  Widget loading() {
    return Center(
      child: CircularProgressIndicator(
        color: Colors.black,
      ),
    );
  }
}
