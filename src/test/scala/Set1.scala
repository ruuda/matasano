// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

import Challenge1._
import Challenge2._

object Set1Spec extends Properties("Set1") {
  property("challenge1") = {
    val input = "49276d206b696c6c696e6720796f757220627261696e206c" +
                "696b65206120706f69736f6e6f7573206d757368726f6f6d"
    val output = "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29" +
                 "ub3VzIG11c2hyb29t"
    output == encodeBase64(decodeHex(input))
  }

  property("challenge2") = {
    val inputA = decodeHex("1c0111001f010100061a024b53535009181c")
    val inputB = decodeHex("686974207468652062756c6c277320657965")
    val output = decodeHex("746865206b696420646f6e277420706c6179")

    output == xor(inputA, inputB)
  }
}

object AdditionalTests extends Properties("Set1") {
  property("bijectionByte") = forAll { (byte: Byte) =>
    decodeByte(encodeByte(byte)) == byte
  }

  property("bijectionTriple") = forAll { (b0: Byte, b1: Byte, b2: Byte) =>
    val triple = Vector(b0, b1, b2)
    decodeTriple(encodeTriple(triple)) == triple
  }

  property("bijectionHex") = forAll { (data: Vector[Byte]) =>
    decodeHex(encodeHex(data)) == data
  }

  property("bijectionBase64") = forAll { (data: Vector[Byte]) =>
    // For now, ensure that the data length is a multiple of 3, because I do not
    // deal with padding yet.
    val data3 = data ++ data ++ data
    decodeBase64(encodeBase64(data3)) == data3
  }
}
