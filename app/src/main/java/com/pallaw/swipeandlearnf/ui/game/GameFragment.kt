package com.pallaw.swipeandlearnf.ui.game

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.pallaw.swipeandlearnf.R
import com.pallaw.swipeandlearnf.databinding.FragmentGameBinding
import com.pallaw.swipeandlearnf.domain.model.Question
import com.pallaw.swipeandlearnf.ui.sheets.ChapterSelectionBottomSheet
import com.pallaw.swipeandlearnf.ui.sheets.RestartBottomSheet
import com.pallaw.swipeandlearnf.ui.sheets.SubjectSelectionBottomSheet
import com.pallaw.swipeandlearnf.ui.sheets.onFilterClick
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeableMethod
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.Boolean
import kotlin.Float
import kotlin.Int
import kotlin.apply
import kotlin.getValue


class GameFragment : Fragment() {

    private var currentCardPosition: Int = 0
    private lateinit var questionAdapter: QuestionsAdapterNew
    private var _binding: FragmentGameBinding? = null

    private lateinit var layoutManager: CardStackLayoutManager
    private var streakCount: Int = 0
    private var timeCounter: Int = 60
    private var currentPosition: Int = 0
    private var countDown: CountDownTimer? = null
    private val binding get() = _binding!!
    private val viewModel: GameViewModel by viewModel()

    private var correctAnswers : List<Boolean> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupQuestionCards()

        addObservers()

        binding.restartCta.setOnClickListener {
            val restartSheet = RestartBottomSheet.newInstance()
            restartSheet.show(requireFragmentManager(), restartSheet.javaClass.name)
        }

        binding.hintBtn.setOnClickListener {
            viewModel.setEvent(GameScreenContract.Event.HintClicked(currentCardPosition))
        }

