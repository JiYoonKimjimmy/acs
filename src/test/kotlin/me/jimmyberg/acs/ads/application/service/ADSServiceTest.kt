package me.jimmyberg.acs.ads.application.service

import me.jimmyberg.acs.support.enumerate.ADSContentType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ADSServiceTest(
    @Mock
    private val ADSService: ADSService
) {

    @DisplayName("도로명주소(한글) 연계정보를 ADSClient 다운로드 요청 실패한다.")
    @Test
    fun receiveFileFailTest() {
        // given
        val content = ADSContentType.JUSUKR

        // when
        assertThrows<Exception> { `when`(ADSService.collect(content)).thenThrow(Exception()) }
    }

    @DisplayName("도로명주소(한글) 연계정보를 다운로드 요청하여 정상 수신하여 건수를 확인한다.")
    @Test
    fun receiveFileSuccessTest() {
        // given
        val content = ADSContentType.JUSUKR
        val ADSContent = listOf(me.jimmyberg.acs.ads.domain.ADSContent(
            name = "도로명주소(한글)",
            details = listOf("도로명주소1", "도로명주소2")
        ))

        // when
        `when`(ADSService.collect(content)).thenReturn(ADSContent)

        // then
        val collection = ADSService.collect(content)[0]
        assertEquals(collection, ADSContent[0])
        assertEquals(collection.details.size, 2)

        verify(ADSService, times(1)).collect(content)
    }

}