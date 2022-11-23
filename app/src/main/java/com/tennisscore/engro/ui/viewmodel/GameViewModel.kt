package com.tennisscore.engro.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tennisscore.engro.model.Player
import com.tennisscore.engro.utils.wonPoint

class GameViewModel:ViewModel() {
    var player1Score = MutableLiveData<Int>()
    var player2Score = MutableLiveData<Int>()
    var player1Points = MutableLiveData<Int>()
    var player2Points = MutableLiveData<Int>()
    var player1WonSet = MutableLiveData<Boolean>()
    var player2WonSet = MutableLiveData<Boolean>()
    var isGameOver = MutableLiveData<Player>()
    var player1 = Player("Player1")
    var player2 = Player("Player2")

    fun playerOnePointUp(){
        player1.wonPoint(opponentPlayer = player2)
        player1Points.value = player1.noOfWinningSets
        player1WonSet.value = player1.wonSet
        isGameOver.value = player1
        setScores()
    }

    fun playerTwoPointUp(){
        player2.wonPoint(opponentPlayer = player1)
        player2Points.value = player2.noOfWinningSets
        player2WonSet.value = player2.wonSet
        isGameOver.value = player2
        setScores()
    }

    private fun setScores(){
        player1Score.value = player1.pointsStack.first()
        player2Score.value = player2.pointsStack.first()
    }

    fun reStartGame() {
        player1 = Player("Player1")
        player2 = Player("Player2")
        player1Points.value = player1.noOfWinningSets
        player1WonSet.value = player1.wonSet
        player2Points.value = player2.noOfWinningSets
        player2WonSet.value = player2.wonSet
    }

}