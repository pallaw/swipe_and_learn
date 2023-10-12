package com.pallaw.swipeandlearnf.ui.sheets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pallaw.swipeandlearnf.R

class ChapterSelectionBottomSheet : BottomSheetDialogFragment() {
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
}