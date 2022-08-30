package tech.figure.spec

import io.provenance.scope.util.toUuid

object ShareclassSpecification : AssetSpecification {

    override val scopeSpecConfig = ScopeSpecConfig(
        id = "6d20aef8-27eb-11ed-b0a8-bbd2b8165ad5".toUuid(),
        name = "Shareclass NFT",
        description = "Shareclass NFT Specification provided by Figure Technologies",
        websiteUrl = websiteUrl
    )

    override val contractSpecConfigs = listOf(
        ContractSpecConfig(
            id = "715859b2-27eb-11ed-a379-2f6c527c4240".toUuid(),
            contractClassname = "tech.figure.spec.OnboardShareclassContractSpec",
            name = "Onboard Shareclass NFT",
            description = "Mint Shareclass NFT Contract Specification provided by Figure Technologies",
            websiteUrl = websiteUrl
        )
    )

    override val recordSpecConfigs = listOf(
        RecordSpecConfig(
            id = "7487e74c-27eb-11ed-aacb-bfec8a5afd5f".toUuid(),
            name = "shareclass",
            typeClassname = "io.provenance.model.v1.Asset",
            contractSpecId = contractSpecConfigs.first().id
        )
    )
}
