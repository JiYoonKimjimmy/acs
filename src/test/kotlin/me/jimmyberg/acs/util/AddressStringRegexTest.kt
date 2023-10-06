package me.jimmyberg.acs.util

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AddressStringRegexTest {

    @Test
    fun `도로명 주소 문자열 공백 제거 by 정규식`() {
        val address1 = "서울특별시 강서구 허준로 1 길"
        val address2 = "서울특별시 강서구 허준로 12 길"
        val address3 = "서울특별시 강서구 허준로 123 길"

        val regex = "(\\S+[로|길]\\b)\\s+(\\d+)\\s+(\\S*[로|길]\\b)".toRegex()
        val replacement = "$1$2$3"

        val result1 = address1.replace(regex, replacement).also(::println)
        val result2 = address2.replace(regex, replacement).also(::println)
        val result3 = address3.replace(regex, replacement).also(::println)

        Assertions.assertEquals(result1, "서울특별시 강서구 허준로1길")
        Assertions.assertEquals(result2, "서울특별시 강서구 허준로12길")
        Assertions.assertEquals(result3, "서울특별시 강서구 허준로123길")
    }

    @Test
    fun `검색어 주소 문자열 & 결과 주소 문자열 일치 여부 확인`() {
        val address = "서울 강서구 허준로 139"
        val result = "인천 강서구 허준로 139"

        // [`address` 문자열과 `result` 문자열 비교]
        // 1. 공백 기준 문자열 분리
        // 2. 분리된 배열 크기에 따라 마지막 인덱스부터 높은 가중치 부여
        // 3. 각 순서대로 문자열 일치 여부 확인

        // 문자열을 공백으로 분리
        val addressParts = address.split(" ")
        val resultParts = result.split(" ")

        // 가중치의 총 합은 100으로 설정
        val totalWeight = 100
        val weightPerPart = totalWeight / addressParts.size

        // 일치하는 부분의 가중치를 누적할 변수
        var totalScore = 0

        // 문자열을 거꾸로 확인하며 일치 여부를 확인하고 가중치를 더함
        for (i in addressParts.indices.reversed()) {
            if (i < resultParts.size) {
                if (i == 0 && resultParts[i].startsWith(addressParts[i])) {
                    totalScore += weightPerPart
                } else if (i > 0 && addressParts[i] == resultParts[i]) {
                    totalScore += weightPerPart
                }
            } else {
                // 일치하지 않으면, 종료
                break
            }
        }

        println("일치 점수: $totalScore")
    }
}