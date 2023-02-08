package team.standalone.core.data.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import team.standalone.core.common.exception.AuthException
import team.standalone.core.common.exception.UserException
import team.standalone.core.common.util.Result
import team.standalone.core.data.data.mapper.UserEntityMapper
import team.standalone.core.data.data.mapper.UserRemoteMapper
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.model.param.SignInParam
import team.standalone.core.data.domain.model.param.SignUpParam
import team.standalone.core.data.domain.repository.AuthRepository
import team.standalone.core.data.util.fake.FakeAuthRemoteDataSource
import team.standalone.core.data.util.fake.FakeUserDao
import team.standalone.core.data.util.fake.FakeUserLocalDataSource
import team.standalone.core.data.util.fake.FakeUserRemoteDataSource
import team.standalone.core.network.util.UserRemoteTestData
import team.standalone.core.network.util.UserRemoteTestData.signedInEmailNotFound
import team.standalone.core.network.util.UserRemoteTestData.signedInNotVerifiedEmail
import team.standalone.core.network.util.UserRemoteTestData.signedInUser
import team.standalone.core.network.util.UserRemoteTestData.signedInUserNotFound

/** Unit tests methods for the [AuthRepositoryImpl] class*/
@ExperimentalCoroutinesApi
class AuthRepositoryTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var authRemoteDataSource: FakeAuthRemoteDataSource
    private lateinit var userRemoteDataSource: FakeUserRemoteDataSource

    private val emailTest = "email1@email.com"
    private val passwordTest = "test1234"
    private val emailTest2 = "test@test.com"

    @Before
    fun setUp() {
        /**create object for the [FakeUserRemoteDataSource]*/
        userRemoteDataSource = FakeUserRemoteDataSource(
            UnconfinedTestDispatcher(),
            UserRemoteTestData.getUserRemoteList()
        )

        /**create object for the [FakeAuthRemoteDataSource]*/
        authRemoteDataSource =
            FakeAuthRemoteDataSource(
                UnconfinedTestDispatcher(),
                userRemoteDataSource
            )

        /**create object for the [AuthRepositoryImpl]*/
        authRepository = AuthRepositoryImpl(
            UnconfinedTestDispatcher(),
            authRemoteDataSource, userRemoteDataSource,
            FakeUserLocalDataSource(UnconfinedTestDispatcher(), FakeUserDao()),
            UserRemoteMapper(), UserEntityMapper()
        )
    }

    /**
     * Test case: should return user when signing in [AuthRepository.signIn].
     * @assertions:
     *      assertTrue: result is Result.Success<User>
     *      assertEquals: user.email is equals to emailTest
     *      assertEquals: user.nickname is equals to "nickName1"
     *      assertEquals: user.firstName is equals to "firstName1"
     *      assertEquals: user.lastName is equals to "lastName1"*/
    @Test
    fun shouldReturnUserWhenSigningIn() = runBlocking {
        authRemoteDataSource.setSignedInUser(signedInUser())

        val signInParam = SignInParam(email = emailTest, password = passwordTest)
        val result = authRepository.signIn(signInParam)

        assertTrue(result is Result.Success<User>)

        val resultAsUser = result as Result.Success<User>
        assertEquals((resultAsUser).data.email, emailTest)
        assertEquals((resultAsUser).data.nickname, "nickName1")
        assertEquals((resultAsUser).data.firstName, "firstName1")
        assertEquals((resultAsUser).data.lastName, "lastName1")
    }

    /**
     * Test case: should throw invalid credentials exception when signing in [AuthRepository.signIn].
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to AuthException.InvalidCredentialsException
     *      assertEquals: result.exception.message is equals to AuthException.InvalidCredentialsException.message*/
    @Test
    fun shouldThrowInvalidCredentialsExceptionWhenSigningIn() = runBlocking {
        authRemoteDataSource.setSignedInUser(signedInUser())

        val signInParam =
            SignInParam(email = emailTest, password = "1234test")
        val result = authRepository.signIn(signInParam)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals((resultAsError).exception, AuthException.InvalidCredentialsException)
        assertEquals(
            (resultAsError).exception.message, AuthException.InvalidCredentialsException.message
        )
    }

    /**
     * Test case: should throw invalid user exception when signing in [AuthRepository.signIn].
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to AuthException.InvalidUserException
     *      assertEquals: result.exception.message is equals to AuthException.InvalidUserException.message*/
    @Test
    fun shouldReturnInvalidUserExceptionWhenSigningIn() = runBlocking {
        authRemoteDataSource.setSignedInUser(signedInUser())

        val signInParam =
            SignInParam(email = emailTest2, password = "1234test")
        val result = authRepository.signIn(signInParam)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals((resultAsError).exception, AuthException.InvalidUserException)
        assertEquals((resultAsError).exception.message, AuthException.InvalidUserException.message)
    }

    /**
     * Test case: should throw unauthorized exception in auth remote when signing in [AuthRepository.signIn].
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to AuthException.UnauthorizedException
     *      assertEquals: result.exception.message is equals to AuthException.UnauthorizedException.message*/
    @Test
    fun shouldThrowUnAuthorizedExceptionWhenSigningIn() = runBlocking {
        val signInParam =
            SignInParam(email = emailTest2, password = passwordTest)

        val result = authRepository.signIn(signInParam)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals((resultAsError).exception, AuthException.UnauthorizedException)
        assertEquals((resultAsError).exception.message, AuthException.UnauthorizedException.message)
    }

    /**
     * Test case: should throw email is not verified exception when signing in [AuthRepository.signIn].
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to AuthException.EmailNotVerifiedException
     *      assertEquals: result.exception.message is equals to AuthException.EmailNotVerifiedException.message*/
    @Test
    fun shouldThrowEmailIsNotVerifiedExceptionWhenSigningIn() = runBlocking {
        authRemoteDataSource.setSignedInUser(signedInNotVerifiedEmail())
        userRemoteDataSource.setSignedInUser(signedInNotVerifiedEmail())

        val signInParam = SignInParam(email = emailTest, password = passwordTest)
        val result = authRepository.signIn(signInParam)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals((resultAsError).exception, AuthException.EmailNotVerifiedException)
        assertEquals(
            (resultAsError).exception.message,
            AuthException.EmailNotVerifiedException.message
        )
    }

    /**
     * Test case: should throw user not found exception when signing in [AuthRepository.signIn].
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to UserException.UserNotFoundException
     *      assertEquals: result.exception.message is equals to UserException.UserNotFoundException.message*/
    @Test
    fun shouldThrowUserNotFoundExceptionWhenSigningIn() = runBlocking {
        authRemoteDataSource.setSignedInUser(signedInUserNotFound())

        val signInParam = SignInParam(email = emailTest, password = passwordTest)
        val result = authRepository.signIn(signInParam)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals((resultAsError).exception, UserException.UserNotFoundException)
        assertEquals((resultAsError).exception.message, UserException.UserNotFoundException.message)
    }

    /**
     * Test case: should return user when signing in [AuthRepository.signIn].
     * @assertions:
     *      assertTrue: result is Result.Success<User>
     *      assertEquals: user.email is equals to emailTest
     *      assertEquals: user.nickname is equals to "nickName1"
     *      assertEquals: user.firstName is equals to "firstName1"
     *      assertEquals: user.lastName is equals to "lastName1"*/
    @Test
    fun shouldReturnUserWhenPassStringSigningIn() = runBlocking {
        userRemoteDataSource.setSignedInUser(signedInUser())
        authRemoteDataSource.setSignedInUser(signedInUser())

        val result = authRepository.signIn(passwordTest)

        assertTrue(result is Result.Success<User>)

        val resultAsUser = result as Result.Success<User>
        assertEquals((resultAsUser).data.email, emailTest)
        assertEquals((resultAsUser).data.nickname, "nickName1")
        assertEquals((resultAsUser).data.firstName, "firstName1")
        assertEquals((resultAsUser).data.lastName, "lastName1")
    }

    /**
     * Test case: should throw unauthorized exception when signing in [AuthRepository.signIn].
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to AuthException.UnauthorizedException
     *      assertEquals: result.exception.message is equals to AuthException.UnauthorizedException.message*/
    @Test
    fun shouldThrowUnAuthorizedExceptionWhenPassStringSigningIn() = runBlocking {
        val result = authRepository.signIn(passwordTest)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals((resultAsError).exception, AuthException.UnauthorizedException)
        assertEquals((resultAsError).exception.message, AuthException.UnauthorizedException.message)
    }

    /**
     * Test case: should throw email not found exception when signing in [AuthRepository.signIn].
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to UserException.EmailNotFoundException
     *      assertEquals: result.exception.message is equals to UserException.EmailNotFoundException.message*/
    @Test
    fun shouldThrowEmailNotFoundExceptionWhenPassStringSignIn() = runBlocking {
        userRemoteDataSource.setSignedInUser(signedInEmailNotFound())

        val result = authRepository.signIn(passwordTest)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals((resultAsError).exception, UserException.EmailNotFoundException)
        assertEquals(
            (resultAsError).exception.message,
            UserException.EmailNotFoundException.message
        )
    }

    /**
     * Test case: should return success when signing up [AuthRepository.signUp].
     * @assertions:
     *      assertTrue: result is Result.Success<Unit>*/
    @Test
    fun shouldReturnSuccessWhenSigningUp() = runBlocking {
        val signUpParam = SignUpParam(email = emailTest2, password = passwordTest)
        val result = authRepository.signUp(signUpParam)

        assertTrue(result is Result.Success<Unit>)
    }

    /**
     * Test case: should throw email existed exception when signing in [AuthRepository.signUp].
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to AuthException.EmailExistedException
     *      assertEquals: result.exception.message is equals to AuthException.EmailExistedException.message*/
    @Test
    fun shouldThrowEmailExistedExceptionWhenSigningUp() = runBlocking {
        authRemoteDataSource.setSignedInUser(signedInUser())

        val signUpParam = SignUpParam(email = emailTest, password = passwordTest)
        val result = authRepository.signUp(signUpParam)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals((resultAsError).exception, AuthException.EmailExistedException)
        assertEquals((resultAsError).exception.message, AuthException.EmailExistedException.message)
    }

    /**
     * Test case: should throw unauthorized exception when signing in [AuthRepository.signUp].
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to AuthException.UnauthorizedException
     *      assertEquals: result.exception.message is equals to AuthException.UnauthorizedException.message*/
    @Test
    fun shouldThrowUnAuthorizedExceptionWhenSigningUp() = runBlocking {
        val signUpParam = SignUpParam(email = "", password = "")
        val result = authRepository.signUp(signUpParam)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals((resultAsError).exception, AuthException.UnauthorizedException)
        assertEquals((resultAsError).exception.message, AuthException.UnauthorizedException.message)
    }

    /**
     * Test case: should return user when signing in [AuthRepository.checkAuthenticatedUser].
     * @assertions:
     *      assertTrue: result is Result.Success<User>
     *      assertEquals: user.email is equals to emailTest
     *      assertEquals: user.nickname is equals to "nickName1"
     *      assertEquals: user.firstName is equals to "firstName1"
     *      assertEquals: user.lastName is equals to "lastName1"*/
    @Test
    fun shouldReturnUserRemoteWhenAuthenticationCheck() = runBlocking {
        authRemoteDataSource.setSignedInUser(signedInUser())

        val result = authRepository.checkAuthenticatedUser()

        assertTrue(result is Result.Success<User>)

        val resultAsUser = result as Result.Success<User>

        assertEquals((resultAsUser).data.email, emailTest)
        assertEquals((resultAsUser).data.nickname, "nickName1")
        assertEquals((resultAsUser).data.firstName, "firstName1")
        assertEquals((resultAsUser).data.lastName, "lastName1")
    }

    /**
     * Test case: should throw user not found exception when signing in [AuthRepository.checkAuthenticatedUser].
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to UserException.UserNotFoundException
     *      assertEquals: result.exception.message is equals to UserException.UserNotFoundException.message*/
    @Test
    fun shouldThrowUserNotFoundExceptionWhenAuthenticationCheck() = runBlocking {
        authRemoteDataSource.setSignedInUser(signedInUserNotFound())

        val result = authRepository.checkAuthenticatedUser()

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals((resultAsError).exception, UserException.UserNotFoundException)
        assertEquals((resultAsError).exception.message, UserException.UserNotFoundException.message)
    }

    /**
     * Test case: should throw unauthorized exception when signing in [AuthRepository.checkAuthenticatedUser].
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to AuthException.UnauthorizedException
     *      assertEquals: result.exception.message is equals to AuthException.UnauthorizedException.message*/
    @Test
    fun shouldThrowUnauthorizedExceptionWhenAuthenticationCheck() = runBlocking {
        val result = authRepository.checkAuthenticatedUser()

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals((resultAsError).exception, AuthException.UnauthorizedException)
        assertEquals((resultAsError).exception.message, AuthException.UnauthorizedException.message)
    }

    /**
     * Test case: should throw email not verified exception when signing in [AuthRepository.checkAuthenticatedUser].
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to AuthException.EmailNotVerifiedException
     *      assertEquals: result.exception.message is equals to AuthException.EmailNotVerifiedException.message*/
    @Test
    fun shouldThrowEmailNotVerifiedExceptionWhenAuthenticationCheck() = runBlocking {
        authRemoteDataSource.setSignedInUser(signedInNotVerifiedEmail())

        val result = authRepository.checkAuthenticatedUser()

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals((resultAsError).exception, AuthException.EmailNotVerifiedException)
        assertEquals(
            (resultAsError).exception.message,
            AuthException.EmailNotVerifiedException.message
        )
    }

    /**
     * Test case: should return success when signing up [AuthRepository.resetPassword].
     * @assertions:
     *      assertTrue: result is Result.Success<Unit>*/
    @Test
    fun shouldReturnSuccessWhenResetPasswordEmailIsFound() = runBlocking {
        authRemoteDataSource.setSignedInUser(signedInUser())
        val result = authRepository.resetPassword(emailTest)

        assertTrue(result is Result.Success<Unit>)
    }

    /**
     * Test case: should throw unauthorized exception when signing in [AuthRepository.resetPassword].
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to AuthException.UnauthorizedException
     *      assertEquals: result.exception.message is equals to AuthException.UnauthorizedException.message*/
    @Test
    fun shouldRThrowUnauthorizedExceptionWhenResetPasswordEmailNotFound() = runBlocking {
        authRemoteDataSource.setSignedInUser(signedInUser())

        val result = authRepository.resetPassword(emailTest2)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals((resultAsError).exception, AuthException.UnauthorizedException)
        assertEquals((resultAsError).exception.message, AuthException.UnauthorizedException.message)
    }

    /**
     * Test case: should return success when signing up [AuthRepository.signOut].
     * @assertions:
     *      assertTrue: result is Result.Success<Unit>*/
    @Test
    fun shouldReturnSuccessWhenUserIsSigningOut() = runBlocking {
        authRemoteDataSource.setSignedInUser(signedInUser())

        val result = authRepository.signOut()

        assertTrue(result is Result.Success<Unit>)
    }

    /**
     * Test case: should throw user not found exception when signing in [AuthRepository.signOut].
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to UserException.UserNotFoundException
     *      assertEquals: result.exception.message is equals to UserException.UserNotFoundException.message*/
    @Test
    fun shouldThrowUserNotFoundExceptionWhenIsNotSignedOut() = runBlocking {
        val result = authRepository.signOut()

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals((resultAsError).exception, UserException.UserNotFoundException)
        assertEquals(resultAsError.exception.message, UserException.UserNotFoundException.message)
    }
}