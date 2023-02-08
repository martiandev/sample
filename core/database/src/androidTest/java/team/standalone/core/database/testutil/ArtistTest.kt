package team.standalone.core.database.testutil

import kotlinx.datetime.Instant
import team.standalone.core.database.room.model.entity.ArtistEntity
import java.util.*
import kotlin.collections.ArrayList

open class ArtistTest {

    /**
     * Generates a test ArtistEntity
     * @param id - id the Artist will use to append to all
     * fields
     * @return ArtistEntity with the uid test<id>
     */
    fun getTestArtist(
        id:Int
    ): ArtistEntity {
        var instant = Instant.fromEpochMilliseconds(Date().time)
        var artist = ArtistEntity(
            uid = "test"+id,
            chatColor = "#ffffff"+id,
            icon = "http://"+id,
            nickname = "artest"+id,
            createdAt = instant,
            updatedAt = instant
        )
        return artist
    }

    /**
     * Generates a test ArtistEntity to simulate updated Artist
     * @param id - id the Artist will use to append to all
     * fields
     * @return ArtistEntity with the uid test<id>
     */
    fun getUpdatedArtist(
        id:Int
    ): ArtistEntity {
        var instant = Instant.fromEpochMilliseconds(Date().time)
        var artist = ArtistEntity(
            uid = "test"+id,
            chatColor = "udpated-#ffffff"+id,
            icon = "udpated-http://"+id,
            nickname = "udpated-artest"+id,
            createdAt = instant,
            updatedAt = instant
        )
        return artist
    }

    /**
     * Generates a list of test ArtistEntity
     * @param count - number of test ArtistEntity to be generated
     * @return Return a list of test ArtistEntity
     */
    fun generateListOfTestArtists(
        count:Int
    ): List<ArtistEntity>{
        var lists = ArrayList<ArtistEntity>()
        for(i in 1..count){
            lists.add(getTestArtist(i))
        }
        return lists
    }

    /**
     * Generates a list of updated test ArtistEntity
     * @param count - number of updated test ArtistEntity to be generated
     * @return Return a list of updated test ArtistEntity
     */
    fun generateListOfUpdatedTestArtists(
        count:Int
    ): List<ArtistEntity>{
        var lists = ArrayList<ArtistEntity>()
        for(i in 1..count){
            lists.add(getUpdatedArtist(i))
        }
        return lists
    }

}