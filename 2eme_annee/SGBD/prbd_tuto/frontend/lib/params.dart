import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:hive_ce/hive.dart';

class Params {
  static const String _box = 'params';

  static Future<void> init() async {
    if (kIsWeb || !Platform.isAndroid) {
      Hive.init('.hive');
    } else {
      Hive.init('/data/data/com.example.prbd_tuto/files/.hive');
    }
    await Hive.openBox(_box);
  }

  static Future<void> setValue(String key, dynamic value) async {
    var box = Hive.box(_box);
    await box.put(key, value);
    await box.compact();
  }

  static dynamic getValue(String key, {dynamic defaultValue}) {
    var box = Hive.box(_box);
    return box.get(key, defaultValue: defaultValue);
  }

  static Future<void> clearValue(String key) async {
    var box = Hive.box(_box);
    await box.delete(key);
  }

  static Future<void> clearAll() async {
    var box = Hive.box(_box);
    await box.clear();
  }

  static Future<void> close() async {
    var box = Hive.box(_box);
    await box.close();
  }
}
