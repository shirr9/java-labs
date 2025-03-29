plugins {
    id("java")
    id("org.flywaydb.flyway") version "9.22.3"
    id("org.hibernate.orm") version "7.0.0.Beta4"
}

group = "org.sirr"
version = "unspecified"

repositories {
    mavenCentral()
}
configurations {
    create("flywayMigration")
}
dependencies {
    testImplementation ("org.mockito:mockito-core:5.7.0")
    testImplementation ("org.mockito:mockito-junit-jupiter:5.7.0")
    implementation (platform ("org.hibernate.orm:hibernate-platform:6.6.11.Final"))
    implementation ("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation ("org.hibernate.orm:hibernate-core")
    implementation ("jakarta.transaction:jakarta.transaction-api")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("org.flywaydb:flyway-core:9.22.3")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    "flywayMigration"("org.postgresql:postgresql:42.7.3")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<JavaExec>("run") {
    group = "application"
    description = "Runs the Java application"
    mainClass.set("Main")
    classpath = sourceSets["main"].runtimeClasspath
}

flyway {
    url = "jdbc:postgresql://localhost:5432/lab2db"
    user = "postgres"
    password = "postgres"
    locations = arrayOf("classpath:db/migration")
    driver = "org.postgresql.Driver"
    configurations = arrayOf("flywayMigration")
}
