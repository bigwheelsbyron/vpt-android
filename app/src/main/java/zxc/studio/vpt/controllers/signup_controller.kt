package zxc.studio.vpt.controllers

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import zxc.studio.vpt.API.FirebaseAPI
import zxc.studio.vpt.data.CacheInterface
import zxc.studio.vpt.sign_up

class SignUpController(context: sign_up) {
    private val firebaseAPI = FirebaseAPI()
    private val cacheInterface = CacheInterface()


    fun fetchTOCs(): Task<DocumentSnapshot> {
        return firebaseAPI.getTOCs()
    }

    fun signup(email: String, password: String, first: String, last: String): Boolean? {
        val result = firebaseAPI.signup (email, password, first, last)
        val sucess = result[0]
        val id =  result[1]
        if (sucess){
            cacheInterface.loggedInUser(id, email)
        }
        return sucess
    }



}
