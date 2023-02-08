package team.standalone.core.data.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import team.standalone.core.common.qualifier.DefaultDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.qualifier.MainDispatcher
import team.standalone.core.common.qualifier.MainImmediateDispatcher

@Module
@InstallIn(SingletonComponent::class)
internal object CoroutineModule {
    @DefaultDispatcher
    @Provides
    fun defaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun mainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @MainImmediateDispatcher
    @Provides
    fun mainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate
}