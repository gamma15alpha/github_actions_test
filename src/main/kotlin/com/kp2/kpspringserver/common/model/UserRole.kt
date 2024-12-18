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

    const val ROLE_MODERATOR_ID = 2
    const val ROLE_LECTOR_ID = 3
    const val ROLE_STUDENT_ID = 4
    const val ROLE_USER_ID = 5

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
    @field:Min(value = UserRoleConstants.MIN_STATUS.toLong(), message = "Статус не может быть меньше " +
            "${UserRoleConstants.MIN_STATUS}")
    @field:Max(value = UserRoleConstants.MAX_STATUS.toLong(), message = "Статус не может быть больше " +
            "${UserRoleConstants.MAX_STATUS}")
    var status: Int?,

    @Column(nullable = false)
    var isDeleted: Boolean? = false,
) {
    constructor() : this(null, null, null, false)

    fun getRoleName(): String {
        return when (status) {
            UserRoleConstants.MIN_STATUS -> UserRoleConstants.ROLE_ADMIN
            UserRoleConstants.ROLE_MODERATOR_ID -> UserRoleConstants.ROLE_MODERATOR
            UserRoleConstants.ROLE_LECTOR_ID -> UserRoleConstants.ROLE_LECTOR
            UserRoleConstants.ROLE_STUDENT_ID -> UserRoleConstants.ROLE_STUDENT
            UserRoleConstants.ROLE_USER_ID -> UserRoleConstants.ROLE_USER
            else -> UserRoleConstants.ROLE_GUEST
        }
    }
}
