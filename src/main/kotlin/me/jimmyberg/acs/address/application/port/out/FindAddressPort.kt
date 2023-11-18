package me.jimmyberg.acs.address.application.port.out

import me.jimmyberg.acs.address.domain.Address

interface FindAddressPort {

    fun findAddressByZipCode(zipCode: String): Address

}