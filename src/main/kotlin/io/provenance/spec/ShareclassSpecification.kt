package io.provenance.spec

import io.provenance.spec.util.SpecWriter
import java.util.*

object ShareclassSpecification {

    val ShareclassScopeSpecification = ScopeSpecConfig(
        id = UUID.fromString("60980579-7533-40be-a4d6-31659806d54e"),
        name = "Shareclass NFT",
        description = "Shareclass NFT Specification provided by the Provenance Blockchain Foundation",
        websiteUrl = websiteUrl
    )

    val ShareclassRecordSpecification = RecordSpecConfig(
        id = UUID.fromString("ee9ec648-c136-4a37-96dd-5a672a205000"),
        name = "shareclass",
        typeClassname = "io.provenance.model.v1.Asset",
    )

    val ShareclassContractSpecification = ContractSpecConfig(
        id = UUID.fromString("a1c11ddf-b9c7-41fd-b1c6-bb538a93480c"),
        contractClassname = "io.provenance.spec.OnboardShareclassContractSpec",
        name = "Onboard Shareclass NFT",
        description = "Mint Shareclass NFT Contract Specification provided by the Provenance Blockchain Foundation",
        websiteUrl = websiteUrl
    )

    fun specificationTx(owner: String) = SpecWriter.buildMetadataSpecificationTransaction(
        owner = owner,
        scopeSpec = ShareclassScopeSpecification,
        recordSpecList = listOf(ShareclassRecordSpecification),
        contractSpecList = listOf(ShareclassContractSpecification)
    )
}