package nozzle.modules

import scala.reflect.macros._
import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros
import scala.annotation.StaticAnnotation
import scala.annotation.compileTimeOnly

@compileTimeOnly("Enable macro paradise to expand macro annotations.")
class module extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro ModuleMacro.impl
}
 
object ModuleMacro {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
 
    def modifiedClass(classDecl: ClassDef, compDeclOpt: Option[ModuleDef]) = {
      val (className, fields) = try {
        val q"class $className(..$fields) extends ..$bases { ..$body }" = classDecl
        (className, fields)
      } catch {
        case _: MatchError => c.abort(c.enclosingPosition, "Annotation is only supported on classes")
      }
      val args = fields.map(_.name)
      val compDecl = compDeclOpt.getOrElse(q"""
        object ${className.toTermName} {
          def apply(implicit ..$fields): $className = new $className(..$args)
        }
      """)
      c.Expr(q"""
        $classDecl
        $compDecl
      """)
    }

    annottees.map(_.tree) match {
      case (classDecl: ClassDef) :: Nil => modifiedClass(classDecl, None)
      case (classDecl: ClassDef) :: (compDecl: ModuleDef) :: Nil => modifiedClass(classDecl, Some(compDecl))
      case _ => c.abort(c.enclosingPosition, "Invalid annottee")
    }
  }
}

