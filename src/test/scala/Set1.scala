// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import org.scalacheck.Properties

import com.matasano.Crypto
import com.matasano.Encoding

object Set1Spec extends Properties("Set1") {
  property("challenge1") = {
    val input = "49276d206b696c6c696e6720796f757220627261696e206c" +
                "696b65206120706f69736f6e6f7573206d757368726f6f6d"
    val output = "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29" +
                 "ub3VzIG11c2hyb29t"
    output == Encoding.encodeBase64(Encoding.decodeHex(input))
  }

  property("challenge2") = {
    val inputA = Encoding.decodeHex("1c0111001f010100061a024b53535009181c")
    val inputB = Encoding.decodeHex("686974207468652062756c6c277320657965")
    val output = Encoding.decodeHex("746865206b696420646f6e277420706c6179")

    output == Crypto.xor(inputA, inputB)
  }

  property("challenge5") = {
    val input = Encoding.encodeAscii("Burning 'em, " +
                                     "if you ain't quick and nimble\n" +
                                     "I go crazy when I hear a cymbal")
    val output = Encoding.decodeHex("0b3637272a2b2e63622c2e69692a23693a2a3" +
                                    "c6324202d623d63343c2a2622632427276527" +
                                    "2a282b2f20430a652e2c652a3124333a653e2" +
                                    "b2027630c692b20283165286326302e27282f")
    val key = Encoding.encodeAscii("ICE")

    output == Crypto.xorRepeat(input, key)
  }
}
