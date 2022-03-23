package io.provenance.spec


import com.google.protobuf.ByteString
import com.google.protobuf.Message
import cosmos.crypto.secp256k1.Keys
import cosmos.tx.v1beta1.ServiceOuterClass
import io.provenance.client.grpc.BaseReqSigner
import io.provenance.client.grpc.GasEstimationMethod
import io.provenance.client.grpc.PbClient
import io.provenance.client.grpc.Signer
import io.provenance.client.protobuf.extensions.toAny
import io.provenance.client.protobuf.extensions.toTxBody
import io.provenance.client.wallet.NetworkType
import io.provenance.hdwallet.bip39.MnemonicWords
import io.provenance.hdwallet.wallet.Account
import io.provenance.hdwallet.wallet.Wallet
import io.provenance.metadata.v1.*
import io.provenance.scope.util.MetadataAddress
import io.provenance.scope.util.ProtoJsonUtil.toJson
import io.provenance.scope.util.toByteString
import java.net.URI
import java.util.*

/*
Usage: chainId, GRPC URI, owner address, wallet mnemonic
Example: chain-local http://localhost:9090 tp1g9jwm8mvr9qc7p777jmuurtt9n48rnfj80j22r mnemonic words here ...
 */
fun main(args: Array<String>) {

    val chainId = args[0]
    val chainURI = args[1]
    val ownerAddress = args[2]
    val mnemonic = args.copyOfRange(3, args.size).joinToString(separator = " ")

    println("Writing specifications to: $chainId at $chainURI with owner address $ownerAddress")

    val pbClient = PbClient(
        chainId,
        URI(chainURI),
        GasEstimationMethod.MSG_FEE_CALCULATION
    )

    val signer = object : Signer {
        val wallet = Wallet.fromMnemonic(
            hrp = NetworkType.TESTNET.prefix,
            passphrase = "",
            mnemonicWords = MnemonicWords.of(mnemonic),
            testnet = true
        )
        val account: Account = wallet[NetworkType.TESTNET.path]

        override fun address(): String = account.address.value

        override fun pubKey(): Keys.PubKey =
            Keys.PubKey
                .newBuilder()
                .setKey(ByteString.copyFrom(account.keyPair.publicKey.compressed()))
                .build()

        override fun sign(data: ByteArray): ByteArray = account.sign(data)
    }

    val txn = AssetSpecifications
        .flatMap { makeExampleNFT(ownerAddress, it) }
        .map { it.toAny() }
        .toTxBody()

    pbClient.estimateAndBroadcastTx(
        txBody = txn,
        signers = listOf(BaseReqSigner(signer)),
        mode = ServiceOuterClass.BroadcastMode.BROADCAST_MODE_BLOCK,
        gasAdjustment = 1.2
    ).also {
        println("Response:")
        println(it.toJson())
    }

}



fun makeExampleNFT(ownerAddress: String, assetSpec: AssetSpecification): List<Message> {

    val scopeId = UUID.randomUUID()
    val sessionId: UUID = UUID.randomUUID()
    val contractSpec = assetSpec.contractSpecConfigs.first()
    val recordSpec = assetSpec.recordSpecConfigs.first()

    println("Creating ${assetSpec.scopeSpecConfig.name} with id: $scopeId    https://explorer.test.provenance.io/nft/${MetadataAddress.forScope(scopeId)}")


    return listOf(

        // write-scope
        MsgWriteScopeRequest.newBuilder().apply {
            addSigners(ownerAddress)
            scopeUuid = scopeId.toString()
            specUuid = assetSpec.scopeSpecConfig.id.toString()
            scopeBuilder
                .setScopeId(MetadataAddress.forScope(scopeId).bytes.toByteString())
                .setValueOwnerAddress(ownerAddress)
                .addAllOwners(
                    listOf(
                        Party.newBuilder().apply {
                            address = ownerAddress
                            role = PartyType.PARTY_TYPE_OWNER
                        }.build()
                    )
                )
        }.build(),

        // write-session
        MsgWriteSessionRequest.newBuilder().apply {
            addSigners(ownerAddress)
            specUuid = contractSpec.id.toString()
            sessionIdComponentsBuilder
                .setScopeUuid(scopeId.toString())
                .setSessionUuid(sessionId.toString())
            sessionBuilder
                .setSessionId(MetadataAddress.forSession(scopeId, sessionId).bytes.toByteString())
                .addParties(Party.newBuilder().apply {
                    address = ownerAddress
                    role = PartyType.PARTY_TYPE_OWNER
                })
                .auditBuilder
                .setCreatedBy(ownerAddress)
                .setUpdatedBy(ownerAddress)
        }.build(),

        // write-record
        MsgWriteRecordRequest.newBuilder().apply {
            addSigners(ownerAddress)
            contractSpecUuid = contractSpec.id.toString()
            recordBuilder
                .setSessionId(MetadataAddress.forSession(scopeId, sessionId).bytes.toByteString())
                .setSpecificationId(
                    MetadataAddress.forRecordSpecification(
                        contractSpec.id,
                        recordSpec.name
                    ).bytes.toByteString()
                )
                .setName(recordSpec.name)
                .addInputs(
                    RecordInput.newBuilder().apply {
                        name = recordSpec.name
                        typeName = recordSpec.typeClassname
                        hash = "Fake data hash"
                        status = RecordInputStatus.RECORD_INPUT_STATUS_PROPOSED
                    }.build()
                )
                .addOutputs(
                    RecordOutput.newBuilder().apply {
                        hash = "Fake data hash"
                        status = ResultStatus.RESULT_STATUS_PASS
                    }.build()
                )
                .processBuilder
                .setName(contractSpec.contractClassname)
                .setMethod(contractSpec.contractClassname)
                .setHash("Not even sure what this hash field is for")
        }.build(),

        )
}

