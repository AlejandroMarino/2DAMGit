package com.moviles.appf1teams.ui.pantalla.recycleView

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moviles.appf1teams.databinding.ActivityRecycleviewBinding
import com.moviles.appf1teams.domain.modelo.Team
import com.moviles.appf1teams.domain.usecases.teams.Delete
import com.moviles.appf1teams.domain.usecases.teams.GetTeams
import com.moviles.appf1teams.ui.pantalla.main.MainActivity
import com.moviles.appf1teams.utils.StringProvider

class RecycleViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecycleviewBinding

    private val viewModel: RecycleViewViewModel by viewModels {
        RecycleViewViewModelFactory(
            StringProvider.instance(this),
            GetTeams(),
            Delete(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadTeams()
        binding = ActivityRecycleviewBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            viewModel.uiState.observe(this@RecycleViewActivity) { state ->
                state.error?.let { error ->
                    Toast.makeText(this@RecycleViewActivity, error, Toast.LENGTH_SHORT).show()
                }
                if (state.error == null) {
                    val lista = list
                    val adapter = TeamsAdapter(state.teams, ::clickWatch, ::clickDelete)
                    lista.let {
                        lista.adapter = adapter
                    }
                }
            }

            button.setOnClickListener {
                val intent = Intent(this@RecycleViewActivity, MainActivity::class.java)

                intent.putExtra("team", Team())
                startActivity(intent)
            }
        }
    }

    private fun clickWatch(team: Team) {
        val intent = Intent(this@RecycleViewActivity, MainActivity::class.java)
        intent.putExtra("team", team)
        startActivity(intent)
    }

    private fun clickDelete(team: Team) {
        viewModel.deleteTeam(team)
    }


}