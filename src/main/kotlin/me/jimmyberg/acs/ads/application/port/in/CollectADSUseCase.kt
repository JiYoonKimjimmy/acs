package me.jimmyberg.acs.ads.application.port.`in`

import me.jimmyberg.acs.ads.domain.ADSContent
import me.jimmyberg.acs.support.enumerate.ADSContentType

interface CollectADSUseCase {

    fun collect(contentType: ADSContentType, date: String): List<ADSContent>

}