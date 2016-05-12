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

  def buildHistogramPairFromFile(fname: String): Histogram[Int] = {
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

  def buildHistogramSingleFromFile(fname: String): SortedMap[Char, Int] = {
    val file = Source.fromFile(fname)
    val chars = file.getLines().flatMap(line => line.to)
    val histogram = buildHistogram(chars)
    file.close()
    histogram
  }

  def buildHistogram(chars: Iterator[Char]): SortedMap[Char, Int] = {
    val empty: SortedMap[Char, Int] = SortedMap()
    chars.map(c => c.toLower).foldLeft(empty) { (hist, c) =>
      val count = hist.getOrElse(c, 0)
      hist.updated(c, count + 1)
    }
  }

  // Ensures that every value in the histogram is in the interval [0, 2].
  def normalizeHistogramMax[Key](counts: SortedMap[Key, Int]): SortedMap[Key, Double] = {
    val maxCount = counts.foldLeft(0) { case (acc, (k, n)) => math.max(acc, n) }
    counts.mapValues(n => 2.0 * n / maxCount)
  }

  // Ensures that all values in the histogram sum to 1.0.
  def normalizeHistogram[Key](counts: SortedMap[Key, Int]): SortedMap[Key, Double] = {
    val sum = counts.foldLeft(0) { case (acc, (k, n)) => acc + n }
    counts.mapValues(n => n / sum.toDouble)
  }

  // Builds a function that ranks strings based on consecutive character
  // frequencies extracted from the sample file.
  def buildRanker(fname: String): String => Double = {
    val histInt = buildHistogramPairFromFile(fname)
    val histDouble = normalizeHistogramMax(histInt)

    def rankChars(chars: (Char, Char)): Double =
      histDouble.getOrElse(chars, 0.05)

    def rankString(str: String): Double = {
      val lowerStr = str.map(c => c.toLower)
      val pairs = lowerStr.zip(lowerStr.drop(1))
      pairs.foldLeft(1.0) { (acc, chars) => acc * rankChars(chars) }
    }

    rankString
  }

  // Builds a function that ranks histograms based on how similar they are to
  // the character frequencies in the sample file.
  def buildHistogramRanker(fname: String): SortedMap[Char, Int] => Double = {
    val histInt = buildHistogramSingleFromFile(fname)
    val histDouble = normalizeHistogram(histInt)

    def rankHistogram(sampleHist: SortedMap[Char, Int]): Double = {
      val sampleDouble = normalizeHistogram(sampleHist)

      // Consider the histograms as vectors, and compute the squared L2 norm of
      // their difference.
      val keys = histDouble.keySet ++ sampleDouble.keySet
      val diffSqr = keys.foldLeft(0.0) { (acc, c) => {
        val left = histDouble.getOrElse(c, 0.0)
        val right = sampleDouble.getOrElse(c, 0.0)
        acc + (left - right) * (left - right)
      }}

      // A higher ranking is better, so negate the difference.
      -diffSqr
    }

    rankHistogram
  }
}
