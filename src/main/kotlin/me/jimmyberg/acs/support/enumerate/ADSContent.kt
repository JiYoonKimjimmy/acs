package me.jimmyberg.acs.support.enumerate

enum class ADSContent(
    val code: String,
    val contents: List<String>
) {
    JUSUKR(
        code = "100001",
        contents = listOf(
            "TH_SGCO_RNADR_LNBR",
            "TH_SGCO_RNADR_MST"
        )
    ),
    JUSUEN(
        code = "100002",
        contents = emptyList()
    )
}