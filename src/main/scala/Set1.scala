// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

object Challenge1 {
  def decodeHex(str: String): Array[Byte] = ???
  def encodeHex(data: Array[Byte]): String = ???
  def decodeBase64(str: String): Array[Byte] = ???
  def encodeBase64(data: Array[Byte]): String = ???

  def main(args: Array[String]) = {
    val input = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"
    val data = decodeHex(input)
    val output = encodeBase64(data)
    println(output)
  }
}
