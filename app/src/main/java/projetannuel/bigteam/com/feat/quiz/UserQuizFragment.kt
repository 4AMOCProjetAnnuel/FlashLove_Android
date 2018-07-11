package projetannuel.bigteam.com.feat.quiz

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.github.salomonbrys.kodein.instance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_user_quiz.btn_submit_user_quiz
import kotlinx.android.synthetic.main.fragment_user_quiz.rv_user_quiz
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.appFirebase.AppFirebaseDatabase
import projetannuel.bigteam.com.feat.quiz.epoxy.UserQuizEpoxyController
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.mvp.AppMvpFragment

/**
 * UserQuizFragment -
 * @author guirassy
 * @version $Id$
 */
class UserQuizFragment : AppMvpFragment<UserQuizContract.Presenter>(), UserQuizContract.View {

    override val presenter: UserQuizContract.Presenter by injector.instance()
    override val defaultLayout: Int = R.layout.fragment_user_quiz
    private var epoxyController = UserQuizEpoxyController(
            { index:Int , text: String ->presenter.updateQuizItemText(index, text)}

    )

    private val appFirebaseDatabase : AppFirebaseDatabase by injector.instance()
    private lateinit var flashLuvUser: FlashLuvUser


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_user_quiz.adapter = epoxyController.adapter
        rv_user_quiz.layoutManager = LinearLayoutManager(context)

        var userQuizEventListener = object : ChildEventListener{

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {}

            override fun onChildRemoved(p0: DataSnapshot?) {}

            override fun onCancelled(p0: DatabaseError?) {}

            override fun onChildChanged(dataSnap: DataSnapshot?, p1: String?) {

                dataSnap?.let {
                    epoxyController.questions = (dataSnap.value as MutableList<String>)
                    epoxyController.requestModelBuild()
                    presenter.setCurrentFlashLuvUser(flashLuvUser)
                }

            }

        }

        FirebaseAuth.getInstance().currentUser?.let {

            appFirebaseDatabase.usersReference.child(it.uid)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {}
                        override fun onDataChange(snap: DataSnapshot?) {
                            snap?.let {
                                flashLuvUser = snap.getValue(FlashLuvUser::class.java)!!
                                epoxyController.questions = flashLuvUser.questions
                                epoxyController.requestModelBuild()
                                presenter.setCurrentFlashLuvUser(flashLuvUser)

                                var query =  appFirebaseDatabase.usersReference.child(flashLuvUser.uid)
                                query.addChildEventListener(userQuizEventListener)

                            }
                        }
                    })




        }

        btn_submit_user_quiz.setOnClickListener {
            presenter.saveQuizChanges()

        }
    }

    companion object {
        const val fragmentTag = "userQuiz_fragment"
    }

    override fun setFlashLuvUserQuestions(questions: MutableList<String>) {
        //epoxyController.requestModelBuild()
    }

}