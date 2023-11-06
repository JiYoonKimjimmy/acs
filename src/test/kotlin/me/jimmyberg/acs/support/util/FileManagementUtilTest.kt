package me.jimmyberg.acs.support.util

import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.charset.Charset
import java.util.zip.ZipFile

class FileManagementUtilTest {

    @Test
    fun `압축 파일 Unzip 테스트`() {
        val basePath = System.getProperty("user.dir")
        val targetPath = "$basePath/files/ADS_100001/20230519"
        val sourcePath = "$targetPath/AlterD.JUSUKR.20230519.ZIP"

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

    @Test
    fun `unzip() 함수 테스트`() {
        val filePath = "/files/ADS_100001/20230519"
        val fileName = "AlterD.JUSUKR.20230519.ZIP"
        val sourcePath = "$filePath/$fileName"

        FileManagementUtil.unzip(source = sourcePath, target = filePath)
    }

    @Test
    fun `파일 Read 테스트`() {
        val basePath = System.getProperty("user.dir")
        val filePath = "$basePath/files/ADS_100001/20230519"
        val fileName = "AlterD.JUSUKR.20230519.TH_SGCO_RNADR_LNBR.TXT"
        val file = File(filePath, fileName)
        val reader = BufferedReader(FileReader(file, Charset.forName("EUC-KR")))

        while (true) {
            val line = reader.readLine() ?: break
            println(line)
        }

        reader.close()
    }

    @Test
    fun `FileManagementUtil read() 함수 테스트`() {
        val path = "/files/ADS_100001/20230519"
        val fileName = "AlterD.JUSUKR.20230519.TH_SGCO_RNADR_LNBR.TXT"

        FileManagementUtil.readFile(path = path, fileName = fileName).forEach(::println)
    }

}