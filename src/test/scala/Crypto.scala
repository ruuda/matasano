// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import org.scalacheck.Gen
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

import com.matasano.Aes
import com.matasano.Crypto

object CryptoTests extends Properties("Crypto") {
  val blocks = Gen.listOfN(16, Gen.choose(0.toByte, 255.toByte)).map(_.toVector)
  val nBlocks = Gen.listOfN(64, Gen.choose(0.toByte, 255.toByte)).map(_.toVector)

  property("bijectionEncryptCbc") = forAll(blocks, blocks, nBlocks) {
    (iv: Vector[Byte], key: Vector[Byte], plaintext: Vector[Byte]) => {
      val decrypt = Crypto.decryptCbc(Aes.decrypt(key)) _
      val encrypt = Crypto.encryptCbc(Aes.encrypt(key)) _
      decrypt(iv, encrypt(iv, plaintext)) == plaintext
    }
  }
}
