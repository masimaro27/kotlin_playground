package com.tutorial.kotlin_playground.infra.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component

@ConstructorBinding
@ConfigurationProperties(prefix = "szs.datasource")
data class DatasourceProperties(
    val main: Properties,
    val replica: Properties
) {
    data class Properties(
        val driverClassName: String,
        val jdbcUrl: String,
        val username: String,
        val password: String
    )
}



