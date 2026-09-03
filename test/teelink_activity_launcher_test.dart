import 'package:flutter_test/flutter_test.dart';
import 'package:teelink_activity_launcher/teelink_activity_launcher.dart';
import 'package:teelink_activity_launcher/teelink_activity_launcher_platform_interface.dart';
import 'package:teelink_activity_launcher/teelink_activity_launcher_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockTeelinkActivityLauncherPlatform
    with MockPlatformInterfaceMixin
    implements TeelinkActivityLauncherPlatform {
  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final TeelinkActivityLauncherPlatform initialPlatform = TeelinkActivityLauncherPlatform.instance;

  test('$MethodChannelTeelinkActivityLauncher is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelTeelinkActivityLauncher>());
  });

  test('getPlatformVersion', () async {
    TeelinkActivityLauncher teelinkActivityLauncherPlugin = TeelinkActivityLauncher();
    MockTeelinkActivityLauncherPlatform fakePlatform = MockTeelinkActivityLauncherPlatform();
    TeelinkActivityLauncherPlatform.instance = fakePlatform;

    expect(await teelinkActivityLauncherPlugin.getPlatformVersion(), '42');
  });
}
