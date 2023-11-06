package me.jimmyberg.acs.api

import me.jimmyberg.acs.service.collector.AddressCollectorService
import me.jimmyberg.acs.support.enumerate.ADSContent
import me.jimmyberg.acs.util.today
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AddressCollectController(
    private val addressCollectorService: AddressCollectorService
) {

    @PostMapping("/api/address/collect/{content}")
    fun addressCollect(
        @PathVariable content: ADSContent,
        @RequestParam date: String? = today()
    ): Int {
        return addressCollectorService.collect(content, date).size
    }

}