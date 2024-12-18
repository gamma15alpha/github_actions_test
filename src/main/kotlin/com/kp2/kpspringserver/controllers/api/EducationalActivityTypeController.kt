package com.kp2.kpspringserver.controllers.api

import com.kp2.kpspringserver.common.model.EducationalActivityType
import com.kp2.kpspringserver.common.service.EducationalActivityTypeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/educational-activity-types")
class EducationalActivityTypeController(@Autowired private val educationalActivityTypeService: EducationalActivityTypeService) {

    // Поиск типов образовательной деятельности по имени и статусу isDeleted
    @GetMapping("/search")
    fun search(@RequestParam(required = false) name: String?,
               @RequestParam(required = false) isDeleted: Boolean?): List<EducationalActivityType> {
        return educationalActivityTypeService.searchByNameAndIsDeleted(name, isDeleted)
    }

    // Получить все типы образовательной деятельности с фильтрацией по isDeleted
    @GetMapping
    fun getAll(@RequestParam(required = false) isDeleted: Boolean?): List<EducationalActivityType> {
        return if (isDeleted != null) {
            educationalActivityTypeService.getAll(isDeleted)
        } else {
            educationalActivityTypeService.getAll(true)
        }
    }

    // Получить все типы с пагинацией
    @GetMapping("/page")
    fun getAllPage(@RequestParam page: Int, @RequestParam size: Int): Page<EducationalActivityType> {
        return educationalActivityTypeService.getAllPage(page, size)
    }

    // Получить тип образовательной деятельности по ID
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<EducationalActivityType> {
        val educationalActivityType = educationalActivityTypeService.getById(id)
        return if (educationalActivityType != null) {
            ResponseEntity.ok(educationalActivityType)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // Создать новый тип образовательной деятельности
    @PostMapping
    fun save(@RequestBody educationalActivityType: EducationalActivityType): ResponseEntity<EducationalActivityType> {
        val savedType = educationalActivityTypeService.save(educationalActivityType)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedType)
    }

    // Обновить тип образовательной деятельности по ID
    @PutMapping("/{id}")
    fun update(@RequestBody educationalActivityType: EducationalActivityType, @PathVariable id: Long): ResponseEntity<EducationalActivityType> {
        val updatedType = educationalActivityTypeService.update(educationalActivityType, id)
        return if (updatedType != null) {
            ResponseEntity.ok(updatedType)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // Мягкое удаление типов образовательной деятельности
    @DeleteMapping("/soft-delete")
    fun softDelete(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        educationalActivityTypeService.softDelete(ids)
        return ResponseEntity.noContent().build()
    }

    // Восстановление удаленных типов
    @PutMapping("/restore")
    fun restore(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        educationalActivityTypeService.restore(ids)
        return ResponseEntity.noContent().build()
    }

    // Удаление типов образовательной деятельности
    @DeleteMapping("/delete")
    fun delete(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        educationalActivityTypeService.delete(ids)
        return ResponseEntity.noContent().build()
    }
}
