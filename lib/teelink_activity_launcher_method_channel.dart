import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'teelink_activity_launcher_platform_interface.dart';

/// An implementation of [TeelinkActivityLauncherPlatform] that uses method channels.
class MethodChannelTeelinkActivityLauncher extends TeelinkActivityLauncherPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('teelink_activity_launcher');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>(
      'getPlatformVersion',
    );
    return version;
  }
}
