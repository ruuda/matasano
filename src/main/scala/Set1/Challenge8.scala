// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import io.Source

import com.matasano.Encoding
import com.matasano.Util

object Challenge8 {
  def repetition(ciphertext: Vector[Byte]): Float = {
    // Compute the Hamming distance between all pairs of blocks. If the
    // blocks are encrypted properly then they are random, so their Hamming
    // distance is 0.5 * 8 * 16 on average. If the blocks were encrypted
    // individually, similarities will yield a lower distance.
    val groups = ciphertext.grouped(16).toVector
    val distances = for {
      i <- 0 to groups.length - 1
      j <- i + 1 to groups.length - 1
    } yield {
      require(groups(i).length == groups(j).length)
      Util.hammingDistance(groups(i), groups(j))
    }

    val numPairs = groups.length * (groups.length - 1) / 2
    var meanDistance = distances.sum.toFloat / numPairs.toFloat

    // Express the distance relative to the expected distance.
    meanDistance / 64.0f
  }

  def main(args: Array[String]) = {
    val file = Source.fromFile("data/challenge8.md")
    val ciphertexts = file.getLines.map(line => Encoding.decodeHex(line))

    val ranked = ciphertexts.map(ct => (repetition(ct), ct))
    val top = ranked.toVector.sortWith {
      case ((r1, _), (r2, _)) => r1 < r2
    } .take(10)

    println("Candidates (with confidence, lower is better)")

    for ((r, ct) <- top) {
      val hdata = Encoding.encodeHex(ct)
      println(s"$r $hdata")
    }

    file.close()
  }
}
