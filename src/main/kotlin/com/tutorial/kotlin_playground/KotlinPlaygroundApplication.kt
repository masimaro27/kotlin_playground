package com.tutorial.kotlin_playground

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication


@SpringBootApplication
class KotlinPlaygroundApplication

fun main(args: Array<String>) {
	runApplication<KotlinPlaygroundApplication>(*args)
}
