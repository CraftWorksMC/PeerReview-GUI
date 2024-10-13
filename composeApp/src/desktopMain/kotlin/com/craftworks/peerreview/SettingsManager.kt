package com.craftworks.peerreview

import java.io.File
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


// Save string data to disk, in OS specific directories
fun saveData(fileName: String, data: String) {
    println("Saving data to $fileName")
    val osName = System.getProperty("os.name").lowercase()
    val homeDir = when {
        osName.contains("win") -> System.getenv("APPDATA") ?: "" // Windows AppData
        osName.contains("mac") -> System.getProperty("user.home") + "/Library/Application Support" // macOS
        osName.contains("nix") || osName.contains("nux") -> System.getProperty("user.home") + "/.config" // Linux config directory
        else -> throw UnsupportedOperationException("Unsupported OS: $osName")
    }
    val file = File("$homeDir/PeerReviewData/$fileName")
    if (!file.parentFile.exists()) {
        file.parentFile.mkdirs()
    }
    if (!file.exists()) file.createNewFile()
    file.writeText(encrypt(data)) // Write string to file
}

// Load string data to disk, in OS specific directories
fun loadData(fileName: String): String? {
    println("Loading data from $fileName")
    val osName = System.getProperty("os.name").lowercase()
    val homeDir = when {
        osName.contains("win") -> System.getenv("APPDATA") ?: "" // Windows AppData
        osName.contains("mac") -> System.getProperty("user.home") + "/Library/Application Support" // macOS
        osName.contains("nix") || osName.contains("nux") -> System.getProperty("user.home") + "/.config" // Linux config directory
        else -> throw UnsupportedOperationException("Unsupported OS: $osName")
    }

    val file = File("$homeDir/PeerReviewData/$fileName")
    return if (file.exists()) {
        decrypt(file.readText()) // Read string from file
    } else {
        null
    }
}

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