package me.jimmyberg.acs.address.adapter.out.persistence

import me.jimmyberg.acs.address.application.port.out.FindAddressPort
import me.jimmyberg.acs.address.domain.Address
import org.springframework.stereotype.Component

@Component
class AddressPersistenceAdapter(
    private val v1AddressDocumentRepository: V1AddressDocumentRepository,
    private val addressMapper: AddressMapper
) : FindAddressPort {

    override fun findAddressByZipCode(zipCode: String): Address {
        return v1AddressDocumentRepository
            .findFirstByZipCode(zipCode)
            .let { addressMapper.mapToDomain(it) }
    }

}