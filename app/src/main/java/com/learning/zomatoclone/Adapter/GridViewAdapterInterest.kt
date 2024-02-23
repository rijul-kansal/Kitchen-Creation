package com.learning.zomatoclone.Adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.learning.zomatoclone.Model.InterestModel
import com.learning.zomatoclone.R

internal class GridViewAdapterInterest(
    private val context: Context,
    private val lis: ArrayList<InterestModel>
) :
    BaseAdapter() {
    private val emptyBackground: Drawable? = ContextCompat.getDrawable(context, R.drawable.interest_empty_bg)
    private val fullBackground: Drawable? = ContextCompat.getDrawable(context, R.drawable.interest_full_bg)
    private var layoutInflater: LayoutInflater? = null
    private lateinit var textView: TextView
    private var selectedPositions = HashSet<Int>() // HashSet to store selected positions

    override fun getCount(): Int {
        return lis.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View? {
        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.interest_ui, null)
            textView = convertView.findViewById(R.id.CatName)
            convertView.tag = textView
        } else {
            textView = convertView.tag as TextView
        }

        // Set background based on selection state
        if (selectedPositions.contains(position)) {
            textView.background = fullBackground
        } else {
            textView.background = emptyBackground
        }

        textView.text = lis[position].name
        return convertView
    }

    // Method to toggle selection of an item
    fun toggleSelection(position: Int) {
        if (selectedPositions.contains(position)) {
            selectedPositions.remove(position)
        } else {
            selectedPositions.add(position)
        }
        notifyDataSetChanged() // Notify the adapter that data has changed
    }
}
