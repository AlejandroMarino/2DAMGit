package org.marino.tfgpagao.ui.screens.groupCreation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.marino.tfgpagao.domain.usecases.group.AddGroup
import org.marino.tfgpagao.utils.StringProvider
import javax.inject.Inject

@HiltViewModel
class GroupCreationViewModel @Inject constructor(
    private val addGroup: AddGroup,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _state = MutableStateFlow(GroupCreationState())
    val state: StateFlow<GroupCreationState> = _state.asStateFlow()

    fun handleEvent(event: GroupCreationEvent) {
        when (event) {
            GroupCreationEvent.AddGroup -> {
                addGroup()
            }
            GroupCreationEvent.ErrorCatch -> {
                errorCatch()
            }
        }
    }

    private fun errorCatch() {
        _state.update {
            it.copy(
                error = "",
            )
        }
    }

    private fun addGroup() {

    }
}