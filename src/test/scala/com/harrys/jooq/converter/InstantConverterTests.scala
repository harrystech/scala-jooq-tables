package com.harrys.jooq.converter

import java.time.Instant

import org.scalacheck.{Properties, Gen}
import org.scalatest.{PropSpec, FlatSpec, ShouldMatchers}
import org.scalatest.prop.GeneratorDrivenPropertyChecks

/**
  * Created by jpetty on 12/15/15.
  */
class InstantConverterTests extends PropSpec with GeneratorDrivenPropertyChecks with ShouldMatchers {

  val converter = new InstantConverter()

  val timestamps = Gen.chooseNum[Long](0, Instant.MAX.getEpochSecond / 10)

  property("serialize and deserialize to the same value") {
    forAll((timestamps, "timestamps")) { (seconds: Long) =>
      val instant = Instant.ofEpochSecond(seconds)
      converter.from(converter.to(instant)) shouldEqual instant
    }
  }


}
