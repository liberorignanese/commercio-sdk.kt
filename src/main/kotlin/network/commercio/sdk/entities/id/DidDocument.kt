package network.commercio.sdk.entities.id

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import network.commercio.sdk.utils.readHex
import java.security.KeyFactory
import java.security.PublicKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec

/**
 * Commercio network's did document is described here:
 * https://scw-gitlab.zotsell.com/Commercio.network/Cosmos-application/blob/master/Commercio%20Decentralized%20ID%20framework.md
 */
data class DidDocument(
    @JsonProperty("@context") val context: String,
    @JsonProperty("id") val did: String,
    @JsonProperty("publicKey") val publicKeys: List<DidDocumentPublicKey>,
    @JsonProperty("authentication") val authentication: List<String>,
    @JsonProperty("proof") val proof: DidDocumentProof,
    @JsonProperty("service") val services: List<DidDocumentService>?
) {

    /**
     * Returns the [PublicKey] that should be used as the public encryption key when encrypting data
     * that can later be read only by the owner of this Did Document.
     */
    @get:JsonIgnore
    val encryptionKey: RSAPublicKey?
        get() {
            // Find the encryption key
            return publicKeys.firstOrNull { it.type == DidDocumentPublicKey.Type.RSA }?.let {
                val pubKeySpec = PKCS8EncodedKeySpec(it.publicKeyHex.readHex())
                KeyFactory.getInstance("RSA").generatePublic(pubKeySpec) as RSAPublicKey
            }
        }
}
