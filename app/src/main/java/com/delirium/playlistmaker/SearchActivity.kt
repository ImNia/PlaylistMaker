package com.delirium.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar

class SearchActivity : AppCompatActivity() {
    lateinit var editSearch: EditText
    lateinit var crossForDelete: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        crossForDelete = findViewById(R.id.clear_search)

        crossForDelete.setOnClickListener {
            editSearch.text.clear()
            it.visibility = View.INVISIBLE
        }
        editSearch = findViewById(R.id.edit_search)
        editSearch.addTextChangedListener(createTextWatcher())
    }

    private fun createTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            crossForDelete.visibility = View.VISIBLE
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            TODO("Not yet implemented")
        }

        override fun afterTextChanged(p0: Editable?) {
//            TODO("Not yet implemented")
        }
    }

}