package io.provenance.spec

import io.provenance.spec.util.SpecWriter
import java.util.*

object CollectableSpecification {

    val CollectableScopeSpecification = ScopeSpecConfig(
        id = UUID.fromString("6efad216-5a9a-4c7d-b3e9-7e4550ca8cf2"),
        name = "Collectable NFT",
        description = "Collectable NFT Specification provided by the Provenance Blockchain Foundation",
        websiteUrl = websiteUrl
    )

    val CollectableRecordSpecification = RecordSpecConfig(
        id = UUID.fromString("73fdbb5d-4234-40e6-abef-dd6c044b4237"),
        name = "collectable",
        typeClassname = "io.provenance.model.v1.Asset",
    )

    val CollectableContractSpecification = ContractSpecConfig(
        id = UUID.fromString("1bef03c6-3f86-481b-927b-5dbec22a7ab2"),
        contractClassname = "io.provenance.spec.OnboardCollectableContractSpec",
        name = "Onboard Collectable NFT",
        description = "Mint Collectable NFT Contract Specification provided by the Provenance Blockchain Foundation",
        websiteUrl = websiteUrl
    )

    fun specificationTx(owner: String) = SpecWriter.buildMetadataSpecificationTransaction(
        owner = owner,
        scopeSpec = CollectableScopeSpecification,
        recordSpecList = listOf(CollectableRecordSpecification),
        contractSpecList = listOf(CollectableContractSpecification)
    )
}