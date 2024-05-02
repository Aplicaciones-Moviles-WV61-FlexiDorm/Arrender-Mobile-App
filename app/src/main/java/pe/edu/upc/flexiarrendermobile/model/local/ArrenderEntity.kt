package pe.edu.upc.flexiarrendermobile.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "arrender")
data class ArrenderEntity(
    @PrimaryKey val id: String,
    val username: String,
    val firstname: String,
    val lastname: String,
    val phoneNumber: String,
    val email: String,
    val address: String,
    val birthDate: String,
    val profilePicture: String,
    val gender: String?,
    val token:String,
    val verifier: Boolean,
)
