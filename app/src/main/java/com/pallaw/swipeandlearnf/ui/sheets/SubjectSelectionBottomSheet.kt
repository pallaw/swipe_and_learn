package com.pallaw.swipeandlearnf.ui.sheets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pallaw.swipeandlearnf.R
import com.pallaw.swipeandlearnf.feature.adapter.SubjectsAdapter


class SubjectSelectionBottomSheet : BottomSheetDialogFragment(), onFilterClick {

    private var subjectsAdapter : SubjectsAdapter? = null
    private var onFilterClick: onFilterClick? = null
    private var selectedList: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_subject_selection_bottom_sheet, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(onFilterClick: onFilterClick) =
            SubjectSelectionBottomSheet().apply {
                this.onFilterClick = onFilterClick
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val result = listOf(
            "All",
            "Physics",
            "Chemistry",
            "Maths",
            )

        view.findViewById<AppCompatImageView>(R.id.closeBottomSheetBtn).setOnClickListener {
            dismiss()
        }
        subjectsAdapter = SubjectsAdapter(result, this)
        val rcv = view.findViewById<RecyclerView>(R.id.chapter_filter_rcv)
        rcv.adapter = subjectsAdapter

        view.findViewById<CardView>(R.id.apply_btn_cv).setOnClickListener {
            selectedList?.let { it1 -> onFilterClick?.setOnFilterClicked(it1) }
            dismiss()
        }
    }

    override fun setOnFilterClicked(list: List<String>) {
        selectedList = list
    }
}

interface onFilterClick {
    fun setOnFilterClicked (list: List<String>)
}