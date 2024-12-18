package com.kp2.kpspringserver.controllers.api

import com.kp2.kpspringserver.common.model.Subject
import com.kp2.kpspringserver.common.service.SubjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/subjects")
class SubjectController(@Autowired private val subjectService: SubjectService) {

    // Получить все предметы с фильтрацией по удаленности
    @GetMapping
    fun getAll(@RequestParam(defaultValue = "false") isDeleted: Boolean): List<Subject> {
        return subjectService.getAll(isDeleted)
    }

    // Получить все предметы
    @GetMapping("/all")
    fun getAll(): List<Subject> {
        return subjectService.getAll(false)
    }

    // Получить предмет по ID
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Subject> {
        val subject = subjectService.getById(id)
        return if (subject != null) {
            ResponseEntity.ok(subject)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // Поиск предметов по имени и удаленности
    @GetMapping("/search")
    fun search(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) isDeleted: Boolean?
    ): List<Subject> {
        return subjectService.searchByNameAndIsDeleted(name, isDeleted)
    }

    // Фильтрация предметов по курсу и удаленности
    @GetMapping("/filter")
    fun filterByCourse(
        @RequestParam course: Int,
        @RequestParam isDeleted: Boolean
    ): List<Subject> {
        return subjectService.filterByCourse(course, isDeleted)
    }

    // Сохранение нового предмета
    @PostMapping
    fun save(@RequestBody subject: Subject): ResponseEntity<Subject> {
        val savedSubject = subjectService.save(subject)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubject)
    }

    // Мягкое удаление предметов по списку ID
    @Transactional
    @PostMapping("/soft-delete")
    fun softDelete(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        subjectService.softDelete(ids)
        return ResponseEntity.noContent().build()
    }

    // Восстановление предметов по списку ID
    @Transactional
    @PostMapping("/restore")
    fun restore(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        subjectService.restore(ids)
        return ResponseEntity.noContent().build()
    }

    // Полное удаление предметов по списку ID
    @Transactional
    @DeleteMapping
    fun delete(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        subjectService.delete(ids)
        return ResponseEntity.noContent().build()
    }
}
