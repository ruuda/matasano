// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import collection.immutable.SortedMap
import io.Source

import Challenge1._
import Challenge2._

object Challenge3 {
  type Histogram[A] = SortedMap[(Char, Char), A]

  def buildHistogram(fname: String): Histogram[Int] = {
    val file = Source.fromFile(fname)
    var histogram: SortedMap[(Char, Char), Int] = SortedMap()

    for (line <- file.getLines()) {
      val lowerLine = line.map(c => c.toLower)
      for (pair <- lowerLine.zip(lowerLine.drop(1))) {
        val count = histogram.getOrElse(pair, 0)
        histogram = histogram.updated(pair, count + 1)
      }
    }

    file.close()
    histogram
  }

  // Ensures that every value in the histogram is in the interval [0, 2].
  def normalizeHistogram(counts: Histogram[Int]): Histogram[Double] = {
    val maxCount = counts.foldLeft(0) { case (acc, (c, n)) => math.max(acc, n) }
    counts.mapValues(n => 2.0 * n / maxCount)
  }

  // Builds a function that ranks strings based on consecutive character
  // frequencies extracted from the sample file.
  def buildRanker(fname: String): String => Double = {
    val histInt = buildHistogram(fname)
    val histDouble = normalizeHistogram(histInt)

    def rankChars(chars: (Char, Char)): Double =
      histDouble.getOrElse(chars, 0.05)

    def rankString(str: String): Double = {
      val lowerStr = str.map(c => c.toLower)
      val pairs = lowerStr.zip(lowerStr.drop(1))
      pairs.foldLeft(1.0) { (acc, chars) => acc * rankChars(chars) }
    }

    rankString
  }

  def xorSingle(a: Vector[Byte], key: Byte): Vector[Byte] = {
    val repeated = Vector.fill(a.length) { key }
    xor(a, repeated)
  }

  def main(args: Array[String]) = {
    val input = "1b37373331363f78151b7f2b783431333d" +
                "78397828372d363c78373e783a393b3736"

    val ciphertext = decodeHex(input)
    val candidates = (0 to 255).map(key => xorSingle(ciphertext, key.toByte))
    val strcandidates = candidates.map(bytes => new String(bytes.toArray, "ASCII"))

    val rank = buildRanker("data/frequency.md")
    val plaintext = strcandidates.maxBy(rank)

    println(plaintext)
  }
}
