package me.jimmyberg.acs.address.adapter.`in`.web

import me.jimmyberg.acs.address.application.port.`in`.SaveAddressUseCase
import me.jimmyberg.acs.address.domain.Address
import me.jimmyberg.acs.service.collector.AddressCollectorService
import me.jimmyberg.acs.support.enumerate.ADSContent
import me.jimmyberg.acs.support.util.today
import org.springframework.web.bind.annotation.*

@RestController
class AddressCollectorController(
    private val addressCollectorService: AddressCollectorService,
    private val saveAddressUseCase: SaveAddressUseCase
) {

    @PostMapping("/api/address/collect/{content}")
    fun collectAddress(
        @PathVariable content: ADSContent,
        @RequestParam date: String? = today()
    ): Int {
        return addressCollectorService.collect(content, date).size
    }

    @PostMapping("/api/address")
    fun saveAddress(@RequestBody request: Address): Address {
        return saveAddressUseCase.save(request)
    }

}