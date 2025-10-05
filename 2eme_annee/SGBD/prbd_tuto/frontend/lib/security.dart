import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:prbd_tuto/word_pair.dart';

class Security {
  static Future<String?> login(String pseudo, String password) async {
    // Simuler un délai
    await Future.delayed(throttleDuration);

    final response = await http.post(
      Uri.parse('$baseUrl/rpc/login'),
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: json.encode({'pseudo': pseudo, 'password': password}),
    );
    if (response.statusCode != 200) {
      throw Exception('Failed to login');
    }
    final dynamic body = json.decode(response.body);
    String token = body['token'];
    return token;
  }
}