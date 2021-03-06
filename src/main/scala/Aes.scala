// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

package com.matasano

object Aes {
  import javax.crypto.Cipher
  import javax.crypto.spec.SecretKeySpec

  def encrypt(key: Vector[Byte]): Vector[Byte] => Vector[Byte] = {
    // Use the Java AES implementation only for its block cipher.
    // The point of this exercise it to learn how to do things myself,
    // so I will not use the standard block-chaining or padding mechanisms.
    val cipher = Cipher.getInstance("AES/ECB/NoPadding")
    val keySpec = new SecretKeySpec(key.toArray, "AES")
    cipher.init(Cipher.ENCRYPT_MODE, keySpec)
    block => {
      require(block.length == 16, "block length must be 16")
      cipher.doFinal(block.toArray).toVector
    }
  }

  def decrypt(key: Vector[Byte]): Vector[Byte] => Vector[Byte] = {
    val cipher = Cipher.getInstance("AES/ECB/NoPadding")
    val keySpec = new SecretKeySpec(key.toArray, "AES")
    cipher.init(Cipher.DECRYPT_MODE, keySpec)
    block => {
      require(block.length == 16, "block length must be 16")
      cipher.doFinal(block.toArray).toVector
    }
  }
}
