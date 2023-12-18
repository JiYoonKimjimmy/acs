package me.jimmyberg.acs.ads.application.service

import me.jimmyberg.acs.ads.domain.ADSContent
import me.jimmyberg.acs.support.enumerate.ADSContentType
import me.jimmyberg.acs.support.enumerate.ADSDateType
import me.jimmyberg.acs.support.enumerate.YesNo
import me.jimmyberg.acs.support.util.FileManagementUtil
import me.jimmyberg.acs.support.util.today
import org.springframework.stereotype.Service

@Service
class ADSService(
    val adsClient: ADSClient
) {

    /**
     * 현재 일자 변동 주소 정보 수집 처리
     */
    fun collect(contentType: ADSContentType, date: String? = today()): List<ADSContent> {
        // 주소 연계 정보 조회 from ADS
        receiveFile(contentType, date!!)
        // 조회 파일 unzip
        unzipFile(contentType, date)
        // 조회 파일 read
        return readFile(contentType, date)
    }

    fun receiveFile(contentType: ADSContentType, date: String) {
        try {
            adsClient
                .apply {
                    dateType = ADSDateType.DATE
                    retry = YesNo.YES
                }
                .receive(contentType, date)
        } catch (e: Exception) {
            throw Exception("FAILED ${contentType.code} receiveFile")
        }
    }

    fun unzipFile(contentType: ADSContentType, date: String) {
        val filePath = "/files/ADS_${contentType.code}/$date"
        val fileName = "AlterD.${contentType.name}.$date.ZIP"
        val sourcePath = "$filePath/$fileName"

        FileManagementUtil.unzip(source = sourcePath, target = filePath)
    }

    fun readFile(contentType: ADSContentType, date: String): List<ADSContent> {
        return contentType
            .contents
            .map {
                val path = "/files/ADS_${contentType.code}/$date"
                val fileName = "AlterD.${contentType.name}.$date.$it.TXT"

                ADSContent(
                    name = it,
                    details = FileManagementUtil.readFile(path = path, fileName = fileName)
                )
            }
    }

}