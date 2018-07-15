package projetannuel.bigteam.com.appFirebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import projetannuel.bigteam.com.model.FlashLuvConversation
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.model.Quiz
import projetannuel.bigteam.com.model.QuizItem

/**
 * AppFirebaseDatabase -
 * @author guirassy
 * @version $Id$
 */

class AppFirebaseDatabase {

    val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().reference

    val usersReference : DatabaseReference = databaseReference.child("users")
    val conversationsRef : DatabaseReference = databaseReference.child("conversations")
    val conversationQuizReference : DatabaseReference = conversationsRef.child("quiz")
    val userConversations : DatabaseReference = databaseReference.child("user-conversations")

    fun saveFlashLuvUser(user: FlashLuvUser) {
        databaseReference.child("users").child(user.uid).setValue(user)
    }

    fun saveConversation(currentRef: String, conversation: FlashLuvConversation) {
        databaseReference.child("conversations").child(currentRef).setValue(conversation)
    }

    fun saveQuizItem(currentConversationRef : String, currentQuizRef: String, quiz: Quiz){
        conversationsRef.child(currentConversationRef).child(currentQuizRef).setValue(quiz)
    }

}