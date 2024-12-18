package com.kp2.kpspringserver.controllers.api

import com.kp2.kpspringserver.common.model.WorkStatus
import com.kp2.kpspringserver.common.service.WorkStatusService
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.beans.factory.annotation.Autowired

@RestController
@RequestMapping("/api/work-statuses")
class WorkStatusController @Autowired constructor(private val workStatusService: WorkStatusService) {

    @GetMapping
    fun getAllWorkStatuses(@RequestParam(required = false) isDeleted: Boolean?): ResponseEntity<List<WorkStatus>> {
        val workStatuses = if (isDeleted != null) {
            workStatusService.getAll(isDeleted)
        } else {
            workStatusService.getAll()
        }
        return ResponseEntity.ok(workStatuses)
    }

    @GetMapping("/{id}")
    fun getWorkStatusById(@PathVariable id: Long): ResponseEntity<WorkStatus> {
        val workStatus = workStatusService.getById(id)
        return if (workStatus != null) {
            ResponseEntity.ok(workStatus)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createWorkStatus(@RequestBody workStatus: WorkStatus): ResponseEntity<WorkStatus> {
        val savedWorkStatus = workStatusService.save(workStatus)
        return ResponseEntity.ok(savedWorkStatus)
    }

    @PostMapping("/soft-delete")
    fun softDeleteWorkStatuses(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        workStatusService.softDelete(ids)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/restore")
    fun restoreWorkStatuses(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        workStatusService.restore(ids)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping
    fun deleteWorkStatuses(@RequestBody ids: List<Long>): ResponseEntity<Void> {
        workStatusService.delete(ids)
        return ResponseEntity.noContent().build()
    }
}
