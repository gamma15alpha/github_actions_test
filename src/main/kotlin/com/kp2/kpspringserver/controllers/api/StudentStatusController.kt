package com.kp2.kpspringserver.controllers.api

import com.kp2.kpspringserver.common.model.StudentStatus
import com.kp2.kpspringserver.common.service.StudentStatusService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/student-statuses")
class StudentStatusController(@Autowired private val studentStatusService: StudentStatusService) {

    // Получить все статусы студентов с фильтрацией по удаленности
    @GetMapping
    fun getAll(@RequestParam(defaultValue = "false") isDeleted: Boolean): List<StudentStatus> {
        return studentStatusService.getAll(isDeleted)
    }

    // Получить все статусы студентов (по умолчанию не удаленные)
    @GetMapping("/all")
    fun getAll(): List<StudentStatus> {
        return studentStatusService.getAll(false)
    }

    // Получить статус студента по ID
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<StudentStatus> {
        val studentStatus = studentStatusService.getById(id)
        return if (studentStatus != null) {
            ResponseEntity.ok(studentStatus)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // Поиск статусов студентов по имени и удаленности
    @GetMapping("/search")
    fun search(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) isDeleted: Boolean?
    ): List<StudentStatus> {
        return studentStatusService.search(name, isDeleted)
    }

    // Сохранение нового статуса студента
    @PostMapping
    fun save(@RequestBody studentStatus: StudentStatus): ResponseEntity<StudentStatus> {
        val savedStudentStatus = studentStatusService.save(studentStatus)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudentStatus)
    }

    // Мягкое удаление статусов студентов по списку ID
    @Transactional
    @PostMapping("/soft-delete")
    fun softDelete(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        studentStatusService.softDelete(ids)
        return ResponseEntity.noContent().build()
    }

    // Восстановление статусов студентов по списку ID
    @Transactional
    @PostMapping("/restore")
    fun restore(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        studentStatusService.restore(ids)
        return ResponseEntity.noContent().build()
    }

    // Полное удаление статусов студентов по списку ID
    @Transactional
    @DeleteMapping
    fun delete(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        studentStatusService.delete(ids)
        return ResponseEntity.noContent().build()
    }
}
