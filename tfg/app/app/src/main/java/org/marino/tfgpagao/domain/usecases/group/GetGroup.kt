package org.marino.tfgpagao.domain.usecases.group

import org.marino.tfgpagao.data.repository.GroupRepository
import javax.inject.Inject

class GetGroup @Inject constructor(private val groupRepository: GroupRepository) {
    operator fun invoke(id: Int) = groupRepository.fetchGroup(id)
}