package com.pallaw.swipeandlearnf.ui.game

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.pallaw.swipeandlearnf.R
import com.pallaw.swipeandlearnf.databinding.FragmentGameBinding
import com.pallaw.swipeandlearnf.feature.adapter.QuestionsAdapter
import com.pallaw.swipeandlearnf.feature.data.CardQuestionData
import com.pallaw.swipeandlearnf.ui.sheets.RestartBottomSheet
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeableMethod
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.String
import kotlin.Boolean
import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.apply
import kotlin.getValue


class GameFragment : Fragment(), CardStackListener, QuestionsAdapter.CardClickListener {

    private var _binding: FragmentGameBinding? = null
    private var adapter : QuestionsAdapter? = null

    private lateinit var layoutManager: CardStackLayoutManager
    private lateinit var cardStack: CardStackView
    private var streakCount: Int = 0
    private var timeCounter: Int = 60
    private var currentPosition: Int = 0
    private var countDown: CountDownTimer? = null
    private val binding get() = _binding!!
    private val viewModel: GameViewModel by viewModel()

    private var questionList : List<CardQuestionData> = emptyList()
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

        lifecycleScope.launch {
            viewModel.uiState.collectLatest { gameState ->
                showUiState(gameState)
            }
        }


        lifecycleScope.launch {
            viewModel.effect.collect { sideEffects ->
                when (sideEffects) {
                    GameScreenContract.Effect.NavigateToRewards -> {
                        countDown?.cancel()
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                    }
                    else ->{}
                }
            }
        }

        binding.streakCountTv.text = streakCount.toString()

        binding.restartCta.setOnClickListener {
            val restartSheet = RestartBottomSheet.newInstance()
            restartSheet.show(requireFragmentManager(), restartSheet.javaClass.name)
        }
    }

    private fun showUiState(gameState: GameScreenContract.State) {
        val questionList : MutableList<CardQuestionData> = gameState.questions.map {
            CardQuestionData(it.question, it.answer, it.hint)
        }.toMutableList()

        correctAnswers = gameState.questions.map {
            CardQuestionData(it.question, it.answer, it.hint)
        }.map {
            it.correctAnswer?:false
        }

        questionList.add(0, CardQuestionData())
        this.questionList = questionList.toList()


        adapter = QuestionsAdapter(questionList, this, requireFragmentManager())

        layoutManager = CardStackLayoutManager(requireContext(), this).apply {
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }

        cardStack = binding.stackView
        layoutManager.setStackFrom(StackFrom.Bottom)
        layoutManager.setVisibleCount(3)
        layoutManager.setTranslationInterval(8.0f)
        layoutManager.setScaleInterval(0.95f)

        layoutManager.setMaxDegree(40.0f)
        binding.stackView.layoutManager = layoutManager
        binding.stackView.adapter = adapter
        binding.stackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }

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

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction?) {
        timeCounter = 60
        countDown?.cancel()
        if(currentPosition == 0){
            currentPosition+=1
            return
        }
        if(correctAnswers.size == currentPosition) return
        if(direction == Direction.Left) {
            if(correctAnswers[currentPosition].not()){
                streakCount +=1
                binding.streakImg.isVisible = false
                binding.streakFire.isVisible = true
            }
            else{
                streakCount = 0
                binding.streakImg.isVisible = true
                binding.streakFire.isVisible = false
            }
        }else if(direction == Direction.Right) {
            if(correctAnswers[currentPosition].not()) {
                streakCount +=1
                binding.streakImg.isVisible = false
                binding.streakFire.isVisible = true
            }
            else{
                streakCount = 0
                binding.streakImg.isVisible = true
                binding.streakFire.isVisible = false
            }
        }
        currentPosition+=1
        binding.streakCountTv.text = streakCount.toString()

    }

    override fun onCardRewound() {
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {

        if(position == 0) {
            layoutManager.setDirections(listOf(Direction.Right))
        }
        else {
            timeCounter = 60
            layoutManager.setDirections(listOf(Direction.Left, Direction.Right))
            countDown = object : CountDownTimer(30000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.countdownTv.text =(if(timeCounter < 0) 0 else timeCounter).toString()
                    timeCounter--
                }
                override fun onFinish() {
                    binding.countdownTv.text = "0"
                    cardStack.swipe()
                    binding.streakImg.isVisible = true
                    binding.streakFire.isVisible = false
                    timeCounter = 0
                }
            }.start()
        }

     }

    override fun onCardDisappeared(view: View?, position: Int) {
        binding.exitRl.isVisible = position == correctAnswers.size
    }

    override fun onCardClicked(contentData: CardQuestionData?, position: Int) {

    }

}