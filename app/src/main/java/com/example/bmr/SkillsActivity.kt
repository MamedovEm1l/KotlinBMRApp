package com.example.bmr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStreamReader

class SkillsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marafon_skills)

        val textView = findViewById<TextView>(R.id.textInfo)
        val fileContent = readFromFile("marathonSkillsInfo.txt")
        textView.text = fileContent
        Log.i("FileContent", fileContent)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.back_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  when (item.itemId) {
            R.id.back -> {
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun readFromFile(fileName: String): String {
        val stringBuilder = StringBuilder()
        try {
            val inputStream = assets.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))
            var i = true

            var line: String? = reader.readLine()
            while (line != null) {
                if (i) {
                    stringBuilder.append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t$line")
                        .append("\n")
                    i = false
                }else
                    stringBuilder.append(line).append("\n")
                line = reader.readLine()
            }

            reader.close()
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ReadFromFile", "Error reading file: ${e.message}")
        }
        return stringBuilder.toString()
    }
}