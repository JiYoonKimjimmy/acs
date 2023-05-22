package me.jimmyberg.acs.client

import kr.go.ads.client.ADSReceiver
import kr.go.ads.client.ADSUtils
import kr.go.ads.client.ReceiveData
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ADSClient(
    var dateType: String = "D",
    var retry: String = "Y"
) {

    // ADS 요청 승인 Key
    @Value("\${acs.client-key}")
    val clientKey = "U01TX0FVVEgyMDIzMDQxNTEzNDU0NzExMzY4OTI="
    // 파일 기본 경로
    @Value("\${acs.file-path}")
    val filePath: String = "files/ADS_"

    fun receive(content: String, today: String): ArrayList<ReceiveData> {
        return ADSReceiver()
            .apply {
                this.setFilePath("$filePath$content")
                this.setCreateDateDirectory(ADSUtils.YYYYMMDD)
            }
            .receiveAddr(clientKey, dateType, content, retry, today, today)
            .getReceiveDatas(ADSUtils.UPDATE_ASC)
    }

}