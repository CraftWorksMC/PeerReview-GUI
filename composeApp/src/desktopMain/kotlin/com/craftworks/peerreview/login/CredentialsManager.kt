package com.craftworks.peerreview.login

import com.craftworks.peerreview.data.Credentials
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class CredentialsManager(private val filePath: String) {
    companion object {
        private val KEY = "rQ7bjJd0fjGvd1ToB3rRg4A9zTzJgkUE".toByteArray()
        private val IV = "f5jKZkdfRi4guH1c".toByteArray()

        private fun encrypt(plainText: String): String {
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            val secretKey = SecretKeySpec(KEY, "AES")
            val ivSpec = IvParameterSpec(IV)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)
            val encrypted = cipher.doFinal(plainText.toByteArray())
            return Base64.getEncoder().encodeToString(encrypted)
        }

        private fun decrypt(cipherText: String): String {
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            val secretKey = SecretKeySpec(KEY, "AES")
            val ivSpec = IvParameterSpec(IV)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)
            val decrypted = cipher.doFinal(Base64.getDecoder().decode(cipherText))
            return String(decrypted)
        }
    }
}