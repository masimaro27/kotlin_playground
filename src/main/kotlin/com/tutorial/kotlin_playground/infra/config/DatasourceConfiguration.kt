package com.tutorial.kotlin_playground.infra.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.*
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
@EnableTransactionManagement
@EnableConfigurationProperties(value = [DatasourceProperties::class])
@Profile("!local")
class DataSourceConfiguration(
    val datasourceProperties: DatasourceProperties
) {
    @Bean
    fun mainDataSource(): DataSource = createDataSource(datasourceProperties.main)

    @Bean
    fun replicaDataSource(): DataSource = createDataSource(datasourceProperties.replica)

    @Bean
    fun routingDataSource(@Qualifier("mainDataSource") mainDataSource: DataSource, @Qualifier("replicaDataSource") replicaDataSource: DataSource) = CustomRoutingDataSource(targetDataSources(mainDataSource, replicaDataSource), replicaDataSource)

    @Primary
    @Bean
    fun dataSource(@Qualifier("routingDataSource") routingDataSource: DataSource) = LazyConnectionDataSourceProxy(routingDataSource)

    private fun targetDataSources(mainDataSource: DataSource, replicaDataSource: DataSource) = mapOf<Any, Any>(
        CustomRoutingDataSource.DataSourceType.MAIN to mainDataSource,
        CustomRoutingDataSource.DataSourceType.REPLICA to replicaDataSource
    )

    private fun createDataSource(properties: DatasourceProperties.Properties) = DataSourceBuilder.create()
                .type(HikariDataSource::class.java)
                .driverClassName(properties.driverClassName)
                .url(properties.jdbcUrl)
                .username(properties.username)
                .password(properties.password)
                .build()

}