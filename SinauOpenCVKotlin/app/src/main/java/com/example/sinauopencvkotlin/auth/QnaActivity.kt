package com.example.sinauopencvkotlin.auth

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sinauopencvkotlin.R

class QnaActivity : AppCompatActivity() {

    private var score1 = 0
    private var score2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qna)

        val radioGroup1: RadioGroup = findViewById(R.id.radioGroupQuestion1)
        val radioGroup2: RadioGroup = findViewById(R.id.radioGroupQuestion2)
        val resultText: TextView = findViewById(R.id.textViewResult)
        val submitButton: Button = findViewById(R.id.buttonSubmit)
        val maxValue = intent.getDoubleExtra("maxValue", 0.0)


        // Set listener untuk radioGroup1
        radioGroup1.setOnCheckedChangeListener { _, checkedId ->
            score1 = when (checkedId) {
                R.id.radioButton1_1 -> 1
                R.id.radioButton1_2 -> 2
                R.id.radioButton1_3 -> 3
                R.id.radioButton1_4 -> 4
                R.id.radioButton1_5 -> 5
                else -> 0
            }
        }

        // Set listener untuk radioGroup2
        radioGroup2.setOnCheckedChangeListener { _, checkedId ->
            score2 = when (checkedId) {
                R.id.radioButton2_1 -> 1
                R.id.radioButton2_2 -> 2
                R.id.radioButton2_3 -> 3
                R.id.radioButton2_4 -> 4
                R.id.radioButton2_5 -> 5
                else -> 0
            }
        }

        // Ketika tombol submit diklik, hitung total skor
        submitButton.setOnClickListener {
            val totalScore = score1 + score2

            // Menentukan tingkat kondisi berdasarkan total skor dan maxValue
            val condition = when {
                totalScore in 1..3 && maxValue.toInt() >= 120 -> "Tidak Sakit (Nilai maksimum 10)"
                totalScore in 4..6 && maxValue.toInt() >= 110 -> "Sakit (Nilai maksimum 20)"
                maxValue < 90 -> "Parah (Nilai maksimum lebih dari 20)"
                else -> "Tidak Terdefinisi"
            }

            // Menampilkan hasil ke TextView
            resultText.text = "Total Score: $totalScore\nKondisi: $condition"
        }

    }
}
