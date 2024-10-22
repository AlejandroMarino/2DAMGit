package org.marino.tfgpagao.data.remote

import org.marino.tfgpagao.domain.model.Member
import org.marino.tfgpagao.network.services.MemberService
import org.marino.tfgpagao.utils.NetworkResult
import javax.inject.Inject

class MemberRemoteDataSource @Inject constructor(private val memberService: MemberService) :
    BaseApiResponse() {

    suspend fun fetchMembersOfGroup(groupId: Int): NetworkResult<List<Member>> {
        return safeApiCall(apiCall = { memberService.getMembersOfGroup(groupId) })
    }

    suspend fun fetchMember(id: Int): NetworkResult<Member> {
        return safeApiCall(apiCall = { memberService.getMember(id) })
    }

    suspend fun getBalanceOfMember(id: Int): NetworkResult<Double> {
        return safeApiCall(apiCall = {memberService.getMemberBalance(id)})
    }

    suspend fun addMember(member: Member): NetworkResult<Void> {
        return safeApiCall(apiCall = { memberService.add(member) })
    }
}