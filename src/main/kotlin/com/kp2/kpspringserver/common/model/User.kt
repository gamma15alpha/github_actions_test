package com.kp2.kpspringserver.common.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @field:NotBlank(message = "Username is required")
    @Column(nullable = false, unique = true)
    var login: String?,

    @field:NotBlank(message = "Password is required")
    var hashedPassword: String?,

    @field:NotBlank(message = "Name is required")
    var name: String?,

    @field:NotBlank(message = "Surname is required")
    var surname: String?,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email should be valid")
    var email: String?,

    @field:NotBlank(message = "Phone is required")
    var phone: String?,

    @field:NotNull(message = "Created date is required")
    var createdDate: Date? = null,

    @field:NotNull(message = "Active status is required")
    var isActive: Boolean? = true,

    @ManyToOne
    var role: UserRole? = null
) : UserDetails {
    constructor() : this(null, null, null, null, null, null, null, null, null)

    @JsonIgnore
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOfNotNull(role?.getRoleName())
            .map { SimpleGrantedAuthority(it) }
    }

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean = true

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean = true

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean = true

    @JsonIgnore
    override fun isEnabled(): Boolean = isActive ?: false

    @JsonIgnore
    override fun getUsername(): String? = login

    @JsonIgnore
    override fun getPassword(): String? = hashedPassword
}
