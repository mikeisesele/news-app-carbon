package com.michael.common

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File

const val MIME_PDF = "application/pdf"

fun Context.downloadFile(
    fileName: String,
    desc: String,
    url: String,
    destinationDir: String = Environment.DIRECTORY_DOWNLOADS,
): Long {
    val request = DownloadManager.Request(Uri.parse(url))
        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        .setTitle(fileName)
        .setDescription(desc)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(true)
        .setDestinationInExternalPublicDir(destinationDir, fileName)
    val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    return downloadManager.enqueue(request)
}

/**
 * If Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q we receive a uri of the content:// scheme hence
 * we don't need to use [FileProvider] to get a proper [Uri].
 * Otherwise we get a file:// scheme which needs a [FileProvider] to parse it into a correct [Uri]
 * The proper type of [Uri] is needed in order to launch the pdf reader app on the phone.
 * Due to Android changes in how different schemes interact with apps storage we need the proper
 * conversion in order to share our apps data (the downloaded file) with external apps such as a pdf
 * reader. By migrating to a content:// scheme (as file is no longer supported), we don't need to request
 * additional read permission as a temporary read permission is added as part of the content:// uri
 */
fun Context.getFileUri(uri: String): Uri {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        Uri.parse(uri)
    } else {
        FileProvider.getUriForFile(this, packageName, File(Uri.parse(uri).path!!))
    }
}

fun Context.openFile(
    uri: String,
    mimeDataType: String? = null,
    onErrorAction: () -> Unit,
) {
    with(Intent(Intent.ACTION_VIEW)) {
        if (!mimeDataType.isNullOrBlank()) {
            setDataAndType(
                getFileUri(uri),
                mimeDataType,
            )
        } else {
            data = getFileUri(uri)
        }
        addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
                or Intent.FLAG_GRANT_READ_URI_PERMISSION,
        )
        try {
            startActivity(this)
        } catch (e: Exception) {
            onErrorAction()
        }
    }
}
