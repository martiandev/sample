package team.standalone.core.network.util.test

import team.standalone.core.network.model.request.AddressRequest

open class AddressTestUtil {

    val expectedNumber: String = "123"
    val expectedCity: String = "Tokyo"
    val expectedPrefecture: String = "Tokyo"
    val expectedStructure: String = "A1"
    val expectedPostalCode: String = "1001A"

    fun getTestAddressRequest(): AddressRequest {
        return AddressRequest(
            number = expectedNumber,
            city = expectedCity,
            prefecture = expectedPrefecture,
            structure = expectedStructure,
            postalCode = expectedPostalCode
        )
    }
}