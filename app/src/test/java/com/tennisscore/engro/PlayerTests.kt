package com.tennisscore.engro

import androidx.lifecycle.MutableLiveData
import com.tennisscore.engro.model.Player
import com.tennisscore.engro.ui.viewmodel.GameViewModel
import com.tennisscore.engro.utils.wonPoint
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


class PlayerTests {
    private val player = Player("player1", pointsStack = ArrayDeque(listOf(40,50,)))
    private val opponentPlayer = Player("player1", pointsStack = ArrayDeque(listOf(30,40,50,)))
    private lateinit var gameViewModel: GameViewModel
    @Before
    fun setUp(){
        gameViewModel = GameViewModel()
        gameViewModel.player1 = this.player
        gameViewModel.player2 = this.opponentPlayer
        gameViewModel.player1Points = MutableLiveData()
        gameViewModel.player2Points = MutableLiveData()
        gameViewModel.player1WonSet = MutableLiveData()
        gameViewModel.isGameOver = MutableLiveData()
        `when`(gameViewModel.player1Points.value).thenReturn(50)
        `when`(gameViewModel.player1WonSet.value).thenReturn(true)
        `when`(gameViewModel.isGameOver).thenReturn(MutableLiveData(player))
        gameViewModel.player2WonSet = MutableLiveData()
        `when`(gameViewModel.player2Points.value).thenReturn(30)
        `when`(gameViewModel.player2WonSet.value).thenReturn(false)
    }

    @Test
    fun testPlayerWinning(){
        gameViewModel.playerOnePointUp()
        verify(player.wonPoint(opponentPlayer), times(1))
    }
    @Test
    fun testPlayerLose(){
        gameViewModel.playerTwoPointUp()
        verify(opponentPlayer.wonPoint(player), times(1))
    }


}