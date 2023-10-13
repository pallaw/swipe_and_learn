package com.pallaw.swipeandlearnf.ui.sheets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pallaw.swipeandlearnf.R

class HintsBottomSheet : BottomSheetDialogFragment() {

    private var contentType: TYPE? = null
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hints_bottom_sheet, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(contentType: TYPE) = HintsBottomSheet().apply {
                    this.contentType = contentType
                }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contentTv = view.findViewById<TextView>(R.id.outOfContentTV)
        val accelerate = view.findViewById<TextView>(R.id.accelerate)
        val accelerate2 = view.findViewById<TextView>(R.id.accelerate2)
        val cancelCTA = view.findViewById<AppCompatImageButton>(R.id.cancel_cta)
        if(contentType == TYPE.HINTS)  {
            contentTv.text = "Out of Hints"
            accelerate.text = "You get 3 hints everyday"
            accelerate2.text = "No worries! Buy another hint for 1 diamond"
        }else if(contentType == TYPE.SKIPS) {
            contentTv.text = "Out of Skips"
            accelerate2.text = "No worries! You can buy more and continue."
            accelerate.text = "You get 2 skips everyday"
        }
        cancelCTA.setOnClickListener {
            dismiss()
        }
    }
}


enum class TYPE  {
    HINTS ,
    SKIPS
}