import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import java.net.URI


buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.18")
    }
}


plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    id("com.google.protobuf") version "0.8.18"
    id("com.github.marcoferrer.kroto-plus") version "0.6.1"
    `java-library`
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    withSourcesJar()
    withJavadocJar()
}

val projectVersion = project.property("version")?.takeIf { it != "unspecified" } ?: "1.0-SNAPSHOT"

object Versions {
    const val Protobuf = "3.19.1"
    const val Provenance = "1.8.0-rc10"

    // Test dependencies
    const val JunitJupiter = "5.8.2"

    // Plugins
    const val Kotlin = "1.5.0"
    const val ProtobufPlugin = "0.8.18"
}
dependencies {

//    implementation("io.provenance.pbc:pbc-proto:${Versions.Provenance}")
    implementation("io.provenance:proto-kotlin:${Versions.Provenance}")
    implementation("io.provenance.scope:util:0.4.9")

    implementation("com.google.protobuf:protobuf-java:${Versions.Protobuf}")
    implementation("com.google.protobuf:protobuf-java-util:${Versions.Protobuf}")
    implementation("javax.annotation:javax.annotation-api:1.3.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.JunitJupiter}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.JunitJupiter}")
}



configurations.forEach { it.exclude("org.slf4j", "slf4j-api") }

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("junit-jupiter")
    }
    testLogging {
        events("passed", "skipped", "failed")
    }
    reports.html.isEnabled = true
}



publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "io.provenance.model"
            artifactId = "asset-specifications"
            version = "$projectVersion"

            from(components["java"])

            pom {
                name.set("Provenance Asset Specifications")
                description.set("Asset/NFT scope specifications for Provenance Blockchain metadata module")
                url.set("https://provenance.io")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("vwagner")
                        name.set("Valerie Wagner")
                        email.set("tech@figure.com")
                    }
                }

                scm {
                    connection.set("git@github.com:provenance-io/asset-specifications.git")
                    developerConnection.set("git@github.com/provenance-io/asset-specifications.git")
                    url.set("https://github.com/provenance-io/asset-specifications")
                }
            }
        }
    }
    signing {
        sign(publishing.publications["maven"])
    }
}


nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(findProject("ossrhUsername")?.toString() ?: System.getenv("OSSRH_USERNAME"))
            password.set(findProject("ossrhPassword")?.toString() ?: System.getenv("OSSRH_PASSWORD"))
            stagingProfileId.set("3180ca260b82a7") // prevents querying for the staging profile id, performance optimization
        }
    }
}

object Repos {
    private object sonatype {
        const val snapshots = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
        const val releases = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
    }

    fun RepositoryHandler.sonatypeOss(projectVersion: String): MavenArtifactRepository {
        val murl =
            if (projectVersion == "1.0-SNAPSHOT") sonatype.snapshots
            else sonatype.releases

        return maven {
            name = "Sonatype"
            url = URI.create(murl)
            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_PASSWORD")
            }
        }
    }
}
