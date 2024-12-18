package com.kp2.kpspringserver.controllers.api

import com.kp2.kpspringserver.common.model.UserRole
import com.kp2.kpspringserver.common.service.UserRoleService
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user-roles")
class UserRoleController(
    private val userRoleService: UserRoleService
) {
    @GetMapping
    fun getAllRoles(
        @RequestParam(required = false, defaultValue = "false") paginated: Boolean,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): Any {
        return if (paginated) {
            userRoleService.getAllPaginated(PageRequest.of(page, size))
        } else {
            userRoleService.getAll(false)
        }
    }


    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserRole> {
        val user = userRoleService.getById(id)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createRole(@Valid @RequestBody userRole: UserRole): ResponseEntity<UserRole> {
        val createdUser = userRoleService.save(userRole)
        return ResponseEntity(createdUser, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateRole(@PathVariable id: Long, @Valid @RequestBody userRole: UserRole): ResponseEntity<UserRole> {
        val updatedUserRole = userRoleService.update(id, userRole)
        return if (updatedUserRole != null) {
            ResponseEntity.ok(updatedUserRole)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping
    fun deleteRoles(@RequestParam ids: List<Long>): ResponseEntity<Void> {
        userRoleService.softDelete(ids)
        return ResponseEntity.ok().build()
    }

    @PatchMapping
    fun restoreRoles(@RequestParam ids: List<Long>): ResponseEntity<List<UserRole?>> {
        userRoleService.restore(ids)
        return ResponseEntity.ok().build()
    }
}
