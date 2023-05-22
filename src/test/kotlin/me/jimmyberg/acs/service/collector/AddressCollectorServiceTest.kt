package me.jimmyberg.acs.service.collector

import me.jimmyberg.acs.service.collector.AddressCollectorService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AddressCollectorServiceTest(
    @Autowired val addressCollectorService: AddressCollectorService
) {

    @Test
    fun `getTodayAddress 함수 테스트`() {
        addressCollectorService.getTodayAddress(content = "100001")
    }

}