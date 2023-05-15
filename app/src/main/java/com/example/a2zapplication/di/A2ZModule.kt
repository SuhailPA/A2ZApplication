package com.example.a2zapplication.di

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.a2zapplication.data.roomDB.A2ZDatabase
import com.example.a2zapplication.data.roomDB.dao.UserDao
import com.example.a2zapplication.repository.addUserDetails.UserDetailsRepo
import com.example.a2zapplication.repository.addUserDetails.UserDetailsRepoImp
import com.example.a2zapplication.repository.login.AuthRepository
import com.example.a2zapplication.repository.login.BaseAuthRepository
import com.example.a2zapplication.repository.login.firebaseAuthenticator.BaseAuthenticator
import com.example.a2zapplication.repository.login.firebaseAuthenticator.FirebaseAuthenticator
import com.example.a2zapplication.repository.login.googleAuthenticator.BaseGoogleAuthenticator
import com.example.a2zapplication.repository.login.googleAuthenticator.GoogleAuthenticator
import com.example.a2zapplication.repository.requestAccess.BaseDbAccess
import com.example.a2zapplication.repository.requestAccess.FirestoreDbAccess
import com.example.a2zapplication.repository.requestAccess.firebaseDbAccess.FirebaseDbAuthImp
import com.example.a2zapplication.repository.requestAccess.firebaseDbAccess.FirebaseDbAuthenticator
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object A2ZModule {

    @Provides
    @Singleton
    fun providesBaseAuthenticator(db: FirebaseFirestore, auth: FirebaseAuth): BaseAuthenticator {
        return FirebaseAuthenticator(db, auth)
    }

    @Provides
    @Singleton
    fun provideGoogleBasAuthenticator(
        context: Context,
        oneTapClient: SignInClient
    ): BaseGoogleAuthenticator {
        return GoogleAuthenticator(context, oneTapClient)
    }

    @Provides
    @Singleton
    fun providesBaseRepository(
        baseAuthenticator: BaseAuthenticator,
        baseGoogleAuthenticator: BaseGoogleAuthenticator
    ): BaseAuthRepository {
        return AuthRepository(baseAuthenticator, baseGoogleAuthenticator)
    }

    @Provides
    @Singleton
    fun providesContext(application: Application): Context {
        return application.applicationContext
    }


    @Provides
    @Singleton
    fun provideGoogleOneTapClient(context: Context): SignInClient {
        return Identity.getSignInClient(context)
    }

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun providesFirebaseDBAccess(
        db: FirebaseFirestore,
        auth: FirebaseAuth
    ): FirebaseDbAuthenticator {
        return FirebaseDbAuthImp(db, auth)
    }

    @Provides
    @Singleton
    fun providesBaseDBAccess(firebaseDBAuthenticator : FirebaseDbAuthenticator) : BaseDbAccess{
        return FirestoreDbAccess(firebaseDBAuthenticator)
    }

    @Provides
    @Singleton
    fun providesLocalDB(@ApplicationContext context: Context) : A2ZDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            A2ZDatabase::class.java,
            "MainDatabase",
        ).build()
    }

    @Provides
    @Singleton
    fun providesDao(a2ZDatabase: A2ZDatabase) : UserDao{
        return a2ZDatabase.userDao()
    }

    @Provides
    @Singleton
    fun providesUserDetailsRepo(db: FirebaseFirestore) : UserDetailsRepo {
        return UserDetailsRepoImp(db)
    }
}