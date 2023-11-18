package me.jimmyberg.acs.address.application.service

import me.jimmyberg.acs.address.application.port.`in`.SaveAddressUseCase
import me.jimmyberg.acs.address.application.port.out.FindAddressPort
import me.jimmyberg.acs.address.domain.Address
import org.springframework.stereotype.Service

@Service
class SaveAddressService(
    private val findAddressPort: FindAddressPort
) : SaveAddressUseCase {

    override fun save(address: Address): Boolean {
        findAddressPort.findAddressByZipCode(address.getZipCode())
        return true
    }

}