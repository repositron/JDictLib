def isEven(n: Int) : Boolean = n%2 == 0

def fn(n: Int) = {
  import List.range
  range(1, n)
    .flatMap(i =>
      range(1, i)
        .withFilter(j => isEven(i+j))
        .map(j => (i, j)))

}


def fn2(n: Int) = {
  import List.range
  for { i <- range(1, n)
        j <- range(1, i)
        if isEven(i+j)
  } yield (i, j)
}

fn(20)

fn(55)