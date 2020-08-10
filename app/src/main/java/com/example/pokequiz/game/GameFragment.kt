package com.example.pokequiz.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.pokequiz.R
import com.example.pokequiz.databinding.FragmentGameBinding
import com.example.pokequiz.model.Question

class GameFragment : Fragment() {

    private val questions: MutableList<Question> = mutableListOf(
        Question(
            text = "What do you need to evolve an Eevee into a Jolteon?",
            answers = listOf("Thunder stone", "Water stone", "Mossy rock", "Fire stone")
        ),
        Question(
            text = "What do you need to evolve an Eevee into a Vaporeon?",
            answers = listOf("Water stone", "Icy rock", "Fire stone", "Mossy rock")
        ),
        Question(
            text = "What Pokemon type is Eevee?",
            answers = listOf("Normal", "Fire", "Electric", "Ice")
        )
    )

    private var score = 0
    private var quantityQuestions = 3
    private var currentQuestionIndex = 0
    private lateinit var currentQuestion: Question
    private lateinit var currentQuestions: MutableList<Question>
    lateinit var currentText: String
    lateinit var currentAnswers: List<String>
    lateinit var currentQuestionText: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )

        play()

        binding.submitButton.isEnabled = binding.questionRadioGroup.checkedRadioButtonId != -1
        binding.submitButton.setOnClickListener { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            onSubmit(checkedId, view)
            binding.invalidateAll()
        }

        binding.game = this

        return binding.root
    }

    private fun play() {
        currentQuestions = getRandomQuestions()

        updateCurrentQuestionData()
    }

    private fun getRandomQuestions(): MutableList<Question> {
        val selectedQuestions: MutableList<Question> = arrayListOf()

        val questionsIndex: MutableList<Int> =
            getListOfRandomInt(quantityQuestions, questions.size - 1)

        questionsIndex.map { index ->
            selectedQuestions.add(questions[index])
        }

        return selectedQuestions
    }

    private fun getListOfRandomInt(quantity: Int, end: Int, start: Int = 0): MutableList<Int> {
        val numberList: MutableList<Int> = arrayListOf()

        while (numberList.size != quantity) {
            val randomNumber = (start..end).random()

            if (numberList.contains(randomNumber).not()) {
                numberList.add(randomNumber)
            }
        }

        return numberList
    }

    private fun updateCurrentQuestionData() {
        currentQuestion = currentQuestions[currentQuestionIndex]
        currentQuestionText = "Question ${currentQuestionIndex + 1}/${quantityQuestions}"
        currentAnswers = currentQuestion.answers.shuffled()
        currentText = currentQuestion.text
    }

    private fun nextQuestion() {
        currentQuestionIndex++

        updateCurrentQuestionData()
    }

    private fun getAnswerIndex(checkedId: Int): Int {
        return when (checkedId) {
            R.id.firstAnswerRadioButton -> 0
            R.id.secondAnswerRadioButton -> 1
            R.id.thirdAnswerRadioButton -> 2
            else -> 3
        }
    }

    private fun onSubmit(checkedId: Int, view: View) {
        if (-1 != checkedId) {
            val answerIndex = getAnswerIndex(checkedId)
            val answer = currentAnswers[answerIndex]
            val isCorrectAnswer = currentQuestion.answers[0] == answer

            if (isCorrectAnswer) score++

            if (currentQuestionIndex == 2) {
                view.findNavController()
                    .navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment(score))
            } else {
                nextQuestion()
            }
        }
    }

}