package com.github.haydensikh.macros

import org.scalatest.{ FunSpec, Matchers }
import com.github.haydensikh.macros.Macros._

class MacroSpec extends FunSpec with Matchers {
  describe("Instance-creating style") {
    it("returns object with val members matching the path") {
      val res = get("/widget/:id/between/:startDate/and/:endDate")

      res.id should not be (null)
      res.startDate should not be (null)
      res.endDate should not be (null)
    }
  }

  /*
  describe("Class-creating style") {
    it("puts a class into scope named Foo") {
      get2("/widget/:id/between/:startDate/and/:endDate")

      classOf[Foo] must not be (null)
    }
  }
  */
}
