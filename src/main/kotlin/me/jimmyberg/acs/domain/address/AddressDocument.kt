package me.jimmyberg.acs.domain.address

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "acs-address-v1")
data class V1AddressDocument(

    @Id
    val id: String?,
    val baseAddress: String,
    val detailAddress: String,
    val zipCode: String

)