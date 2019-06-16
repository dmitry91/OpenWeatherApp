package com.dmitry.openweatherapp.ui.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.dmitry.openweatherapp.R
import com.dmitry.openweatherapp.models.Weather
import com.dmitry.openweatherapp.ui.MainFragment
import com.dmitry.openweatherapp.ui.MainFragment.OnListFragmentInteractionListener
import com.dmitry.openweatherapp.utils.DefaultCities
import kotlinx.android.synthetic.main.city_item.view.*

class MainItemRecyclerViewAdapter(
    private var mValues: ArrayList<Weather>,
    private val mListener: OnListFragmentInteractionListener?,
    private val mainFragment: MainFragment
) : RecyclerView.Adapter<MainItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
                if (mainFragment.isInternetAvailable()){
                    val item = v.tag as Weather
                    mListener?.onListFragmentInteraction(item)
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.city_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("PrivateResource")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues.get(position)
        holder.mContentView.text = item.name
        holder.mTemp.text = item.main!!.temp
        if (item.name == DefaultCities.CITY_1 || item.name == DefaultCities.CITY_2 || item.name == DefaultCities.CITY_3) {
            holder.mBtnDel.visibility = View.GONE
        } else {
            holder.mBtnDel.setOnClickListener {
                mainFragment.delete(item)
            }
        }
        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }


    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentView: TextView = mView.content
        val mTemp: TextView = mView.main_temp
        val mBtnDel : Button = mView.buttonDelete
        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
