package com.pallaw.swipeandlearnf.ui.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.pallaw.swipeandlearnf.R
import com.pallaw.swipeandlearnf.feature.data.CardQuestionData

class QuestionsAdapter(
    private val questionsData: List<CardQuestionData>,
    cardClick: CardClickListener,
) : RecyclerView.Adapter<QuestionsAdapter.CardQuestionVH>()  {
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
        if (position == 0)
            showIntroCard(holder)
        else if(position > 0){
            showQuestions(holder)
            holder.questionNameTV.text = questionsData[position].question

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

        init {
            questionNameTV = view.findViewById(R.id. question_tv)
            questionIntroCard = view.findViewById(R.id.introduction_card)
            questionCards = view.findViewById(R.id.main_questions_card_rv)
            leftOverLay = view.findViewById(R.id.left_overlay)
            rightOverlay = view.findViewById(R.id.right_overlay)
        }
    }

    interface CardClickListener {
        fun onCardClicked (contentData: CardQuestionData?, position : Int)
    }


}
