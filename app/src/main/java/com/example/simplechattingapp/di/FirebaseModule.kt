package com.example.simplechattingapp.di

import com.example.simplechattingapp.constants.Option
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier


@InstallIn(SingletonComponent::class)
@Module
class FirebaseModule {


    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseRealtimeDB(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    fun provideChatDB(database: FirebaseDatabase): DatabaseReference = database.getReference(Option.DB_NAME)

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ChatDB

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class WebConnected

}