package me.jimmyberg.acs.service.collector

import me.jimmyberg.acs.support.enumerate.ADSContent
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AddressCollectorServiceTest {

    @DisplayName("도로명주소(한글) 연계정보를 ADSClient 다운로드 요청 실패한다.")
    @Test
    fun receiveFileFailTest() {
        // given
        val content = ADSContent.JUSUKR

        // when
        val exception = assertThrows<Exception> { throw Exception("FAILED ${content.code} receiveFile") }

        // then
        assertThat(exception)
            .message()
            .isEqualTo("FAILED ${content.code} receiveFile")
    }

}