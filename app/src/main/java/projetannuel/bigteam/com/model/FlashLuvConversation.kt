package projetannuel.bigteam.com.model

/**
 * FlashLuvConversation -
 * @author guirassy
 * @version $Id$
 */
class FlashLuvConversation(var fromId: String,
        var quiz: MutableMap<String, String>,
        var recordedHeartBeat: Int = 0,
        var recordedHumidity: Int = 0,
        var recordedTemperature: Int = 0,
        var timestamp: Long,
        var toId: String) {

    constructor() : this("", mutableMapOf(), 0, 0,
            0, 0, "")
}