package team.standalone.core.data.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import team.standalone.core.common.exception.AuthException.*
import team.standalone.core.common.exception.UserException.EmailNotFoundException
import team.standalone.core.common.exception.UserException.UserNotFoundException
import team.standalone.core.common.util.Result
import team.standalone.core.data.data.mapper.UserEntityMapper
import team.standalone.core.data.data.mapper.UserRemoteMapper
import team.standalone.core.data.data.repository.AuthRepositoryImpl
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.model.param.SignInParam
import team.standalone.core.data.domain.repository.AuthRepository
import team.standalone.core.data.util.fake.FakeAuthRemoteDataSource
import team.standalone.core.data.util.fake.FakeUserDao
import team.standalone.core.data.util.fake.FakeUserLocalDataSource
import team.standalone.core.data.util.fake.FakeUserRemoteDataSource
import team.standalone.core.network.util.UserRemoteTestData
import team.standalone.core.network.util.UserRemoteTestData.signedInEmailNotFound
import team.standalone.core.network.util.UserRemoteTestData.signedInInvalidUser
import team.standalone.core.network.util.UserRemoteTestData.signedInNotVerifiedEmail
import team.standalone.core.network.util.UserRemoteTestData.signedInUser
import team.standalone.core.network.util.UserRemoteTestData.signedInUserNotFound

@ExperimentalCoroutinesApi
class SignInUseCaseTest {
    private lateinit var signInUseCase: SignInUseCase
    private lateinit var authRepository: AuthRepository

    private lateinit var fakeUserRemoteDataSource: FakeUserRemoteDataSource
    private lateinit var fakeAuthRemoteDataSource: FakeAuthRemoteDataSource

    private val emailTest = "email1@email.com"
    private val emailTest2 = "test@test.com"
    private val passwordTest = "test1234"
    private val passwordTest2 = "1234test"

    @Before
    fun setUp() {
        fakeUserRemoteDataSource = FakeUserRemoteDataSource(
            UnconfinedTestDispatcher(), UserRemoteTestData.getUserRemoteList()
        )

        fakeAuthRemoteDataSource = FakeAuthRemoteDataSource(
            UnconfinedTestDispatcher(), fakeUserRemoteDataSource
        )

        authRepository = AuthRepositoryImpl(
            UnconfinedTestDispatcher(), fakeAuthRemoteDataSource, fakeUserRemoteDataSource,
            FakeUserLocalDataSource(UnconfinedTestDispatcher(), FakeUserDao()),
            UserRemoteMapper(), UserEntityMapper()
        )

        signInUseCase = SignInUseCase(
            ioDispatcher = UnconfinedTestDispatcher(),
            authRepository = authRepository
        )
    }

    /**
     * Test case: should return [User] object when calling [SignInUseCase] passing password string.
     * @assertions:
     *      assertTrue: result is Result.Success<User>
     *      assertEquals: user.email is equals to emailTest
     *      assertEquals: user.nickname is equals to "nickName1"
     *      assertEquals: user.firstName is equals to "firstName1"
     *      assertEquals: user.lastName is equals to "lastName1"*/
    @Test
    fun shouldReturnUserWhenSigningInUsingPassword() = runBlocking {
        fakeAuthRemoteDataSource.setSignedInUser(signedInUser())
        fakeUserRemoteDataSource.setSignedInUser(signedInUser())

        val result = signInUseCase.invoke(passwordTest)

        assertTrue(result is Result.Success<User>)

        val resultAsUser = result as Result.Success<User>
        assertEquals(resultAsUser.data.email, emailTest)
        assertEquals(resultAsUser.data.nickname, "nickName1")
        assertEquals(resultAsUser.data.firstName, "firstName1")
        assertEquals(resultAsUser.data.lastName, "lastName1")
    }

