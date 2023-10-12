package com.pallaw.swipeandlearnf.ui.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pallaw.swipeandlearnf.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class GameoverFragment : Fragment() {
    private val viewModel: GameViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gameover, container, false)
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
                    GameScreenContract.Effect.NavigateToGameOver -> {
                        findNavController().navigate(R.id.action_FirstFragment_to_gameoverFragment)
                    }
                    else ->{}
                }
            }
        }

    }

    private fun showUiState(gameState: GameScreenContract.State) {
    }
        companion object {

        @JvmStatic
        fun newInstance() =
            GameoverFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}