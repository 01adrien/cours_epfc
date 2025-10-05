import 'dart:convert';
import 'dart:io';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;
import 'package:prbd_tuto/params.dart';

const throttleDuration = Duration(seconds: 0);

String get baseUrl => kIsWeb || !Platform.isAndroid
    ? 'http://localhost:3000'
    : 'http://10.0.2.2:3000';

class WordPair {
  final int id;
  final String first;
  final String second;

  WordPair({
    required this.id,
    required this.first,
    required this.second,
  });

  factory WordPair.fromJson(Map<String, dynamic> json) {
    return WordPair(
      id: json['id'],
      first: json['first_word'],
      second: json['second_word'],
    );
  }

  String get asLowerCase => first.toLowerCase() + second.toLowerCase();

  @override
  String toString() {
    return '$id $first $second';
  }

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is WordPair && runtimeType == other.runtimeType && id == other.id;

  @override
  int get hashCode => id.hashCode;

  static Future<WordPair> getRandom() async {
    // Simuler un délai
    await Future.delayed(throttleDuration);

    final response = await http.get(
      Uri.parse('$baseUrl/rpc/get_random_pair'),
      headers: {'Authorization': 'Bearer ${Params.getValue('token')}'},
    );

    if (response.statusCode == 200) {
      return WordPair.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed to get random word pair');
    }
  }

  static Future<List<WordPair>> getFavorites() async {
    await Future.delayed(throttleDuration);

    final response = await http.get(
      Uri.parse('$baseUrl/rpc/get_favorites'),
      headers: {'Authorization': 'Bearer ${Params.getValue('token')}'},
    );

    if (response.statusCode == 200) {
      final List<dynamic> body = json.decode(response.body);
      return body.map((dynamic item) => WordPair.fromJson(item)).toList();
    } else {
      throw Exception('Failed to get favorite pairs');
    }
  }

  Future<void> toggleFavorite() async {
    await Future.delayed(throttleDuration);

    final response = await http.post(
      Uri.parse('$baseUrl/rpc/toggle_favorite'),
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': 'Bearer ${Params.getValue('token')}',
      },
      body: json.encode({'id': id}),
    );
    if (response.statusCode != 204) {
      throw Exception('Failed to update favorite');
    }
  }
}
