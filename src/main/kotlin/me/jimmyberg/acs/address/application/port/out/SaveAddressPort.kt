package me.jimmyberg.acs.address.application.port.out

import me.jimmyberg.acs.address.domain.Address

interface SaveAddressPort {

    fun save(address: Address): Address

}