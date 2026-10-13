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

        val action = intent.action

        if (
            action != Intent.ACTION_BOOT_COMPLETED &&
            action != Intent.ACTION_USER_PRESENT
        ) {
            return
        }

        Log.i("TeeLinkBoot", "Event received: $action")

        val launchIntent = context.packageManager
            .getLaunchIntentForPackage(context.packageName)

        if (launchIntent == null) {
            Log.e("TeeLinkBoot", "Launch intent unavailable")
            return
        }

        launchIntent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK or
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
            Intent.FLAG_ACTIVITY_SINGLE_TOP
        )

        try {
            context.startActivity(launchIntent)
            Log.i("TeeLinkBoot", "App launch requested after: $action")
        } catch (e: Exception) {
            Log.e("TeeLinkBoot", "Launch failed", e)
        }
    }
}