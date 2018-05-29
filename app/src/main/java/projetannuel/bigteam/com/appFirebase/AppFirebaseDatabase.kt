package projetannuel.bigteam.com.appFirebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import projetannuel.bigteam.com.model.FlashLuvUser

/**
 * AppFirebaseDatabase -
 * @author guirassy
 * @version $Id$
 */

class AppFirebaseDatabase {

    val databaseReference = FirebaseDatabase.getInstance().reference

    val usersReference : DatabaseReference = databaseReference.child("users")

    fun saveFlashLuvUser(user: FlashLuvUser) {
        databaseReference.child("users").child(user.uid).setValue(user)
    }

}