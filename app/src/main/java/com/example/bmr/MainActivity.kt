package com.example.bmr

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var  manImage: ImageView
    private lateinit var  womanImage: ImageView
    private var BMR:Double = 0.0
    private var gender:String = ""

    private lateinit var heightEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var bmrTextView: TextView
    private lateinit var sitActTextView: TextView
    private lateinit var minActTextView: TextView
    private lateinit var avgActTextView: TextView
    private lateinit var strActActTextView: TextView
    private lateinit var maxActActTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manImage = findViewById(R.id.manImage);
        womanImage = findViewById(R.id.womanImage);

        heightEditText = findViewById(R.id.height)
        weightEditText = findViewById(R.id.weight)
        ageEditText = findViewById(R.id.age)
        bmrTextView = findViewById(R.id.bmr)
        sitActTextView = findViewById(R.id.sitActivity)
        minActTextView = findViewById(R.id.minActivity)
        avgActTextView = findViewById(R.id.avgActivity)
        strActActTextView = findViewById(R.id.strActivity)
        maxActActTextView = findViewById(R.id.maxActivity)

        manImage.setOnClickListener {
            handleImageSelection(manImage, getString(R.string.male))
        }

        womanImage.setOnClickListener {
            handleImageSelection(womanImage, getString(R.string.female))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  when (item.itemId) {
            R.id.marafon -> {
                var intent = Intent(this, SkillsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.log -> {
                Log.i("BMRCalculator", "BMR: $BMR, Gender: $gender")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun extraInfo(v:View){
        val intent = Intent(this, AddInfoActivity::class.java)
        startActivity(intent)
    }
    private fun handleImageSelection(imageView: ImageView?, selectedGender: String) {
    // Сбросить выделение на всех изображениях
        manImage.isSelected = false
        womanImage.isSelected = false

        // Выделить выбранное изображение
        imageView?.isSelected = true
        gender = selectedGender
    }

    private fun showGenderDialog() {
        val genderOptions = arrayOf(getString(R.string.male), getString(R.string.female))

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.choose_your_gender))
            .setSingleChoiceItems(genderOptions, -1) { _: DialogInterface, which: Int ->
                // Записываем выбранный гендер в переменную gender
                gender = genderOptions[which]
                if (gender == getString(R.string.male))
                    manImage.isSelected = true
                else
                    womanImage.isSelected = true

            }
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ ->

                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setPositiveButton("OK") { dialog: DialogInterface, _ ->
                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.show()
    }

    fun calculatebtm(v:View){
        try{
            val heightText = heightEditText.text.toString()
            val weightText = weightEditText.text.toString()
            val ageText = ageEditText.text.toString()
            var sitAct:Double
            var minAct:Double
            var avgAct:Double
            var strAct:Double
            var maxAct:Double

            when {
                gender.isNullOrEmpty() -> showGenderDialog()
                heightText.isBlank() -> showAlertDialog(getString(R.string.height_mistake))
                weightText.isBlank() -> showAlertDialog(getString(R.string.weight_mistake))
                ageText.isBlank() -> showAlertDialog(getString(R.string.age_error))
                heightText.toDoubleOrNull() == null -> showAlertDialog(getString(R.string.int_format_error))
                weightText.toDoubleOrNull() == null -> showAlertDialog(getString(R.string.int_format_error))
                ageText.toIntOrNull() == null -> showAlertDialog(getString(R.string.int_format_error))
                heightText.toDouble() !in 50.0..300.0 -> showAlertDialog(getString(R.string.height_range_error))
                weightText.toDouble() !in 10.0..500.0 -> showAlertDialog(getString(R.string.weight_range_error))
                ageText.toInt() !in 1..150 -> showAlertDialog(getString(R.string.age_range_error))
                else -> {
                    if (gender == getString(R.string.male)){
                        BMR = 66 + (13.7 * weightText.toFloat()) + (5 * heightText.toFloat()) - (6.8 * ageText.toFloat())
                        sitAct = BMR * 1.2
                        minAct = BMR * 1.375
                        avgAct = BMR * 1.55
                        strAct = BMR * 1.725
                        maxAct = BMR * 1.9

                    }else{
                        BMR = 655 + (9.6 * weightText.toFloat()) + (1.8 * heightText.toFloat()) - (4.7 * ageText.toFloat())
                        sitAct = BMR * 1.2
                        minAct = BMR * 1.375
                        avgAct = BMR * 1.55
                        strAct = BMR * 1.725
                        maxAct = BMR * 1.9
                    }
                    bmrTextView.text = String.format("%.2f",BMR)
                    sitActTextView.text =  "Сидячий образ: ${String.format("%.2f",sitAct)}"
                    minActTextView.text ="Маленькая активность: ${String.format("%.2f",minAct)}"
                    avgActTextView.text = "Средняя активность: ${String.format("%.2f",avgAct)}"
                    strActActTextView.text = "Сильная активность: ${String.format("%.2f",strAct)}"
                    maxActActTextView.text = "Максимальная активность: ${String.format("%.2f",maxAct)}"
                }
            }
        }catch (e:Exception){
            showAlertDialog(getString(R.string.format_error))
        }
    }

    fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.сonfirmation))
            .setMessage(getString(R.string.sure))
            .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                cancelData()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.show()
    }

    fun cancelbtm(v: View) {
        showConfirmationDialog()
    }

    fun cancelData() {
        gender = ""
        heightEditText.setText("")
        weightEditText.setText("")
        ageEditText.setText("")
        bmrTextView.text = ""
        sitActTextView.text = "Сидячий образ:"
        minActTextView.text = "Маленькая активность"
        avgActTextView.text = "Средняя активность:"
        strActActTextView.text = "Сильная активность:"
        maxActActTextView.text = "Максимальная активность:"
        manImage.isSelected = false
        womanImage.isSelected = false
    }
    /* Списковое диалоговое окно
    val colors = arrayOf("Красный", "Зеленый", "Синий")
    val listDialog = AlertDialog.Builder(this)
        .setTitle("Выберите цвет")
        .setItems(colors) { dialog, which ->
            val selectedColor = colors[which]
            // Обработка выбранного цвета
            dialog.dismiss()
        }
        .show()

    // Множественный выбор в списковом диалоговом окне
    val multiChoiceItems = arrayOf("Пункт 1", "Пункт 2", "Пункт 3")
    val checkedItems = booleanArrayOf(false, false, false)
    val multiChoiceDialog = AlertDialog.Builder(this)
        .setTitle("Выберите пункты")
        .setMultiChoiceItems(multiChoiceItems, checkedItems) { _, which, isChecked ->
            // Обработка выбора пунктов
        }
        .setPositiveButton("Готово") { dialog, _ ->
            // Обработка нажатия кнопки "Готово"
            dialog.dismiss()
        }
        .show()
     */
}