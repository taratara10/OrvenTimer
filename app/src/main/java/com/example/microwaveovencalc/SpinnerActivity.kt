package com.example.microwaveovencalc

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView

class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        var packageWatts = parent.selectedItem
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}