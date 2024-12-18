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
    @Size(max = WorkConstants.MAX_TITLE_LENGTH, message = "Название не должно превышать" +
            " ${WorkConstants.MAX_TITLE_LENGTH} символов.")
    @Pattern(
        regexp = WorkConstants.TITLE_DESCRIPTION_PATTERN,
        message = "Название может содержать только русские и латинские буквы, цифры и специальные символы."
    )
    var title: String? = null,

    @Column(nullable = false)
    @Pattern(
        regexp = WorkConstants.TITLE_DESCRIPTION_PATTERN,
        message = "Описание может содержать только русские и латинские буквы, цифры и специальные символы."
    )
    var description: String? = null,

    @Column(nullable = false)
    @Min(WorkConstants.MIN_MARK.toLong(), message = "Отметка не может быть ниже чем ${WorkConstants.MIN_MARK}")
    @Max(WorkConstants.MAX_MARK.toLong(), message = "Отметка не может быть выше чем ${WorkConstants.MAX_MARK}")
    var mark: Int? = null,

    @Column(nullable = false)
    var date: Date? = null,

    @Column(nullable = false)
    var isDeleted: Boolean? = false,

    @ManyToOne
    var student: Student? = null,

    @ManyToOne
    var status: WorkStatus? = null,

    @ManyToOne
    @JsonBackReference
    var task: Task? = null
) {
    constructor() : this(null, null, null, null, null, null,
        null, null, null)
}

object WorkConstants {
    const val MAX_TITLE_LENGTH = 1024
    const val MIN_MARK = 1
    const val MAX_MARK = 5
    const val TITLE_DESCRIPTION_PATTERN =
        "^[а-яА-ЯёЁa-zA-Z0-9\\s!@#\$%^&*()_+\\-=\\[\\]{};':\",./<>?|]*\$"
}
