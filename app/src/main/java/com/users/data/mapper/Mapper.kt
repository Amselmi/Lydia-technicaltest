package com.users.data.mapper

import com.users.data.db.UserEntity
import com.users.data.model.ResultData
import com.users.data.model.UsersData
import com.users.domain.model.User

object Mapper {
    fun mapUser(userEntity: UserEntity) = User(
        id = userEntity.id,
        name = userEntity.name,
        cell = userEntity.cell,
        mail = userEntity.mail,
        thumbnail = userEntity.thumbnail,
        userId = userEntity.userId
    )

    fun mapUsersToEntity(usersData: UsersData?): List<UserEntity> {
        var users = mutableListOf<UserEntity>()
        var page = usersData?.info?.page ?: 1
        usersData?.resultData?.forEachIndexed { position, result ->
            users.add(
                UserEntity(
                    id = ((page - 1).times(20) + (position + 1)),
                    name = getName(result),
                    cell = result?.cell ?: "",
                    mail = result?.email ?: "",
                    thumbnail = result?.picture?.thumbnail,
                    picture = result?.picture?.large,
                    address = getAddress(result),
                    userId = result?.login?.uuid ?: ""
                )
            )
        }
        return users.toList()
    }

    fun mapToUser(usersEntity: List<UserEntity>?): List<User> {
        var users = mutableListOf<User>()
        usersEntity?.forEach { result ->
            users.add(
                User(
                    id = result.id,
                    name = result.name,
                    cell = result.cell,
                    mail = result.mail,
                    thumbnail = result.thumbnail,
                    userId = result.userId ?: ""
                )
            )
        }
        return users.toList()
    }

    private fun getName(resultData: ResultData?): String {
        val title = resultData?.name?.title ?: ""
        val first = resultData?.name?.first ?: ""
        val last = resultData?.name?.last ?: ""
        return "$title $first $last"
    }

    private fun getAddress(resultData: ResultData?): String {
        val streetName = resultData?.location?.street?.name ?: ""
        val streetNumber = resultData?.location?.street?.number ?: ""

        val city = resultData?.location?.city ?: ""
        val postcode = resultData?.location?.postcode ?: ""
        val state = resultData?.location?.state ?: ""
        return "$streetNumber $streetName $city $postcode $state"
    }
}
