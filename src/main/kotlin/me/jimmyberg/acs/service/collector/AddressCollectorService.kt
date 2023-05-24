package me.jimmyberg.acs.service.collector

import me.jimmyberg.acs.client.ADSClient
import me.jimmyberg.acs.support.enumerate.ADSContent
import me.jimmyberg.acs.support.enumerate.ADSDateType
import me.jimmyberg.acs.support.enumerate.YesNo
import me.jimmyberg.acs.util.FileManagementUtil
import me.jimmyberg.acs.util.today
import org.springframework.stereotype.Service

@Service
class AddressCollectorService(
    val adsClient: ADSClient
) {

    /**
     * 현재 일자 변동 주소 정보 수집 처리
     */
    fun getTodayAddress(content: ADSContent): List<AddressContent> {
        // 주소 연계 정보 조회 from ADS
        receiveFile(content)
        // 조회 파일 unzip
        unzipFile(content)
        // 조회 파일 read
        return readFile(content)
    }

    fun receiveFile(content: ADSContent) {
        adsClient
            .apply {
                dateType = ADSDateType.DATE
                retry = YesNo.YES
            }
            .receive(content, today())
    }

    fun unzipFile(content: ADSContent) {
        val filePath = "/files/ADS_${content.code}/${today()}"
        val fileName = "AlterD.${content.name}.${today()}.ZIP"
        val sourcePath = "$filePath/$fileName"

        FileManagementUtil.unzip(source = sourcePath, target = filePath)
    }

    fun readFile(content: ADSContent): List<AddressContent> {
        return content
            .contents
            .map {
                val path = "/files/ADS_${content.code}/${today()}"
                val fileName = "AlterD.${content.name}.${today()}.$it.TXT"

                AddressContent(
                    name = it,
                    details = FileManagementUtil.readFile(path = path, fileName = fileName)
                )
            }
    }

}