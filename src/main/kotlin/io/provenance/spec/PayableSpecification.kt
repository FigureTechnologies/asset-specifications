package io.provenance.spec

import io.provenance.spec.util.SpecWriter
import java.util.*

object PayableSpecification {

    val PayableScopeSpecification = ScopeSpecConfig(
        id = UUID.fromString("11e7d541-a120-48a7-98c5-e32b8aa0707f"),
        name = "Payable NFT",
        description = "Payable NFT Specification provided by the Provenance Blockchain Foundation",
        websiteUrl = websiteUrl
    )

    val PayableRecordSpecification = RecordSpecConfig(
        id = UUID.fromString("2bd4e986-0964-46bb-a8b3-8d4989a287e2"),
        name = "payable",
        typeClassname = "io.provenance.model.v1.Asset",
    )

    val PayableContractSpecification = ContractSpecConfig(
        id = UUID.fromString("a965a903-af84-4322-a645-d11ceb55c17f"),
        contractClassname = "io.provenance.spec.OnboardPayableContractSpec",
        name = "Onboard Payable NFT",
        description = "Mint Payable NFT Contract Specification provided by the Provenance Blockchain Foundation",
        websiteUrl = websiteUrl
    )

    fun specificationTx(owner: String) = SpecWriter.buildMetadataSpecificationTransaction(
        owner = owner,
        scopeSpec = PayableScopeSpecification,
        recordSpecList = listOf(PayableRecordSpecification),
        contractSpecList = listOf(PayableContractSpecification)
    )
}