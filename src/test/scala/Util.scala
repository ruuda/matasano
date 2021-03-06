// Copyright 2016 Ruud van Asseldonk
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// A copy of the License is available in the root of the repository.

import org.scalacheck.Arbitrary
import org.scalacheck.Gen
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

import com.matasano.Encoding
import com.matasano.Util

object UtilTests extends Properties("Util") {
  val bytevectors = Arbitrary.arbitrary[Vector[Byte]]
  val blockLength = Gen.choose(0, 64)

  property("hammingDistance") = {
    val a = Encoding.encodeAscii("this is a test")
    val b = Encoding.encodeAscii("wokka wokka!!!")
    Util.hammingDistance(a, b) == 37
  }

  property("hammingDistanceBoundedByInput") = forAll { (a: Vector[Byte], b: Vector[Byte]) =>
    val diff = Util.hammingDistance(a, b)
    val maxDiff = math.max(a.length, b.length) * 8
    diff <= maxDiff
  }

  property("bijectionPadPkcs7") = forAll(bytevectors, blockLength) { (a: Vector[Byte], b: Int) =>
    a == Util.unpadPkcs7(Util.padPkcs7(a, b))
  }
}
