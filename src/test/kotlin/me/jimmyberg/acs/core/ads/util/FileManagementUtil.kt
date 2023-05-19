package me.jimmyberg.acs.core.ads.util

import java.io.File
import java.util.zip.ZipFile

class FileManagementUtil(
    val basePath: String
) {

    fun unzip(source: String, target: String) {
        val targetPath = "$basePath$target"
        val sourcePath = "$targetPath${File.separator}$source"

        ZipFile(sourcePath).use { zip ->
            zip.entries().asSequence().forEach { entry ->
                println("entry : $entry, entry.name: ${entry.name}")
                val fileName = entry.name
                val newFile = File("$targetPath${File.separator}$fileName")

                File(newFile.parent).mkdirs()
                zip.getInputStream(entry).use { input ->
                    File(targetPath, fileName).outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            }
        }
    }

}