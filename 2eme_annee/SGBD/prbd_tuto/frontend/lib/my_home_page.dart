import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:prbd_tuto/favorites_page.dart';
import 'package:prbd_tuto/generator_page.dart';
import 'package:prbd_tuto/security_provider.dart';

class MyHomePage extends ConsumerStatefulWidget {
  @override
  ConsumerState<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends ConsumerState<MyHomePage> {
  var selectedIndex = 0;

  @override
  Widget build(BuildContext context) {
    ref.watch(securityProvider);
    final securityNotifier = ref.read(securityProvider.notifier);

    Widget page;
    switch (selectedIndex) {
      case 0:
        page = GeneratorPage();
        break;
      case 1:
        page = FavoritesPage();
        break;
      default:
        throw UnimplementedError('no widget for $selectedIndex');
    }

    return LayoutBuilder(builder: (context, constraints) {
      return Scaffold(
        body: Row(
          children: [
            SafeArea(
              child: NavigationRail(
                extended: constraints.maxWidth >= 600,
                destinations: [
                  NavigationRailDestination(
                    icon: Icon(Icons.home),
                    label: Text('Home'),
                  ),
                  NavigationRailDestination(
                    icon: Icon(Icons.favorite),
                    label: Text('Favorites'),
                  ),
                  NavigationRailDestination(
                    icon: Icon(Icons.logout),
                    label: Text(
                        'Logout${securityNotifier.isLoggedIn ? ' (${securityNotifier.loggedUser})' : ''}'),
                  ),
                ],
                selectedIndex: selectedIndex,
                onDestinationSelected: (value) {
                  if (value == 2) {
                    Navigator.pushReplacementNamed(context, '/login');
                    securityNotifier.logout();
                  } else {
                    setState(() {
                      selectedIndex = value;
                    });
                  }
                },
              ),
            ),
            Expanded(
              child: Container(
                color: Theme.of(context).colorScheme.primaryContainer,
                child: page,
              ),
            ),
          ],
        ),
      );
    });
  }
}
