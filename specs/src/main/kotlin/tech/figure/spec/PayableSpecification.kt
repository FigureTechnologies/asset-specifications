package tech.figure.spec

import io.provenance.scope.util.toUuid

object PayableSpecification : AssetSpecification {

    override val scopeSpecConfig = ScopeSpecConfig(
        id = "47716634-27eb-11ed-81d5-877cf2b20ca6".toUuid(),
        name = "Payable NFT",
        description = "Payable NFT Specification provided by Figure Technologies",
        websiteUrl = websiteUrl
    )

    override val contractSpecConfigs = listOf(
        ContractSpecConfig(
            id = "4b44ffdc-27eb-11ed-bef5-53479060599c".toUuid(),
            contractClassname = "tech.figure.spec.OnboardPayableContractSpec",
            name = "Onboard Payable NFT",
            description = "Mint Payable NFT Contract Specification provided by Figure Technologies",
            websiteUrl = websiteUrl
        )
    )

    override val recordSpecConfigs = listOf(
        RecordSpecConfig(
            id = "4f7dc4d0-27eb-11ed-a8fb-33003bfde41f".toUuid(),
            name = "payable",
            typeClassname = "io.provenance.model.v1.Asset",
            contractSpecId = contractSpecConfigs.first().id
        )
    )

}
