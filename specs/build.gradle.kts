plugins {
    id("core-config")
    `maven-publish`
    signing
}

dependencies {
    // Do not expose the kotlin version via API because consumers can easily be backwards compatible
    implementation(libs.bundles.kotlin)

    listOf(
        libs.bundles.protobuf,
        libs.provenanceProto,
        libs.provenanceScopeUtil,
    ).forEach(::api)
}

val artifactName = "asset-$name"

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = artifactName
            version = project.version.toString()

            from(components["java"])

            pom {
                name.set("Figure Technologies Asset Specifications")
                description.set("Scope specifications for classifying various types of Asset proto")
                url.set("https://figure.tech")
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
                        email.set("vwagner@figure.com")
                    }
                    developer {
                        id.set("hyperschwartz")
                        name.set("Jake Schwartz")
                        email.set("jschwartz@figure.com")
                    }
                }

                scm {
                    developerConnection.set("git@github.com:FigureTechnologies/asset-specifications")
                    connection.set("https://github.com/FigureTechnologies/asset-specifications.git")
                    url.set("https://github.com/FigureTechnologies/asset-specifications")
                }
            }
        }
    }

    configure<SigningExtension> {
        sign(publications["maven"])
    }
}
