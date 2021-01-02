package com.example.microwaveovencalc

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.AdapterView.OnItemSelectedListener as AdapterViewOnItemSelectedListener


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        numberPicker
//
        val packageMin = findViewById<NumberPicker>(R.id.packageMin)
        packageMin.minValue = 0
        packageMin.maxValue = 10
        packageMin.wrapSelectorWheel = false
        packageMin.setOnValueChangedListener(){ numberPicker: NumberPicker, i: Int, i1: Int ->
            calcWatts()
        }

        val packageSec = findViewById<NumberPicker>(R.id.packageSec)
        packageSec.minValue = 0
        packageSec.maxValue = 59
        packageSec.setOnValueChangedListener(){ numberPicker: NumberPicker, i: Int, i1: Int ->
            calcWatts()
        }

//
//        sharedPreferences
//
        val prefs = getSharedPreferences("lastSelectedWatts", MODE_PRIVATE)
        val editor = prefs.edit()
        val packageSelectedWatts = prefs.getInt("packageSelectedWatts", 0)
        val selfSelectedWatts = prefs.getInt("selfSelectedWatts", 0)

//
// spinnerの処理
//


        //Package Spinner
        val packageSpinner = findViewById<Spinner>(R.id.packageSpinner)
        val packageItem = resources.getStringArray(R.array.package_watts_items)
        setSpinnerItem(this, packageSpinner, packageItem)
        packageSpinner.setSelection(packageSelectedWatts)

        //SelfWattsSpinner
        val selfWattsSpinner = findViewById<Spinner>(R.id.selfWattsSpinner)
        val selfWattsItem = resources.getStringArray(R.array.self_watts_items)
        setSpinnerItem(this, selfWattsSpinner, selfWattsItem)
        selfWattsSpinner.setSelection(selfSelectedWatts)

        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                calcWatts()
                editor.putInt("packageSelectedWatts",packageSpinner.selectedItemPosition)
                editor.putInt("selfSelectedWatts",selfWattsSpinner.selectedItemPosition)
                editor.apply()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) { TODO("Not yet implemented") }
        }


        packageSpinner.onItemSelectedListener = spinnerListener
        selfWattsSpinner.onItemSelectedListener = spinnerListener




    } //onCreate


    private fun setSpinnerItem(context: Context, spinner: Spinner, item: Array<String>): Spinner {
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        return spinner
         }

     private fun calcWatts(){
        val min = packageMin.value.toString().toInt()
        val ss = packageSec.value.toString().toInt()

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


