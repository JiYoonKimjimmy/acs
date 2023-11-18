package me.jimmyberg.acs.address.adapter.out.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "acs-address-v1")
data class V1AddressDocument(

    @Id
    private val id: String? = null,
    private val zipCode: String,
    private val baseAddress: String,
    private val detailAddress: String

) {

    fun getZipCode() = this.zipCode
    fun getBaseAddress() = this.baseAddress
    fun getDetailAddress() = this.detailAddress

}