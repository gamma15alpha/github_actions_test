package com.kp2.kpspringserver.controllers.api

import com.kp2.kpspringserver.common.model.EducationalActivity
import com.kp2.kpspringserver.common.service.EducationalActivityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.sql.Date

@RestController
@RequestMapping("/api/educational-activities")
class EducationalActivityController(@Autowired private val educationalActivityService: EducationalActivityService) {

    // Поиск образовательной деятельности по дате и статусу isDeleted
    @GetMapping("/search")
    fun search(@RequestParam(required = false) date: Date?,
               @RequestParam(required = false) isDeleted: Boolean?): List<EducationalActivity> {
        return educationalActivityService.searchByDateAndIsDeleted(date, isDeleted)
    }

    // Получить все образовательные деятельности с фильтрацией по isDeleted
    @GetMapping
    fun getAll(@RequestParam(required = false) isDeleted: Boolean?): List<EducationalActivity> {
        return if (isDeleted != null) {
            educationalActivityService.getAll(isDeleted)
        } else {
            educationalActivityService.getAll(true)
        }
    }

    // Получить все образовательные деятельности с пагинацией
    @GetMapping("/page")
    fun getAllPage(@RequestParam page: Int, @RequestParam size: Int): Page<EducationalActivity> {
        return educationalActivityService.getAllPage(page, size)
    }

    // Получить образовательную деятельность по ID
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<EducationalActivity> {
        val educationalActivity = educationalActivityService.getById(id)
        return if (educationalActivity != null) {
            ResponseEntity.ok(educationalActivity)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // Создать новую образовательную деятельность
    @PostMapping
    fun save(@RequestBody educationalActivity: EducationalActivity): ResponseEntity<EducationalActivity> {
        val savedActivity = educationalActivityService.save(educationalActivity)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedActivity)
    }

    // Мягкое удаление образовательных деятельностей
    @DeleteMapping("/soft-delete")
    fun softDelete(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        educationalActivityService.softDelete(ids)
        return ResponseEntity.noContent().build()
    }

    // Восстановление удаленных образовательных деятельностей
    @PutMapping("/restore")
    fun restore(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        educationalActivityService.restore(ids)
        return ResponseEntity.noContent().build()
    }

    // Удаление образовательных деятельностей
    @DeleteMapping("/delete")
    fun delete(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        educationalActivityService.delete(ids)
        return ResponseEntity.noContent().build()
    }
}
