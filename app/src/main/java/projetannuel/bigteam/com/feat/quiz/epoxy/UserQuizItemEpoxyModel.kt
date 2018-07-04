package projetannuel.bigteam.com.feat.quiz.epoxy

import android.support.constraint.ConstraintLayout
import com.airbnb.epoxy.EpoxyModel
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.item_user_quiz.view.ed_question_item
import projetannuel.bigteam.com.R

/**
 * UserQuizItemEpoxyModel -
 * @author guirassy
 * @version $Id$
 */
class UserQuizItemEpoxyModel(private val index: Int, private val question: String,
        private val onQuizItemTextChange: (index: Int, text: String) -> Any) : EpoxyModel<ConstraintLayout>() {

    override fun getDefaultLayout(): Int = R.layout.item_user_quiz

    override fun bind(view: ConstraintLayout) {
        view.apply {
            this.ed_question_item.setText(question)
            RxTextView.afterTextChangeEvents(this.ed_question_item)
                    .subscribe {
                        onQuizItemTextChange(index, this.ed_question_item.text.toString())
                    }
        }
    }
}

