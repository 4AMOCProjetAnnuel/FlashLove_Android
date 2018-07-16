package projetannuel.bigteam.com.feat.flirtList.model

import android.graphics.Bitmap

/**
 * FlirtListViewModel -
 * @author guirassy
 * @version $Id$
 */
class FlirtListViewModel(val conversationId : String,
        val fromId : String,
        val toId : String,
        val recordedTemperature : Long,
        val recordedHeartBeat : Long,
        val recordedHumidity : Long,
        val fromPhotoUrl : String,
        val toPhotoUrl : String,
        val fromBitmap : Bitmap,
        val toBitmap : Bitmap)
