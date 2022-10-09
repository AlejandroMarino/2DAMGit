package com.moviles.appf1teams.ui.pantallaMain

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.slider.Slider
import com.moviles.appf1teams.R
import com.moviles.appf1teams.databinding.ActivityMainBinding
import com.moviles.appf1teams.domain.usecases.teams.AddTeam
import com.moviles.appf1teams.domain.usecases.teams.Delete
import com.moviles.appf1teams.domain.usecases.teams.GetTeams
import com.moviles.appf1teams.domain.usecases.teams.Update
import com.moviles.appf1teams.utils.StringProvider

@SuppressLint("UseSwitchCompatOrMaterialCode")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var actualIndex : Int = 0

//    private lateinit var name: EditText
//    private lateinit var performance: Slider
//    private lateinit var tyre: MaterialButtonToggleGroup
//    private lateinit var winner: Switch
//    private lateinit var add: Button
//    private lateinit var delete: Button
//    private lateinit var update: Button
//    private lateinit var next: Button
//    private lateinit var previous: Button

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            StringProvider.instance(this),
            AddTeam(),
            Delete(),
            Update(),
            GetTeams(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        name = this.findViewById(R.id.textField)
//        performance = findViewById(R.id.slider)
//        tyre = findViewById(R.id.toggleButton)
//        winner = findViewById(R.id.switchMaterial)
//        add = findViewById(R.id.floatingActionButton)
//        delete = findViewById(R.id.itemDelete)
//        update = findViewById(R.id.itemUpdate)
//        next = findViewById(R.id.itemRight)
//        previous = findViewById(R.id.itemLeft)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)


            viewModel.uiState.observe(this@MainActivity) { state ->
                state.error?.let { error ->
                    Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
                    viewModel.errorMostrado()
                }
                if (state.error == null){
//                    name.setText(state.team.name)
//                    performance.value = state.team.performance
//                    tyre.check(state.team.tyre)
//                    winner.isChecked = state.team.winner
                }
            }
        }

//        previous.setOnClickListener {
//            val newIndex = viewModel.previousTeam(actualIndex)
//            actualIndex = newIndex
//        }
//
//        next.setOnClickListener {
//            val newIndex = viewModel.nextTeam(actualIndex)
//            actualIndex = newIndex
//        }

    }
}