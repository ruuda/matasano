// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import io.Source

import com.matasano.Encoding

object Challenge6 {
  def main(args: Array[String]) = {
    val file = Source.fromFile("data/challenge6.md")
    val ciphertext = Encoding.decodeBase64(file.getLines.mkString)
    file.close()
  }
}
