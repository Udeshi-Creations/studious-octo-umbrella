import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'teelink_activity_launcher_method_channel.dart';

abstract class TeelinkActivityLauncherPlatform extends PlatformInterface {
  /// Constructs a TeelinkActivityLauncherPlatform.
  TeelinkActivityLauncherPlatform() : super(token: _token);

  static final Object _token = Object();

  static TeelinkActivityLauncherPlatform _instance = MethodChannelTeelinkActivityLauncher();

  /// The default instance of [TeelinkActivityLauncherPlatform] to use.
  ///
  /// Defaults to [MethodChannelTeelinkActivityLauncher].
  static TeelinkActivityLauncherPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [TeelinkActivityLauncherPlatform] when
  /// they register themselves.
  static set instance(TeelinkActivityLauncherPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
