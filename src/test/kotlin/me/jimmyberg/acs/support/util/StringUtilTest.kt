package me.jimmyberg.acs.support.util

import org.junit.jupiter.api.Test

class StringUtilTest {

    @Test
    fun `주어진 문자열이 '로, 길' 문자열을 끝나는지 확인한다`() {
        val regex = "(.+)[로|길]"
        print("하이로", regex)
        print("하이길", regex)
        print("하이대길", regex)
        print("하이대길로", regex)
        print("하이대길로하", regex)
    }

    @Test
    fun `주어진 문자열이 '로, 길' 문자열을 포함하는지 확인한다`() {
        val regex = ".*(로|길).*"
        print("길", regex)
        print("로", regex)
        print("하이로", regex)
        print("하이대로", regex)
        print("하이대길", regex)
        print("하이대", regex)
    }

    @Test
    fun `주어진 문자열이 숫자를 포함하는지 확인한다`() {
        val regex = ".*[0-9].*"
        print("하이1", regex)
        print("하이", regex)
        print("하이1루", regex)
    }

    @Test
    fun `숫자 앞 문자열이 '로, 길' 로 끝나는지와 뒤 문자열이 '로, 길' 을 포함하는지 확인한다`() {
        val regex = "^.*[로|길](\\s*)[0-9](\\s*)[로|길].*\$"
        print("하이", regex)
        print("하이대로", regex)
        print("하이대로1", regex)
        print("하이1", regex)
        print("하이대로1길", regex)
        print("하이로1로", regex)
        print("하이로1길", regex)
        print("하이로만1길", regex)
        print("서울특별시 하이대로1길", regex)
        print("서울특별시 하이대로 1 길", regex)
    }

    @Test
    fun `정규식 조건에 일치하는 문자열 추출하여 확인한다`() {
        val regex = "^.*[로|길](\\s*)[0-9](\\s*)[로|길].*\$"
        regex.toRegex().find("서울특별시 하이대로1길 여기주소").also { println(it!!.value) }
        regex.toRegex().replace("서울특별시 하이대로1길 여기주소", "HELLO").also { println(it) }
    }

    @Test
    fun `정규식 조건에 일치하는 문자열 추출한다`() {
        val regex = "[로|길](\\s*)[0-9](\\s*)[로|길]"
        regex.toRegex().find("서울특별시 하이대로 1 길 여기주소").also { println(it!!.value) }
        regex.toRegex().replace("서울특별시 하이대로1길 여기주소", "HELLO").also { println(it) }
        regex.toRegex().replace("서울특별시 하이대로 1 길 여기주소") { it.value.replace(" ", "") }.also { println(it) }
    }

    private fun print(text: String, regex: String) {
        regex.toRegex().matches(text).also { println("$text : $it") }
    }

}