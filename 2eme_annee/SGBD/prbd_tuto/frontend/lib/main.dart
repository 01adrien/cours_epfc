import 'package:flutter/material.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:prbd_tuto/my_app.dart';
import 'package:prbd_tuto/params.dart';

void main() async {
  await Params.init();
  runApp(
    ProviderScope(
      child: MyApp(),
    ),
  );
}