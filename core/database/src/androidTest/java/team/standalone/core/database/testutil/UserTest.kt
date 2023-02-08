package team.standalone.core.database.testutil

import kotlinx.datetime.Instant
import team.standalone.core.database.room.model.entity.UserEntity
import team.standalone.core.database.room.model.vo.AddressVO
import team.standalone.core.database.room.model.vo.BirthdateVO
import team.standalone.core.database.room.model.vo.SubscriptionVO
import java.util.*

open class UserTest {

    /**
     * Generates a test UserEntity
     * @param id - id the UserEntity will use to append to all
     * fields
     * @return UserEntity with the uid test<id>
     */
    fun getTestUser(
        id:Int
    ): UserEntity {
        var instant = Instant.fromEpochMilliseconds(Date().time)
        var user = UserEntity(
            uid = "test"+id,
            email = "test@mailinator.com"+id,
            firstName = "Testfirstname"+id,
            lastName = "Testlastname"+id,
            firstNameKana = ""+id,
            lastNameKana = ""+id,
            phoneNumber = ""+id,
            gender = "male"+id,
            birthdate = BirthdateVO(
                day = id,
                month = 1,
                year = 2000
            ),
            address = AddressVO(
                structure = ""+id,
                number = ""+id,
                city = ""+id,
                prefecture = ""+id,
                postalCode = ""+id
            ),
            subscription = SubscriptionVO(
                subscribed = true,
                paused = false,
                firstBillingDate = instant,
                startDate = instant,
                expireDate = null
            ),
            nickname = "user"+id,
            memberNumber = "memberNo:"+id,
            photoUrl = "user"+id,
            withdraw = false,
            createdAt = instant,
            updatedAt = instant
        )
        return user
    }

    /**
     * Generates an updated test UserEntity
     * @param id - id the UserEntity will use to append to all
     * fields
     * @return UserEntity with the uid test<id>
     */
    fun getUpdatedUser(
        id:Int
    ): UserEntity {
        var updatedPrefix ="updated_"
        var instant = Instant.fromEpochMilliseconds(Date().time)
        var user = UserEntity(
            uid = "test"+id,
            email = updatedPrefix+"test@mailinator.com"+id,
            firstName = updatedPrefix+"Testfirstname"+id,
            lastName = updatedPrefix+"Testlastname"+id,
            firstNameKana = updatedPrefix+""+id,
            lastNameKana = updatedPrefix+""+id,
            phoneNumber = updatedPrefix+""+id,
            gender = updatedPrefix+"male"+id,
            birthdate = BirthdateVO(
                day = id,
                month = 1,
                year = 2000
            ),
            address = AddressVO(
                structure = updatedPrefix+""+id,
                number = updatedPrefix+""+id,
                city = updatedPrefix+""+id,
                prefecture = updatedPrefix+""+id,
                postalCode = updatedPrefix+""+id
            ),
            subscription = SubscriptionVO(
                subscribed = true,
                paused = false,
                firstBillingDate = instant,
                startDate = instant,
                expireDate = null
            ),
            nickname = updatedPrefix+"user"+id,
            memberNumber = updatedPrefix+"memberNo:"+id,
            photoUrl = updatedPrefix+"user"+id,
            withdraw = false,
            createdAt = instant,
            updatedAt = instant
        )
        return user
    }


    /**
     * Generates a list of test UserEntity
     * @param count - number of test UserEntity to be generated
     * @return Return a list of test UserEntity
     */
    fun generateListOfTestUsers(
        count:Int
    ): List<UserEntity>{
        var lists = ArrayList<UserEntity>()
        for(i in 1..count){
            lists.add(getTestUser(i))
        }
        return lists
    }

    /**
     * Generates a list of updated test UserEntity
     * @param count - number of updated test UserEntity to be generated
     * @return Return a list of updated test UserEntity
     */
    fun generateListOfUpdatedTestUsers(
        count:Int
    ): List<UserEntity>{
        var lists = ArrayList<UserEntity>()
        for(i in 1..count){
            lists.add(getUpdatedUser(i))
        }
        return lists
    }
}