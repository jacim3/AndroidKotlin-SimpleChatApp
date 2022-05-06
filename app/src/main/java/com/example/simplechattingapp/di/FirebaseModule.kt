package com.example.simplechattingapp.di

import com.example.simplechattingapp.constants.Option
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class FirebaseModule {


    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseRealtimeDB(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    fun provideUserDB(database: FirebaseDatabase): DatabaseReference = database.getReference(Option.DB_NAME)
}