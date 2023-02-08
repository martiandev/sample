package team.standalone.core.network.util

import team.standalone.core.network.model.UserRemote

object UserRemoteTestData {

    /**generate [UserRemote] test data*/
    fun getUserRemoteList(): List<UserRemote> {
        val list = mutableListOf<UserRemote>()
        for (i in 1..10) {
            val userRemote = UserRemote(
                uid = "uid-$i",
                email = "email$i@email.com",
                icon = null,
                nickname = "nickName$i",
                firstName = "firstName$i",
                lastName = "lastName$i",
                firstNameKana = "firstKanaName$i",
                lastNameKana = "lastKanaName$i",
                memberNumber = "$i",
                phoneNumber = "1234567$i",
                gender = "male",
                withdraw = false,
                birthDay = 1,
                birthMonth = 1,
                birthYear = 2020,
                addressStructure = "address structure$i",
                addressNumber = "address number$i",
                addressCity = "address city$i",
                addressPrefecture = "prefecture$i",
                addressPostalCode = "01232$i",
                subscription = false,
                subscriptionPaused = false,
                subscriptionStartDate = "",
                subscriptionExpireDate = "",
                createdAt = null,
                updatedAt = null,
                firstBillingDate = null,
                platform = "android",
                playerId = "",
                purchaseTokenAndroid = "",
                role = ""
            )
            list.add(userRemote)
        }
        return list
    }

    /**find and return [UserRemote] test data by email*/
    fun getUserRemote(users: List<UserRemote>, email: String): UserRemote? {
        return users.find { it.email == email }
    }

    /**call for sign in successfully user*/
    fun signedInUser(
    ): FakeUser {
        return FakeUser(
            uid = "uid-1",
            email = "email1@email.com",
            password = "test1234",
            isEmailVerified = true
        )
    }

    /**call for sign in successfully user but email is not verified*/
    fun signedInNotVerifiedEmail(): FakeUser {
        return FakeUser(
            uid = "uid-1",
            email = "email1@email.com",
            password = "test1234",
            isEmailVerified = false
        )
    }

    /**call for sign in where user not found*/
    fun signedInUserNotFound(): FakeUser {
        return FakeUser(
            uid = "test-uid",
            email = "email1@email.com",
            password = "test1234",
            isEmailVerified = true
        )
    }

    /**call for sign in where email not found*/
    fun signedInEmailNotFound(): FakeUser {
        return FakeUser(
            uid = "uid-2",
            email = null,
            password = "test1234",
            isEmailVerified = false
        )
    }

    /**call for sign in user with no uid*/
    fun signedInUserNoUid(
    ): FakeUser {
        return FakeUser(
            uid = "",
            email = "email1@email.com",
            password = "test1234",
            isEmailVerified = true
        )
    }

    /**call for sign in invalid userd*/
    fun signedInInvalidUser(
    ): FakeUser {
        return FakeUser(
            uid = "test-uid",
            email = "test@email.com",
            password = "1234test",
            isEmailVerified = false
        )
    }
}