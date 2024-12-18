package com.kp2.kpspringserver.common.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.sql.Date

@Entity
@Table(name = "works")
data class Work(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @Column(nullable = false)
    @Size(max = 1024, message = "Название не должно превышать 1024 символа.")
    @Pattern(
        regexp = "^[а-яА-ЯёЁa-zA-Z0-9\\s!@#\$%^&*()_+\\-=\\[\\]{};':\",./<>?|]*\$",
        message = "Название может содержать только русские и латинские буквы, цифры и специальные символы."
    )
    var title: String? = null,
    @Column(nullable = false)
    @Pattern(
        regexp = "^[а-яА-ЯёЁa-zA-Z0-9\\s!@#\$%^&*()_+\\-=\\[\\]{};':\",./<>?|]*\$",
        message = "Название может содержать только русские и латинские буквы, цифры и специальные символы."
    )
    var description: String? = null,
    @Column(nullable = false)
    @Min(1, message = "Отметка не может быть ниже чем 1")
    @Max(5, message = "Отметка не может быть выше чем 5")
    var mark: Int? = null,
    @Column(nullable = false)
    var date: Date? = null,
    @Column(nullable = false)
    var isDeleted: Boolean? = false,

    @ManyToOne()
    var student: Student? = null,
    @ManyToOne()
    var status: WorkStatus? = null,
    @ManyToOne
    @JsonBackReference
    var task: Task? = null
)
{
    constructor() : this(null,null,null,null,null,null, null, null, null)
}
