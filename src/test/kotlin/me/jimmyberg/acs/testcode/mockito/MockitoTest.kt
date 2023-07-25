package me.jimmyberg.acs.testcode.mockito

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class MockitoTest {

    @DisplayName("Mockito 테스트코드 기본 동작을 확인한다.")
    @Test
    fun sampleTest() {
        val mockedList = mock(List::class.java)

        `when`(mockedList.size).thenReturn(5)

        assertEquals(5, mockedList.size)
    }

}