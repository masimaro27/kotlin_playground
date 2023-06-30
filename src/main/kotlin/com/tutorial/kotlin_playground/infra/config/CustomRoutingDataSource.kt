package com.tutorial.kotlin_playground.infra.config

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager
import javax.sql.DataSource

class CustomRoutingDataSource(
    targetDataSources: Map<Any, Any>,
    defaultTargetDataSource: DataSource
): AbstractRoutingDataSource() {

    init {
        super.setTargetDataSources(targetDataSources)
        super.setDefaultTargetDataSource(defaultTargetDataSource)
    }

    override fun determineCurrentLookupKey(): Any? {
        return if(TransactionSynchronizationManager.isCurrentTransactionReadOnly()) DataSourceType.MAIN else DataSourceType.REPLICA
    }

    enum class DataSourceType {
        MAIN,
        REPLICA
    }
}