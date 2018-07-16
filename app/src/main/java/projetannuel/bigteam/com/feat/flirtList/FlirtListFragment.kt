package projetannuel.bigteam.com.feat.flirtList

import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.github.salomonbrys.kodein.instance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_flirt_list.rv_flirts_list
import kotlinx.android.synthetic.main.toolbar.app_toolbar
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.appFirebase.AppFirebaseDatabase
import projetannuel.bigteam.com.feat.flirtList.epoxy.FlirtListEpoxyController
import projetannuel.bigteam.com.feat.flirtList.model.FlirtListViewModel
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.mvp.AppMvpFragment
import java.net.HttpURLConnection
import java.net.URL

/**
 * FlirtListFragment -
 * @author guirassy
 * @version $Id$
 */

class FlirtListFragment : AppMvpFragment<FlirtListContract.Presenter>(),
        FlirtListContract.View {

    override val presenter: FlirtListContract.Presenter by injector.instance()

    override val defaultLayout: Int = R.layout.fragment_flirt_list

    companion object {
        const val FLIRT_LIST_FRAGMENT_TAG = "flirtList"
    }

    var epoxyController = FlirtListEpoxyController()

    private val appFirebaseDatabase: AppFirebaseDatabase by injector.instance()

    private lateinit var flirtsList: MutableList<FlirtListViewModel>

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        (activity as AppCompatActivity).app_toolbar.title = getString(R.string.flirt_list_fragment_toolbar_title)

        (activity as AppCompatActivity).supportActionBar!!.setIcon(
                ColorDrawable(ContextCompat.getColor(activity, R.color.fui_transparent)).apply {
                    setBounds(0, 0, 0, 0)
                }
        )

        rv_flirts_list.apply {
            adapter = epoxyController.adapter
            layoutManager = LinearLayoutManager(this@FlirtListFragment.context)
            epoxyController.requestModelBuild()
        }

        FirebaseAuth.getInstance().currentUser?.let {

            appFirebaseDatabase.userConversations
                    .child(it.uid)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {}
                        override fun onDataChange(snap: DataSnapshot?) {
                            snap?.let {

                                flirtsList = mutableListOf()

                                it.children?.forEach {

                                    appFirebaseDatabase.conversationsRef
                                            .child(it.key)
                                            .addListenerForSingleValueEvent(object : ValueEventListener {

                                                override fun onCancelled(p0: DatabaseError?) {}

                                                override fun onDataChange(snap: DataSnapshot?) {
                                                    if (snap != null && snap.value != null) {

                                                        val conversationId = snap.key
                                                        val fromId = (snap.child("fromId").value as String)
                                                        val toId = (snap.child("toId").value as String)
                                                        val recordedHeartBeat = (snap.child("recordedHeartBeat").value as Long)
                                                        val recordedHumidity = (snap.child("recordedHumidity").value as Long)
                                                        val recordedTemperature = (snap.child("recordedTemperature").value as Long)


                                                        appFirebaseDatabase.usersReference
                                                                .child(fromId)
                                                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                                                    override fun onCancelled(p0: DatabaseError?) {}

                                                                    override fun onDataChange(snap: DataSnapshot?) {

                                                                        if (snap != null && snap.value != null && snap.getValue(FlashLuvUser::class.java) != null) {

                                                                            val flashLuvUser = snap.getValue(FlashLuvUser::class.java)!!

                                                                            val fromPhotoUrl = flashLuvUser.photoUrl


                                                                            appFirebaseDatabase.usersReference
                                                                                    .child(toId)
                                                                                    .addListenerForSingleValueEvent(object : ValueEventListener {
                                                                                        override fun onCancelled(p0: DatabaseError?) {}

                                                                                        override fun onDataChange(snap: DataSnapshot?) {

                                                                                            if (snap != null && snap.value != null && snap.getValue(FlashLuvUser::class.java) != null) {

                                                                                                val flashLuvUser = snap.getValue(FlashLuvUser::class.java)!!
                                                                                                val toPhotoUrl = flashLuvUser.photoUrl


                                                                                                val fromBitmapStream = URL(fromPhotoUrl).openStream()
                                                                                                val fromBitmap = BitmapFactory.decodeStream(fromBitmapStream)


                                                                                                val toBitmapStream = URL(toPhotoUrl).openStream()
                                                                                                val toBitmap = BitmapFactory.decodeStream(toBitmapStream)


                                                                                                val flirtListItem = FlirtListViewModel(
                                                                                                        conversationId = conversationId,
                                                                                                        fromId = fromId,
                                                                                                        toId = toId,
                                                                                                        recordedHeartBeat = recordedHeartBeat,
                                                                                                        recordedHumidity = recordedHumidity,
                                                                                                        recordedTemperature = recordedTemperature,
                                                                                                        fromPhotoUrl = fromPhotoUrl,
                                                                                                        toPhotoUrl = toPhotoUrl,
                                                                                                        fromBitmap = fromBitmap,
                                                                                                        toBitmap = toBitmap)

                                                                                                flirtsList.add(flirtListItem)

                                                                                                epoxyController.flirtsList = flirtsList
                                                                                                epoxyController.requestModelBuild()


                                                                                                fromBitmapStream.close()

                                                                                            }
                                                                                        }

                                                                                    })
                                                                        }

                                                                    }

                                                                })

                                                    }
                                                }
                                            })
                                }
                            }
                        }
                    })
        }
    }

    override fun setFlirtListViewModel(flirtsList: MutableList<FlirtListViewModel>) {
        epoxyController.flirtsList = flirtsList
        epoxyController.requestModelBuild()
    }

}