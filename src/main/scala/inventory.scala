import sfml.window.*
import sfml.graphics.*

class Item {}

class Consumable() extends Item {}

class Equipment() extends Item {}

class Inventory {
  // needs to have a list of items
  // a list of :
  //  - owned pokemons
  //  - active pokemons
  // a list of consumable items

  var ownedPokList = Array[Pokemon]()
  var activePok = Array[Pokemon]()

  var consumables = Array[Consumable]()

  var equipment = Array[Equipment]()

}


class InventoryMenu() extends Menu {
  
  val font = Font("src/main/resources/fonts/Castforce.ttf")

  val consumableButton = new Button(ButtonTextures.GenericMenu, (300, 300), (150, 80))
  consumableButton.setText("Consumables", 70, font)
  def consumableOnCLick() = {println("consumable")}
  consumableButton.setVisible(true)
  consumableButton.setActive(true)
  consumableButton.setOnClick(consumableOnCLick)

  val equipmentButton = new Button(ButtonTextures.GenericMenu, (300 + 200, 300), (150, 80))
  equipmentButton.setText("Equipment", 70, font)
  def equipmentOnCLick() = {println("equipment")}
  equipmentButton.setVisible(true)
  equipmentButton.setActive(true)
  equipmentButton.setOnClick(equipmentOnCLick)

  val pokemonButton = new Button(ButtonTextures.GenericMenu, (300, 300 + 50), (150, 80))
  pokemonButton.setText("Pokemon", 70, font)
  def pokemonOnCLick() = {println("pokemon")}
  pokemonButton.setVisible(true)
  pokemonButton.setActive(true)
  pokemonButton.setOnClick(pokemonOnCLick)


  val buttons = Array[GraphicObj](consumableButton, equipmentButton, pokemonButton)
  
  def getGraphicObjects(): Array[GraphicObj] = {
    return buttons ++ Array[GraphicObj]()
  }
}


class PokemonInfoSheet(pokemon: Pokemon) extends Displayable {

  def setActive(active: Boolean) = {}
  def getPokInfoText(pokemon: Pokemon): String = {
    var pokInfoText = "Name : " + pokemon.pokemonName + "\n"
    pokInfoText += "HP : " + pokemon.currHP.toString + "/" + pokemon.maxHP.toString + "\n"
    pokInfoText += "PP : " + pokemon.currPP.toString + "/" + pokemon.maxPP.toString + "\n"
    def addAtk(prevText: String, atk: Attack): String = {
      return prevText + atk.attackName + " : " + atk.atkDescription + "\n"
    }
    for (i <- 0 to pokemon.atk_set.size) {
      pokInfoText = addAtk(pokInfoText, pokemon.atk_set(i))
    }
    pokInfoText += "Type : " + pokemon.pokTyp.typString()
    return pokInfoText
  }
  
  val textBox = new TextBox(Texture("src/main/resources/ui_sprites/pok_sheet.png"), (600, 50), (350, 700), getPokInfoText(pokemon), 14)

  def getGraphicObjects(): Array[GraphicObj] = {
    return Array(textBox)
  }
}

class PokemonInventoryMenu(inventory: Inventory) extends Menu {
  
  val font = Font("src/main/resources/fonts/Castforce.ttf")


  val nextButton = new Button(ButtonTextures.GenericMenu, (1000, 700), (150, 50))
  nextButton.setActive(true)
  nextButton.setVisible(true)
  nextButton.setText("Next Page", 50, font)
  def nextOnClick() = {
    println("next page")
  }
  nextButton.setOnClick(nextOnClick)

  val buttons = Array[GraphicObj](nextButton)
  
  def getGraphicObjects(): Array[GraphicObj] = {
    return buttons ++ Array[GraphicObj]()
  }


}

object playerInventory extends Inventory {}
