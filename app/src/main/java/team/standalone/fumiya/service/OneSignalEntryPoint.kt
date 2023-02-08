package team.standalone.fumiya.service

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.standalone.core.data.domain.usecase.CheckAuthenticatedUserUseCase

@EntryPoint
@InstallIn(SingletonComponent::class)
interface OneSignalEntryPoint {
    val checkAuthenticatedUserUseCase: CheckAuthenticatedUserUseCase
}