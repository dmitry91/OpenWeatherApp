package com.dmitry.openweatherapp.ui

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText
import com.dmitry.openweatherapp.R
import com.dmitry.openweatherapp.models.Weather
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainFragment.OnListFragmentInteractionListener {

    private var fTrans: FragmentTransaction? = null
    var mainFrag = MainFragment()

    override fun onListFragmentInteraction(item: Weather?) {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putString("param1", item!!.id)
            fragment.setArguments(args)
            fTrans = supportFragmentManager.beginTransaction()
            fTrans!!.replace(R.id.mainContainer, fragment)
            fTrans!!.addToBackStack(null);
            fTrans!!.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            showAlertWithTextInputLayout(this)
        }

        //mainFrag = MainFragment()
        fTrans = supportFragmentManager.beginTransaction()
        fTrans!!.add(R.id.mainContainer, mainFrag)
        fTrans!!.commit()
    }

    private fun showAlertWithTextInputLayout(context: Context) {
        val textInputLayout = TextInputLayout(context)

        val input = EditText(context)
        textInputLayout.hint = "city name"
        textInputLayout.addView(input)

        val alert = AlertDialog.Builder(context)
            .setTitle("Add city")
            .setView(textInputLayout)
            .setMessage("Enter city name for add")
            .setPositiveButton("Submit") { dialog, _ ->
                mainFrag!!.addCity(input.text.toString())
                dialog.cancel()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }.create()

        alert.show()
    }

    fun showFloatingActionButton() {
        fab.show()
    };

    fun hideFloatingActionButton() {
        fab.hide()
    };

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
