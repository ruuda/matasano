// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

package com.matasano

object Crypto {
  def xor(a: Vector[Byte], b: Vector[Byte]): Vector[Byte] =
    a.zip(b).map { case (x, y) => (x ^ y).toByte }

  def xorSingle(a: Vector[Byte], key: Byte): Vector[Byte] = {
    val repeated = Vector.fill(a.length) { key }
    xor(a, repeated)
  }
}
