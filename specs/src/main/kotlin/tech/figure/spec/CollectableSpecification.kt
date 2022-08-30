package tech.figure.spec

import io.provenance.scope.util.toUuid

object CollectableSpecification : AssetSpecification {

    override val scopeSpecConfig = ScopeSpecConfig(
        id = "060e0cec-27eb-11ed-b329-3b1a158b3efa".toUuid(),
        name = "Collectable NFT",
        description = "Collectable NFT Specification provided by Figure Technologies",
        websiteUrl = websiteUrl
    )

    override val contractSpecConfigs = listOf(
        ContractSpecConfig(
            id = "0acf25fe-27eb-11ed-a462-632414f7f96f".toUuid(),
            contractClassname = "tech.figure.spec.OnboardCollectableContractSpec",
            name = "Onboard Collectable NFT",
            description = "Mint Collectable NFT Contract Specification provided by Figure Technologies",
            websiteUrl = websiteUrl
        )
    )

    override val recordSpecConfigs = listOf(
        RecordSpecConfig(
            id = "0f569972-27eb-11ed-a208-934170c51a60".toUuid(),
            name = "collectable",
            typeClassname = "io.provenance.model.v1.Asset",
            contractSpecId = contractSpecConfigs.first().id
        )
    )

}
