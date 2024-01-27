package com.learning.zomatoclone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.learning.zomatoclone.Model.Categories.CatShown
import com.learning.zomatoclone.R

internal class GridViewAdapter(
    private val context: Context,
    private val lis: ArrayList<CatShown>
) :
    BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
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
            convertView = layoutInflater!!.inflate(R.layout.cat_or_cus_layout, null)
        }
        imageView = convertView!!.findViewById(R.id.catImaveView)
        textView = convertView.findViewById(R.id.catTextView)
        Glide
            .with(context)
            .load(lis[position].image)
            .centerCrop()
            .placeholder(R.drawable.img)
            .into(imageView)
        textView.text = lis[position].name
        return convertView
    }
}