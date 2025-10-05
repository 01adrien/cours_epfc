import 'package:flutter/material.dart';
import 'package:prbd_tuto/abstract_async_notifier.dart';

class DataErrorWidget extends StatelessWidget {
  final Object error;
  final StackTrace? stackTrace;
  final AbstractAsyncNotifier notifier;

  const DataErrorWidget({
    required this.error,
    this.stackTrace,
    required this.notifier,
  });

  @override
  Widget build(BuildContext context) {
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
              notifier.refresh();
            },
            child: Text('Retry'),
          ),
        ],
      ),
    );
  }
}
