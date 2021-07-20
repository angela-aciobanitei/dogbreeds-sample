package com.ang.acb.dogbreeds.di

import com.ang.acb.dogbreeds.FakeBreedsRepository
import com.ang.acb.dogbreeds.domain.BreedsGateway
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

/**
 * Repository binding to use in tests.
 *
 * Hilt will inject a fake repository instead of the real one.
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepoModule::class]
)
abstract class TestRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindRepository(repo: FakeBreedsRepository): BreedsGateway
}
