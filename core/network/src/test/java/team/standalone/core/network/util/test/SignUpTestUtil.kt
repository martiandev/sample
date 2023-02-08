package team.standalone.core.network.util.test

import team.standalone.core.network.model.request.SignUpRequest

open class SignUpTestUtil {

    val expectedEmail: String = "test@fumiya.com"
    val expectedPassword: String = "123abc"

    fun getTestSignUpRequest(): SignUpRequest {
        return SignUpRequest(
            email = expectedEmail,
            password = expectedPassword
        )
    }
}