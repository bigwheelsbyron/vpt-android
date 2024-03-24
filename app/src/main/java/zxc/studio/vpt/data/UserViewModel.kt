package zxc.studio.vpt.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import okhttp3.Dispatcher
import zxc.studio.vpt.data.Databases.UserDatabase

class UserViewModel(application: Application):AndroidViewModel(application) {

    private val readAllData: LiveData<List<UserData>>
    private val respository: UserRepository


    init {
        val userDao = UserDatabase.getDataBase(application).userDao()
        respository = UserRepository(userDao)
        readAllData = respository.readAllData
    }




}