def concatenator(w1: String) : String => String = w2 => w1 + " " + w2


val heyWorld = concatenator("Hey")

heyWorld("currying")
