package com.pallaw.swipeandlearnf.ui.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pallaw.swipeandlearnf.databinding.ElementQuestionCardBinding
import com.yuyakaido.android.cardstackview.Direction
import com.pallaw.swipeandlearnf.domain.model.Question

class QuestionsAdapterNew(
    val ctaClickListener: (action: QuestionCardCtaAction) -> Unit
): ListAdapter<Question, QuestionsAdapterNew.CardQuestionViewHolder>(questionDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardQuestionViewHolder {
        val binding =
            ElementQuestionCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardQuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardQuestionViewHolder, position: Int) {
        holder.bindData(position)
    }

    inner class CardQuestionViewHolder(val binding: ElementQuestionCardBinding): ViewHolder(binding.root){
        init {
            binding.introChapterCta.setOnClickListener {
                ctaClickListener.invoke(QuestionCardCtaAction.CTA_CHAPTER_CLICKED)
            }

            binding.introSubjectCta.setOnClickListener {
                ctaClickListener.invoke(QuestionCardCtaAction.CTA_SUBJECT_CLICKED)
            }

            binding.hintBtn.setOnClickListener {
                ctaClickListener.invoke(QuestionCardCtaAction.CTA_HINT_CLICKED)
            }

            binding.skipBtn.setOnClickListener {
                ctaClickListener.invoke(QuestionCardCtaAction.CTA_SKIP_CLICKED)
            }
        }

        fun bindData(position: Int) {

            val currentQuestion = getItem(position)
            if (position == 0) {
                showIntroCard()
            } else {
                showQuestionCard()
            }

            binding.questionTv.text = currentQuestion.question ?: ""

        }

        private fun showQuestionCard() {
            binding.questionCardLayout.visibility = View.VISIBLE
            binding.introductionCardLayout.visibility = View.GONE
            binding.leftOverlay.visibility = View.VISIBLE
            binding.rightOverlay.visibility = View.VISIBLE
        }

        private fun showIntroCard() {
            binding.introductionCardLayout.visibility = View.VISIBLE
            binding.questionCardLayout.visibility = View.GONE
            binding.leftOverlay.visibility = View.GONE
            binding.rightOverlay.visibility = View.GONE
        }

//        private fun showQuestions(holder: QuestionsAdapter.CardQuestionVH) {
//            holder.questionIntroCard.isVisible = false
//            holder.questionCards.isVisible = true
//            holder.rightOverlay.isVisible = true
//            holder.leftOverLay.isVisible = true
//        }
//
//        private fun showIntroCard(holder: QuestionsAdapter.CardQuestionVH) {
//            holder.questionIntroCard.isVisible = true
//            holder.questionCards.isVisible = false
//            holder.rightOverlay.isVisible = false
//            holder.leftOverLay.isVisible = false
//        }

    }

    enum class QuestionCardCtaAction(){
        CTA_SUBJECT_CLICKED,
        CTA_CHAPTER_CLICKED,
        CTA_HINT_CLICKED,
        CTA_SKIP_CLICKED
    }




}

object questionDiffUtil: DiffUtil.ItemCallback<Question>() {
    override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem.id == newItem.id
    }

}
