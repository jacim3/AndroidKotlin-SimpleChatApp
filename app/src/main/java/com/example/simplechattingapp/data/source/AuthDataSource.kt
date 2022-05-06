package com.example.simplechattingapp.data.source

import android.annotation.SuppressLint
import android.database.Observable
import android.util.Log
import com.example.simplechattingapp.data.model.LoggedInUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
@Singleton
class AuthDataSource @Inject constructor(
    private val auth: FirebaseAuth
) {

    fun signUp(userName: String, password: String): Task<AuthResult> = auth.createUserWithEmailAndPassword(userName, password)


    fun signIn(userName: String, password: String) = auth.signInWithEmailAndPassword(userName, password)


    fun logout() {
        auth.signOut()
    }

    fun getUserEmail() = auth.currentUser?.email
}