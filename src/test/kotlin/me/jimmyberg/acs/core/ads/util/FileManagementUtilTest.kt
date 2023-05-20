package me.jimmyberg.acs.core.ads.util

import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.charset.Charset
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FileManagementUtilTest {

    @Test
    fun `압축 파일 Unzip 테스트`() {
        val filePath = "/files/ADS_100001/20230519"
        val fileName = "AlterD.JUSUKR.20230519.ZIP"
        val sourcePath = "$filePath/$fileName"

        val util = FileManagementUtil()
        util.unzip(source = sourcePath, target = filePath)
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

}