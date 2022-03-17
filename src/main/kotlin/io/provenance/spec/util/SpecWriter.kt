package io.provenance.spec.util

import cosmos.tx.v1beta1.TxOuterClass
import io.provenance.client.protobuf.extensions.toAny
import io.provenance.client.protobuf.extensions.toTxBody
import io.provenance.metadata.v1.*
import io.provenance.scope.util.MetadataAddress
import io.provenance.scope.util.toByteString
import io.provenance.spec.ContractSpecConfig
import io.provenance.spec.RecordSpecConfig
import io.provenance.spec.ScopeSpecConfig

object SpecWriter {

    // Builds the Provenance metadata transaction for writing contract/scope/record specifications to the chain
    fun buildMetadataSpecificationTransaction(
        owner: String,
        scopeSpec: ScopeSpecConfig,
        recordSpecList: List<RecordSpecConfig>,
        contractSpecList: List<ContractSpecConfig>,
    ): TxOuterClass.TxBody {

        val contractSpecsMsgs = buildContractSpecs(contractSpecList, owner)
        val scopeSpecMsg = buildScopeSpec(owner, scopeSpec, contractSpecsMsgs)
        val recordSpecMsgs = buildRecordSpecs(owner, recordSpecList)

        return (contractSpecsMsgs + scopeSpecMsg + recordSpecMsgs)
            .map { it.toAny() }
            .toTxBody()
    }


    private fun buildScopeSpec(
        owner: String,
        scopeSpec: ScopeSpecConfig,
        contractSpecsMsgs: List<MsgWriteContractSpecificationRequest>
    ): MsgWriteScopeSpecificationRequest =

        MsgWriteScopeSpecificationRequest.newBuilder().apply {
            specUuid = scopeSpec.id.toString()
            specificationBuilder.apply {
                descriptionBuilder.apply {
                        name = scopeSpec.name
                        description = scopeSpec.description
                        websiteUrl = scopeSpec.websiteUrl
                    }
            }
                .addAllContractSpecIds(
                    contractSpecsMsgs.map { it.specification.specificationId }
                )
                .addAllOwnerAddresses(listOf(owner))
                .addAllPartiesInvolved(
                    listOf(
                        PartyType.PARTY_TYPE_OWNER
                    )
                )
        }.addAllSigners(listOf(owner)).build()


    private fun buildRecordSpecs(
        owner: String,
        recordSpecs: List<RecordSpecConfig>
    ): List<MsgWriteRecordSpecificationRequest> = recordSpecs.map { recordSpec ->
        // write-record-specification
        MsgWriteRecordSpecificationRequest.newBuilder().apply {
            specificationBuilder
                .setName(recordSpec.name)
                .setTypeName(recordSpec.typeClassname)
                .setSpecificationId(
                    MetadataAddress.forRecordSpecification(recordSpec.id, recordSpec.name).bytes.toByteString()
                )
                .setResultType(DefinitionType.DEFINITION_TYPE_RECORD)
                .addAllResponsibleParties(
                    listOf(
                        PartyType.PARTY_TYPE_OWNER
                    )
                )
                .addInputs(
                    InputSpecification.newBuilder().apply {
                        name = recordSpec.name
                        typeName = recordSpec.typeClassname
                        hash = recordSpec.name
                    }
                )
        }.addAllSigners(listOf(owner)).build()
    }

    fun buildContractSpecs(
        contractSpecList: List<ContractSpecConfig>,
        owner: String
    ): List<MsgWriteContractSpecificationRequest> = contractSpecList.map { contractSpec ->

        MsgWriteContractSpecificationRequest.newBuilder().apply {
            specificationBuilder
                .setSpecificationId(MetadataAddress.forContractSpecification(contractSpec.id).bytes.toByteString())
                .setClassName(contractSpec.contractClassname)
                .setHash(contractSpec.contractClassname)
                .addAllOwnerAddresses(listOf(owner))
                .addAllPartiesInvolved(
                    listOf(
                        PartyType.PARTY_TYPE_OWNER
                    )
                )
        }.addAllSigners(listOf(owner)).build()
    }
}