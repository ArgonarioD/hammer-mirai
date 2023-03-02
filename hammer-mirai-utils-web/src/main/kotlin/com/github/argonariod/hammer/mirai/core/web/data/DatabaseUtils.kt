package com.github.argonariod.hammer.mirai.core.web.data

import org.babyfish.jimmer.sql.kt.newKSqlClient
import javax.sql.DataSource

fun initSqlClient(dataSource: DataSource) = newKSqlClient {
    setConnectionManager {
        dataSource.connection.use {
            proceed(it)
        }
    }
}