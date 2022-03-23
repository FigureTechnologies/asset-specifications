package io.provenance.spec

import java.util.*

object ShareclassSpecification : AssetSpecification {

    override val scopeSpecConfig = ScopeSpecConfig(
        id = UUID.fromString("60980579-7533-40be-a4d6-31659806d54e"),
        name = "Shareclass NFT",
        description = "Shareclass NFT Specification provided by the Provenance Blockchain Foundation",
        websiteUrl = websiteUrl
    )

    override val contractSpecConfigs = listOf(
        ContractSpecConfig(
            id = UUID.fromString("a1c11ddf-b9c7-41fd-b1c6-bb538a93480c"),
            contractClassname = "io.provenance.spec.OnboardShareclassContractSpec",
            name = "Onboard Shareclass NFT",
            description = "Mint Shareclass NFT Contract Specification provided by the Provenance Blockchain Foundation",
            websiteUrl = websiteUrl
        )
    )

    override val recordSpecConfigs = listOf(
        RecordSpecConfig(
            id = UUID.fromString("ee9ec648-c136-4a37-96dd-5a672a205000"),
            name = "shareclass",
            typeClassname = "io.provenance.model.v1.Asset",
            contractSpecId = contractSpecConfigs.first().id
        )
    )
}