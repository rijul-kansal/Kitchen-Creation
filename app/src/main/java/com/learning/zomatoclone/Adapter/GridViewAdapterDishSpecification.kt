package com.learning.zomatoclone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learning.zomatoclone.databinding.CusianLayoutBinding
import com.learning.zomatoclone.databinding.SimpleListItem1Binding

class GridViewAdapterDishSpecification(
    private val items: List<String>,
    private val context: Context
) :
    RecyclerView.Adapter<GridViewAdapterDishSpecification.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SimpleListItem1Binding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = item
    }
    override fun getItemCount(): Int {
        return items.size
    }
    class ViewHolder(binding: SimpleListItem1Binding) : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.textView
    }
}

