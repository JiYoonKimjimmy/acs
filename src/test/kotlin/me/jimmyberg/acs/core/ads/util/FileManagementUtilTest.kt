package me.jimmyberg.acs.core.ads.util

import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FileManagementUtilTest {

    @Test
    fun `압축 파일 Unzip 테스트`() {
        println("==================== START ====================")
        val basePath = "/Users/jim/Desktop/00_kjy/01_dev/workspace/demo-acs"
        val fileName = "AlterD.JUSUKR.${today()}.ZIP"
        val targetPath = "$basePath/files/ADS_100001/${today()}"
        val sourcePath = "$targetPath/$fileName"

        val util = FileManagementUtil()
        util.unzip(sourcePath = sourcePath, targetPath = targetPath)
        println("===================== END =====================")
    }

    private fun today(): String {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    }

}