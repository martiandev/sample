package team.standalone.core.network.util.test

import team.standalone.core.network.model.request.SignInRequest

open class SignInTestUtil {

    val expectedEmail: String = "test@fumiya.com"
    val expectedPassword: String = "123abc"

    fun getTestSignInRequest(): SignInRequest {
        return SignInRequest(
            email = expectedEmail,
            password = expectedPassword
        )
    }
}