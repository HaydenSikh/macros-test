package com.github.haydensikh.macros

import scala.reflect.macros.whitebox.Context
import scala.reflect.runtime.{ universe => u }
import language.experimental.macros

object Macros {
  def get(path: String): Any = macro get_impl

  def get_impl(c: Context)(path: c.Expr[String]) = {
    import c.universe._

    path.tree match {
      case Literal(Constant(s: String)) =>
        val paramDefs = paramsOf(s)
          .map(TermName(_ ))
          .map(tn => q"""val $tn: String = "a" """)

        q"""
        new {
          ..$paramDefs
        }
        """
      case _ => c.abort(c.enclosingPosition, s"\n    Found: $path\n    Required: String literal")
    }
  }

  def get2(path: String): Unit = macro get_impl2

  def get_impl2(c: Context)(path: c.Expr[String]) = {
    import c.universe._

    path.tree match {
      case Literal(Constant(s: String)) =>
        val paramDefs = paramsOf(s)
          .map(TermName(_ ))
          .map(name => q"""val $name: String""")

        c.Expr(q"""
        class Foo(val x: String)
        """)
      case _ => c.abort(c.enclosingPosition, s"\n    Found: $path\n    Required: String literal")
    }
  }

  private val rx = """:([^:\s/]*)\s*""".r

  private def paramsOf(path: String) = rx.findAllMatchIn(path)
    .toList
    .map(_.group(1))  
}
