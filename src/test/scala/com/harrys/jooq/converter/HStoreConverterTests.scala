package com.harrys.jooq.converter

import com.harrys.jooq.HStoreValue
import org.postgresql.util.HStoreConverter
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

import scala.collection.JavaConversions

/**
  * Created by jpetty on 12/15/15.
  */
object HStoreConverterTests extends Properties("HStoreValueConverter") {

  val converter = new HStoreValueConverter()

  property("parse") = forAll { (map: Map[String, String]) =>
    val direct = new HStoreValue(JavaConversions.mapAsJavaMap(map))
    val parsed = converter.from(HStoreConverter.toString(JavaConversions.mapAsJavaMap(map)))
    direct.equals(parsed)
  }

  property("serialize") = forAll { (map: Map[String, String]) =>
    val value   = new HStoreValue(JavaConversions.mapAsJavaMap(map))
    converter.from(converter.to(value)).equals(value)
  }

}
