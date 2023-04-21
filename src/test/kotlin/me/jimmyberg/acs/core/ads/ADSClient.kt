package me.jimmyberg.acs.core.ads

import kr.go.ads.client.ADSReceiver
import kr.go.ads.client.ADSUtils
import org.junit.jupiter.api.Test

/**
 * `U01TX0FVVEgyMDIzMDQxNTEzNDU0NzExMzY4OTI=`
 */
class ADSClient {

    @Test
    fun daily() {
        val key      = "U01TX0FVVEgyMDIzMDQxNTEzNDU0NzExMzY4OTI=" // 승인키
        val dateType = "D"        // 날짜 구분
        val content  = "100001"   // 자료 요청 구분 (100001: 도로명주소(한글), 009000: )
        val retry    = "Y"        // 재반영 요청 여부
        val fromDate = "20230420" // 요청일자(From)
        val toDate   = "20230420" // 요청일자(To)


        val ads = ADSReceiver()
        ads.setFilePath("files/ADS_100001")
        ads.setCreateDateDirectory(ADSUtils.YYYYMMDD)

        val receiveAddr = ads.receiveAddr(key, dateType, content, retry, fromDate, toDate)

        val receiveResult = receiveAddr.getReceiveDatas(ADSUtils.UPDATE_ASC)

        receiveResult
            .forEach {
                println("cntcCode: ${it.cntcCode}, resCode: ${it.resCode}")
            }
    }

}