// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

package com.matasano

import collection.immutable.SortedMap
import io.Source

object TextDetection {
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
}
