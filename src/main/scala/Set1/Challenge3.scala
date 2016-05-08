// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import com.matasano.Crypto
import com.matasano.Encoding
import com.matasano.TextDetection

object Challenge3 {
  def main(args: Array[String]) = {
    val input = "1b37373331363f78151b7f2b783431333d" +
                "78397828372d363c78373e783a393b3736"

    val ciphertext = Encoding.decodeHex(input)
    val candidates = (0 to 255).map(key => Crypto.xorSingle(ciphertext, key.toByte))
    val strcandidates = candidates.map(bytes => new String(bytes.toArray, "ASCII"))

    val rank = TextDetection.buildRanker("data/frequency.md")
    val plaintext = strcandidates.maxBy(rank)

    println(plaintext)
  }
}
