package me.jimmyberg.acs.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AddressMatchTest {

    private fun isContainsAddressWords(word1: CharArray, word2: CharArray): Boolean {
        return word1.all { word2.contains(it) }
    }

    @DisplayName("도로명 타입의 검색 주소 정보와 결과 주소 정보의 일치 비율을 판단한다")
    @Test
    fun isMatchRequestAndResponseAddressTest() {
        // given
        val request = "서울시 강서구 허준로 139"
        val response = "서울특별시 강서구 허준로 139"

        // when
        /**
         * [주소 정보 일치 여부 판별]
         * 1. `request` 문자열을 공백 기준으로 분리
         * 2. 각 분리된 문자열 기준 배열 순서대로 `response` 문자열의 표함 여부 확인
         *      - 포함 조건 : 문자열을 문자 하니씩 분리하여 포함되어 있는지 확인
         * 3. `request` 문자열 모두 포함되어 있다면, true 반환
         */
        val request_arr = request.split(" ").map(String::toCharArray)
        val response_arr = response.split(" ").map(String::toCharArray)
        var result = true

        request_arr.forEachIndexed { i, it -> if (result) result = isContainsAddressWords(it, response_arr[i]) }

        // then
        assertThat(result).isTrue()
    }

    @DisplayName("'서울시' 문자열이 '서울특별시' 문자열에 모두 포함된다면 true 를 반환한다")
    @Test
    fun isContainsAddressWordsTest() {
        // given
        val word1 = "서울시"
        val word2 = "서울특별시"

        // when
        val result = word1.all { word2.contains(it) }

        // then
        assertThat(result).isTrue()
    }
}