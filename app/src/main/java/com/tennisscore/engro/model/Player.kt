package com.tennisscore.engro.model

data class Player(
    var name: String,
    var pointsStack: ArrayDeque<Int> = ArrayDeque(listOf(0, 15, 30, 40, 50)),
    var presentPoints: Int = pointsStack.first(),
    var isWinner: Boolean = false,
    var noOfWinningSets: Int = 0,
    var wonSet: Boolean = false
)
