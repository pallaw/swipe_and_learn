package com.pallaw.swipeandlearnf.ui.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.pallaw.swipeandlearnf.R
import com.pallaw.swipeandlearnf.ui.sheets.ChapterSelectionBottomSheet
import com.pallaw.swipeandlearnf.ui.sheets.HintsBottomSheet
import com.pallaw.swipeandlearnf.ui.sheets.SubjectSelectionBottomSheet
import com.pallaw.swipeandlearnf.ui.sheets.TYPE
import com.pallaw.swipeandlearnf.ui.sheets.onFilterClick
import com.pallaw.swipeandlearnf.domain.model.Question

class QuestionsAdapter(
    private val questionsData: List<Question>,
    cardClick: CardClickListener,
    var manager: FragmentManager
) : RecyclerView.Adapter<QuestionsAdapter.CardQuestionVH>(), onFilterClick  {
    private val cardClick: CardClickListener

    init {
        this.cardClick = cardClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardQuestionVH {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.element_question_card,
            parent,
            false
        )
        return CardQuestionVH(view)
    }

    override fun onBindViewHolder(holder: CardQuestionVH, position: Int) {
        if (position == 0) {
            showIntroCard(holder)
            holder.introChapterSelector.setOnClickListener{
                val ins =  ChapterSelectionBottomSheet.newInstance()
                ins.show(manager, ins.javaClass.name);
            }
            holder.introSubjectedSelector.setOnClickListener {
                val ins =  SubjectSelectionBottomSheet.newInstance(this)
                ins.show(manager, ins.javaClass.name);
            }
        }
        else if(position > 0) {
            showQuestions(holder)
            holder.questionNameTV.text = questionsData[position].question
            holder.hintButton.setOnClickListener {
                val hintsSheet =  HintsBottomSheet.newInstance(TYPE.HINTS)
                hintsSheet.show(manager, hintsSheet.javaClass.name)
            }
            holder.skipButton.setOnClickListener {
                val hintsSheet =  HintsBottomSheet.newInstance(TYPE.SKIPS)
                hintsSheet.show(manager, hintsSheet.javaClass.name)
            }
        }
    }

    private fun showQuestions(holder: CardQuestionVH) {
        holder.questionIntroCard.isVisible = false
        holder.questionCards.isVisible = true
        holder.rightOverlay.isVisible = true
        holder.leftOverLay.isVisible = true
    }

    private fun showIntroCard(holder: CardQuestionVH) {
        holder.questionIntroCard.isVisible = true
        holder.questionCards.isVisible = false
        holder.rightOverlay.isVisible = false
        holder.leftOverLay.isVisible = false
    }

    override fun getItemCount(): Int {
        return this.questionsData.size
    }

    class CardQuestionVH constructor(view: View) : RecyclerView.ViewHolder(view) {
        var questionNameTV: TextView
        var questionIntroCard: RelativeLayout
        var questionCards: RelativeLayout
        var leftOverLay: RelativeLayout
        var rightOverlay: RelativeLayout
        var hintButton: AppCompatImageButton
        var skipButton: AppCompatImageButton
        var introChapterSelector: AppCompatImageButton
        var introSubjectedSelector: AppCompatImageButton


        init {
            questionNameTV = view.findViewById(R.id. question_tv)
            questionIntroCard = view.findViewById(R.id.introduction_card)
            questionCards = view.findViewById(R.id.main_questions_card_rv)
            leftOverLay = view.findViewById(R.id.left_overlay)
            rightOverlay = view.findViewById(R.id.right_overlay)
            hintButton = view.findViewById(R.id.hint_btn)
            skipButton = view.findViewById(R.id.skip_btn)
            introChapterSelector = view.findViewById(R.id.intro_chapter_cta)
            introSubjectedSelector = view.findViewById(R.id.intro_subject_cta)
        }
    }

    interface CardClickListener {
        fun onCardClicked (contentData: Question?, position : Int)
    }

    override fun setOnFilterClicked(list: List<String>) {}
}

enum class ButtonTypeClicked (var type: String){
    HINT ("hint"),
    SKIP ("skip"),

}
