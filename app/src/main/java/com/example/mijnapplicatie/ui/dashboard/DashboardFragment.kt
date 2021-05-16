package com.example.mijnapplicatie.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mijnapplicatie.R
import com.google.firebase.database.*

const val TAG = "MainActivity"

val database = FirebaseDatabase.getInstance("https://android-database-nick-default-rtdb.europe-west1.firebasedatabase.app/")
var myRef: DatabaseReference = database.getReference("score")

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        //val textView: TextView = root.findViewById(R.id.text_dashboard)
        //dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
        //    textView.text = it
        //})



        fun getScores(myRef: DatabaseReference) {
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = dataSnapshot.getValue(kotlin.String::class.java)!!
                    if (myRef == database.getReference("score1")){
                        val textView: TextView = root.findViewById(R.id.score1)
                        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
                            textView.text = value
                        })
                        println("NUmmer1")
                        Log.d(TAG, "Value is: $value")
                    }else if (myRef == database.getReference("score2")){
                        val textView: TextView = root.findViewById(R.id.score2)
                        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
                            textView.text = value
                        })
                        println("Nummer2")
                        Log.d(TAG, "Value is: $value")
                    }else if (myRef == database.getReference("score3")){
                        val textView: TextView = root.findViewById(R.id.score3)
                        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
                            textView.text = value
                        })
                        println("Nummer3")
                        Log.d(TAG, "Value is: $value")
                    }else if (myRef == database.getReference("score4")){
                        val textView: TextView = root.findViewById(R.id.score4)
                        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
                            textView.text = value
                        })
                        println("Nummer4")
                        Log.d(TAG, "Value is: $value")
                    }else if (myRef == database.getReference("score5")){
                        val textView: TextView = root.findViewById(R.id.score5)
                        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
                            textView.text = value
                        })
                        println("Nummer5")
                        Log.d(TAG, "Value is: $value")
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read value.", error.toException())
                }
            })
        }
        for (i in 5 downTo 1) {
            myRef = database.getReference("score$i")
            getScores(myRef)
        }


        return root
    }



}