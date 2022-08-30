package tech.figure.spec

import io.provenance.scope.util.toUuid

object PropertySpecification : AssetSpecification {

    override val scopeSpecConfig = ScopeSpecConfig(
        id = "60e391fa-27eb-11ed-94ca-ef8743476060".toUuid(),
        name = "Property NFT",
        description = "Property NFT Specification provided by Figure Technologies",
        websiteUrl = websiteUrl
    )

    override val contractSpecConfigs = listOf(
        ContractSpecConfig(
            id = "65e7ee26-27eb-11ed-bd26-f3053634bbb5".toUuid(),
            contractClassname = "tech.figure.spec.OnboardPropertyContractSpec",
            name = "Onboard Property NFT",
            description = "Mint Property NFT Contract Specification provided by Figure Technologies",
            websiteUrl = websiteUrl
        )
    )

    override val recordSpecConfigs = listOf(
        RecordSpecConfig(
            id = "69d9770c-27eb-11ed-ad3e-1b3d0349f661".toUuid(),
            name = "property",
            typeClassname = "io.provenance.model.v1.Asset",
            contractSpecId = contractSpecConfigs.first().id
        )
    )

}
