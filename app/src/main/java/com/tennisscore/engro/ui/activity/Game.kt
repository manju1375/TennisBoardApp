package com.tennisscore.engro.ui.activity

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tennisscore.engro.R
import com.tennisscore.engro.databinding.ActivityGameBinding
import com.tennisscore.engro.model.Player
import com.tennisscore.engro.ui.viewmodel.GameViewModel


class Game : AppCompatActivity() {
    companion object {
        const val ADVANCE_POINTS = 45
    }
    private var applauseMatch: MediaPlayer? = null
    private var applauseSet: MediaPlayer? = null
    private var beep: MediaPlayer? = null
    private var changeCourt: MediaPlayer? = null
    private var gamePoint: MediaPlayer? = null
    private var matchPoint: MediaPlayer? = null
    private var pointPlay1UP: ImageButton? = null
    private var pointPlay2UP: ImageButton? = null
    private var setPoint: MediaPlayer? = null
    private lateinit var binding: ActivityGameBinding
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(bundle: Bundle?) {
        requestWindowFeature(1)
        window.setFlags(1024, 1024)
        super.onCreate(bundle)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        beep = MediaPlayer.create(applicationContext, R.raw.beep)
        applauseMatch = MediaPlayer.create(applicationContext, R.raw.aplause_match)
        applauseSet = MediaPlayer.create(applicationContext, R.raw.aplause_set)
        changeCourt = MediaPlayer.create(applicationContext, R.raw.changecourt)
        gamePoint = MediaPlayer.create(applicationContext, R.raw.gamepoint)
        matchPoint = MediaPlayer.create(applicationContext, R.raw.matchpoint)
        setPoint = MediaPlayer.create(applicationContext, R.raw.setpoint)
        setUpObserves()
        binding.gameBPointPlayer1UP.setOnClickListener {
            beep?.start()
            gameViewModel.playerOnePointUp()
        }
        binding.gameBPointPlayer2UP.setOnClickListener {
            beep?.start()
            gameViewModel.playerTwoPointUp()
        }
    }

    private fun setUpObserves() {
        gameViewModel.player1Score.observe(this) { score ->
            if (score == ADVANCE_POINTS) {
                binding.gamePlayer1Score.text = resources.getString(R.string.game_ad)
                return@observe
            }
            binding.gamePlayer1Score.text = "$score"
        }
        gameViewModel.player2Score.observe(this) { score ->
            if (score == ADVANCE_POINTS) {
                binding.gamePlayer2Score.text = resources.getString(R.string.game_ad)
                return@observe
            }
            binding.gamePlayer2Score.text = "$score"
        }
        gameViewModel.isGameOver.observe(this) { player ->
            if (player.isWinner) {
                matchPoint?.start()
                playApplause(1)
                showGameOverDialog(player)
            }
            pointPlay1UP?.isClickable = !player.isWinner
            pointPlay2UP?.isClickable = !player.isWinner
        }
        gameViewModel.player1Points.observe(this) { playerPoints ->
            binding.player1Points.text = "$playerPoints"
        }
        gameViewModel.player2Points.observe(this) { playerPoints ->
            binding.player2Points.text = "$playerPoints"
        }
        gameViewModel.player1WonSet.observe(this) { isSetWon ->
            if (isSetWon) {
                setPoint?.start()
                playApplause(0)
            }
        }
        gameViewModel.player2WonSet.observe(this) { isSetWon ->
            if (isSetWon) {
                setPoint?.start()
                playApplause(0)
            }
        }
    }

    private fun playApplause(i: Int) {
            if (i == 1) {
                applauseSet?.pause()
                applauseMatch?.start()
                return
            }
            applauseSet?.start()
    }

    private fun showGameOverDialog(playerWon: Player) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.game_end_game))
        builder.setMessage(
            "${playerWon.name} ${resources.getString(R.string.game_win)}"
        )
        builder.setPositiveButton(
            resources.getString(R.string.game_again)
        ) { _, _ ->
            gameViewModel.reStartGame()
        }.setNegativeButton(
            resources.getString(R.string.exit_game)
        ) { _, _ -> finish() }
        builder.show()
    }
}