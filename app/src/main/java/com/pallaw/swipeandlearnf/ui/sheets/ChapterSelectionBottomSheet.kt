package com.pallaw.swipeandlearnf.ui.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pallaw.swipeandlearnf.R
import com.pallaw.swipeandlearnf.feature.adapter.SubjectsAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChapterSelectionBottomSheet : BottomSheetDialogFragment(), onFilterClick {

    private var subjectsAdapter: SubjectsAdapter? = null
    private val viewModel: ChapterSubjectViewModel by viewModel()


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
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { listingState ->
                showUiState(listingState)
            }
        }
    }

    private fun showUiState(chapterState: ChapterSubjectContract.State) {
        if(chapterState.subjects.isEmpty()) return
        val chapterListing = chapterState.subjects[0].chapters
        subjectsAdapter = SubjectsAdapter(chapterListing, this)
        val rcv = view?.findViewById<RecyclerView>(R.id.chapter_filter_rcv)
        view?.findViewById<AppCompatImageView>(R.id.closeBottomSheetBtn)?.setOnClickListener {
            dismiss()
        }
        rcv?.adapter = subjectsAdapter

    }

    override fun setOnFilterClicked(list: List<String>) {}
}