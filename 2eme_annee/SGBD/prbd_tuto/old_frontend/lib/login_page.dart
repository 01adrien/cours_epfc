import 'package:flutter/material.dart';

import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:prbd_tuto/security_provider.dart';

class LoginPage extends ConsumerWidget {
  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final securityNotifier = ref.read(securityProvider.notifier);
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
              onPressed: () => _doLogin(context, securityNotifier, 'ben'),
              child: Text('Login as ben'),
            ),
            SizedBox(height: 10),
            ElevatedButton(
              onPressed: () => _doLogin(context, securityNotifier, 'boris'),
              child: Text('Login as boris'),
            ),
            SizedBox(height: 10),
            ElevatedButton(
              onPressed: () => _doLogin(context, securityNotifier, 'geo'),
              child: Text('Login as geo'),
            ),
          ],
        ),
      ),
    );
  }

  void _doLogin(
    BuildContext context,
    SecurityNotifier notifier,
    String pseudo,
  ) async {
    Navigator.pushReplacementNamed(context, '/home');
    await notifier.login(pseudo, pseudo);
  }
}
