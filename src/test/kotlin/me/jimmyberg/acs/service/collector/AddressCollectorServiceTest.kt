package me.jimmyberg.acs.service.collector

import me.jimmyberg.acs.support.enumerate.ADSContent
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class AddressCollectorServiceTest(
    @Mock
    private val addressCollectorService: AddressCollectorService
) {

    @DisplayName("도로명주소(한글) 연계정보를 ADSClient 다운로드 요청 실패한다.")
    @Test
    fun receiveFileFailTest() {
        // given
        val content = ADSContent.JUSUKR

        // when
        assertThrows<Exception> { `when`(addressCollectorService.collect(content)).thenThrow(Exception()) }
    }

    @DisplayName("도로명주소(한글) 연계정보를 다운로드 요청하여 정상 수신하여 건수를 확인한다.")
    @Test
    fun receiveFileSuccessTest() {
        // given
        val content = ADSContent.JUSUKR
        val addressContent = listOf(AddressContent(name = "도로명주소(한글)", details = listOf("도로명주소1", "도로명주소2")))

        // when
        `when`(addressCollectorService.collect(content)).thenReturn(addressContent)

        // then
        val collection = addressCollectorService.collect(content)[0]
        assertEquals(collection, addressContent[0])
        assertEquals(collection.details.size, 2)

        verify(addressCollectorService, times(1)).collect(content)
    }

}