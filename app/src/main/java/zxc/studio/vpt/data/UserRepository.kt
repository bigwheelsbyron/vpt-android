package zxc.studio.vpt.data

import androidx.lifecycle.LiveData
import zxc.studio.vpt.data.DAOs.UserDAO

class UserRepository(private val userDAO: UserDAO) {

    val readAllData: LiveData<List<UserData>> = userDAO.readAllData()

    suspend fun addUser(user:UserData){
        userDAO.addUser(user)
    }

}