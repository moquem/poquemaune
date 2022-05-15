import scala.io.Source
import sfml.graphics.*
import sfml.window.*
import sfml.system.*


class Tile(texture: Texture, pos: (Int, Int), size: (Int, Int)) extends GraphicObj {
  var tileVisible = false

  val tileSprite = Sprite(texture)
  tileSprite.position = pos

  def updateObj() = {
    tileSprite.scale = new Vector2f (size._1.toFloat / tileSprite.textureRect._3.toFloat, size._2.toFloat / tileSprite.textureRect._4.toFloat)
  }
  
  def setMapCoord(x: Int, y: Int) = {
    tileSprite.position = (pos._1 + x * size._1, pos._2 + y * size._2)
  }

  def handleInputs(event: Event) = {
  }
  
  def getSprites(): Array[Sprite] = {
    return Array(tileSprite)
  }

  def getTexts() : Array[Text] = {
    return Array()
  }

  def setVisible(visible: Boolean) = {
    tileVisible = visible
  }

  def isVisible() : Boolean = {
    return tileVisible
  }

  def copy() : Tile = {
    val tileCopy = new Tile(texture, pos, size)
    return tileCopy
  }

}

object MapMenu extends Menu {
  
  val font = Font("src/main/resources/fonts/Castforce.ttf")


  def openInventory() = {println("no inventory yet!")}
  val inventoryButton = new Button(ButtonTextures.GenericMenu, (1280 - 250, 100), (200, 100))
  inventoryButton.setOnClick(openInventory)
  inventoryButton.setText("Inventory", 80, font)
  inventoryButton.setVisible(true)
  inventoryButton.setActive(true)

  def returnOnClick() = {println("return to main menu")}
  val returnButton = new Button(ButtonTextures.GenericMenu, (1280 - 250, 250), (200, 100))
  returnButton.setOnClick(returnOnClick)
  returnButton.setText("Return", 80, font)
  returnButton.setVisible(true)
  returnButton.setActive(true)
  
  val map = new Map()
  map.setActive(true)
  val seaTile = new Tile(Texture("src/main/resources/ui_sprites/sea_tile.png"), (50, 50), (50, 50))
  val grassTile = new Tile(Texture("src/main/resources/ui_sprites/grass_tile.png"), (50, 50), (50, 50))
  map.setTileTypes(Array(seaTile, grassTile))
  

  val rawMapArray = Source.fromFile("src/main/resources/levels/levelMap_1.txt").getLines.toArray
  val rawMapMatrix = rawMapArray.map(_.split(" "))
  val mapArr = rawMapMatrix.flatMap(_.toList).map(_.toInt)
  map.initMap((9, 7), mapArr)

  val buttons = Array[GraphicObj](inventoryButton, returnButton)

  def getGraphicObjects() : Array[GraphicObj] = {
    return buttons ++ map.getGraphicObjects()
  }

}

class Map extends Displayable {
  
  var mapActive = false
  var tileTypes = Array[Tile]()
  
  var mapTiles = Array[Tile]()
  var mapSize = (0, 0)

  def setTileTypes(newTileTypes: Array[Tile]) = {
    tileTypes = newTileTypes
  }

  def initMap(sizeOfMap: (Int, Int), mapArr: Array[Int]) = {
    mapTiles = mapArr.map(tileTypes(_).copy())
    mapSize = sizeOfMap
    mapTiles.foreach(_.setVisible(true))
    for (i <- 0 to mapSize._2 - 1) {
      for (j<- 0 to mapSize._1 - 1) {
        mapTiles(i*mapSize._1 + j).setMapCoord(i, j)
      }
    }
  }

  var graphicObjects = Array[GraphicObj]()
  def convertTile(tile: Tile): GraphicObj = {
    return tile
  }
  
  
  def getGraphicObjects() : Array[GraphicObj] = {
    graphicObjects = mapTiles.map(convertTile(_))
    return graphicObjects
  }

  def setActive(active: Boolean) = {
    mapActive = active
  }
  
}

