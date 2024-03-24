package zxc.studio.vpt.data.Databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import zxc.studio.vpt.data.DAOs.UserDAO
import zxc.studio.vpt.data.UserData

@Database(entities = [UserData::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {

    abstract  fun userDao(): UserDAO

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDataBase(context: Context): UserDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }


    }



}