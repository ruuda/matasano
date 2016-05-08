// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import io.Source

import com.matasano.Crypto
import com.matasano.Encoding
import com.matasano.TextDetection

object Challenge4 {
  def main(args: Array[String]) = {
    val file = Source.fromFile("data/challenge4.md")
    val rank = TextDetection.buildRanker("data/frequency.md")

    val strcandidates = file.getLines().flatMap(line => {
      val ciphertext = Encoding.decodeHex(line)
      val candidates = (0 to 255).map(key => Crypto.xorSingle(ciphertext, key.toByte))
      candidates.map(bytes => new String(bytes.toArray, "ASCII"))
    })

    val plaintext = strcandidates.maxBy(rank)
    println(plaintext)

    file.close()
  }
}
