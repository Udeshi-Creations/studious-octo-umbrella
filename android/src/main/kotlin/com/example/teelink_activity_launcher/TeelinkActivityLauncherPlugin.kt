package com.example.teelink_activity_launcher

import android.content.Context
import android.content.Intent
import android.net.Uri
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class TeelinkActivityLauncherPlugin :
    FlutterPlugin,
    MethodChannel.MethodCallHandler {

    private lateinit var channel: MethodChannel
    private lateinit var context: Context

    override fun onAttachedToEngine(
        flutterPluginBinding: FlutterPlugin.FlutterPluginBinding
    ) {
        context = flutterPluginBinding.applicationContext

        channel = MethodChannel(
            flutterPluginBinding.binaryMessenger,
            "teelink_activity_launcher"
        )

        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(
        call: MethodCall,
        result: MethodChannel.Result
    ) {
        when (call.method) {

            "openDeepLink" -> {

                val uri = call.argument<String>("uri")

                if (uri.isNullOrBlank()) {
                    result.error(
                        "INVALID_URI",
                        "URI was null or empty",
                        null
                    )
                    return
                }

                try {

                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(uri)
                    ).apply {

                        setClassName(
                            "cloud.teelink.teelinkkiosk",
                            "cloud.teelink.teelinkkiosk.MainActivity"
                        )

                        flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_SINGLE_TOP or
                            Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }

                    context.startActivity(intent)

                    result.success(true)

                } catch (e: Exception) {

                    result.error(
                        "LAUNCH_FAILED",
                        e.message,
                        null
                    )
                }
            }

            else -> result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(
        binding: FlutterPlugin.FlutterPluginBinding
    ) {
        channel.setMethodCallHandler(null)
    }
}
