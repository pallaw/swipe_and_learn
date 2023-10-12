package com.pallaw.swipeandlearnf.ui.game

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DefaultItemAnimator
import com.pallaw.swipeandlearnf.R
import com.pallaw.swipeandlearnf.databinding.FragmentGameBinding
import com.pallaw.swipeandlearnf.feature.adapter.QuestionsAdapter
import com.pallaw.swipeandlearnf.feature.data.CardQuestionData
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeableMethod
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.StringBuilder


class GameFragment : Fragment(), CardStackListener, QuestionsAdapter.CardClickListener {

    private var _binding: FragmentGameBinding? = null
    private val adapter = QuestionsAdapter(listOf(
        CardQuestionData(),
        CardQuestionData(),
        CardQuestionData(),
        CardQuestionData(),),this )

    private lateinit var layoutManager: CardStackLayoutManager


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: GameViewModel by viewModel()

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

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.uiState.collect { gameState ->
                showUiState(gameState)
            }
        }

        lifecycleScope.launch {
            viewModel.effect.collect{ sideEffects ->
                when(sideEffects) {
                    GameScreenContract.Effect.NavigateToRewards -> {
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                    }
                }

            }
        }

        layoutManager = CardStackLayoutManager(requireContext(), this).apply {
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }

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

    private fun showUiState(gameState: GameScreenContract.State) {
        val previousText = binding.textviewFirst.text

        binding.textviewFirst.text = StringBuilder().apply {
            append(previousText)
            append("\n\n")
            append("$gameState")
        }.toString()
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

    }

    override fun onCardRewound() {
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
    }

    override fun onCardDisappeared(view: View?, position: Int) {
    }

    override fun onVideoClicked(contentData: CardQuestionData?) {
    }

}