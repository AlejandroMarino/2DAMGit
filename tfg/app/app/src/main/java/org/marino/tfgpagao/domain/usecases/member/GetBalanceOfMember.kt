package org.marino.tfgpagao.domain.usecases.member

import org.marino.tfgpagao.data.repository.MemberRepository
import javax.inject.Inject

class GetBalanceOfMember @Inject constructor(private val memberRepository: MemberRepository){
    operator fun invoke(id: Int) = memberRepository.getBalanceOfMember(id)
}