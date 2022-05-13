plugins {
    id("core-config")
}

dependencies {
    listOf(
        project(":specs"),
        libs.bundles.grpc,
        libs.bundles.kotlin,
        libs.bundles.protobuf,
        libs.provenanceGrpcClient,
        libs.provenanceHdWallet,
        libs.provenanceProto,
        libs.provenanceScopeUtil,
    ).forEach(::implementation)
}
