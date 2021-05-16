package com.example.mijnapplicatie.ui.notifications

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mijnapplicatie.R
import com.google.firebase.database.*

const val TAG = "MainActivity"

//android:background="@drawable/layerlist"

val database = FirebaseDatabase.getInstance("https://android-database-nick-default-rtdb.europe-west1.firebasedatabase.app/")
var myRef: DatabaseReference = database.getReference("score")

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        //val textView: TextView = root.findViewById(R.id.text_notifications)
        //notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
        //    textView.text = it
        //})

        //https://programmer.group/introduction-to-basic-use-of-layer-list-for-android-layer.html


        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(Long::class.java)!!.toString()
                Log.d(TAG, "Value is: $value")
                val textView: TextView = root.findViewById(R.id.latestScore)
                notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
                    textView.text = value
                })
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        val siteButton = root.findViewById<Button>(R.id.siteButton)

        siteButton.setOnClickListener(View.OnClickListener(){
                val siteUrl = Intent(Intent.ACTION_VIEW)
                siteUrl.setData(Uri.parse("https://theuselessweb.com/"))
                startActivity(siteUrl)
        })

        return root
    }

}