package zxc.studio.vpt.data.DAOs

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import zxc.studio.vpt.data.UserData

@Dao
interface UserDAO {

    @Insert()
    fun addUser(userdata: UserData)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<UserData>>

}