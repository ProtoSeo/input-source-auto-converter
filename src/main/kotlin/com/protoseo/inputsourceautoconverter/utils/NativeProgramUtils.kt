package com.protoseo.inputsourceautoconverter.utils

import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.StandardCopyOption.REPLACE_EXISTING

internal object NativeProgramUtils {

    @Throws(IOException::class)
    fun loadFileFromResources(path: String): String {
        val loadedFile = File.createTempFile("/input-source-auto-converter","").also {
            NativeProgramUtils::class.java.getResourceAsStream(path).use { `is` ->
                Files.copy(`is` as InputStream, it.toPath(), REPLACE_EXISTING)
            }
            check (it.setExecutable(true)) {
                "can't set executable this file"
            }
        }
        return loadedFile.absolutePath
    }

}
