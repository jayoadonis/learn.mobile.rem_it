package learn.mobile.rem_it.utils

import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import java.security.SecureRandom
import java.util.Base64

//REM: Function to hash the rawString (includes salt within the hash itself)
fun hash_it(rawString: String, iterations: Int = 10000, keyLength: Int = 256): String {
    //REM: Generate a random salt (16 bytes), must be the same with encryption part
    val salt = ByteArray(16)
    val random = SecureRandom()
    random.nextBytes(salt)

    //REM: Create the PBKDF2 hashing instance, note expensive cloning
    val spec = PBEKeySpec(rawString.toCharArray(), salt, iterations, keyLength)
    val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
    val hashedPassword = factory.generateSecret(spec).encoded

    //REM: Combine salt and hashed rawString, then return it as a Base64-encoded string
    val saltedHash = ByteArray(salt.size + hashedPassword.size)
    /**
     * p1 - src_arr
     * p2 - src_arr_offset_pos
     * p3 - dest_arr
     * p4 - dest_arr_offset_pos
     * p5 - src_arr_size
     */
    System.arraycopy(salt, 0, saltedHash, 0, salt.size)
    System.arraycopy(hashedPassword, 0, saltedHash, salt.size, hashedPassword.size)

    return Base64.getEncoder().encodeToString(saltedHash)
}

//REM: Function to verify the rawString (extracts the salt and verifies the hash)
fun verify_it(rawString: String, storedHash: String, iterations: Int = 10000, keyLength: Int = 256): Boolean {
    //REM: Decode the stored hash (which includes salt and hash)
    val decodedHash = Base64.getDecoder().decode(storedHash)

    //REM: Extract the salt (first 16 bytes), must be the same with encryption part
    val salt = decodedHash.copyOfRange(0, 16)

    //REM: Extract the stored hash (remaining bytes)
    val storedPasswordHash = decodedHash.copyOfRange(16, decodedHash.size)

    //REM: Rehash the input rawString with the extracted salt, note expensive cloning
    val spec = PBEKeySpec(rawString.toCharArray(), salt, iterations, keyLength)
    val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
    val hashedInputPassword = factory.generateSecret(spec).encoded

    //REM: Compare the computed hash with the stored hash
    return storedPasswordHash.contentEquals(hashedInputPassword)
}

