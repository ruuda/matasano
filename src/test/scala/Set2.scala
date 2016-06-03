// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import org.scalacheck.Properties

import com.matasano.Encoding
import com.matasano.Util

object Set2Spec extends Properties("Set2") {
  property("challenge9") = {
    val input = Encoding.encodeAscii("YELLOW SUBMARINE")
    val output = Encoding.encodeAscii("YELLOW SUBMARINE\u0004\u0004\u0004\u0004")

    output == Util.padPkcs7(input, 20)
  }
}
