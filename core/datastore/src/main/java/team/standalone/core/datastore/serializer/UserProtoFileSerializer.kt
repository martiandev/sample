package team.standalone.core.datastore.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import team.standalone.core.datastore.proto.model.UserProtoFile
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

internal class UserProtoFileSerializer @Inject constructor(
) : Serializer<UserProtoFile> {
    override val defaultValue: UserProtoFile = UserProtoFile.getDefaultInstance()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): UserProtoFile {
        try {
            return UserProtoFile.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: UserProtoFile, output: OutputStream) = t.writeTo(output)
}