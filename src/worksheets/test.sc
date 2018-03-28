import scala.util.Random

var a  = List("now", "it", "istime");
a.flatMap(_.toUpperCase)
a.map(_.toUpperCase)


var optionList = List[Option[String]](Some("xyz"), Some("abc"), None);
optionList.flatten

trait Friend {
  val name : String
  def listen() = println("I'm " + name + " listening")
}

class Human(val name : String) extends Friend {
  //def listen() = println("I'm " + name + " listening")
}

class Animal(val name : String)

class Dog(override val name : String) extends Animal(name)  with Friend


val same = new Human("Sam")
same.listen()

val dog = new Dog("Lilly")
dog.listen()

def f = (x: Int) => x*2
f(10)


val somenumbers = List(10, 4, 3, 20, 55)
somenumbers.filter(_ > 5)
def specialFilter(list : List[Int], v : Int ) : List[Int] = list.filter(_ >  v)


specialFilter(somenumbers, 10)

val fnX = { Random.nextInt}
fnX

val even: (Int => Boolean) = {
  println("even val version")
  (x => x % 2 == 0)
}

even(2)
even(1)
even(9000)

def evenDef: (Int => Boolean) = {
  println("even def version")
  (x => x % 2 == 0)
}

evenDef(4)

def f1(x: Int) : (Int) => (Int) = {
  def g1(y: Int) : Int= {
    return x + y
  }
  return g1
}

def f2(x: Int) : (Int) => (Int) = {
  return x + _
}

f1(1)(2)

f1(3)(4)

f1(5)(30)


f2(4)(30)

val xfff = f2(40)

xfff(444)
xfff(330)



var factor = 2
val multiplier = (i: Int) => i * factor

(1 to 10) filter (_ % 2 == 0) map multiplier reduce (_ * _)

factor = 3

(1 to 10) filter (_ % 2 == 0) map multiplier product

var capital = Map("US" -> "Washington", "France" -> "Paris")
capital.toList
capital("US")





5 match {
  case 5 =>
    println("5")
  case 6 =>
    println("6")
}


def fnTestReturn(v: Int) : Int = {
  if (v == 10)
    50
  else if (v == 20)
    60
  else
    70
}

fnTestReturn(10)
fnTestReturn(20)
fnTestReturn(30)

