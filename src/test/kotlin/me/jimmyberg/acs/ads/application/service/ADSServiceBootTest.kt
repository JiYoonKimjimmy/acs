package me.jimmyberg.acs.ads.application.service

import me.jimmyberg.acs.support.enumerate.ADSContentType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ADSServiceBootTest(
    @Autowired
    val ADSService: ADSService
) {

    @DisplayName("도로명주소(한글) 연계정보를 다운로드 수집 요청하여 데이터 출력한다.")
    @Test
    fun getJUSUKR() {
        // given
        val content = ADSContentType.JUSUKR

        // when
        val collection = ADSService.collect(contentType = content, date = "20230828")

        // then
        val log = collection.joinToString(separator = "", transform = this::log)
        assertThat(log).isNotEmpty()
    }

    @DisplayName("도로명 연계정보를 다운로드 수집 요청하여 데이터 출력한다.")
    @Test
    fun getJUSUZR() {
        // given
        val content = ADSContentType.JUSUZR

        // when
        val collection = ADSService.collect(contentType = content)

        // then
        val log = collection.joinToString(separator = "", transform = this::log)
        assertThat(log).isNotEmpty()
    }

    @DisplayName("도로명주소 출입구 정보 연계정보를 다운로드 수집 요청하여 데이터 출력한다.")
    @Test
    fun getJUSUEC() {
        // given
        val content = ADSContentType.JUSUEC

        // when
        val collection = ADSService.collect(contentType = content, date = "20230828")

        // then
        val log = collection.joinToString(separator = "", transform = this::log)
        assertThat(log).isNotEmpty()
    }

    @DisplayName("기초번호 연계정보를 다운로드 수집 요청하여 데이터 출력한다.")
    @Test
    fun getJUSUIN() {
        // given
        val content = ADSContentType.JUSUIN

        // when
        val collection = ADSService.collect(contentType = content)

        // then
        val log = collection.joinToString(separator = "", transform = this::log)
        assertThat(log).isNotEmpty()
    }

    private fun log(content: me.jimmyberg.acs.ads.domain.ADSContent): String {
        return buildString {
            this.append("\n================== START [${content.name}] ==================\n")
            this.append(content.details.joinToString(separator = "\n") { it })
            this.append("\n=================== END [${content.name}] ===================\n")
        }
    }

}