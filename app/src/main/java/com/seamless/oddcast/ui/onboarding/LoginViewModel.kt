package com.seamless.oddcast.ui.onboarding

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.seamless.oddcast.ui.onboarding.AuthRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor (
    application: Application,
    val authRepository: AuthRepository,
) : AndroidViewModel(application) {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    var request_code = 1234
    var firebaseAuth = FirebaseAuth.getInstance()

    private lateinit var googleAccount: GoogleSignInAccount

    private var existingCode = ""


    private val mContext: Context = application.applicationContext

    // LiveData for data loading progress indication
    val loading = MutableLiveData<Boolean>()


    //livedata for getting network status
    val networkStatusLiveData: LiveData<Boolean>
        get() = networkStatusMutableLiveData
    private val networkStatusMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()





}