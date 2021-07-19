package com.ang.acb.dogbreeds.di

import com.ang.acb.dogbreeds.data.BreedsRepository
import com.ang.acb.dogbreeds.domain.BreedsGateway
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideBreedsGateway(
        breedsRepository: BreedsRepository
    ): BreedsGateway = breedsRepository
}
