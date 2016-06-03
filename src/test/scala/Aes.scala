// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import org.scalacheck.Gen
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

import com.matasano.Aes

object AesTests extends Properties("Aes") {
  val blocks = Gen.listOfN(16, Gen.choose(0.toByte, 255.toByte)).map(_.toVector)

  property("bijectionBlock") = forAll(blocks, blocks) { (block: Vector[Byte], key: Vector[Byte]) =>
    Aes.decrypt(key)(Aes.encrypt(key)(block)) == block
  }

  property("bijectionBlock") = forAll(blocks, blocks) { (block: Vector[Byte], key: Vector[Byte]) =>
    Aes.encrypt(key)(Aes.decrypt(key)(block)) == block
  }
}
