// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

import com.matasano.Aes

object AesTests extends Properties("Aes") {
  property("bijectionBlock") = forAll { (block: Vector[Byte], key: Vector[Byte]) =>
    // TODO: Ensure block and key have proper length.
    Aes.decrypt(Aes.encrypt(block, key), key) == block
  }

  property("bijectionBlock") = forAll { (block: Vector[Byte], key: Vector[Byte]) =>
    // TODO: Ensure block and key have proper length.
    Aes.encrypt(Aes.decrypt(block, key), key) == block
  }
}