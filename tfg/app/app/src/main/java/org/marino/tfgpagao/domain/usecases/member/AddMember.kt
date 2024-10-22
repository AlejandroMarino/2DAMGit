package org.marino.tfgpagao.domain.usecases.member

import org.marino.tfgpagao.data.repository.MemberRepository
import org.marino.tfgpagao.domain.model.Member
import javax.inject.Inject

class AddMember @Inject constructor(private val memberRepository: MemberRepository) {
    operator fun invoke(member: Member) = memberRepository.addMember(member)
}