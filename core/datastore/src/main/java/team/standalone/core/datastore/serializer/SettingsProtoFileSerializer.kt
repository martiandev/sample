package team.standalone.core.datastore.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import team.standalone.core.datastore.proto.model.SettingsProtoFile
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

internal class SettingsProtoFileSerializer @Inject constructor(
) : Serializer<SettingsProtoFile> {
        override val defaultValue: SettingsProtoFile =
        SettingsProtoFile.getDefaultInstance()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): SettingsProtoFile {
        try {
            return SettingsProtoFile.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: SettingsProtoFile, output: OutputStream) =
        t.writeTo(output)
}