package me.jimmyberg.acs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class DemoAcsApplication

fun main(args: Array<String>) {
    runApplication<DemoAcsApplication>(*args)
}
