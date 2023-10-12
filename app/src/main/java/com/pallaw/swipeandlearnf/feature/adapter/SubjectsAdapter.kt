package com.pallaw.swipeandlearnf.feature.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pallaw.swipeandlearnf.R

class SubjectsAdapter constructor(private var subjectData: List<String>): RecyclerView.Adapter<SubjectsAdapter.SubjectsVH>()  {
    private var selectedSubjects: ArrayList<String>? = null
    class SubjectsVH constructor(view: View) : RecyclerView.ViewHolder(view) {
        var filterName: TextView
        var reviewCheckBox: CheckBox

        init {
            filterName = view.findViewById(R.id. filter_chapter_name_tv)
            reviewCheckBox = view.findViewById(R.id. review_checkbox)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectsVH {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.element_neet_bookmark_chapter_filter,
            parent,
            false
        )
        return SubjectsVH(view)
    }

    override fun getItemCount(): Int {
        return subjectData.size
    }

    override fun onBindViewHolder(holder: SubjectsVH, position: Int) {
        holder.filterName.text = subjectData[position]
        holder.reviewCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                selectedSubjects?.add(subjectData[position])
            }
        }
    }

}