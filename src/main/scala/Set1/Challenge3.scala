// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import Challenge1._
import Challenge2._

object Challenge3 {
  def xorSingle(a: Vector[Byte], key: Byte): Vector[Byte] = {
    val repeated = Vector.fill(a.length) { key }
    xor(a, repeated)
  }

  def main(args: Array[String]) = {
    val input = "1b37373331363f78151b7f2b783431333d" +
                "78397828372d363c78373e783a393b3736"
    val ciphertext = decodeHex(input)

    for (key <- 0 to 255) {
      val plaintext = xorSingle(ciphertext, key.toByte)
      println(plaintext)
    }
  }
}
