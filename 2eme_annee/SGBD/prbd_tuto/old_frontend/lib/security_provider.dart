import 'dart:async';

import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:jwt_decoder/jwt_decoder.dart';
import 'package:prbd_tuto/params.dart';
import 'package:prbd_tuto/security.dart';

final securityProvider = AsyncNotifierProvider<SecurityNotifier, String?>(
  () => SecurityNotifier(),
);

class SecurityNotifier extends AsyncNotifier<String?> {
  @override
  Future<String?> build() async {
    // pour que le state soit initialisée de manière synchrone
    state = AsyncData(Params.getValue('token'));
    return state.value;
  }

  Future<void> login(String pseudo, String password) async {
    state = const AsyncLoading();
    try {
      var token = await Security.login(pseudo, password);
      Params.setValue('token', token);
      state = AsyncData(token);
    } catch (e) {
      state = AsyncError(
          "Something went wrong!\nPlease try again later.", StackTrace.current);
    }
  }

  void logout() {
    Params.clearValue('token');
    state = AsyncData(null);
  }

  bool get isLoggedIn => loggedUser != null;

  String? get loggedUser {
    return _getUserFromToken(state.value);
  }

  static String? _getUserFromToken(String? token) {
    if (token == null) return null;
    try {
      Map<String, dynamic> decodedToken = JwtDecoder.decode(token);
      return decodedToken['sub'];
    } catch (e) {
      return null;
    }
  }
}
