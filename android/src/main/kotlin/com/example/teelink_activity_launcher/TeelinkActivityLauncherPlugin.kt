package com.example.teelink_activity_launcher

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class TeelinkActivityLauncherPlugin :
    FlutterPlugin,
    MethodChannel.MethodCallHandler {

    companion object {
        private const val TAG = "TeeLinkLauncher"

        private const val CHANNEL =
            "teelink_activity_launcher"

        private const val APP_PACKAGE =
            "cloud.teelink.teelinkkiosk"

        private const val MAIN_ACTIVITY =
            "cloud.teelink.teelinkkiosk.MainActivity"
    }

    private lateinit var channel: MethodChannel
    private lateinit var context: Context

    override fun onAttachedToEngine(
        binding: FlutterPlugin.FlutterPluginBinding
    ) {
        context = binding.applicationContext

        channel = MethodChannel(
            binding.binaryMessenger,
            CHANNEL
        )

        channel.setMethodCallHandler(this)

        Log.d(
            TAG,
            "Plugin attached"
        )
    }

    override fun onMethodCall(
        call: MethodCall,
        result: MethodChannel.Result
    ) {
        when (call.method) {

            "openDeepLink" -> {
                val uri =
                    call.argument<String>("uri")

                if (uri.isNullOrBlank()) {
                    result.error(
                        "INVALID_URI",
                        "URI is missing",
                        null
                    )

                    return
                }

                openDeepLink(
                    uri,
                    result
                )
            }

            else -> {
                result.notImplemented()
            }
        }
    }

    private fun openDeepLink(
        uri: String,
        result: MethodChannel.Result
    ) {
        try {
            Log.d(
                TAG,
                "Requested URI: $uri"
            )

            //
            // STEP 1:
            // Try to bring our existing task to the front.
            //
            val activityManager =
                context.getSystemService(
                    Context.ACTIVITY_SERVICE
                ) as ActivityManager

            var taskMovedToFront = false

            try {
                val appTasks =
                    activityManager.appTasks

                Log.d(
                    TAG,
                    "Found ${appTasks.size} app task(s)"
                )

                for (appTask in appTasks) {
                    val taskInfo =
                        appTask.taskInfo

                    val baseIntent =
                        taskInfo.baseIntent

                    val component =
                        baseIntent.component

                    Log.d(
                        TAG,
                        "Task ${taskInfo.taskId}: $component"
                    )

                    if (
                        component?.packageName ==
                        APP_PACKAGE
                    ) {
                        Log.d(
                            TAG,
                            "Moving task ${taskInfo.taskId} to front"
                        )

                        activityManager.moveTaskToFront(
                            taskInfo.taskId,
                            ActivityManager.MOVE_TASK_WITH_HOME
                        )

                        taskMovedToFront = true

                        break
                    }
                }
            } catch (e: Exception) {
                Log.e(
                    TAG,
                    "moveTaskToFront failed",
                    e
                )
            }

            //
            // STEP 2:
            // Give Android a moment to surface the task.
            //
            Handler(
                Looper.getMainLooper()
            ).postDelayed({

                try {
                    //
                    // STEP 3:
                    // Explicitly deliver the FlutterFlow
                    // deep-link intent to MainActivity.
                    //
                    val intent =
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(uri)
                        ).apply {

                            setClassName(
                                APP_PACKAGE,
                                MAIN_ACTIVITY
                            )

                            addFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK
                            )

                            addFlags(
                                Intent.FLAG_ACTIVITY_SINGLE_TOP
                            )

                            addFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_TOP
                            )

                            addFlags(
                                Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                            )
                        }

                    Log.d(
                        TAG,
                        "Starting explicit MainActivity"
                    )

                    context.startActivity(
                        intent
                    )

                    Log.d(
                        TAG,
                        "Intent delivered. " +
                        "taskMovedToFront=$taskMovedToFront"
                    )

                    result.success(true)

                } catch (e: Exception) {
                    Log.e(
                        TAG,
                        "Explicit activity launch failed",
                        e
                    )

                    result.error(
                        "LAUNCH_FAILED",
                        e.message,
                        null
                    )
                }

            }, 150)

        } catch (e: Exception) {
            Log.e(
                TAG,
                "openDeepLink failed",
                e
            )

            result.error(
                "OPEN_FAILED",
                e.message,
                null
            )
        }
    }

    override fun onDetachedFromEngine(
        binding: FlutterPlugin.FlutterPluginBinding
    ) {
        channel.setMethodCallHandler(null)

        Log.d(
            TAG,
            "Plugin detached"
        )
    }
}
