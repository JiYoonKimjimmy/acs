package me.jimmyberg.acs.support.enumerate

enum class ADSContent(
    val code: String,
    val files: List<String>
) {
    JUSUKR(
        code = "100001",
        files = listOf(
            "TH_SGCO_RNADR_LNBR",
            "TH_SGCO_RNADR_MST"
        )
    ),
    JUSUEN(
        code = "100002",
        files = emptyList()
    )
}