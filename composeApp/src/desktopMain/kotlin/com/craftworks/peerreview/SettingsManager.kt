package com.craftworks.peerreview

import java.io.File


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
    if(!file.exists()) file.createNewFile()
    file.writeText(data) // Write string to file
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
        file.readText() // Read string from file
    } else {
        null
    }
}
