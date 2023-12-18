package me.jimmyberg.acs.address.application.service

import me.jimmyberg.acs.address.application.port.`in`.SaveAddressCommand
import me.jimmyberg.acs.address.application.port.`in`.SaveAddressUseCase
import me.jimmyberg.acs.address.application.port.out.SaveAddressPort
import me.jimmyberg.acs.address.domain.Address
import org.springframework.stereotype.Service

@Service
class SaveAddressService(
    private val saveAddressPort: SaveAddressPort
) : SaveAddressUseCase {

    override fun save(command: SaveAddressCommand): Address {
        return Address(
                zipCode = command.getZipCode(),
                baseAddress = command.getBaseAddress(),
                detailAddress = command.getDetailAddress()
            )
            .let { saveAddressPort.save(it) }
    }

}