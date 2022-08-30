package tech.figure.spec

import io.provenance.scope.util.toUuid

object MortgageSpecification : AssetSpecification {

    override val scopeSpecConfig = ScopeSpecConfig(
        id = "388b1b06-27eb-11ed-97d9-7fe43d2b97ee".toUuid(),
        name = "Mortgage NFT",
        description = "Mortgage NFT Specification provided by Figure Technologies",
        websiteUrl = websiteUrl
    )

    override val contractSpecConfigs = listOf(
        ContractSpecConfig(
            id = "3c214c40-27eb-11ed-bc9d-e351c32eedf0".toUuid(),
            contractClassname = "tech.figure.spec.OnboardMortgageContractSpec",
            name = "Onboard Mortgage NFT",
            description = "Mint Mortgage NFT Contract Specification provided by Figure Technologies",
            websiteUrl = websiteUrl
        )
    )

    override val recordSpecConfigs = listOf(
        RecordSpecConfig(
            id = "415c13e8-27eb-11ed-ae35-931d15348812".toUuid(),
            name = "mortgage",
            typeClassname = "io.provenance.model.v1.Asset",
            contractSpecId = contractSpecConfigs.first().id
        )
    )
}
