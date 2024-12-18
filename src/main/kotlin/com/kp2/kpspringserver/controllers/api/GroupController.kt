package com.kp2.kpspringserver.controllers.api

import com.kp2.kpspringserver.common.model.Group
import com.kp2.kpspringserver.common.service.GroupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/groups")
class GroupController(@Autowired private val groupService: GroupService) {

    // Поиск групп по имени и статусу isDeleted
    @GetMapping("/search")
    fun search(@RequestParam(required = false) name: String?,
               @RequestParam(required = false) isDeleted: Boolean?): List<Group> {
        return groupService.search(name, isDeleted)
    }

    // Фильтрация групп по курсу и статусу isDeleted
    @GetMapping("/filter")
    fun filterByCourse(@RequestParam course: Int, @RequestParam isDeleted: Boolean): List<Group> {
        return groupService.filterByCourse(course, isDeleted)
    }

    // Получить все группы, фильтруя по статусу isDeleted
    @GetMapping
    fun getAll(@RequestParam(required = false) isDeleted: Boolean?): List<Group> {
        return if (isDeleted != null) {
            groupService.getAll(isDeleted)
        } else {
            groupService.getAll()
        }
    }

    // Получить группу по ID
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Group> {
        val group = groupService.getById(id)
        return if (group != null) {
            ResponseEntity.ok(group)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // Сохранить новую группу
    @PostMapping
    fun save(@RequestBody group: Group): ResponseEntity<Group> {
        val savedGroup = groupService.save(group)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGroup)
    }

    // Мягкое удаление групп
    @DeleteMapping("/soft-delete")
    fun softDelete(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        groupService.softDelete(ids)
        return ResponseEntity.noContent().build()
    }

    // Восстановить удаленные группы
    @PutMapping("/restore")
    fun restore(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        groupService.restore(ids)
        return ResponseEntity.noContent().build()
    }

    // Удалить группы
    @DeleteMapping("/delete")
    fun delete(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        groupService.delete(ids)
        return ResponseEntity.noContent().build()
    }
}
