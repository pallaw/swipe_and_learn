package com.pallaw.swipeandlearnf.ui.reward

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pallaw.swipeandlearnf.R
import com.pallaw.swipeandlearnf.databinding.FragmentRewardsBinding
import com.pallaw.swipeandlearnf.ui.game.GameScreenContract
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.StringBuilder

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RewardsFragment : Fragment() {

    private var _binding: FragmentRewardsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RewardViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRewardsBinding.inflate(inflater, container, false)
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
            viewModel.effect.collect{sideEffects ->
                when(sideEffects) {
                    is RewardScreenContract.Effect.ShowRewardDialog -> {
                        Toast.makeText(context, "Need to show reward dialog at this event", Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }

    private fun showUiState(gameState: RewardScreenContract.State) {
        val previousText = binding.textviewSecond.text

        binding.textviewSecond.text = StringBuilder().apply {
            append(previousText)
            append("\n\n")
            append("$gameState")
        }.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}