        binding.skipBtn.setOnClickListener {
            viewModel.setEvent(GameScreenContract.Event.SkipClicked(currentCardPosition))
        }

    }

    private fun addObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { gameState ->
                showUiState(gameState)
            }
        }


        lifecycleScope.launch {
            viewModel.effect.collect { sideEffects ->
                when (sideEffects) {
                    is GameScreenContract.Effect.NavigateToRewards -> {
                        countDown?.cancel()
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                    }
                    is GameScreenContract.Effect.ShowMsg -> {
                       showMsg(sideEffects.msg)
                    }
                    is GameScreenContract.Effect.ResetGame -> {
                        resetGame()
                    }
                    is GameScreenContract.Effect.SkipQuestion -> {
                        binding.questionCardStackView.swipe()
                    }
                    else ->{}
                }
            }
        }
    }

    private fun showUiState(gameState: GameScreenContract.State) {

        //show diamonds
        binding.hintsCountTv.text = "${gameState.diamondsCount}"
        binding.streakCountTv.text = "${gameState.streakCount}"
        binding.hintBtn.text = "Hint: ${gameState.hintCount}"
        binding.skipBtn.text = "Skip: ${gameState.skipCount}"

        //update question card
        val questionList : List<Question> = gameState.questions
        questionAdapter.submitList(questionList)

    }

    private fun setupQuestionCards() {
        questionAdapter = QuestionsAdapterNew() {ctaClickAction ->
            when(ctaClickAction) {
                QuestionsAdapterNew.QuestionCardCtaAction.CTA_SUBJECT_CLICKED -> showSubjectDialog()
                QuestionsAdapterNew.QuestionCardCtaAction.CTA_CHAPTER_CLICKED -> showMsg("chapter clicked")
                QuestionsAdapterNew.QuestionCardCtaAction.CTA_HINT_CLICKED -> showMsg("Hint clicked")
                QuestionsAdapterNew.QuestionCardCtaAction.CTA_SKIP_CLICKED -> showMsg("Skip clicked")
            }
        }

        val cardStackListener = object : CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {}

            override fun onCardSwiped(direction: Direction?) {
                Log.d("pallaw", "onCardSwiped ${direction}")
                if (currentCardPosition != 0) {
                    val answerForCurrentQuestion = direction == Direction.Right
                    viewModel.setEvent(GameScreenContract.Event.SubmitAnswer(
                        currentCardPosition,
                        answerForCurrentQuestion
                    ))
                }
                timeCounter = 60
                countDown?.cancel()



//                timeCounter = 60
//                countDown?.cancel()
//                if(currentPosition == 0){
//                    currentPosition+=1
//                    return
//                }
//                if(correctAnswers.size == currentPosition) return
//                if(direction == Direction.Left) {
//                    if(correctAnswers[currentPosition].not()){
//                        streakCount +=1
//                        binding.streakImg.isVisible = false
//                        binding.streakFire.isVisible = true
//                    }
//                    else{
//                        streakCount = 0
//                        binding.streakImg.isVisible = true
//                        binding.streakFire.isVisible = false
//                    }
//                }else if(direction == Direction.Right) {
//                    if(correctAnswers[currentPosition].not()) {
//                        streakCount +=1
//                        binding.streakImg.isVisible = false
//                        binding.streakFire.isVisible = true
//                    }
//                    else{
//                        streakCount = 0
//                        binding.streakImg.isVisible = true
//                        binding.streakFire.isVisible = false
//                    }
//                }
//                currentPosition+=1
//                binding.streakCountTv.text = streakCount.toString()
            }

            override fun onCardRewound() {}

            override fun onCardCanceled() {}

            override fun onCardAppeared(view: View?, position: Int) {
                currentCardPosition = position
                Log.d("pallaw", "cardAppeared ${position}")
                if (position > 0) {
                    timeCounter = 60
                    countDown = object : CountDownTimer(30000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            binding.countdownTv.text =
                                (if (timeCounter < 0) 0 else timeCounter).toString()
                            timeCounter--
                        }

                        override fun onFinish() {
                            binding.countdownTv.text = "0"
                            binding.questionCardStackView.swipe()
                            binding.streakImg.isVisible = true
                            binding.streakFire.isVisible = false
                            resetGame()
                            timeCounter = 0
                        }
                    }.start()

                }
//                if(position == 0) {
//                    layoutManager.setDirections(listOf(Direction.Right))
//                }
//                else {
//                    timeCounter = 60
//                    layoutManager.setDirections(listOf(Direction.Left, Direction.Right))
//                    countDown = object : CountDownTimer(30000, 1000) {
//                        override fun onTick(millisUntilFinished: Long) {
//                            binding.countdownTv.text =(if(timeCounter < 0) 0 else timeCounter).toString()
//                            timeCounter--
//                        }
//                        override fun onFinish() {
//                            binding.countdownTv.text = "0"
//                            binding.questionCardStackView.swipe()
//                            binding.streakImg.isVisible = true
//                            binding.streakFire.isVisible = false
//                            timeCounter = 0
//                        }
//                    }.start()
//                }
            }

            override fun onCardDisappeared(view: View?, position: Int) {
                Log.d("pallaw", "onCardDisappeared ${position}")
//                viewModel.setEvent(position, )
//                binding.allDoneView.isVisible = position == correctAnswers.size
            }

        }
        
        layoutManager = CardStackLayoutManager(requireContext(), cardStackListener).apply {
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }

        layoutManager.setStackFrom(StackFrom.Bottom)
        layoutManager.setVisibleCount(3)
        layoutManager.setTranslationInterval(8.0f)
        layoutManager.setScaleInterval(0.95f)

        layoutManager.setMaxDegree(40.0f)
        binding.questionCardStackView.layoutManager = layoutManager
        binding.questionCardStackView.adapter = questionAdapter
        binding.questionCardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun showSubjectDialog() {
        fragmentManager?.let {
            val subjectSelectionBottomSheet = SubjectSelectionBottomSheet.newInstance(object : onFilterClick {
                override fun setOnFilterClicked(list: List<String>) {
                    showMsg("subject dialog clicked")
                }
            })
            subjectSelectionBottomSheet.show(it, SubjectSelectionBottomSheet.javaClass.name)
        }
    }


    private fun showMsg(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
        Log.d("pallaw", "${s}")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_rewards -> {
                viewModel.setEvent(GameScreenContract.Event.RewardClicked)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startCountDown () {
        timeCounter = 60
        countDown = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.countdownTv.text =(if(timeCounter < 0) 0 else timeCounter).toString()
                timeCounter--
            }
            override fun onFinish() {
                timeCounter = 0
                binding.countdownTv.text = "0"
                binding.questionCardStackView.swipe()
            }
        }.start()
    }

    private fun resetCountDown (){
        timeCounter = 60
        countDown?.cancel()
    }

    fun resetGame(){
        resetCountDown()
        viewModel.setEvent(GameScreenContract.Event.ResetGame)
    }


}