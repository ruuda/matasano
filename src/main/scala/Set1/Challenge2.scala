// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import com.matasano.Crypto
import com.matasano.Encoding

object Challenge2 {
  def main(args: Array[String]) = {
    val inputA = Encoding.decodeHex("1c0111001f010100061a024b53535009181c")
    val inputB = Encoding.decodeHex("686974207468652062756c6c277320657965")
    val data = Crypto.xor(inputA, inputB)
    val output = Encoding.encodeHex(data)
    println(output)
  }
}
