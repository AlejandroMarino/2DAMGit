package org.marino.tfgpagao.domain.usecases.group

import org.marino.tfgpagao.data.repository.GroupRepository
import org.marino.tfgpagao.domain.model.Group
import javax.inject.Inject

class AddGroup @Inject constructor(private val groupRepository: GroupRepository) {
    operator fun invoke(group: Group) = groupRepository.addGroup(group)
}