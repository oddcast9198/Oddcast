package com.seamless.oddcast.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.seamless.oddcast.R
import com.seamless.oddcast.databinding.FragmentLoginBinding
import com.seamless.oddcast.utils.TakePhoneNumberBottomSheetFragmentDialog


class LoginFragment : Fragment(), View.OnClickListener,
    TakePhoneNumberBottomSheetFragmentDialog.PhoneNumberBottomSheetListener {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val mViewModel: LoginViewModel by viewModels()

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    var request_code = 1234
    var firebaseAuth = FirebaseAuth.getInstance()

    private lateinit var googleAccount: GoogleSignInAccount

    private var existingCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(requireActivity())

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenerInitialization()
    }



    private fun listenerInitialization(){
        binding.btnSignInWithGoogle.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){

            R.id.btnSignInWithGoogle ->{
              signInGoogle()
            }

        }
    }



    fun setObservers(){
        mViewModel.loading.observe(viewLifecycleOwner, androidx.lifecycle.Observer { it ->
            binding.progressBarLoading.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

   fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, request_code)
    }

    private fun signOutGoogle() {
        mGoogleSignInClient.signOut().addOnCompleteListener {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == request_code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }


    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(requireActivity(), e.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                googleAccount = account

//                childFragmentManager?.let {
//                    TakePhoneNumberBottomSheetFragmentDialog(this).apply {
//                        show(it, tag)
//                    }
//                }
            }
        }
    }

    override fun onSavePhoneNumberListener(mobileNumber: String) {

    }
}