package com.example.ovenTimer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ovenTimer.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: SharedPreferences


    private val sectionList = arrayOf("00", "10","20", "30", "40", "50")

    private var mMin = 0
    private var mSec = 0
    private var mPackageWatts = 1
    private var mSelfWatts = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = getSharedPreferences("lastSelectedWatts", MODE_PRIVATE)

        binding.apply {

            //numberPicker display warm time
            setupNumberPicker(npPackageMinute, 0, 10,  null)
            setupNumberPicker(npPackageSecound, 0, 5, sectionList)
            npPackageMinute.setOnValueChangedListener { _, _, newVal ->
                mMin = newVal
                updateWarmingTimeTextView()
            }
            npPackageSecound.setOnValueChangedListener { _, _, newVal ->
                //秒数は10秒毎のlistで表示してるので、positionのitemを取得
                mSec = sectionList[newVal].toInt()
                updateWarmingTimeTextView()
            }

            //Spinner display Watts
            setupSpinner(context = this@MainActivity,
                spinner = spPackageWatts,
                item = resources.getStringArray(R.array.package_watts_items),
                lastSelected = prefs.getInt("packageSelectedWatts", 0))
            setupSpinner(context = this@MainActivity,
                spinner = spSelfWatts,
                item = resources.getStringArray(R.array.self_watts_items),
                lastSelected = prefs.getInt("selfSelectedWatts", 0))

            spPackageWatts.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    mPackageWatts = convertWattsToInt(parent?.getItemAtPosition(position).toString())
                    updateWarmingTimeTextView()
                    prefs.edit().putInt("packageSelectedWatts",position).apply()
                }
                override fun onNothingSelected(parent: AdapterView<*>?) { }
            }

            spSelfWatts.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    mSelfWatts = convertWattsToInt(parent?.getItemAtPosition(position).toString())
                    updateWarmingTimeTextView()
                    prefs.edit().putInt("selfSelectedWatts", position).apply()
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) { }
                }

        }

    }

    private fun setupNumberPicker(view: NumberPicker, min: Int, max:Int, displayedValue: Array<String>?) {
        view.minValue = min
        view.maxValue = max
        if (displayedValue != null) { view.displayedValues = displayedValue }
    }

    private fun setupSpinner(context: Context, spinner: Spinner, item: Array<String>, lastSelected: Int){
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(lastSelected)
    }

    //"500W" -> 500
    fun convertWattsToInt(item: String):Int = item.removeSuffix("W").toInt()

    fun updateWarmingTimeTextView(){
        val warmTime = calcWarmingTime(mMin,mSec,mPackageWatts,mSelfWatts)
        binding.tvResult.text = String.format("${warmTime/60}分${warmTime%60}秒")
    }

    fun calcWarmingTime(min: Int, sec: Int, packageWatts: Int, selfWatts: Int): Int {
        //秒数に変換
        val warmingSec = min * 60 + sec
        //熱量 = ワット x 時間
        val requireJules = warmingSec * packageWatts
        //熱量を任意のワット数における秒数に変換
        return requireJules / selfWatts
    }

}














