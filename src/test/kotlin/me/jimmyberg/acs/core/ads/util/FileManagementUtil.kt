package me.jimmyberg.acs.core.ads.util

import java.io.File
import java.util.zip.ZipFile

class FileManagementUtil {

    private val basePath: String by lazy { System.getProperty("user.dir") }

    /**
     * 압축 파일 해제 처리
     */
    fun unzip(source: String, target: String) {
        val sourcePath = "$basePath$source"
        val targetPath = "$basePath$target"

        ZipFile(sourcePath).use { zip ->
            zip.entries().asSequence().forEach { entry ->
                println("entry : $entry, entry.name: ${entry.name}")
                val fileName = entry.name
                val newFile = File("$targetPath${File.separator}$fileName")
                // directory 생성
                File(newFile.parent).mkdirs()
                // unzip file 생성
                zip.getInputStream(entry).use { input ->
                    File(targetPath, fileName).outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            }
        }
    }

}