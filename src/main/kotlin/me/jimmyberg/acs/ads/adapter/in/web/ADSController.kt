package me.jimmyberg.acs.ads.adapter.`in`.web

import me.jimmyberg.acs.ads.application.port.`in`.CollectADSUseCase
import me.jimmyberg.acs.support.enumerate.ADSContentType
import me.jimmyberg.acs.support.util.today
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ADSController(
    private val collectADSUseCase: CollectADSUseCase
) {

    @PostMapping("/api/ads/collect/{content}")
    fun collectADS(
        @PathVariable content: ADSContentType,
        @RequestParam date: String?
    ): Int {
        return collectADSUseCase.collect(content, date ?: today()).size
    }

}