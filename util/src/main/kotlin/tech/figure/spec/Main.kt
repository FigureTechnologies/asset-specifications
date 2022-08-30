package tech.figure.spec

import com.google.protobuf.ByteString
import cosmos.crypto.secp256k1.Keys
import cosmos.tx.v1beta1.ServiceOuterClass
import io.provenance.client.grpc.BaseReqSigner
import io.provenance.client.grpc.GasEstimationMethod
import io.provenance.client.grpc.PbClient
import io.provenance.client.grpc.Signer
import io.provenance.client.protobuf.extensions.toAny
import io.provenance.client.protobuf.extensions.toTxBody
import io.provenance.hdwallet.bip39.MnemonicWords
import io.provenance.hdwallet.wallet.Account
import io.provenance.hdwallet.wallet.Wallet
import io.provenance.scope.util.ProtoJsonUtil.toJson
import tech.figure.spec.util.ProvenanceNetworkType
import java.net.URI

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
            hrp = ProvenanceNetworkType.TESTNET.prefix,
            passphrase = "",
            mnemonicWords = MnemonicWords.of(mnemonic),
            testnet = true
        )
        val account: Account = wallet[ProvenanceNetworkType.TESTNET.hdPath]

        override fun address(): String = account.address.value

        override fun pubKey(): Keys.PubKey =
            Keys.PubKey
                .newBuilder()
                .setKey(ByteString.copyFrom(account.keyPair.publicKey.compressed()))
                .build()

        override fun sign(data: ByteArray): ByteArray = account.sign(data)
    }


    val txn = AssetSpecifications
        .flatMap { it.specificationMsgs(ownerAddress) }
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
