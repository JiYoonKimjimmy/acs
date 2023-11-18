package me.jimmyberg.acs.address.adapter.out.persistence

import me.jimmyberg.acs.address.domain.Address
import org.springframework.stereotype.Component

@Component
class AddressMapper {

    fun mapToDomain(document: V1AddressDocument): Address {
        return Address(
            zipCode = document.getZipCode(),
            baseAddress = document.getBaseAddress(),
            detailAddress = document.getDetailAddress()
        )
    }

}