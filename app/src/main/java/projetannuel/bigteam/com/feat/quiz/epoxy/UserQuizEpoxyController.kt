package projetannuel.bigteam.com.feat.quiz.epoxy

import com.airbnb.epoxy.EpoxyController

/**
 * UserQuizEpoxyController -
 * @author guirassy
 * @version $Id$
 */
class UserQuizEpoxyController(private val onQuizItemTextChanges: (index: Int, text: String) -> Any)
    : EpoxyController() {

    var questions = mutableListOf<String>()

    override fun buildModels() {
        questions.let {
            it.forEachIndexed { index, question ->
                UserQuizItemEpoxyModel(index, question, onQuizItemTextChanges)
                        .id(index)
                        .addTo(this)

            }
        }
    }
}