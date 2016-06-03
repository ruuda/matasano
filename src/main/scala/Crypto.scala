// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

package com.matasano

object Crypto {
  def xor(a: Vector[Byte], b: Vector[Byte]): Vector[Byte] =
    a.zip(b).map { case (x, y) => (x ^ y).toByte }

  def xorSingle(a: Vector[Byte], key: Byte): Vector[Byte] = {
    val repeated = Vector.fill(a.length) { key }
    xor(a, repeated)
  }

  def xorRepeat(a: Vector[Byte], key: Vector[Byte]): Vector[Byte] = {
    val repeated = Stream.continually(key).flatten.take(a.length).toVector
    xor(a, repeated)
  }

  def encryptCbc(encrypt: Vector[Byte] => Vector[Byte])
                (iv: Vector[Byte], plaintext: Vector[Byte]): Vector[Byte] = {
    // Assume a block size of 16 for now.
    require(iv.length == 16)
    require(plaintext.length % 16 == 0)

    plaintext.grouped(16).scanLeft(iv) {
      case (prev, block) => encrypt(xor(prev, block))
    }
    .flatMap(block => block)
    .toVector
  }

  def decryptCbc(decrypt: Vector[Byte] => Vector[Byte])
                (iv: Vector[Byte], ciphertext: Vector[Byte]): Vector[Byte] = {
    // Assume a block size of 16 for now.
    require(iv.length == 16)
    require(ciphertext.length % 16 == 0)

    val blocks = ciphertext.grouped(16).toVector
    val withIv = Vector(iv) ++ blocks
    withIv.zip(blocks).flatMap {
      case (prev, block) => xor(prev, decrypt(block))
    }
    .toVector
  }
}
