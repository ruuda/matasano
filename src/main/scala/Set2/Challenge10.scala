// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import io.Source

import com.matasano.Aes
import com.matasano.Crypto
import com.matasano.Encoding

object Challenge10 {
  def main(args: Array[String]) = {
    val file = Source.fromFile("data/challenge10.txt")
    val ciphertext = Encoding.decodeBase64(file.getLines.mkString)
    val key = Encoding.encodeAscii("YELLOW SUBMARINE")
    val decrypt = Aes.decrypt(key)
    val iv: Vector[Byte] = Vector(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val plaintext = Crypto.decryptCbc(decrypt)(iv, ciphertext)

    println(Encoding.decodeAscii(plaintext))

    file.close()
  }
}
