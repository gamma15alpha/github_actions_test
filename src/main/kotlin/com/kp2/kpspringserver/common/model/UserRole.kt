package com.kp2.kpspringserver.common.model

import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

object UserRoleConstants {
    const val ROLE_ADMIN = "ROLE_ADMIN"
    const val ROLE_MODERATOR = "ROLE_MODERATOR"
    const val ROLE_LECTOR = "ROLE_LECTOR"
    const val ROLE_STUDENT = "ROLE_STUDENT"
    const val ROLE_USER = "ROLE_USER"
    const val ROLE_GUEST = "ROLE_GUEST"

    const val MIN_STATUS = 1
    const val MAX_STATUS = 6
}

@Entity
@Table(name = "user_roles")
data class UserRole(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    @field:NotBlank(message = "Role name is required")
    var name: String?,

    @Column(nullable = false)
    @field:Min(value = UserRoleConstants.MIN_STATUS.toLong(), message = "Статус не может быть меньше ${UserRoleConstants.MIN_STATUS}")
    @field:Max(value = UserRoleConstants.MAX_STATUS.toLong(), message = "Статус не может быть больше ${UserRoleConstants.MAX_STATUS}")
    var status: Int?,

    @Column(nullable = false)
    var isDeleted: Boolean? = false,
) {
    constructor() : this(null, null, null, false)

    fun getRoleName(): String {
        return when (status) {
            UserRoleConstants.MIN_STATUS -> UserRoleConstants.ROLE_ADMIN
            2 -> UserRoleConstants.ROLE_MODERATOR
            3 -> UserRoleConstants.ROLE_LECTOR
            4 -> UserRoleConstants.ROLE_STUDENT
            5 -> UserRoleConstants.ROLE_USER
            else -> UserRoleConstants.ROLE_GUEST
        }
    }
}
