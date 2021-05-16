package com.example.mijnapplicatie.ui.home

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mijnapplicatie.MemoryKaart
import com.example.mijnapplicatie.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_home.*


// Logica memory https://www.youtube.com/watch?v=U4Wtjewy7EY
const val TAG = "MainActivity"
//        app:backgroundTint=""
//        android:background="@drawable/custom_reset_button"

/*<item name="colorPrimary">#FF0000</item>
<item name="colorPrimaryVariant">#F26767</item>
<item name="colorOnPrimary">@color/white</item>
<!-- Secondary brand color. -->
<item name="colorSecondary">#F26767</item>
<item name="colorSecondaryVariant">#F26767</item>
<item name="colorOnSecondary">@color/black</item>*/
 class HomeFragment : Fragment() {

    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryKaart>
    private var indexOfSelectedMemoryCard: Int? = null
    private var numberOfPairs = 0
    private lateinit var homeViewModel: HomeViewModel

    val database = FirebaseDatabase.getInstance("https://android-database-nick-default-rtdb.europe-west1.firebasedatabase.app/")
    var myRef = database.getReference("score")

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val resetButton = root.findViewById<Button>(R.id.buttonReset)

        val startGameButton = root.findViewById<FloatingActionButton>(R.id.startGame)


        //val textView: TextView = root.findViewById(R.id.text_home)
        //homeViewModel.text.observe(viewLifecycleOwner, Observer {
        //    textView.text = it
        //})

        buttons = listOf(root.findViewById<ImageButton>(R.id.imageButton1), root.findViewById<ImageButton>(R.id.imageButton2), root.findViewById<ImageButton>(R.id.imageButton3), root.findViewById<ImageButton>(R.id.imageButton4),
            root.findViewById<ImageButton>(R.id.imageButton5), root.findViewById<ImageButton>(R.id.imageButton6), root.findViewById<ImageButton>(R.id.imageButton7), root.findViewById<ImageButton>(R.id.imageButton8))

        resetButton.setOnClickListener{forceResetGame()}

        //val stopTime: Long=0
        startGameButton.setOnClickListener{
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()
            resetEntireGame()
        }





        //resetEntireGame()
        return root



    }


    fun forceResetGame(){
        Log.i(TAG, "RESET knop aangetikt")
        resetEntireGame()
        chronometer.stop()
        chronometer.base = SystemClock.elapsedRealtime()
    }

    fun resetEntireGame(){
        numberOfPairs = 0
        val images = mutableListOf(R.drawable.ic_airplane_vector, R.drawable.ic_batteryalert_vector, R.drawable.ic_fastfood_vector, R.drawable.ic_hourglass_vector)
        // Voegt elke foto 2 keer in
        images.addAll(images)
        // Zorgt ervoor dat de foto's in random volgorde zijn
        images.shuffle()


        val button1 = buttons[0]
        button1.setImageResource(R.drawable.ic_photo_vector)
        button1.alpha = 1f
        val button2 = buttons[1]
        button2.setImageResource(R.drawable.ic_photo_vector)
        button2.alpha = 1f
        val button3 = buttons[2]
        button3.setImageResource(R.drawable.ic_photo_vector)
        button3.alpha = 1f
        val button4 = buttons[3]
        button4.setImageResource(R.drawable.ic_photo_vector)
        button4.alpha = 1f
        val button5 = buttons[4]
        button5.setImageResource(R.drawable.ic_photo_vector)
        button5.alpha = 1f
        val button6 = buttons[5]
        button6.setImageResource(R.drawable.ic_photo_vector)
        button6.alpha = 1f
        val button7 = buttons[6]
        button7.setImageResource(R.drawable.ic_photo_vector)
        button7.alpha = 1f
        val button8 = buttons[7]
        button8.setImageResource(R.drawable.ic_photo_vector)
        button8.alpha = 1f
        cards = buttons.indices.map { index ->
            MemoryKaart(images[index])
        }

        buttons.forEachIndexed{index, button->
            button.setOnClickListener {
                Log.i(TAG, "Memory kaart aangetikt")
                updateModels(index)
                updateViews()
            }
        }
    }




    private fun updateViews(){
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            if(card.pairFormed){
                button.alpha = 0.2f
            }
            button.setImageResource(if (card.cardFaceUp) card.identifier else R.drawable.ic_photo_vector)
            /*for (card in cards) {
                if (card.cardFaceUp) {
                    button.setImageResource(card.identifier)
                } else {
                    button.setImageResource(R.drawable.ic_photo_vector)
                }
            }*/
        }
    }

    private fun updateModels(position: Int){
        val card= cards[position]
        if (card.cardFaceUp == true){
            Toast.makeText(activity,"You can't flip a flipped card that has already been flipped", Toast.LENGTH_SHORT).show()
            return
        }


        if(indexOfSelectedMemoryCard == null){
            resetCards()
            indexOfSelectedMemoryCard = position
        }else{
            checkForPair(indexOfSelectedMemoryCard!!, position)
            indexOfSelectedMemoryCard = null
        }
        // maakt kaart face up van true naar false of omegekeerd
        card.cardFaceUp = !card.cardFaceUp
    }

    private fun resetCards() {
        for (card in cards){
            if (!card.pairFormed){
                card.cardFaceUp = false
            }
        }
    }

    private fun checkForPair(firstPosition: Int, secondPosition: Int){
        if(cards[firstPosition].identifier == cards[secondPosition].identifier){
            cards[firstPosition].pairFormed = true
            cards[secondPosition].pairFormed = true
            numberOfPairs ++
            Log.i(TAG, "Number of pairs formed " + numberOfPairs)
            if (numberOfPairs == 4){
                resetEntireGame()
                chronometer.stop()
                val timeUntilMemoryFinished = SystemClock.elapsedRealtime() - chronometer.base
                var string = '"'
                Toast.makeText(activity, "Score: " + timeUntilMemoryFinished, Toast.LENGTH_LONG).show()
                //myRef = database.getReference("score")
                myRef.setValue(timeUntilMemoryFinished)
                //chronometer.base = SystemClock.elapsedRealtime()
                //chronometer.start()
            }
            //Toast.makeText(activity, "Pair formed!", Toast.LENGTH_SHORT).show()
        }
    }

 }