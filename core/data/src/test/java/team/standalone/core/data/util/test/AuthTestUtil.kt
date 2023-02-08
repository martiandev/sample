package team.standalone.core.data.util.test

import team.standalone.core.data.domain.model.param.SignInParam
import team.standalone.core.data.domain.model.param.SignUpParam

open class AuthTestUtil {

    val expectedEmail: String = "test@fumiya.com"
    val expectedPassword: String = "test1234"

    fun getTestSignInParam(): SignInParam {
        return SignInParam(
            email = expectedEmail,
            password = expectedPassword
        )
    }

    fun getTestSignUpParam(): SignUpParam {
        return SignUpParam(
            email = expectedEmail,
            password = expectedPassword
        )
    }
}