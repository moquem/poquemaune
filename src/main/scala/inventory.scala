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


class InventoryMenu(inventory: Inventory) extends Menu {
  
  val font = Font("src/main/resources/fonts/Castforce.ttf")
 
  val pokemonInventoryMenu = new PokemonInventoryMenu(inventory)
  pokemonInventoryMenu.setActive(false)

  val consumableButton = new Button(ButtonTextures.GenericMenu, (200, 300), (200, 100))
  consumableButton.setText("Consumables", 70, font)
  def consumableOnCLick() = {println("consumable")}
  consumableButton.setVisible(true)
  consumableButton.setActive(true)
  consumableButton.setOnClick(consumableOnCLick)

  val equipmentButton = new Button(ButtonTextures.GenericMenu, (1280/2 - 100, 300), (200, 100))
  equipmentButton.setText("Equipment", 70, font)
  def equipmentOnCLick() = {println("equipment")}
  equipmentButton.setVisible(true)
  equipmentButton.setActive(true)
  equipmentButton.setOnClick(equipmentOnCLick)

  val pokemonButton = new Button(ButtonTextures.GenericMenu, (1280 - 400 , 300), (200, 100))
  pokemonButton.setText("Pokemon", 70, font)
  def pokemonOnCLick() = {
    pokemonInventoryMenu.setActive(true)
    consumableButton.setVisible(false)
    equipmentButton.setVisible(false)
    pokemonButton.setVisible(false)
    returnButton.setVisible(true)
  }
  pokemonButton.setVisible(true)
  pokemonButton.setActive(true)
  pokemonButton.setOnClick(pokemonOnCLick)

  val returnButton = new Button(ButtonTextures.GenericMenu, (300 + 200, 300 + 50), (150, 80))
  returnButton.setText("Return", 70, font)
  def returnOnCLick() = {println("return")}
  returnButton.setVisible(true)
  returnButton.setActive(true)
  returnButton.setOnClick(returnOnCLick)


  val buttons = Array[GraphicObj](consumableButton, equipmentButton, pokemonButton)
  
  def getGraphicObjects(): Array[GraphicObj] = {
    return buttons ++ Array[GraphicObj]() ++ pokemonInventoryMenu.getGraphicObjects().filter(_.isVisible())
  }
}


class PokemonInfoSheet(pokemon: Pokemon) extends Displayable {
 
  def getPokInfoText(pokemon: Pokemon): String = {
    var pokInfoText = "Name : " + pokemon.pokemonName + "\n"
    pokInfoText += "HP : " + pokemon.currHP.toString + "/" + pokemon.maxHP.toString + "\n"
    pokInfoText += "PP : " + pokemon.currPP.toString + "/" + pokemon.maxPP.toString + "\n"
    def addAtk(prevText: String, atk: Attack): String = {
      return prevText + atk.attackName + " : " + atk.atkDescription + "\n"
    }
    for (i <- 0 to pokemon.atk_set.size-1) {
      pokInfoText = addAtk(pokInfoText, pokemon.atk_set(i))
    }
    pokInfoText += "Type : " + pokemon.pokTyp.typString()
    return pokInfoText
  }
  
  val textBox = new TextBox(Texture("src/main/resources/ui_sprites/pok_sheet.png"), (0, 0), (1280, 720), getPokInfoText(pokemon), 25)

  def setActive(active: Boolean) = {
    textBox.setVisible(active)
  }
  
  def getGraphicObjects(): Array[GraphicObj] = {
    //println(textBox.isVisible())
    return Array(textBox)
  }
}

class PokemonInventoryMenu(inventory: Inventory) extends Menu {
  
  val font = Font("src/main/resources/fonts/Castforce.ttf")
  
  
  var pokemonButtons = Array[Button]()
  var pokemonInfoSheets = Array[PokemonInfoSheet]()
  for (i <- 0 to inventory.ownedPokList.size-1) {
    var newPokButton = new Button(ButtonTextures.GenericMenu, (0, 0), (50, 50))
    newPokButton.setText(inventory.ownedPokList(i).pokemonName, 60, font)
    var pokInfoSheet = new PokemonInfoSheet(inventory.ownedPokList(i))
    pokInfoSheet.setActive(false)
    def pokButtonOnClick() = {
      pokInfoSheet.setActive(true)
      pokListMenu.setActive(false)
      deactivatePokButtons()
    }
    newPokButton.setOnClick(pokButtonOnClick)
    pokemonButtons = pokemonButtons ++ Array(newPokButton)
    pokemonInfoSheets = pokemonInfoSheets ++ Array(pokInfoSheet)
  }
  
  def deactivatePokButtons() = {
    pokemonButtons.foreach(_.setVisible(false))
  }

  val pokListMenu = new ListMenu(pokemonButtons)
  pokListMenu.setActive(false)

  def toGraphicObj(button: Button): GraphicObj = {return button}

  val buttons = Array[GraphicObj]() ++ pokemonButtons.map(toGraphicObj)
  val pokInfoSheetsGraphicObjects = pokemonInfoSheets.map(_.getGraphicObjects()).flatMap(_.toList)
  def getGraphicObjects(): Array[GraphicObj] = {
    //println(pokInfoSheetsGraphicObjects(0).isVisible())
    return buttons.filter(_.isVisible()) ++ Array[GraphicObj]() ++ pokListMenu.getGraphicObjects() ++ pokInfoSheetsGraphicObjects.filter(_.isVisible())
  }


}

object playerInventory extends Inventory {}
