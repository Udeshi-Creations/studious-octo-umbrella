import 'package:flutter/services.dart';

class TeelinkActivityLauncher {
  static const MethodChannel _channel =
      MethodChannel(
    'teelink_activity_launcher',
  );

  static Future<bool> openDeepLink(
    String uri,
  ) async {
    final result =
        await _channel.invokeMethod<bool>(
      'openDeepLink',
      {
        'uri': uri,
      },
    );

    return result ?? false;
  }
}
