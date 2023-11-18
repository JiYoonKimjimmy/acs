package me.jimmyberg.acs.address.adapter.out.persistence

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface V1AddressDocumentRepository : ElasticsearchRepository<V1AddressDocument, String> {

    fun findFirstByZipCode(zipCode: String): V1AddressDocument

}