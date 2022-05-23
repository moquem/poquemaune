import sfml.graphics.*
import sfml.system.*
import sfml.window.*

object Player {
  
  val inventory = new Inventory()
  var ownedPokList = Array[Pokemon](Pokemon("src/main/resources/pokemons/Jean.txt"))
  ownedPokList = ownedPokList ++ Array(Pokemon("src/main/resources/pokemons/Arthur.txt"))
  ownedPokList = ownedPokList ++ Array(Pokemon("src/main/resources/pokemons/Antonin.txt"))
  ownedPokList = ownedPokList ++ Array(Pokemon("src/main/resources/pokemons/Augustin.txt"))
  ownedPokList = ownedPokList ++ Array(Pokemon("src/main/resources/pokemons/Pauline.txt"))
  ownedPokList = ownedPokList ++ Array(Pokemon("src/main/resources/pokemons/Leopold.txt"))
  ownedPokList = ownedPokList ++ Array(Pokemon("src/main/resources/pokemons/Tanguy.txt"))
  ownedPokList = ownedPokList ++ Array(Pokemon("src/main/resources/pokemons/Erwann.txt"))
  ownedPokList = ownedPokList ++ Array(Pokemon("src/main/resources/pokemons/Esteban.txt"))
  ownedPokList = ownedPokList ++ Array(Pokemon("src/main/resources/pokemons/Hubert.txt"))
  ownedPokList = ownedPokList ++ Array(Pokemon("src/main/resources/pokemons/Ewen.txt"))
  ownedPokList = ownedPokList ++ Array(Pokemon("src/main/resources/pokemons/Mina.txt"))
  ownedPokList = ownedPokList ++ Array(Pokemon("src/main/resources/pokemons/Maena.txt"))
  ownedPokList = ownedPokList ++ Array(Pokemon("src/main/resources/pokemons/Vincent.txt"))
  ownedPokList = ownedPokList ++ Array(Pokemon("src/main/resources/pokemons/Valentin.txt"))
  for (i <- 0 to 22) {
    ownedPokList = ownedPokList ++ Array(Pokemon("src/main/resources/pokemons/uselessPok.txt"))
  }

  inventory.ownedPokList = ownedPokList

}
