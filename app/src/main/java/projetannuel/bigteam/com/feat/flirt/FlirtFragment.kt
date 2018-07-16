package projetannuel.bigteam.com.feat.flirt


import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.kodein
import com.github.salomonbrys.kodein.with
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.app_toolbar
import kotlinx.android.synthetic.main.fragment_flirt.iv_user_profile_pic
import kotlinx.android.synthetic.main.fragment_flirt.rv_flirt_chat
import kotlinx.android.synthetic.main.fragment_flirt.tv_body_humidity
import kotlinx.android.synthetic.main.fragment_flirt.tv_body_temperature
import kotlinx.android.synthetic.main.fragment_flirt.tv_heartBeat
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.appFirebase.AppFirebaseDatabase
import projetannuel.bigteam.com.feat.flirt.epoxy.FlirtEpoxyController
import projetannuel.bigteam.com.feat.flirt.model.FlirtViewModel
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.model.QuizItem
import projetannuel.bigteam.com.mvp.AppMvpFragment


class FlirtFragment : AppMvpFragment<FlirtContract.Presenter>(),
        FlirtContract.View {

    override val presenter: FlirtContract.Presenter by lazy {
        val flashedUserId = arguments?.getString(FlirtFragment.FLASHED_USER_ID_TAG) ?: "0"
        val flashingUserId = arguments?.getString(FlirtFragment.FLASHING_USER_ID_TAG) ?: "0"
        val isFromMessaging = arguments?.getBoolean(FlirtFragment.ISFROMMESSSAGING_TAG) ?: false

        val params = FlirtPresenter.FactoryParameters(flashedUserId,
                flashingUserId, isFromMessaging)

        kodein()
                .value
                .with(params)
                .instance<FlirtContract.Presenter>()

    }

    override val defaultLayout: Int = R.layout.fragment_flirt


    companion object {

        const val fragmentTag = "Flirt"
        const val FLASHED_USER_ID_TAG = "flashedUserId"
        const val FLASHING_USER_ID_TAG = "flashingUserId"
        const val ISFROMMESSSAGING_TAG = "isFromMessaging"


        fun newInstance(flashedUserId: String, flashingUserId: String, isFromMessaging : Boolean): FlirtFragment {
            val fragment = FlirtFragment()
            val args = Bundle()
            args.putString(FLASHED_USER_ID_TAG, flashedUserId)
            args.putString(FLASHING_USER_ID_TAG, flashingUserId)
            args.putBoolean(ISFROMMESSSAGING_TAG, isFromMessaging)
            fragment.arguments = args
            return fragment
        }
    }


    override fun provideOverridingModule() = Kodein.Module {
        bind<FlirtContract.View>() with instance(this@FlirtFragment)
    }

    private lateinit var epoxyController: FlirtEpoxyController

    private val appFirebaseDatabase: AppFirebaseDatabase by injector.instance()
    private lateinit var currentConversationChildEventListener: ChildEventListener
    private lateinit var query: Query
    private lateinit var flirtItems: MutableList<FlirtViewModel>


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).app_toolbar.title = FlirtFragment.fragmentTag
        (activity as AppCompatActivity).app_toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        (activity as AppCompatActivity).supportActionBar!!.setIcon(
                ColorDrawable(ContextCompat.getColor(activity, R.color.fui_transparent)).apply {
                    setBounds(0, 0, 0, 0)
                }
        )
        (activity as AppCompatActivity).app_toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }

        epoxyController = FlirtEpoxyController(
                { currentFlirtItem: FlirtViewModel -> presenter.updateFlirt(currentFlirtItem) }
        )

        rv_flirt_chat.adapter = epoxyController.adapter
        rv_flirt_chat.layoutManager = LinearLayoutManager(context)

    }


    override fun setFlashedUserInfo(flashedUser: FlashLuvUser) {

        if (this.view != null) {

            Glide.with(this.view)
                    .load(Uri.parse(flashedUser.photoUrl))
                    .into(iv_user_profile_pic)

            tv_heartBeat.text = resources.getString(R.string.flirt_current_user_heartbeat, flashedUser.heartbeat)
            tv_body_temperature.text = resources.getString(R.string.flirt_current_user_temperature, flashedUser.temperature)
            tv_body_humidity.text = resources.getString(R.string.flirt_current_user_humidity, flashedUser.humidity)
        }
    }

    override fun setCurrentConversationKey(key: String) {

    }

    override fun setCurrentFlirtViewModel(flirtViewModels: MutableList<FlirtViewModel>) {
        //if (this.view != null) {
        epoxyController.flirtViewModels = flirtViewModels
        epoxyController.requestModelBuild()
        flirtItems = flirtViewModels
        //}
    }

    override fun onResume() {
        super.onResume()

        currentConversationChildEventListener = object : ChildEventListener {

            override fun onCancelled(p0: DatabaseError?) {}

            override fun onChildMoved(snap: DataSnapshot?, p1: String?) {}

            override fun onChildChanged(snap: DataSnapshot?, childName: String?) {

                snap?.let {

                    if (it.value != null) {
                        flirtItems.clear()

                        it.child("quiz")
                                .children
                                .forEach {
                                    if(it.getValue(QuizItem::class.java) != null) {
                                        val quizItem = it.getValue(QuizItem::class.java)!!
                                        val currentFlirt = FlirtViewModel(it.key, quizItem.question, quizItem.response)
                                        flirtItems.add(currentFlirt)
                                        epoxyController.flirtViewModels = flirtItems
                                        epoxyController.requestModelBuild()
                                    }
                                }
                    }
                }
            }

            override fun onChildAdded(snap: DataSnapshot?, p1: String?) {

                snap?.let {

                    if (it.value != null) {
                        flirtItems = mutableListOf()
                        it.child("quiz")
                                .children
                                .forEach {
                                    if(it.getValue(QuizItem::class.java) != null) {
                                        val quizItem = it.getValue(QuizItem::class.java)!!
                                        val currentFlirt = FlirtViewModel(it.key, quizItem.question, quizItem.response)
                                        flirtItems.add(currentFlirt)
                                        epoxyController.flirtViewModels = flirtItems
                                        epoxyController.requestModelBuild()
                                    }
                                }
                    }
                }
            }

            override fun onChildRemoved(p0: DataSnapshot?) {}
        }

        query = appFirebaseDatabase.conversationsRef
        query.addChildEventListener(currentConversationChildEventListener)
    }

    override fun onStop() {
        query.removeEventListener(currentConversationChildEventListener)
        super.onStop()
    }
}
