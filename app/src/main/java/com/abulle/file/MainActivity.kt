package com.abulle.file

import android.app.ActivityManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.ActivityNotFoundException
import android.widget.Toast

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        // Convert path to Uri
        val folderUri = Uri.parse("content://com.android.externalstorage.documents/document/primary%3AAndroid%2Fdata%2F");

        // Create intent
        val intent = Intent(Intent.ACTION_VIEW)
            .setPackage("com.google.android.documentsui")
            .setDataAndType(folderUri, "vnd.android.document/directory")
            .setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .setFlags(Intent.FLAG_RECEIVER_FOREGROUND)
            .setClassName(
                "com.google.android.documentsui",
                "com.android.documentsui.files.FilesActivity"
            );

        // Remove app from recent history
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager;
        activityManager.appTasks[0].setExcludeFromRecents(true);

        try {
            startActivity(intent);
            Toast.makeText(this@MainActivity, "Open Android data directory.", Toast.LENGTH_SHORT).show();
            finish();
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this@MainActivity, "An error occurred.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
