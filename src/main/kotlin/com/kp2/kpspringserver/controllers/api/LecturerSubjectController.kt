package com.kp2.kpspringserver.controllers.api

import com.kp2.kpspringserver.common.model.LecturerSubject
import com.kp2.kpspringserver.common.model.Subject
import com.kp2.kpspringserver.common.model.User
import com.kp2.kpspringserver.common.service.LecturerSubjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/lecturer-subjects")
class LecturerSubjectController(@Autowired private val lecturerSubjectService: LecturerSubjectService) {

    // Получить все связи лектора с предметами по предмету
    @GetMapping("/by-subject")
    fun getBySubject(@RequestParam subjectId: Long): List<LecturerSubject> {
        val subject = Subject()
        subject.id = subjectId
        return lecturerSubjectService.getBySubject(subject)
    }

    // Получить все связи лектора с предметами по лектору
    @GetMapping("/by-lecturer")
    fun getByLecturer(@RequestParam lecturerId: Long): List<LecturerSubject> {
        val lecturer = User()
        lecturer.id = lecturerId// предполагается, что id лектора передается
        return lecturerSubjectService.getByLecturer(lecturer)
    }

    // Получить связь лектора и предмета по ID
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<LecturerSubject> {
        val lecturerSubject = lecturerSubjectService.getById(id)
        return if (lecturerSubject != null) {
            ResponseEntity.ok(lecturerSubject)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun save(@RequestBody lecturerSubject: LecturerSubject): ResponseEntity<LecturerSubject> {
        val savedLecturerSubject = lecturerSubjectService.save(lecturerSubject)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLecturerSubject)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        lecturerSubjectService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
