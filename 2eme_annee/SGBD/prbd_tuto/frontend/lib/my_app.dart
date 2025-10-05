import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:prbd_tuto/my_home_page.dart';
import 'package:prbd_tuto/login_page.dart';
import 'package:prbd_tuto/security_provider.dart';


class MyApp extends ConsumerWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final securityNotifier = ref.read(securityProvider.notifier);
    return MaterialApp(
      title: 'Namer App',
      theme: ThemeData(
        useMaterial3: true,
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.blue),
      ),
      initialRoute: securityNotifier.isLoggedIn ? '/home' : '/login',
      routes: {
        '/login': (context) => LoginPage(),
        '/home': (context) => MyHomePage(),
      },
    );
  }
}
