package team.standalone.core.data.util.test

import team.standalone.core.data.domain.model.Address
import team.standalone.core.data.domain.model.param.AddressParam

open class AddressTestUtil {

    val expectedNumber: String = "123"
    val expectedCity: String = "Tokyo"
    val expectedPrefecture: String = "Tokyo"
    val expectedStructure: String = "A1"
    val expectedPostalCode: String = "1001A"

    fun getTestAddress(): Address {
        return Address(
            number = expectedNumber,
            city = expectedCity,
            prefecture = expectedPrefecture,
            structure = expectedStructure,
            postalCode = expectedPostalCode
        )
    }

    fun getTestAddressParam(): AddressParam {
        return AddressParam(
            number = expectedNumber,
            city = expectedCity,
            prefecture = expectedPrefecture,
            structure = expectedStructure,
            postalCode = expectedPostalCode
        )
    }
}