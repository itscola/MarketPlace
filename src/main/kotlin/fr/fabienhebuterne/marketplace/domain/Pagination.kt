package fr.fabienhebuterne.marketplace.domain

data class Pagination<T>(
        val results: List<T>,
        val currentPage: Int = 1,
        val total: Int = 0
)