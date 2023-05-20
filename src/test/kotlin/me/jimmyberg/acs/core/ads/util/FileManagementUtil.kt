package me.jimmyberg.acs.core.ads.util

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.charset.Charset
import java.util.zip.ZipFile

object FileManagementUtil {

    private val basePath: String by lazy { System.getProperty("user.dir") }

    /**
     * 압축 파일 해제 처리
     */
    fun unzip(source: String, target: String) {
        val sourcePath = "$basePath$source"
        val targetPath = "$basePath$target"

        ZipFile(sourcePath).use { zip ->
            zip.entries().asSequence().forEach { entry ->
                println("entry : $entry")
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

    /**
     * 파일 Read 처리
     */
    fun readFile(path: String, fileName: String, charset: Charset = Charset.forName("EUC-KR")): MutableList<String> {
        val result = mutableListOf<String>()
        val file = File("$basePath$path", fileName)
        val reader = BufferedReader(FileReader(file, charset))

        while (true) {
            val line = reader.readLine() ?: break
            result += line
        }

        reader.close()

        return result
    }

}