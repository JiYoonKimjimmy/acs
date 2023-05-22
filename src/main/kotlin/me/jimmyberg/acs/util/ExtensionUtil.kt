package me.jimmyberg.acs.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun today(): String {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
}