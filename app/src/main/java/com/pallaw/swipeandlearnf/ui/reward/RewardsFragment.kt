package com.pallaw.swipeandlearnf.ui.reward

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RewardsFragment : Fragment() {

    private lateinit var composeView: ComposeView
    private val viewModel: RewardViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).also {
            composeView = it
        }

    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        composeView.setContent {
            MaterialTheme {
                val rewardState by viewModel.uiState.collectAsState()
                RewardsListScreen(
                    state = rewardState,
                    rewardClicked = {
                        viewModel.setEvent(RewardScreenContract.Event.RewardClicked(it))
                    }
                )
            }
        }

        lifecycleScope.launch {
            viewModel.effect.collect { sideEffects ->
                when (sideEffects) {
                    is RewardScreenContract.Effect.ShowRewardDialog -> {
                        Toast.makeText(
                            context,
                            "Reward ${sideEffects.reward.id} clicked",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

}