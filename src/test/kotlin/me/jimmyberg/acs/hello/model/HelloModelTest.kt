package me.jimmyberg.acs.hello.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HelloModelTest {

    @Test
    fun `HelloModel 생성 테스트`() {
        val name = "jimmyberg"
        val age = 34

        val hello = HelloModel(name = name, age = age)

        assertEquals(hello.name, name)
        assertEquals(hello.age, age)
    }

}