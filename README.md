# asset-specifications
[Scope Specifications](https://docs.provenance.io/modules/metadata-module#scope-specification) for classified asset/NFT types.

This library exposes helper utilities for creating a collection of Provenance Blockchain msg write protos for creating:
- [Scope Specifications](https://docs.provenance.io/modules/metadata-module#scope-specification)
- [Record Specifications](https://docs.provenance.io/modules/metadata-module#record-specification)
- [Contract Specifications](https://docs.provenance.io/modules/metadata-module#contract-specification)

These core components comprise the baseline needed for generating new [Provenance Metadata Module Scopes](https://docs.provenance.io/modules/metadata-module#scope-data-structures).

## Status
[![Latest Release][release-badge]][release-latest]
[![Maven Central][maven-badge]][maven-url]
[![Apache 2.0 License][license-badge]][license-url]

[license-badge]: https://img.shields.io/github/license/FigureTechnologies/asset-specifications.svg
[license-url]: https://github.com/FigureTechnologies/asset-specifications/blob/main/LICENSE
[maven-badge]: https://maven-badges.herokuapp.com/maven-central/tech.figure.spec/asset-specs/badge.svg
[maven-url]: https://maven-badges.herokuapp.com/maven-central/tech.figure.spec/asset-specs
[release-badge]: https://img.shields.io/github/tag/FigureTechnologies/asset-specifications.svg
[release-latest]: https://github.com/FigureTechnologies/asset-specifications/releases/latest

### Release Process
- All new features merge to develop
- Code ready for release merges to main
- Only draft release artifacts from the main branch

### Using the asset-specs library

To create Provenance Blockchain msg protos that will onboard a scope specification, simply use the provided
[AssetSpecifications](specs/src/main/kotlin/tech/figure/spec/AssetSpecification.kt/) object, or refer to a specification
directly:

```kotlin
import com.google.protobuf.Message
import io.provenance.scope.util.toUuid
import tech.figure.spec.AssetSpecification
import tech.figure.spec.AssetSpecifications
import tech.figure.spec.ContractSpecConfig
import tech.figure.spec.HELOCSpecification
import tech.figure.spec.RecordSpecConfig
import tech.figure.spec.ScopeSpecConfig

// To generate all messages, owned by the same address
fun generateAllSpecificationMessages(specOwnerBech32Address: String): List<Message> = AssetSpecifications
    .flatMap { it.specificationMsgs(ownerAddress = specOwnerBech32Address) }

// To generate a single type's messages, owned by an address
fun generateHelocSpecificationMessages(specOwnerBech32Address: String): List<Message> = HELOCSpecification
    .specificationMsgs(ownerAddress = specOwnerBech32Address)

// Creating your own specification is easy, too!
object MyNewSpecification : AssetSpecification {
    override val scopeSpecConfig: ScopeSpecConfig = ScopeSpecConfig(
        // UUID generation is mandatory for creating a new scope spec.  This will be used to generate its bech32 address
        id = "798e4bac-d2df-11ec-9b56-ef6cd818428b".toUuid(),
        name = "My New NFT",
        description = "Provenance Blockchain's new NFT",
        websiteUrl = "<enter your own website here>",
    )

    // Multiple contract specs are not a requirement, but are allowed and supported. Simply add more than one item to the
    // list and it will appear in the generated messages when invoking specificationMsgs() on this object
    override val contractSpecConfigs: List<ContractSpecConfig> = listOf(
        ContractSpecConfig(
            // UUID generation is mandatory for creating a new contract spec.  This will be used to generate its bech32 address
            id = "cd849fea-d2df-11ec-a8be-ef96042c1133".toUuid(),
            contractClassname = "com.mycompany.spec.OnboardMyNewContractSpec",
            name = "Onboard My New NFT",
            description = "Mint My New NFT provided by My Cool Company",
            websiteUrl = "<enter your own website here>",
        )
    )

    // Multiple record specs are not a requirement, but are allowed and supported. Simply add more than one item to the
    // list and it will appear in the generated messages when invoking specificationMsgs() on this object
    override val recordSpecConfigs: List<RecordSpecConfig> = listOf(
        RecordSpecConfig(
            // UUID generation is mandatory for creating a new record spec.  This will be used to generate its bech32 address
            id = "2abdbdb8-d2e0-11ec-a14a-1f9a19c1cfe4".toUuid(),
            name = "mynewnft",
            // Provenance Blockchain provides an asset model at this repository, but any protobuf data structure can be used:
            // https://github.com/provenance-io/metadata-asset-model
            typeClassname = "io.provenance.model.v1.Asset",
            // With multiple record specs in a more complex structure, direct contract specification uuid values may
            // need to be provided here instead of just referencing the first entry.
            contractSpecId = contractSpecConfigs.first().id,
        )
    )
}
```
