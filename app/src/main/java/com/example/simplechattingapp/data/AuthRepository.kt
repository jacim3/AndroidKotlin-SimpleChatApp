package com.example.simplechattingapp.data

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.simplechattingapp.data.model.LoggedInUser
import com.example.simplechattingapp.data.source.AuthDataSource
import com.example.simplechattingapp.presentation.LobbyActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers.io
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

@Singleton
class AuthRepository @Inject constructor(
    @ApplicationContext private val  context: Context,
    private val dataSource: AuthDataSource
) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(userName: String, password: String): Disposable {
        Log.e("login()", "passed")
        return Single.create<Result<LoggedInUser>?> {

            dataSource.signUp(userName, password).addOnCompleteListener { task ->
                Log.e("sadfasdf", task.isSuccessful.toString())
                when (task.isSuccessful) {
                    true -> task.result?.let { result ->
                        it.onSuccess(Result.Success(LoggedInUser(userName)))
                    }
                    false -> task.exception?.let { result ->
                        it.onError(result)
                    }
                }
            }
        }.observeOn(io()).subscribeOn(
            AndroidSchedulers.mainThread()
        ).subscribe(
            {  it ->
                dataSource.signIn(userName, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "안녕하세요 ${userName}님", Toast.LENGTH_SHORT).show()
                        context.startActivity(Intent(context, LobbyActivity::class.java))
                    } else {
                        Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            {
                dataSource.signIn(userName, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "안녕하세요 ${userName}님", Toast.LENGTH_SHORT).show()
                        context.startActivity(Intent(context, LobbyActivity::class.java))
                    } else {
                        Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}