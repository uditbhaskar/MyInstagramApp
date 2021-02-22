 package com.mindorks.bootcamp.instagram.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import com.mindorks.bootcamp.instagram.R
import com.mindorks.bootcamp.instagram.di.component.ActivityComponent
import com.mindorks.bootcamp.instagram.ui.base.BaseActivity
import com.mindorks.bootcamp.instagram.ui.dummy.DummyActivity
import com.mindorks.bootcamp.instagram.utils.common.Status
import kotlinx.android.synthetic.main.activity_login.*

 class LoginActivity :BaseActivity<LoginViewModel>(){

    companion object{
        const val TAG = "LoginActivity"
    }

    override fun provideLayoutId(): Int = R.layout.activity_login

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
        et_email.addTextChangedListener(object :TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){
                viewModel.onEmailChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        et_password.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){
                viewModel.onPasswordChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        bt_login.setOnClickListener { viewModel.onLogin() }
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.launchDummy.observe(this, Observer {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext,DummyActivity::class.java))
                finish()
            }
        })

        viewModel.emailField.observe(this,Observer{
            if (et_email.text.toString() != it.toString()) et_email.setText(it)
        })

        viewModel.emailValidation.observe(this, Observer {
            when(it.status){
                Status.ERROR-> layout_email.error = it.data?.run {getString(this)}
                else -> layout_email.isErrorEnabled = false
            }
        })

        viewModel.passwordField.observe(this,Observer{
            if (et_password.text.toString() != it.toString()) et_password.setText(it)
        })

        viewModel.passwordValidation.observe(this, Observer {
            when(it.status){
                Status.ERROR-> layout_password.error = it.data?.run {getString(this)}
                else -> layout_password.isErrorEnabled = false
            }
        })

        viewModel.loggingIn.observe(this, Observer {
            pb_loading.visibility = if (it) View.VISIBLE else View.GONE
        })
    }


}