package com.example.a2zapplication.di

import com.example.a2zapplication.repository.login.AuthRepository
import com.example.a2zapplication.repository.login.BaseAuthRepository
import com.example.a2zapplication.repository.login.firebaseAuthenticator.BaseAuthenticator
import com.example.a2zapplication.repository.login.firebaseAuthenticator.FirebaseAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object A2ZModule {

    @Provides
    @Singleton
    fun providesBaseAuthenticator() : BaseAuthenticator {
        return FirebaseAuthenticator()
    }

    @Provides
    @Singleton
    fun providesBaseRepository(baseAuthenticator: BaseAuthenticator) : BaseAuthRepository {
        return AuthRepository(baseAuthenticator)
    }
}