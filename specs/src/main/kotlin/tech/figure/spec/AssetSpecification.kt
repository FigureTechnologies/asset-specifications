package tech.figure.spec

import tech.figure.spec.util.SpecBuilder

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
    val contractSpecConfigs: List<ContractSpecConfig>
    val recordSpecConfigs: List<RecordSpecConfig>

    fun specificationMsgs(ownerAddress: String) = SpecBuilder.buildMetadataSpecificationTransaction(
        ownerAddress = ownerAddress,
        scopeSpec = scopeSpecConfig,
        recordSpecList = recordSpecConfigs,
        contractSpecList = contractSpecConfigs
    )
}
