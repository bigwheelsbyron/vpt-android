package zxc.studio.vpt.data

import androidx.room.Embedded
import androidx.room.PrimaryKey
import zxc.studio.vpt.models.Entity
import zxc.studio.vpt.models.User


@Entity()
data class UserData(
        @PrimaryKey
        val firebase_id: String,
        @Embedded
        val user: User
) {
}