package com.example.teelink_activity_launcher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) {
            return
        }

        Log.i("TeeLinkBoot", "Device boot completed")

        val launchIntent = context.packageManager
            .getLaunchIntentForPackage(context.packageName)

        if (launchIntent == null) {
            Log.e("TeeLinkBoot", "No launch intent found")
            return
        }

        launchIntent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK or
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
            Intent.FLAG_ACTIVITY_SINGLE_TOP
        )

        try {
            context.startActivity(launchIntent)
            Log.i("TeeLinkBoot", "App launch requested")
        } catch (e: Exception) {
            Log.e("TeeLinkBoot", "Failed to launch app", e)
        }
    }
}