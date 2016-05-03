// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

object Challenge1 {
  val hexChars = "0123456789abcdef"
  val base64Chars = ???

  def decodeByte(str: String): Byte = {
    if (str.length != 2) {
      throw new Exception("Invalid input, not an integer number of bytes.")
    }
    val high = hexChars.indexOf(str(0))
    val low = hexChars.indexOf(str(1))
    if (high == -1 || low == -1) {
      throw new Exception(s"Invalid input, '$str' is not hexadecimal.")
    } else {
      ((high << 4) | low).toByte
    }
  }

  def decodeHex(str: String): Array[Byte] =
    str.grouped(2).map(b => decodeByte(b)).toArray

  def encodeHex(data: Array[Byte]): String = ???
  def decodeBase64(str: String): Array[Byte] = ???
  def encodeBase64(data: Array[Byte]): String = ???

  def main(args: Array[String]) = {
    val input = "49276d206b696c6c696e6720796f757220627261696e206c" +
                "696b65206120706f69736f6e6f7573206d757368726f6f6d"
    val data = decodeHex(input)
    val output = encodeBase64(data)
    println(output)
  }
}
