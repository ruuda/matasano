// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import org.scalacheck.Properties

import com.matasano.Encoding
import com.matasano.Util

object UtilTests extends Properties("Util") {
  property("hammingDistance") = {
    val a = Encoding.encodeAscii("this is a test")
    val b = Encoding.encodeAscii("wokka wokka!!!")
    Util.hammingDistance(a, b) == 37
  }
}