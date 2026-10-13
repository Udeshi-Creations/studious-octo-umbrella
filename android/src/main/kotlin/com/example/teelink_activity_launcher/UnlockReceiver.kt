
package com.example.teelink_activity_launcher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class UnlockReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "TeeLinkUnlock"
    }

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        if (intent.action != Intent.ACTION_USER_PRESENT) {
            return
        }

        Log.i(TAG, "Device unlocked")

        val launchIntent = context.packageManager
            .getLaunchIntentForPackage(context.packageName)

        if (launchIntent == null) {
            Log.e(TAG, "Launch intent unavailable")
            return
        }

        launchIntent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK or
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
            Intent.FLAG_ACTIVITY_SINGLE_TOP
        )

        try {
            Log.i(TAG, "Attempting to open application")

            context.startActivity(launchIntent)

            Log.i(TAG, "Launch requested after unlock")

        } catch (e: Exception) {
            Log.e(
                TAG,
                "Failed to launch after unlock",
                e
            )
        }
    }
}
