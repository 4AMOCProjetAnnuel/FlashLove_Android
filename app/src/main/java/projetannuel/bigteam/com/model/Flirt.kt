package projetannuel.bigteam.com.model

import android.support.annotation.Keep

/**
 * Flirt -
 * @author guirassy
 * @version $Id$
 */

@Keep
data class Flirt(val quiz: MutableList<QuizItem> = mutableListOf(),
        val recordedHeartBeat: Int,
        val recordedTemperature: Int,
        val recordedHumidity: Int,
        val otherFlashLuvUser: FlashLuvUser) {

    constructor() :this(mutableListOf<QuizItem>(), 0,
            0, 0, FlashLuvUser())
}