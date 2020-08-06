package com.example.pokequiz.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.pokequiz.R
import com.example.pokequiz.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    lateinit var answers: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
                inflater,
                R.layout.fragment_game,
                container,
                false )

        binding.submitButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_gameFragment_to_gameOverFragment)
        )

        binding.game = this

        return binding.root
    }

}