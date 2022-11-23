package com.tennisscore.engro.utils

import com.tennisscore.engro.model.Player

fun playGame(player: Player, opponentPlayer: Player) {
    player.wonSet = false
    opponentPlayer.wonSet = false
    if (player.pointsStack.first() == 40 && opponentPlayer.pointsStack.first() == 40) {
        player.pointsStack.addFirst(45)
    }
    else if (opponentPlayer.pointsStack.first() == 45) {
        opponentPlayer.pointsStack.addFirst(40)
    }
    else if(player.pointsStack.first()==40 || player.pointsStack.first() == 45){
        checkWinnerOrSetterWinner(player,opponentPlayer)
    }
     else {
        player.pointsStack.removeFirst()
        player.presentPoints = player.pointsStack.first()
    }
    println("${player.name}:${player.pointsStack.first()}   ${opponentPlayer.name}:${opponentPlayer.pointsStack.first()}")
}

fun Player.wonPoint(opponentPlayer: Player) {
    playGame(this, opponentPlayer)
}

fun clearPlayersPointsStack(player: Player, opponentPlayer: Player) {
 player.pointsStack.clear()
 opponentPlayer.pointsStack.clear()
 player.pointsStack = ArrayDeque(listOf(0, 15, 30, 40, 50))
 opponentPlayer.pointsStack = ArrayDeque(listOf(0, 15, 30, 40, 50))
}

fun checkWinnerOrSetterWinner(player: Player,opponentPlayer:Player){
    println("Tennis Winner of the set:${player.name}")
    clearPlayersPointsStack(player, opponentPlayer)
    player.noOfWinningSets++;
    player.wonSet = true;
    if (player.noOfWinningSets == 6) {
        player.isWinner = true
        println("Tennis Winner of the Game:${player.name}")
        clearPlayersPointsStack(player,opponentPlayer)
        return
    }
}