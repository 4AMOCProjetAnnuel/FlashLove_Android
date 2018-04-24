package projetannuel.bigteam.com.appFirebase

import com.google.firebase.database.FirebaseDatabase
import projetannuel.bigteam.com.model.FlashLuvUser

/**
 * AppFirebaseDatabase -
 * @author guirassy
 * @version $Id$
 */

class AppFirebaseDatabase {

    private val databaseReference = FirebaseDatabase.getInstance().reference

    fun saveFlashLuvUser(user: FlashLuvUser) {
        val key = databaseReference.child("users").push().key
        databaseReference.child("users").child(key).setValue(user)
    }

}