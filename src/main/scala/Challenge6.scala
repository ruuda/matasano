// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import io.Source

import com.matasano.Encoding
import com.matasano.Util

object Challenge6 {
  /// Returns a list of key size guesses, from most likely to least likely.
  def guessKeySize(ciphertext: Vector[Byte], minSize: Int, maxSize: Int): Vector[Int] = {
    val rankedSizes = (minSize to maxSize).map(keySize => {
      // Inspect the first ten blocks for this key size.
      val groups = ciphertext.grouped(keySize).take(10).toVector
      val distances = for {
        i <- 0 to groups.length - 1
        j <- i + 1 to groups.length - 1
      } yield Util.hammingDistance(groups(i), groups(j))

      // Compute average number of different bits per byte.
      val numGroups = groups.length * (groups.length - 1) / 2
      var meanDistance = distances.sum.toFloat / numGroups.toFloat
      var normDistance = meanDistance / keySize.toFloat

      (normDistance, keySize)
    })

    rankedSizes.sorted.map { case (d, sz) => sz } .toVector
  }

  def main(args: Array[String]) = {
    val file = Source.fromFile("data/challenge6.md")
    val ciphertext = Encoding.decodeBase64(file.getLines.mkString)

    val keySize = guessKeySize(ciphertext, 2, 40).head
    println(s"key size guess: $keySize")

    file.close()
  }
}
