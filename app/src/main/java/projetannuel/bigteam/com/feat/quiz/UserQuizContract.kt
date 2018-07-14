package projetannuel.bigteam.com.feat.quiz

import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.mvp.BasePresenter
import projetannuel.bigteam.com.mvp.BaseView

/**
 * UserQuizContract -
 * @author guirassy
 * @version $Id$
 */
interface UserQuizContract {

    interface View : BaseView<Presenter> {
        fun setUserQuestionsViewModel(questions: MutableList<String>)
        fun notifyUpdateSuccess()
    }

    interface Presenter: BasePresenter {
        fun updateQuizItemText(index: Int, text: String)
        fun setCurrentFlashLuvUser(flashLuvUser: FlashLuvUser)
        fun saveQuizChanges()
    }

}