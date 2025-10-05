import 'dart:async';

import 'package:hooks_riverpod/hooks_riverpod.dart';

abstract class AbstractAsyncNotifier<T> extends AsyncNotifier<T> {
  Future<void> refresh();
}
