// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

object Challenge1 {
  val hexChars = "0123456789abcdef"
  val base64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"

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

  def encodeByte(byte: Byte): String = {
    val index0 = byte >> 4
    val index1 = byte
    val indices = Vector(index0 & 15, index1 & 15)
    val chars = indices.map(i => hexChars(i))
    chars.mkString
  }

  def encodeTriple(triple: Vector[Byte]): String = {
    val index0 = triple(0) >> 2
    val index1 = ((triple(0) & 3) << 4) | (triple(1) >> 4)
    val index2 = ((triple(1) & 15) << 2) | (triple(2) >> 6)
    val index3 = triple(2)
    val indices = Vector(index0 & 63, index1 & 63, index2 & 63, index3 & 63)
    val chars = indices.map(i => base64Chars(i))
    chars.mkString
  }

  def decodeHex(str: String): Vector[Byte] =
    str.grouped(2).map(b => decodeByte(b)).toVector

  def encodeHex(data: Vector[Byte]): String =
    data.map(b => encodeByte(b)).mkString

  def decodeBase64(str: String): Vector[Byte] = ???

  def encodeBase64(data: Vector[Byte]): String =
    data.grouped(3).map(t => encodeTriple(t)).mkString

  def main(args: Array[String]) = {
    val input = "49276d206b696c6c696e6720796f757220627261696e206c" +
                "696b65206120706f69736f6e6f7573206d757368726f6f6d"
    val data = decodeHex(input)
    val output = encodeBase64(data)
    println(output)
  }
}
