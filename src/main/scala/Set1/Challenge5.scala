// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import com.matasano.Crypto
import com.matasano.Encoding

object Challenge5 {
  def main(args: Array[String]) = {
    val key = Encoding.encodeAscii("ICE")
    val input = Encoding.encodeAscii("Burning 'em, " +
                                     "if you ain't quick and nimble\n" +
                                     "I go crazy when I hear a cymbal")
    val data = Crypto.xorRepeat(input, key)
    val output = Encoding.encodeHex(data)
    println(output)
  }
}

