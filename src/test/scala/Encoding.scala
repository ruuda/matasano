// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

import com.matasano.Encoding

object EncodingTests extends Properties("Encoding") {
  property("bijectionByte") = forAll { (byte: Byte) =>
    Encoding.decodeByte(Encoding.encodeByte(byte)) == byte
  }

  property("bijectionTriple") = forAll { (b0: Byte, b1: Byte, b2: Byte) =>
    val triple = Vector(b0, b1, b2)
    Encoding.decodeTriple(Encoding.encodeTriple(triple)) == triple
  }

  property("bijectionHex") = forAll { (data: Vector[Byte]) =>
    Encoding.decodeHex(Encoding.encodeHex(data)) == data
  }

  property("bijectionBase64") = forAll { (data: Vector[Byte]) =>
    // For now, ensure that the data length is a multiple of 3, because I do not
    // deal with padding yet.
    val data3 = data ++ data ++ data
    Encoding.decodeBase64(Encoding.encodeBase64(data3)) == data3
  }
}
