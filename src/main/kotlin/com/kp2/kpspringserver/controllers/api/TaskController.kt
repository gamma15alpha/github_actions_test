package com.kp2.kpspringserver.controllers.api

import com.kp2.kpspringserver.common.model.Task
import com.kp2.kpspringserver.common.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tasks")
class TaskController(@Autowired private val taskService: TaskService) {

    // Получить все задачи с фильтрацией по удаленности
    @GetMapping
    fun getAll(@RequestParam(defaultValue = "false") isDeleted: Boolean): List<Task> {
        return taskService.getAll(isDeleted)
    }

    // Получить все задачи
    @GetMapping("/all")
    fun getAll(): List<Task> {
        return taskService.getAll()
    }

    // Получить задачу по ID
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Task> {
        val task = taskService.getById(id)
        return if (task != null) {
            ResponseEntity.ok(task)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // Поиск задач по названию и удаленности
    @GetMapping("/search")
    fun search(
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) isDeleted: Boolean?
    ): List<Task> {
        return taskService.searchByTitleAndIsDeleted(title, isDeleted)
    }

    // Сохранить новую задачу
    @PostMapping
    fun save(@RequestBody task: Task): ResponseEntity<Task> {
        val savedTask = taskService.save(task)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask)
    }

    // Мягкое удаление задач по списку ID
    @Transactional
    @PostMapping("/soft-delete")
    fun softDelete(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        taskService.softDelete(ids)
        return ResponseEntity.noContent().build()
    }

    // Восстановление задач по списку ID
    @Transactional
    @PostMapping("/restore")
    fun restore(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        taskService.restore(ids)
        return ResponseEntity.noContent().build()
    }

    // Полное удаление задач по списку ID
    @Transactional
    @DeleteMapping
    fun delete(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        taskService.delete(ids)
        return ResponseEntity.noContent().build()
    }
}
