package me.jimmyberg.acs.service.collector

import me.jimmyberg.acs.support.enumerate.ADSContent
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AddressCollectorServiceTest(
    @Autowired val addressCollectorService: AddressCollectorService
) {

    @Test
    fun `getTodayAddress 함수 테스트`() {
        addressCollectorService
            .collect(content = ADSContent.JUSUKR)
            .forEach(this::print)
    }

    private fun print(content: AddressContent) {
        println("================== START [${content.name}] ==================")
        content.details.forEach(::println)
        println("=================== END [${content.name}] ===================")
    }

}