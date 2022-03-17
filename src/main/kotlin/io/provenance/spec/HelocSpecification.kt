package io.provenance.spec

import java.util.*

object HELOCSpecification : AssetSpecification {

    override val scopeSpecConfig = ScopeSpecConfig(
        id = UUID.fromString("7205eaf2-4e1c-486c-a5f2-c0794f28a780"),
        name = "HELOC NFT",
        description = "HELOC NFT Specification provided by the Provenance Blockchain Foundation",
        websiteUrl = websiteUrl
    )

    override val recordSpecConfigs= listOf(
        RecordSpecConfig(
            id = UUID.fromString("d93e855e-a1aa-4f40-a653-34e89272d845"),
            name = "heloc",
            typeClassname = "io.provenance.model.v1.Asset",
        )
    )

    override val contractSpecConfigs = listOf(
        ContractSpecConfig(
            id = UUID.fromString("95732991-f4f2-4e1b-8022-43d150031315"),
            contractClassname = "io.provenance.spec.OnboardHELOCContractSpec",
            name = "Onboard HELOC NFT",
            description = "Mint HELOC NFT Contract Specification provided by the Provenance Blockchain Foundation",
            websiteUrl = websiteUrl
        )
    )

}