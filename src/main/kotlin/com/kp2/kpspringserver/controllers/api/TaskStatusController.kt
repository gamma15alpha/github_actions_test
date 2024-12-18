package com.kp2.kpspringserver.controllers.api

import com.kp2.kpspringserver.common.model.TaskStatus
import com.kp2.kpspringserver.common.service.TaskStatusService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/task-statuses")
class TaskStatusController @Autowired constructor(private val taskStatusService: TaskStatusService) {

    // Поиск состояний задач по статусу и флагу удаления
    @GetMapping("/search")
    fun searchTaskStatuses(
        @RequestParam(required = false) status: String?,
        @RequestParam(required = false) isDeleted: Boolean?
    ): ResponseEntity<List<TaskStatus>> {
        val taskStatuses = taskStatusService.searchByStatusAndIsDeleted(status, isDeleted)
        return ResponseEntity.ok(taskStatuses)
    }

    // Получение всех состояний задач с фильтрацией по флагу удаления
    @GetMapping
    fun getAllTaskStatuses(@RequestParam(required = false) isDeleted: Boolean?): ResponseEntity<List<TaskStatus>> {
        val taskStatuses = if (isDeleted != null) {
            taskStatusService.getAll(isDeleted)
        } else {
            taskStatusService.getAll()
        }
        return ResponseEntity.ok(taskStatuses)
    }

    // Получение состояния задачи по id
    @GetMapping("/{id}")
    fun getTaskStatusById(@PathVariable id: Long): ResponseEntity<TaskStatus> {
        val taskStatus = taskStatusService.getById(id)
        return if (taskStatus != null) {
            ResponseEntity.ok(taskStatus)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // Создание нового состояния задачи
    @PostMapping
    fun createTaskStatus(@RequestBody taskStatus: TaskStatus): ResponseEntity<TaskStatus> {
        val savedTaskStatus = taskStatusService.save(taskStatus)
        return ResponseEntity.ok(savedTaskStatus)
    }

    // Мягкое удаление состояний задач
    @PostMapping("/soft-delete")
    fun softDeleteTaskStatuses(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        taskStatusService.softDelete(ids)
        return ResponseEntity.noContent().build()
    }

    // Восстановление удаленных состояний задач
    @PostMapping("/restore")
    fun restoreTaskStatuses(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        taskStatusService.restore(ids)
        return ResponseEntity.noContent().build()
    }

    // Удаление состояний задач
    @DeleteMapping
    fun deleteTaskStatuses(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        taskStatusService.delete(ids)
        return ResponseEntity.noContent().build()
    }
}
