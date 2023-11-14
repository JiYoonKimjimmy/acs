package me.jimmyberg.acs.repository.collector

import me.jimmyberg.acs.domain.address.V1AddressDocument
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface V1AddressDocumentRepository : ElasticsearchRepository<V1AddressDocument, String>