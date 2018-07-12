package projetannuel.bigteam.com.model

/**
 * Flirt -
 * @author guirassy
 * @version $Id$
 */

data class Flirt(val quiz: MutableList<QuizItem>,
        val recordedHeartBeat: Int,
        val recordedTemperature: Int,
        val recordedHumidity: Int)