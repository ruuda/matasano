// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

package com.matasano

object Encoding {
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
    val index0 = (byte & 255) >> 4
    val index1 = (byte & 255)
    val indices = Vector(index0 & 15, index1 & 15)
    val chars = indices.map(i => hexChars(i))
    chars.mkString
  }

  def decodeTriple(str: String): Vector[Byte] = {
    if (str.length != 4) {
      throw new Exception("Invalid input, not an integer number of bytes.")
    }

    val index0 = base64Chars.indexOf(str(0))
    val index1 = base64Chars.indexOf(str(1))
    val index2 = base64Chars.indexOf(str(2))
    val index3 = base64Chars.indexOf(str(3))

    if (index0 == -1 || index1 == -1 || index2 == -1 || index3 == -1) {
      throw new Exception(s"Invalid input, '$str' is not base64.")
    }

    val byte0 = (index0 << 2) | (index1 >> 4)
    val byte1 = (index1 << 4) | (index2 >> 2)
    val byte2 = (index2 << 6) | (index3)
    Vector(byte0.toByte, byte1.toByte, byte2.toByte)
  }

  def encodeTriple(triple: Vector[Byte]): String = {
    require(triple.length == 3)

    // Note: The & 255 is required here to make the shift behave like a regular
    // shift of an unsigned integer.
    val index0 = (triple(0) & 255) >> 2
    val index1 = ((triple(0) & 3) << 4) | ((triple(1) & 255) >> 4)
    val index2 = ((triple(1) & 15) << 2) | ((triple(2) & 255) >> 6)
    val index3 = (triple(2) & 255)
    val indices = Vector(index0 & 63, index1 & 63, index2 & 63, index3 & 63)
    val chars = indices.map(i => base64Chars(i))
    chars.mkString
  }

  def decodeHex(str: String): Vector[Byte] =
    str.grouped(2).map(b => decodeByte(b)).toVector

  def encodeHex(data: Vector[Byte]): String =
    data.map(b => encodeByte(b)).mkString

  def decodeBase64(str: String): Vector[Byte] =
    str.grouped(4).flatMap(t => decodeTriple(t)).toVector

  def encodeBase64(data: Vector[Byte]): String =
    data.grouped(3).map(t => encodeTriple(t)).mkString

  def decodeAscii(data: Vector[Byte]): String =
    // Actually use Latin 1 to ensure that the upper 128 bytes
    // can be mapped as well. Do not use UTF-8, because not every
    // byte sequence is valid UTF-8.
    new String(data.toArray, "ISO8859_1")

  def encodeAscii(str: String): Vector[Byte] =
    str.getBytes("ISO8859_1").toVector
}
