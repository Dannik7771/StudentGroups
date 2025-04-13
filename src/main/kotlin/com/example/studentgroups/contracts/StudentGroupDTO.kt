package com.example.studentgroups.contracts

data class StudentGroupDTO(
    val id: Long? = null,
    val name: String,
    val parent_id: Long?,
    val subGroups: List<StudentGroupDTO>?
) {
}