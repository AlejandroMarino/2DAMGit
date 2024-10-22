package org.marino.tfgpagao.data.remote

import org.marino.tfgpagao.domain.model.Group
import org.marino.tfgpagao.network.services.GroupService
import org.marino.tfgpagao.utils.NetworkResult
import javax.inject.Inject

class GroupRemoteDataSource @Inject constructor(private val groupService: GroupService) :
    BaseApiResponse() {

    suspend fun fetchGroups(): NetworkResult<List<Group>> {
        return safeApiCall(apiCall = { groupService.getGroups() })
    }

    suspend fun fetchGroup(id: Int): NetworkResult<Group> {
        return safeApiCall(apiCall = { groupService.getGroup(id) })
    }

    suspend fun addGroup(group: Group): NetworkResult<Void> {
        return safeApiCall(apiCall = { groupService.add(group) })
    }
}