package me.jimmyberg.acs.service.collector

import me.jimmyberg.acs.support.enumerate.ADSContent
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

class AddressCollectorServiceTest {

    @DisplayName("도로명주소(한글) 연계정보를 ADSClient 다운로드 요청 실패한다.")
    @Test
    fun receiveFileFailTest() {
        // given
        val service = mock(AddressCollectorService::class.java)
        val content = ADSContent.JUSUKR

        // when
        assertThrows<Exception> { `when`(service.collect(content)).thenThrow(Exception()) }
    }

}