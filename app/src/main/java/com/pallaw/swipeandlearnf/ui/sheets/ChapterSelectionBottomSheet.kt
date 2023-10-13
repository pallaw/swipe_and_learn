package com.pallaw.swipeandlearnf.ui.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pallaw.swipeandlearnf.R
import com.pallaw.swipeandlearnf.feature.adapter.SubjectsAdapter

class ChapterSelectionBottomSheet : BottomSheetDialogFragment(), onFilterClick {

    private var subjectsAdapter : SubjectsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chapter_selection_bottom_sheet, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ChapterSelectionBottomSheet().apply {
                arguments = Bundle().apply {
                }
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
        subjectsAdapter = SubjectsAdapter(result, this)
        val rcv = view.findViewById<RecyclerView>(R.id.chapter_filter_rcv)
        view.findViewById<AppCompatImageView>(R.id.closeBottomSheetBtn).setOnClickListener {
            dismiss()
        }
        rcv.adapter = subjectsAdapter

    }

    override fun setOnFilterClicked(list: List<String>) {}
}