// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import com.matasano.Encoding

object Challenge1 {
  def main(args: Array[String]) = {
    val input = "49276d206b696c6c696e6720796f757220627261696e206c" +
                "696b65206120706f69736f6e6f7573206d757368726f6f6d"
    val data = Encoding.decodeHex(input)
    val output = Encoding.encodeBase64(data)
    println(output)
  }
}
