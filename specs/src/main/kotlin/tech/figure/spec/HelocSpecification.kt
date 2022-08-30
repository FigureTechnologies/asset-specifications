package tech.figure.spec

import io.provenance.scope.util.toUuid

object HELOCSpecification : AssetSpecification {

    override val scopeSpecConfig = ScopeSpecConfig(
        id = "19e637d0-27eb-11ed-9c63-878b98ca90d8".toUuid(),
        name = "HELOC NFT",
        description = "HELOC NFT Specification provided by Figure Technologies",
        websiteUrl = websiteUrl
    )

    override val contractSpecConfigs = listOf(
        ContractSpecConfig(
            id = "1e769c7c-27eb-11ed-b7e1-8bcc0de384bd".toUuid(),
            contractClassname = "tech.figure.spec.OnboardHELOCContractSpec",
            name = "Onboard HELOC NFT",
            description = "Mint HELOC NFT Contract Specification provided by Figure Technologies",
            websiteUrl = websiteUrl
        )
    )

    override val recordSpecConfigs = listOf(
        RecordSpecConfig(
            id = "2d36b1ca-27eb-11ed-8e75-cf2150f5e5e4".toUuid(),
            name = "heloc",
            typeClassname = "io.provenance.model.v1.Asset",
            contractSpecId = contractSpecConfigs.first().id
        )
    )
}
