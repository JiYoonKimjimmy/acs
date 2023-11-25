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

    fun mapToV1Document(address: Address): V1AddressDocument {
        return V1AddressDocument(
            zipCode = address.getZipCode(),
            baseAddress = address.getBaseAddress(),
            detailAddress = address.getDetailAddress()
        )
    }

}