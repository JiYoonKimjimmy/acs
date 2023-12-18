package me.jimmyberg.acs.ads.adapter.`in`.web

import me.jimmyberg.acs.ads.application.service.ADSService
import me.jimmyberg.acs.support.enumerate.ADSContentType
import me.jimmyberg.acs.support.util.today
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ADSController(
    private val adsService: ADSService
) {

    @PostMapping("/api/address/collect/{content}")
    fun collectAddress(
        @PathVariable content: ADSContentType,
        @RequestParam date: String? = today()
    ): Int {
        return adsService.collect(content, date).size
    }

}