package com.bmatjik.pokedex.core.remote.di

import com.bmatjik.pokedex.core.remote.RemoteDataSource
import com.bmatjik.pokedex.core.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RemoteDataSourceModule {
    @Binds
    abstract fun bindsRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource
}