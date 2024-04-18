package com.enesterzi.catchthekennykotlin

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.enesterzi.catchthekennykotlin.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var runnable: Runnable = Runnable {}
    var handler: Handler = Handler(Looper.getMainLooper())
    private var score = 0
    private val imageArray = mutableListOf<ImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageArray.add(binding.imageView1)
        imageArray.add(binding.imageView2)
        imageArray.add(binding.imageView3)
        imageArray.add(binding.imageView4)
        imageArray.add(binding.imageView5)
        imageArray.add(binding.imageView6)
        imageArray.add(binding.imageView7)
        imageArray.add(binding.imageView8)
        imageArray.add(binding.imageView9)

        hideImages()

        object: CountDownTimer(15500, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.textTimer.text = "Time: ${millisUntilFinished/1000}"
            }

            override fun onFinish() {
                binding.textTimer.text = "Time: 0"
                handler.removeCallbacks(runnable)

                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }

                val alertDialog = AlertDialog.Builder(this@MainActivity)

                alertDialog
                    .setTitle("Game Over")
                    .setMessage("Do you want to start again?")
                    .setPositiveButton("Yes") {
                            dialog, which ->
                            val intentFromMain = intent
                        startActivity(intentFromMain)
                        finish()

                    }
                    .setNegativeButton("No") {
                        dialog, which ->
                        Toast.makeText(this@MainActivity, "Game Over", Toast.LENGTH_LONG).show()
                    }.show()

            }
        }.start()

    }


    fun hideImages() {

        runnable = object : Runnable {
            override fun run() {
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }

                val random = Random()
                val randomIndex = random.nextInt(9)
                imageArray[randomIndex].visibility = View.VISIBLE

                handler.postDelayed(runnable,500)
            }

        }

        handler.post(runnable)

    }

    fun increaseScore(view: View){
        score = score + 1
        binding.textScore.text = "Score: $score"

    }


}