package me.jimmyberg.acs.mockk

import io.mockk.*
import org.junit.jupiter.api.Test

class MockedClass {
    fun sum(a: Int, b: Int) = a + b
}

class CaptureTest {

    @Test
    fun capturingTest() {
        val obj = mockk<MockedClass>()
        val slot = slot<Int>()

        every {
            obj.sum(any(), capture(slot))
        } answers {
            1 + firstArg<Int>() + slot.captured
        }

        obj.sum(2, 2) // returns 5
        obj.sum(1, 2) // returns 4
        obj.sum(1, 3) // returns 5

        // `verifyAll()` 순서를 확인하지 않고, 전체 호출 여부 확인
        verifyAll {
            obj.sum(1, 3)
            obj.sum(1, 2)
            obj.sum(2, 2)
        }

        // `verifySequence()` 지정된 순서대로 전체 호출 여부 확인
        verifySequence {
            obj.sum(2, 2)
            obj.sum(1, 2)
            obj.sum(1, 3)
        }

        // `verifyOrder()` 특정 순서대로 호출 여부 확인
        verifyOrder {
            obj.sum(2, 2)
            obj.sum(1, 2)
        }

        val obj2 = mockk<MockedClass>()
        val obj3 = mockk<MockedClass>()

        // `wasNot Called` 모의 or 모의 목록 전혀 호출되지 않은 것을 확인
        verify {
            listOf(obj2, obj3) wasNot Called
        }

        // 모든 호출이 검증이 완료되었음을 확인
        confirmVerified(obj)
    }

}