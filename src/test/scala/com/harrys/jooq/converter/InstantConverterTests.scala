package com.harrys.jooq.converter

import java.time.Instant

import org.scalacheck.{Gen, Properties}
import org.scalacheck.Prop.forAll

/**
  * Created by jpetty on 12/15/15.
  */
object InstantConverterTests extends Properties("InstantConverter") {

  val converter = new InstantConverter()

  val timestamps = Gen.chooseNum[Long](0, Instant.MAX.getEpochSecond / 10)

  property("serialization") = forAll(timestamps) { (seconds: Long) =>
    val instant = Instant.ofEpochSecond(seconds)
    converter.from(converter.to(instant)).equals(instant)
  }
}
