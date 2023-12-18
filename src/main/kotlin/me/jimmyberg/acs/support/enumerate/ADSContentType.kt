package me.jimmyberg.acs.support.enumerate

enum class ADSContentType(
    val title: String,
    val code: String,
    val contents: List<String>
) {
    JUSUKR(
        title = "도로명주소 한글",
        code = "100001",
        contents = listOf(
            "TH_SGCO_RNADR_LNBR",
            "TH_SGCO_RNADR_MST"
        )
    ),
    JUSUEN(
        title = "도로명주소 영어",
        code = "100002",
        contents = emptyList()
    ),
    JUSUZR(
        title = "도로명",
        code = "100005",
        contents = listOf(
            "TI_SPRD_RDNM"
        )
    ),
    JUSUEC(
        title = "도로명주소 출입구 정보",
        code = "200001",
        contents = listOf(
            "TH_SGCO_RNADR_POSITION "
        )
    ),
    JUSUIN(
        title = "기초번호",
        code = "200002",
        contents = listOf(
            "TH_SPRD_INTRVL_POSITION"
        )
    )
}