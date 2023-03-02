dependencies {
    val ktorVersion = "2.2.3"
    api(project(":hammer-core"))
    api("com.zaxxer:HikariCP:5.0.1")
    api("org.mapstruct:mapstruct:1.5.3.Final")
    api("org.babyfish.jimmer:jimmer-sql-kotlin:0.7.0")
    api("io.ktor:ktor-client-okhttp-jvm:$ktorVersion")
    api("io.ktor:ktor-client-content-negotiation:$ktorVersion")
}