    /**
     * Test case: should throw unauthorized exception when calling [SignInUseCase] passing password string.
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to AuthException.UnauthorizedException
     *      assertEquals: result.exception.message is equals to AuthException.UnauthorizedException.message*/
    @Test
    fun shouldThrowUnAuthorizedExceptionWhenSigningInUsingPassword() = runBlocking {
        val result = signInUseCase.invoke(passwordTest)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals(resultAsError.exception, UnauthorizedException)
        assertEquals(resultAsError.exception.message, UnauthorizedException.message)
    }

    /**
     * Test case: should throw email not found exception when calling [SignInUseCase] passing password string.
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to UserException.EmailNotFoundException
     *      assertEquals: result.exception.message is equals to UserException.EmailNotFoundException.message*/
    @Test
    fun shouldThrowEmailNotFoundExceptionWhenSigningInUsingPassword() = runBlocking {
        fakeUserRemoteDataSource.setSignedInUser(signedInEmailNotFound())

        val result = signInUseCase.invoke(passwordTest)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals(resultAsError.exception, EmailNotFoundException)
        assertEquals(resultAsError.exception.message, EmailNotFoundException.message)
    }

    /**
     * Test case: should throw invalid credentials exception when calling [SignInUseCase] passing password string.
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to AuthException.InvalidCredentialsException
     *      assertEquals: result.exception.message is equals to AuthException.InvalidCredentialsException.message*/
    @Test
    fun shouldThrowInvalidCredentialsExceptionWhenSigningInUsingPassword() = runBlocking {
        fakeAuthRemoteDataSource.setSignedInUser(signedInUser())
        fakeUserRemoteDataSource.setSignedInUser(signedInUser())

        val result = signInUseCase.invoke(passwordTest2)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals(resultAsError.exception, InvalidCredentialsException)
        assertEquals(resultAsError.exception.message, InvalidCredentialsException.message)
    }

    /**
     * Test case: should throw invalid user exception when calling [SignInUseCase] passing password string.
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to AuthException.InvalidUserException
     *      assertEquals: result.exception.message is equals to AuthException.InvalidUserException.message*/
    @Test
    fun shouldReturnInvalidUserExceptionWhenSigningInUsingPassword() = runBlocking {
        fakeAuthRemoteDataSource.setSignedInUser(signedInInvalidUser())
        fakeUserRemoteDataSource.setSignedInUser(signedInUser())

        val result = signInUseCase.invoke(passwordTest)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals(resultAsError.exception, InvalidUserException)
        assertEquals(resultAsError.exception.message, InvalidUserException.message)
    }

    /**
     * Test case: should throw email is not verified exception when calling [SignInUseCase] passing password string.
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to AuthException.EmailNotVerifiedException
     *      assertEquals: result.exception.message is equals to AuthException.EmailNotVerifiedException.message*/
    @Test
    fun shouldThrowEmailIsNotVerifiedExceptionWhenSigningInUsingPassword() = runBlocking {
        fakeAuthRemoteDataSource.setSignedInUser(signedInNotVerifiedEmail())
        fakeUserRemoteDataSource.setSignedInUser(signedInNotVerifiedEmail())

        val result = signInUseCase.invoke(passwordTest)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals(resultAsError.exception, EmailNotVerifiedException)
        assertEquals(resultAsError.exception.message, EmailNotVerifiedException.message)
    }

    /**
     * Test case: should return [User] object when calling [SignInUseCase] passing [SignInParam] object.
     * @assertions:
     *      assertTrue: result is Result.Success<User>
     *      assertEquals: user.email is equals to emailTest
     *      assertEquals: user.nickname is equals to "nickName1"
     *      assertEquals: user.firstName is equals to "firstName1"
     *      assertEquals: user.lastName is equals to "lastName1"*/
    @Test
    fun shouldReturnUserWhenSigningIn() = runBlocking {
        fakeAuthRemoteDataSource.setSignedInUser(signedInUser())

        val signInParam = SignInParam(email = emailTest, password = passwordTest)
        val result = signInUseCase.invoke(signInParam)

        assertTrue(result is Result.Success<User>)

        val resultAsUser = result as Result.Success<User>
        assertEquals(resultAsUser.data.email, emailTest)
        assertEquals(resultAsUser.data.nickname, "nickName1")
        assertEquals(resultAsUser.data.firstName, "firstName1")
        assertEquals(resultAsUser.data.lastName, "lastName1")
    }

