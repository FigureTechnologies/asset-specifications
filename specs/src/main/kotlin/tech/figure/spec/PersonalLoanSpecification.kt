package tech.figure.spec

import io.provenance.scope.util.toUuid

object PersonalLoanSpecification : AssetSpecification {

    override val scopeSpecConfig = ScopeSpecConfig(
        id = "54c4b26e-27eb-11ed-8c87-53fbe8bf8d09".toUuid(),
        name = "Personal Loan NFT",
        description = "Personal Loan NFT Specification provided by Figure Technologies",
        websiteUrl = websiteUrl
    )

    override val contractSpecConfigs = listOf(
        ContractSpecConfig(
            id = "57cab698-27eb-11ed-b4db-9b502cfcbe6a".toUuid(),
            contractClassname = "tech.figure.spec.OnboardPersonalLoanContractSpec",
            name = "Onboard Personal Loan NFT",
            description = "Mint Personal Loan NFT Contract Specification provided by Figure Technologies",
            websiteUrl = websiteUrl
        )
    )

    override val recordSpecConfigs = listOf(
        RecordSpecConfig(
            id = "5d00931c-27eb-11ed-9e62-3369af5209b7".toUuid(),
            name = "personalLoan",
            typeClassname = "io.provenance.model.v1.Asset",
            contractSpecId = contractSpecConfigs.first().id
        )
    )
}
