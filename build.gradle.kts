buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
        classpath("com.google.protobuf:protobuf-gradle-plugin:${libs.versions.protobufGradlePlugin.get()}")
    }
}


plugins {
    idea
    // The linter doesn't understand this syntax, possibly should file a bug for this, but it compiles and works as intended
    id("io.github.gradle-nexus.publish-plugin") version libs.versions.gradleNexusPlugin.get()
}

configurations.forEach { it.exclude("org.slf4j", "slf4j-api") }

configure<io.github.gradlenexus.publishplugin.NexusPublishExtension> {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(findProject("ossrhUsername")?.toString() ?: System.getenv("OSSRH_USERNAME"))
            password.set(findProject("ossrhPassword")?.toString() ?: System.getenv("OSSRH_PASSWORD"))
            stagingProfileId.set("858b6e4de4734a") // tech.figure staging profile id
        }
    }
}