    /**
     * Test case: should throw invalid credentials exception when calling [SignInUseCase] passing [SignInParam] object.
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to AuthException.InvalidCredentialsException
     *      assertEquals: result.exception.message is equals to AuthException.InvalidCredentialsException.message*/
    @Test
    fun shouldThrowInvalidCredentialsExceptionWhenSigningIn() = runBlocking {
        fakeAuthRemoteDataSource.setSignedInUser(signedInUser())

        val signInParam = SignInParam(email = emailTest, password = passwordTest2)
        val result = signInUseCase.invoke(signInParam)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals(resultAsError.exception, InvalidCredentialsException)
        assertEquals(resultAsError.exception.message, InvalidCredentialsException.message)
    }

    /**
     * Test case: should throw invalid user exception when calling [SignInUseCase] passing [SignInParam] object.
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to AuthException.InvalidUserException
     *      assertEquals: result.exception.message is equals to AuthException.InvalidUserException.message*/
    @Test
    fun shouldReturnInvalidUserExceptionWhenSigningIn() = runBlocking {
        fakeAuthRemoteDataSource.setSignedInUser(signedInUser())

        val signInParam = SignInParam(email = emailTest2, password = passwordTest2)
        val result = signInUseCase.invoke(signInParam)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals(resultAsError.exception, InvalidUserException)
        assertEquals(resultAsError.exception.message, InvalidUserException.message)
    }

    /**
     * Test case: should throw unauthorized exception when calling [SignInUseCase] passing [SignInParam] object.
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to AuthException.UnauthorizedException
     *      assertEquals: result.exception.message is equals to AuthException.UnauthorizedException.message*/
    @Test
    fun shouldThrowUnAuthorizedExceptionWhenSigningIn() = runBlocking {
        val signInParam = SignInParam(email = emailTest2, password = passwordTest)

        val result = signInUseCase.invoke(signInParam)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals(resultAsError.exception, UnauthorizedException)
        assertEquals(resultAsError.exception.message, UnauthorizedException.message)
    }

    /**
     * Test case: should throw email is not verified exception when calling [SignInUseCase] passing [SignInParam] object.
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to AuthException.EmailNotVerifiedException
     *      assertEquals: result.exception.message is equals to AuthException.EmailNotVerifiedException.message*/
    @Test
    fun shouldThrowEmailIsNotVerifiedExceptionWhenSigningIn() = runBlocking {
        fakeAuthRemoteDataSource.setSignedInUser(signedInNotVerifiedEmail())
        fakeUserRemoteDataSource.setSignedInUser(signedInNotVerifiedEmail())

        val signInParam = SignInParam(email = emailTest, password = passwordTest)
        val result = signInUseCase.invoke(signInParam)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals(resultAsError.exception, EmailNotVerifiedException)
        assertEquals(resultAsError.exception.message, EmailNotVerifiedException.message)
    }

    /**
     * Test case: should throw user not found exception when calling [SignInUseCase] passing [SignInParam] object.
     * @assertions:
     *      assertTrue: result is Result.Error
     *      assertEquals: result.exception is equals to UserException.UserNotFoundException
     *      assertEquals: result.exception.message is equals to UserException.UserNotFoundException.message*/
    @Test
    fun shouldThrowUserNotFoundExceptionWhenSigningIn() = runBlocking {
        fakeAuthRemoteDataSource.setSignedInUser(signedInUserNotFound())

        val signInParam = SignInParam(email = emailTest, password = passwordTest)
        val result = signInUseCase.invoke(signInParam)

        assertTrue(result is Result.Error)

        val resultAsError = result as Result.Error
        assertEquals(resultAsError.exception, UserNotFoundException)
        assertEquals(resultAsError.exception.message, UserNotFoundException.message)
    }
}