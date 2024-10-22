package org.marino.tfgpagao.domain.usecases.group

import org.marino.tfgpagao.data.repository.GroupRepository
import javax.inject.Inject

class GetGroups @Inject constructor(private val groupRepository: GroupRepository) {
    operator fun invoke() = groupRepository.fetchGroups()
}