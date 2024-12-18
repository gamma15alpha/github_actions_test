package com.kp2.kpspringserver.controllers.api

import com.kp2.kpspringserver.common.model.Work
import com.kp2.kpspringserver.common.service.WorkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/works")
class WorkController(@Autowired private val workService: WorkService) {

    // Получить все работы, которые не удалены
    @GetMapping
    fun getAll(@RequestParam(defaultValue = "false") isDeleted: Boolean): List<Work> {
        return workService.getAll(isDeleted)
    }

    // Получить работу по ID
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Work> {
        val work = workService.getById(id)
        return if (work != null) {
            ResponseEntity.ok(work)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // Сохранить работу
    @PostMapping
    fun save(@RequestBody work: Work): ResponseEntity<Work> {
        val savedWork = workService.save(work)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWork)
    }

    // Мягкое удаление работ по списку ID
    @Transactional
    @PostMapping("/soft-delete")
    fun softDelete(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        workService.softDelete(ids)
        return ResponseEntity.noContent().build()
    }

    // Восстановление работ по списку ID
    @Transactional
    @PostMapping("/restore")
    fun restore(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        workService.restore(ids)
        return ResponseEntity.noContent().build()
    }

    // Полное удаление работ по списку ID
    @Transactional
    @DeleteMapping
    fun delete(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        workService.delete(ids)
        return ResponseEntity.noContent().build()
    }
}
