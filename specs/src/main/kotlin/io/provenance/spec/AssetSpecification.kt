package io.provenance.spec

import io.provenance.spec.util.SpecBuilder

val AssetSpecifications: List<AssetSpecification> = listOf(
    CollectableSpecification,
    HELOCSpecification,
    MortgageSpecification,
    PayableSpecification,
    PersonalLoanSpecification,
    PropertySpecification,
    ShareclassSpecification
)


interface AssetSpecification {

    val scopeSpecConfig: ScopeSpecConfig
    val recordSpecConfigs: List<RecordSpecConfig>
    val contractSpecConfigs: List<ContractSpecConfig>

    fun specificationTx(owner: String) = SpecBuilder.buildMetadataSpecificationTransaction(
        owner = owner,
        scopeSpec = scopeSpecConfig,
        recordSpecList = recordSpecConfigs,
        contractSpecList = contractSpecConfigs
    )
}