package me.jimmyberg.acs.domain.address

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "acs-address-v1")
data class V1AddressDocument(

    @Id
    private val id: String? = null,
    private val baseAddress: String,
    private val detailAddress: String,
    private val zipCode: String

)