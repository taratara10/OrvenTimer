package com.example.microwaveovencalc

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //NumberPicker
        val packageMin = findViewById<NumberPicker>(R.id.packageMin)
        packageMin.minValue = 0
        packageMin.maxValue = 30

        val packageSec = findViewById<NumberPicker>(R.id.packageSec)
        packageSec.minValue = 0
        packageSec.maxValue = 60

        //Package Spinner
        val packageSpinner = findViewById<Spinner>(R.id.packageSpinner)
        val packageItem = resources.getStringArray(R.array.package_watts_items)

        setSpinnerItem(this, packageSpinner, packageItem)

        //SelfWattsSpinner
        val selfWattsSpinner = findViewById<Spinner>(R.id.selfWattsSpinner)
        val selfWattsItem = resources.getStringArray(R.array.self_watts_items)

        setSpinnerItem(this, selfWattsSpinner, selfWattsItem)

        packageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //　アイテムが選択された時
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {
//                val spinnerParent = parent as Spinner
//                val item = spinnerParent.selectedItem as String
//                // Kotlin Android Extensions
//                val result = findViewById<TextView>(R.id.result)
//                result.text = item

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        selfWattsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //　アイテムが選択された時
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {
//                val spinnerParent = parent as Spinner
//                val item = spinnerParent.selectedItem as String
//                // Kotlin Android Extensions
//                val result = result
//                result.text = item

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


    }

    private fun setSpinnerItem(context: Context, spinner: Spinner,item:Array<String>): Spinner {
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        return spinner
         }

     fun calcWatts(){
        val min = packageMin.displayedValues.toString().toInt()
        val ss = packageSec.displayedValues.toString().toInt()

        val selfWatts = selfWattsSpinner.selectedItem.toString() //500W -> 500:Intに変換
        val selfWattsInt = selfWatts.removeSuffix("W").toInt()

        val packageWatts = packageSpinner.selectedItem.toString()
        val packageWattsInt = packageWatts.removeSuffix("W").toInt()

        val warmTime = min*60 + ss //秒に変換
        val packageRequireJules = warmTime * packageWattsInt
        val selfWattsWarmTime = packageRequireJules/selfWattsInt

        val resultText = "${selfWattsWarmTime/60}分${selfWattsWarmTime%60}秒"

        val result = findViewById<TextView>(R.id.result)
        result.text = resultText
    }














    }


