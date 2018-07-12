package projetannuel.bigteam.com.feat.quiz

import projetannuel.bigteam.com.appFirebase.AppFirebaseDatabase
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.mvp.AppMvpPresenter
import projetannuel.bigteam.com.navigation.AppNavigator

/**
 * UserQuizPresenter -
 * @author guirassy
 * @version $Id$
 */
class UserQuizPresenter (view: UserQuizContract.View,
        navigator: AppNavigator,
        private val appFirebaseDatabase: AppFirebaseDatabase) :
        AppMvpPresenter<AppNavigator, UserQuizContract.View>(view, navigator),
        UserQuizContract.Presenter {

    private lateinit var user: FlashLuvUser

    override fun resume() {}

    override fun updateQuizItemText(index: Int, text: String) {
        user.questions[index] = text
    }

    override fun setCurrentFlashLuvUser(flashLuvUser: FlashLuvUser) {
        this.user = flashLuvUser
    }

    override fun saveQuizChanges() {
        appFirebaseDatabase.saveFlashLuvUser(user)
    }

}