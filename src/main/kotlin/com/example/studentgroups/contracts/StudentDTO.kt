package com.example.studentgroups.contracts

import com.example.studentgroups.models.StudentGroup
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

data class StudentDTO(
    val id: Long? = null,
    val name: String,
    val email: String,
    val group_id: Long? = null
) {
}