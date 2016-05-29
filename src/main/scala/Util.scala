// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

package com.matasano

object Util {
  val bitCount: Int => Int = java.lang.Integer.bitCount

  def hammingDistance(a: Vector[Byte], b: Vector[Byte]): Int = {
    // Number of bits that differ.
    val baseDiff = a.zip(b).map { case (x, y) => bitCount((x ^ y) & 0xff) } .sum

    // Also count a length mismatch as eight different bits per byte.
    val extraDiff = math.abs(a.length - b.length) * 8

    baseDiff + extraDiff
  }

  def padPkcs7(a: Vector[Byte], blockSize: Int): Vector[Byte] = {
    val modSz = (a.length % blockSize)
    val padLen = if (modSz == 0) { blockSize } else { blockSize - modSz }
    val padding = Vector.fill(padLen)(padLen.toByte)
    a ++ padding
  }
}
