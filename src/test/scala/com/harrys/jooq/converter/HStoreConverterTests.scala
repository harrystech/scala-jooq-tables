package com.harrys.jooq.converter

import com.harrys.jooq.HStoreValue
import org.postgresql.util.HStoreConverter
import org.scalatest.{PropSpec, Matchers, FlatSpec, ShouldMatchers}
import org.scalatest.prop.{PropertyChecks, GeneratorDrivenPropertyChecks}

import scala.collection.JavaConversions

/**
  * Created by jpetty on 12/15/15.
  */
class HStoreConverterTests extends PropSpec with GeneratorDrivenPropertyChecks with Matchers {

  val converter = new HStoreValueConverter()

  property("maintain key ordering after conversion") {
    forAll("map") { (map: Map[String, String]) =>
      val direct = new HStoreValue(JavaConversions.mapAsJavaMap(map))
      val parsed = converter.from(HStoreConverter.toString(JavaConversions.mapAsJavaMap(map)))
      direct shouldEqual parsed
    }
  }

  property("serialize and deserialize to the same value") {
    forAll("map") { (map: Map[String, String]) =>
      val value = new HStoreValue(JavaConversions.mapAsJavaMap(map))
      converter.from(converter.to(value)) shouldEqual value
    }
  }
}
