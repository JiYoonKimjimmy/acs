package me.jimmyberg.acs.service.collector

import me.jimmyberg.acs.support.enumerate.ADSContent
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AddressCollectorServiceBootTest(
    @Autowired
    val addressCollectorService: AddressCollectorService
) {

    @DisplayName("도로명주소(한글) 연계정보를 다운로드 수집 요청하여 데이터 출력한다.")
    @Test
    fun getTodayAddress() {
        // given
        val content = ADSContent.JUSUKR

        // when
        val collection = addressCollectorService.collect(content = content)

        // then
        val log = collection.joinToString(separator = "", transform = this::log)
        assertThat(log).isNotEmpty()
    }

    private fun log(content: AddressContent): String {
        return buildString {
            this.append("\n================== START [${content.name}] ==================\n")
            this.append(content.details.joinToString(separator = "\n") { it })
            this.append("\n=================== END [${content.name}] ===================\n")
        }
    }

}