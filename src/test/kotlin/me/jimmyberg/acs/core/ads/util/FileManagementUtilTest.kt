package me.jimmyberg.acs.core.ads.util

import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FileManagementUtilTest {

    private fun today(): String {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    }

    @Test
    fun `압축 파일 Unzip 테스트`() {
        val filePath = "/files/ADS_100001/${today()}"
        val fileName = "AlterD.JUSUKR.${today()}.ZIP"
        val sourcePath = "$filePath/$fileName"

        val util = FileManagementUtil()
        util.unzip(source = sourcePath, target = filePath)
    }

}