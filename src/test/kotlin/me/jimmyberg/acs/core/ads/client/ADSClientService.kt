package me.jimmyberg.acs.core.ads.client

import kr.go.ads.client.ADSReceiver
import kr.go.ads.client.ADSUtils
import org.springframework.stereotype.Service

@Service
class ADSClientService(
    var dateType: String = "D",
    var retry: String = "Y"
) {

    // ADS 요청 승인 Key
    private val clientKey = "U01TX0FVVEgyMDIzMDQxNTEzNDU0NzExMzY4OTI="
    // 파일 기본 경로
    private val filePath = "files/ADS_"

    fun receive(content: String, today: String) {
        ADSReceiver()
            .apply {
                this.setFilePath("$filePath$content")
                this.setCreateDateDirectory(ADSUtils.YYYYMMDD)
            }
            .receiveAddr(clientKey, dateType, content, retry, today, today)
            .getReceiveDatas(ADSUtils.UPDATE_ASC)
            .forEach { println("cntcCode: ${it.cntcCode}, resCode: ${it.resCode}") }
    }

}
