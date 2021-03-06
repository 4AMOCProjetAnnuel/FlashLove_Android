package projetannuel.bigteam.com.feat.flirt

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import projetannuel.bigteam.com.appFirebase.AppFirebaseDatabase
import projetannuel.bigteam.com.feat.flirt.model.FlirtViewModel
import projetannuel.bigteam.com.model.FlashLuvConversation
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.model.QuizItem
import projetannuel.bigteam.com.mvp.AppMvpPresenter
import projetannuel.bigteam.com.navigation.AppNavigator
import java.util.Date

/**
 * FlirtPresenter -
 * @author guirassy
 * @version $Id$
 */

class FlirtPresenter(view: FlirtContract.View,
        navigator: AppNavigator,
        private val appFirebaseDatabase: AppFirebaseDatabase,
        private val flashedUserId: String,
        private val flashingUserId: String,
        private val isFromMessaging: Boolean) :
        AppMvpPresenter<AppNavigator, FlirtContract.View>(view, navigator),
        FlirtContract.Presenter {


    private lateinit var flashedUser: FlashLuvUser
    private val flashedUserQuery = appFirebaseDatabase.usersReference.child(flashedUserId)

    private lateinit var flashingUser: FlashLuvUser
    private lateinit var flirtViewModels: MutableList<FlirtViewModel>

    private var currentConversationRef: String? = null
    private var conversation = FlashLuvConversation()


    override fun resume() {

        appFirebaseDatabase.usersReference.child(flashedUserId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {}
                    override fun onDataChange(snap: DataSnapshot?) {
                        snap?.let {
                            it.getValue(FlashLuvUser::class.java)?.let {

                                flashedUser = it

                                view.setFlashedUserInfo(it)

                                flashedUserQuery.addChildEventListener(flashedUSerSensorValuesListener)

                                appFirebaseDatabase.usersReference.child(flashingUserId)
                                        .addListenerForSingleValueEvent(object : ValueEventListener {
                                            override fun onCancelled(p0: DatabaseError?) {}
                                            override fun onDataChange(snap: DataSnapshot?) {
                                                snap?.let {
                                                    it.getValue(FlashLuvUser::class.java)?.let {

                                                        flashingUser = it

                                                        currentConversationRef = appFirebaseDatabase.conversationsRef.push().key

                                                        if (!isFromMessaging) {
                                                            createDBConversation()
                                                            createDBQuiz()
                                                        } else {
                                                            loadConversation()
                                                        }
                                                    }
                                                }
                                            }
                                        })
                            }
                        }
                    }
                })
    }


    override fun reloadFlashedUserWithSensorValues() {

        appFirebaseDatabase.usersReference.child(flashedUserId)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError?) {}

                    override fun onDataChange(snap: DataSnapshot?) {
                        snap?.let {
                            it.getValue(FlashLuvUser::class.java)?.let {
                                flashedUser = it


                                appFirebaseDatabase.conversationsRef
                                        .child(currentConversationRef)
                                        .child("recordedHeartBeat")
                                        .setValue(flashedUser.heartbeat)

                                appFirebaseDatabase.conversationsRef
                                        .child(currentConversationRef)
                                        .child("recordedHumidity")
                                        .setValue(flashedUser.humidity)

                                appFirebaseDatabase.conversationsRef
                                        .child(currentConversationRef)
                                        .child("recordedTemperature")
                                        .setValue(flashedUser.temperature)

                                view.setFlashedUserInfo(it)
                            }
                        }
                    }
                })
    }


    override fun loadConversation() {

        appFirebaseDatabase
                .conversationsRef
                .child(currentConversationRef)
                .child("quiz")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {}

                    override fun onDataChange(snap: DataSnapshot?) {
                        snap?.let {

                            it.children?.forEach { curentChild ->

                                it.getValue(QuizItem::class.java)?.let {

                                    flirtViewModels.add(FlirtViewModel(curentChild.key, it.question, it.response))

                                    view.setCurrentFlirtViewModel(flirtViewModels)
                                }

                            }

                        }
                    }
                })
    }

    private fun createDBConversation() {

        conversation.fromId = flashingUserId
        conversation.toId = flashedUserId
        conversation.timestamp = Date().time

        conversation.recordedHeartBeat = flashedUser.heartbeat
        conversation.recordedHumidity = flashedUser.humidity
        conversation.recordedTemperature = flashedUser.temperature

        appFirebaseDatabase.saveConversation(currentConversationRef!!, conversation)

        appFirebaseDatabase.userConversations
                .child(flashedUserId)
                .child(currentConversationRef)
                .setValue(1)
        appFirebaseDatabase.userConversations
                .child(flashingUserId)
                .child(currentConversationRef)
                .setValue(1)
    }

    private fun createDBQuiz() {

        flirtViewModels = mutableListOf()

        flashedUser.questions.forEach {

            val databaseReference = appFirebaseDatabase.conversationsRef
                    .child(currentConversationRef)
                    .child("quiz")
                    .push()

            val quizItem = QuizItem(it, "")

            databaseReference.setValue(quizItem)

            flirtViewModels.add(FlirtViewModel(databaseReference.key, quizItem.question, quizItem.response))

        }

        view.setCurrentFlirtViewModel(flirtViewModels)
    }

    override fun updateFlirt(flirtViewModel: FlirtViewModel) {

        appFirebaseDatabase.conversationsRef
                .child(currentConversationRef)
                .child("quiz")
                .child(flirtViewModel.databaseKey)
                .setValue(flirtViewModel)

    }

    private val flashedUSerSensorValuesListener  = object : ChildEventListener {
        override fun onCancelled(p0: DatabaseError?) {}

        override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}

        override fun onChildChanged(snap: DataSnapshot?, tableChild: String?) {
            reloadFlashedUserWithSensorValues()
        }
        override fun onChildAdded(p0: DataSnapshot?, p1: String?) {}

        override fun onChildRemoved(p0: DataSnapshot?) {}
    }

    override fun stop() {
        flashedUserQuery.removeEventListener(flashedUSerSensorValuesListener)
        super.stop()
    }

    data class FactoryParameters(val flashedUserId: String, val flashingUserId: String, val isFromMessaging: Boolean)
}