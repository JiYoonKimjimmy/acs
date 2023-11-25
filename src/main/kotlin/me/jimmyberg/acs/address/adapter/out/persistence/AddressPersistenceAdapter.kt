package me.jimmyberg.acs.address.adapter.out.persistence

import me.jimmyberg.acs.address.application.port.out.SaveAddressPort
import me.jimmyberg.acs.address.domain.Address
import org.springframework.stereotype.Component

@Component
class AddressPersistenceAdapter(
    private val v1AddressDocumentRepository: V1AddressDocumentRepository,
    private val addressMapper: AddressMapper
) : SaveAddressPort {

    override fun save(address: Address): Address {
        return addressMapper.mapToV1Document(address)
            .let { v1AddressDocumentRepository.save(it) }
            .let { addressMapper.mapToDomain(it) }
    }

}