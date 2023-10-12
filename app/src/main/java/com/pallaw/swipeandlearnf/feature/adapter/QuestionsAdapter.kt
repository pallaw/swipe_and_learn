package com.pallaw.swipeandlearnf.feature.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pallaw.swipeandlearnf.R
import com.pallaw.swipeandlearnf.feature.data.CardQuestionData

class QuestionsAdapter(
    questionsData: List<CardQuestionData>,
    videoClickListener: CardClickListener,
) : RecyclerView.Adapter<QuestionsAdapter.CardQuestionVH>() {
    private val questions: List<CardQuestionData>
    private val videoClickListener: CardClickListener

    init {
        this.questions = questionsData

        this.videoClickListener = videoClickListener
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
        //  val currentItem: VideoBookmarkData = videoBookmarkData[position]
       // holder.questionNameTV.text = ""
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    class CardQuestionVH constructor(view: View) : RecyclerView.ViewHolder(view) {
        var questionNameTV: TextView

        init {
            questionNameTV = view.findViewById(R.id.question_tv)
        }
    }

    interface CardClickListener {
        fun onVideoClicked(contentData: CardQuestionData?)
    }


}
