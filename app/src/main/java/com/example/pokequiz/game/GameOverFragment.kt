package com.example.pokequiz.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.pokequiz.R
import com.example.pokequiz.databinding.FragmentGameOverBinding

class GameOverFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGameOverBinding>(
            inflater,
            R.layout.fragment_game_over,
            container,
            false)

        val args = arguments?.let { GameOverFragmentArgs.fromBundle(it) }

        binding.playAgainButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_gameOverFragment_to_gameFragment)
        )

        binding.tvScore.text = args?.score.toString()

        return binding.root
    }

}