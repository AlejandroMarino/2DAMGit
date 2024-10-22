package org.marino.tfgpagao.domain.usecases.member

import org.marino.tfgpagao.data.repository.GroupRepository
import org.marino.tfgpagao.data.repository.MemberRepository
import javax.inject.Inject

class GetMembersOfGroup @Inject constructor(private val memberRepository: MemberRepository) {
    operator fun invoke(groupId: Int) = memberRepository.fetchMembersOfGroup(groupId)
}