package me.jimmyberg.acs.address.application.port.`in`

import me.jimmyberg.acs.address.domain.Address

interface SaveAddressUseCase {

    fun save(command: SaveAddressCommand): Address

}