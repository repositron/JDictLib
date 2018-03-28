import scala.collection.mutable
import scala.collection.mutable.Map
import scala.xml.XML

val l1 = List(1, 2, 3, 4, 5)

def fn1(v: Int, l : List[Int]) : Int = {
  l.foreach(x => if (x == v) return x else println(x))
  99
}

fn1(7, l1)
fn1(1, l1)

(1 to 1000).reduceLeft( _ + _ )
(1 to 1000).sum


val wordList = List("scala", "akka", "play framework", "sbt", "typesafe")
val tweet = "This is an example tweet talking about scala and sbt."

(wordList.foldLeft(false)( _ || tweet.contains(_) ))
wordList.exists(tweet.contains)

//val results = XML.load("http://search.twitter.com/search.atom?&q=scala")

List(14, 35, -7, 46, 98).reduceLeft ( _ min _ )
List(14, 35, -7, 46, 98).min

List(14, 35, -7, 46, 98).reduceLeft ( _ max _ )
List(14, 35, -7, 46, 98).max

object X1 {


  var myMap = Map("a" -> "b", "c" -> "charlie")
  myMap.foreach(x => println(x._1 + "," + x._2))

  override def toString: String = myMap.foldLeft("")((x,y) => x + " " + y)
  def f111(m: mutable.Map[String, String]) = {
    m += ("d" -> "jjjj")
    m.foreach(x => println(x._1 + "," + x._2))
  }
}

X1.f111(X1.myMap)

println (X1.myMap.toString())








var l2 : List[String] = (1 to 100000).toList.map(x => x.toString)

object abc {
  def f1(l1: List[String]) : List[String] = {
    l1 match {
      case x :: xs => x :: f1(xs)
      case Nil => Nil
    }
  }

  def f2(l1: List[String]) : List[String] = {
    def loop(l1: List[String], acc: List[String]) : List[String] = {
      l1 match {
        case x :: xs => loop(xs, x :: acc)
        case Nil => acc
      }
    }
   loop(l1, List.empty).reverse
  }
}

abc.f2(l2)

val ll1  = 1 to 20
ll1.iterator
  .sliding(2)
  .forall(x => {
    x(0) < x(1)
  })



