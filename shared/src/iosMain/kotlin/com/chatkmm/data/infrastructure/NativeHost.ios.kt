package com.chatkmm.data.infrastructure

import platform.Foundation.NSBundle
import platform.Foundation.NSDictionary
import platform.Foundation.dictionaryWithContentsOfFile

actual object NativeHost {
    actual fun getUrl(): String {
        return getAPI(type = APIType.URL_KEY)
    }

    private fun getAPI(type: APIType): String {
        val name = "SecureKeys"
        val mainBundle = NSBundle.mainBundle
        val filePath = mainBundle.pathForResource(name, ofType = "plist")
        val plist = filePath?.let { file ->
            NSDictionary.dictionaryWithContentsOfFile(file)
        } ?: throw RuntimeException("Couldn't find file plist")

        val urlApiKey = plist["URL_API_KEY"] as? String ?: ""

        return when (type) {
            APIType.URL_KEY -> urlApiKey
        }
    }

    private enum class APIType {
        URL_KEY,
    }
}