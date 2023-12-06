package me.jimmyberg.acs.address.adapter.`in`.web

import me.jimmyberg.acs.address.application.port.`in`.SaveAddressUseCase
import me.jimmyberg.acs.address.domain.Address
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AddressCollectorController(
    private val saveAddressUseCase: SaveAddressUseCase
) {

    @PostMapping("/api/v1/address")
    fun saveAddress(@RequestBody request: Address): Address {
        return saveAddressUseCase.save(request)
    }

}