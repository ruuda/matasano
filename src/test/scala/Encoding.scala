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

  property("bijectionTriplePad1") = forAll { (b0: Byte, b1: Byte) =>
    val pair = Vector(b0, b1)
    Encoding.decodeTriple(Encoding.encodeTriple(pair)) == pair
  }

  property("bijectionTriplePad2") = forAll { (b0: Byte) =>
    val byte = Vector(b0)
    Encoding.decodeTriple(Encoding.encodeTriple(byte)) == byte
  }

  property("bijectionHex") = forAll { (data: Vector[Byte]) =>
    Encoding.decodeHex(Encoding.encodeHex(data)) == data
  }

  property("bijectionBase64") = forAll { (data: Vector[Byte]) =>
    Encoding.decodeBase64(Encoding.encodeBase64(data)) == data
  }

  property("bijectionAscii") = forAll { (data: Vector[Byte]) =>
    Encoding.encodeAscii(Encoding.decodeAscii(data)) == data
  }
}
