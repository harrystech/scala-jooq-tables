package com.harrys.jooq.converter

import java.time.{ZoneId, ZonedDateTime, Instant}

import org.scalacheck.Gen
import org.scalatest._
import org.scalatest.prop.GeneratorDrivenPropertyChecks


/**
 * Created by chris on 2/2/16.
 */
class ZonedDateTimeConverterTests extends PropSpec with GeneratorDrivenPropertyChecks with ShouldMatchers {

  val converter = new ZonedDateTimeConverter()
  val zoneIds   = scala.collection.JavaConversions.asScalaSet(ZoneId.getAvailableZoneIds).toSeq.map(zone => ZoneId.of(zone))

  val zonedDateTimeGen = for {
    epoch <- Gen.chooseNum[Long](0, Instant.MAX.getEpochSecond / 10)
    timezone <- Gen.oneOf[ZoneId](zoneIds)
  } yield ZonedDateTime.ofInstant(Instant.ofEpochSecond(epoch), timezone)

  property("ensure mapping between ZonedDateTime and Timestamp (and back) refer to same getInstant") {
    forAll((zonedDateTimeGen, "zonedDateTimes")) { (zonedDateTime: ZonedDateTime) =>
      val timestamp = converter.to(zonedDateTime)
      timestamp.toInstant shouldEqual zonedDateTime.toInstant

      val newZonedDateTime = converter.from(timestamp)
      newZonedDateTime.toInstant shouldEqual timestamp.toInstant
    }
  }
}
