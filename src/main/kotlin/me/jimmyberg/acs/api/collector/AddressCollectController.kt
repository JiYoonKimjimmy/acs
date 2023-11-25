package me.jimmyberg.acs.api.collector

import me.jimmyberg.acs.domain.address.V1AddressDocument
import me.jimmyberg.acs.repository.collector.V1AddressDocumentRepository
import me.jimmyberg.acs.service.collector.AddressCollectorService
import me.jimmyberg.acs.support.enumerate.ADSContent
import me.jimmyberg.acs.support.util.today
import org.springframework.web.bind.annotation.*

@RestController
class AddressCollectController(
    private val addressCollectorService: AddressCollectorService,
    private val addressDocumentRepository: V1AddressDocumentRepository
) {

    @PostMapping("/api/address/collect/{content}")
    fun collectAddress(
        @PathVariable content: ADSContent,
        @RequestParam date: String? = today()
    ): Int {
        return addressCollectorService.collect(content, date).size
    }

    @PostMapping("/api/address")
    fun saveAddress(@RequestBody request: V1AddressDocument): V1AddressDocument {
        return addressDocumentRepository.save(request)
    }

}