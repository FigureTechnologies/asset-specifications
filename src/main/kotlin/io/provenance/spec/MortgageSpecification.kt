package io.provenance.spec

import io.provenance.spec.util.SpecWriter
import java.util.*

object MortgageSpecification {

    val MortgageScopeSpecification = ScopeSpecConfig(
        id = UUID.fromString("86fef324-a9d0-4d00-ae2f-e03a85c8224c"),
        name = "Mortgage NFT",
        description = "Mortgage NFT Specification provided by the Provenance Blockchain Foundation",
        websiteUrl = websiteUrl
    )

    val MortgageRecordSpecification = RecordSpecConfig(
        id = UUID.fromString("25bb5aa7-8bbe-43d6-a48c-900c0b772ed1"),
        name = "mortgage",
        typeClassname = "io.provenance.model.v1.Asset",
    )

    val MortgageContractSpecification = ContractSpecConfig(
        id = UUID.fromString("f4ac5c9c-a220-4403-85d3-9f0eb62b8bc1"),
        contractClassname = "io.provenance.spec.OnboardMortgageContractSpec",
        name = "Onboard Mortgage NFT",
        description = "Mint Mortgage NFT Contract Specification provided by the Provenance Blockchain Foundation",
        websiteUrl = websiteUrl
    )

    fun specificationTx(owner: String) = SpecWriter.buildMetadataSpecificationTransaction(
        owner = owner,
        scopeSpec = MortgageScopeSpecification,
        recordSpecList = listOf(MortgageRecordSpecification),
        contractSpecList = listOf(MortgageContractSpecification)
    )
}