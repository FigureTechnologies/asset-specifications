package io.provenance.spec

import io.provenance.spec.util.SpecBuilder
import java.util.*

data class ContractSpecConfig(
    val id: UUID,
    val contractClassname: String,
    val name: String,
    val description: String,
    val websiteUrl: String
)

data class ScopeSpecConfig(
    val id: UUID,
    val name: String,
    val description: String,
    val websiteUrl: String
)


data class RecordSpecConfig(
    val id: UUID,
    val name: String, // fact name
    val typeClassname: String,
    val contractSpecId: UUID
)

const val websiteUrl = "https://github.com/provenance-io/asset-specifications"
