package io.provenance.spec

import io.provenance.spec.util.SpecWriter
import java.util.*

object PropertySpecification {

    val PropertyScopeSpecification = ScopeSpecConfig(
        id = UUID.fromString("79c47c7b-9206-470c-b9bc-f6d80e1ffdda"),
        name = "Property NFT",
        description = "Property NFT Specification provided by the Provenance Blockchain Foundation",
        websiteUrl = websiteUrl
    )

    val PropertyRecordSpecification = RecordSpecConfig(
        id = UUID.fromString("8b834744-01a4-4638-9261-cc4b2e19dd9b"),
        name = "property",
        typeClassname = "io.provenance.model.v1.Asset",
    )

    val PropertyContractSpecification = ContractSpecConfig(
        id = UUID.fromString("ffe52f9c-482f-439c-8788-ad20cdf14338"),
        contractClassname = "io.provenance.spec.OnboardPropertyContractSpec",
        name = "Onboard Property NFT",
        description = "Mint Property NFT Contract Specification provided by the Provenance Blockchain Foundation",
        websiteUrl = websiteUrl
    )

    fun specificationTx(owner: String) = SpecWriter.buildMetadataSpecificationTransaction(
        owner = owner,
        scopeSpec = PropertyScopeSpecification,
        recordSpecList = listOf(PropertyRecordSpecification),
        contractSpecList = listOf(PropertyContractSpecification)
    )
}