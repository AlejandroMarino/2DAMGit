package org.marino.tfgpagao.ui.screens.groupCreation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.marino.tfgpagao.domain.model.Group
import org.marino.tfgpagao.domain.model.Member
import org.marino.tfgpagao.domain.usecases.group.AddGroup
import org.marino.tfgpagao.ui.model.MemberVO
import org.marino.tfgpagao.utils.NetworkResult
import org.marino.tfgpagao.utils.StringProvider
import org.marino.tfgpagao.utils.Utils
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
            is GroupCreationEvent.AddGroup -> {
                addGroup(event.goGroupList)
            }

            GroupCreationEvent.ErrorCatch -> {
                errorCatch()
            }

            is GroupCreationEvent.NameChanged -> {
                nameChanged(event.newName)
            }

            is GroupCreationEvent.DescriptionChanged -> {
                descriptionChanged(event.newDescription)
            }

            GroupCreationEvent.MemberAdded -> {
                memberAdded()
            }

            is GroupCreationEvent.MemberNameChanged -> {
                memberNameChanged(event.index, event.newName)
            }

            is GroupCreationEvent.MemberDeleted -> {
                memberDeleted(event.index)
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

    private fun nameChanged(newName: String) {
        _state.update { it.copy(name = newName) }
    }

    private fun descriptionChanged(newDescription: String) {
        _state.update { it.copy(description = newDescription) }
    }

    private fun memberAdded() {
        _state.update { it.copy(members = it.members + MemberVO("", false)) }
    }

    private fun validatedMembers(members: List<MemberVO>): List<MemberVO> {
        val validatedMembers = members.map { member ->
            val isValid =
                member.name.isNotBlank() && (members.count { other ->
                    other.name == member.name
                } == 1)
            member.copy(valid = isValid)
        }
        return validatedMembers
    }

    private fun memberNameChanged(index: Int, newName: String) {
        _state.update {
            val members = it.members.mapIndexed { i, member ->
                if (i == index) member.copy(name = newName) else member
            }

            it.copy(members = validatedMembers(members))
        }
    }

    private fun memberDeleted(index: Int) {
        _state.update {
            val members = it.members.toMutableList().apply { removeAt(index) }
            it.copy(members = validatedMembers(validatedMembers(members)))
        }
    }

    private fun addGroup(goGroupList: () -> Unit) {
        viewModelScope.launch {
            val validList = _state.value.members.filter { it.name.isNotBlank() }.distinct()
            val nameIsValid = _state.value.name.isNotEmpty()
            val membersAreValid = validList.isNotEmpty()
            if (nameIsValid && membersAreValid) {
                val members =
                    validList.map { member -> Member(id = 0, name = member.name, balance = 0.0) }
                val group = Group(
                    name = _state.value.name,
                    description = _state.value.description,
                    members = members,
                    receipts = emptyList()
                )
                if (Utils.hasInternetConnection(stringProvider.context)) {
                    addGroup.invoke(group).collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _state.update {
                                    it.copy(
                                        error = result.message ?: "",
                                        isLoading = false
                                    )
                                }
                            }
                            is NetworkResult.Loading -> _state.update { it.copy(isLoading = true) }
                            is NetworkResult.Success -> {
                                _state.update { it.copy(isLoading = false) }
                                goGroupList()
                            }
                            is NetworkResult.SuccessNoData ->  {
                                _state.update { it.copy(isLoading = false) }
                                goGroupList()
                            }
                        }
                    }
                } else {
                    _state.update { it.copy(error = "Internet connection needed") }
                }
            } else {
                if (!nameIsValid) {
                    _state.update { it.copy(error = "Invalid name for group") }
                } else {
                    _state.update { it.copy(error = "Invalid member list") }
                }
            }
        }
    }
}