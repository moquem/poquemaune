import sfml.graphics.*
import sfml.system.*
import sfml.window.*

object Player {
  
  val inventory = new Inventory()
  var ownedPokList = Array[Pokemon](Pokemon("src/main/resources/pokemons/bestPokemon.txt"))
  for (i <- 0 to 35) {
    ownedPokList = ownedPokList ++ Array(Pokemon("src/main/resources/pokemons/uselessPok.txt"))
  }
  ownedPokList = ownedPokList ++ Array(Pokemon("src/main/resources/pokemons/testPokemon.txt"))

  inventory.ownedPokList = ownedPokList

}
