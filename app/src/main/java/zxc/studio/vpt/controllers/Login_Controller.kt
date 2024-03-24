package zxc.studio.vpt.controllers

import zxc.studio.vpt.API.FirebaseAPI
import zxc.studio.vpt.ui.login.LoginActivity

class Login_Controller(context: LoginActivity) {

    private var context = context
    private var firebaseAPI = FirebaseAPI()

    fun login(email: String, password: String) {
        firebaseAPI.login(email,password,context)
    }

    fun forgot_password(email: String){
        firebaseAPI.forgotPassword(email, context)
    }

}