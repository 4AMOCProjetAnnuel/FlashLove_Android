package projetannuel.bigteam.com.model

import android.support.annotation.Keep

/**
 * QuizItem -
 * @author guirassy
 * @version $Id$
 */

@Keep
data class QuizItem(val question: String="", val response: String="") {
    constructor():this("","")
}