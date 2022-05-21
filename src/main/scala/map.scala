import scala.io.Source
import sfml.graphics.*
import sfml.window.*
import sfml.system.*

trait Updateable () {
  def handleEvent(event: Event) : Unit
}

enum TileTypes (val texture: Texture,
               val walkable: Boolean) {
  
  case GrassTile extends TileTypes(Texture("src/main/resources/ui_sprites/grass_tile.png"), true)
  case SeaTile extends TileTypes(Texture("src/main/resources/ui_sprites/sea_tile.png"), false)
}


class Tile(tileList: Array[TileTypes], pos: (Int, Int), size: (Int, Int)) extends GraphicObj {
  var tileVisible = false
  var currIndex = 0
  var tileSize = size
  var tileInfo = tileList(0)

  val tileSprite = Sprite(tileList(0).texture)
  tileSprite.position = (pos._2, pos._1)
  
  def setIndex(index: Int) = {
    currIndex = index
    tileSprite.texture = tileList(index).texture
    tileInfo = tileList(index)
  }

  def updateObj() = {
    tileSprite.scale = new Vector2f (size._1.toFloat / tileSprite.textureRect._3.toFloat, size._2.toFloat / tileSprite.textureRect._4.toFloat)
  }
  
  def setMapCoord(x: Int, y: Int) = {
    tileSprite.position = (pos._2 + y * size._2, pos._1 + x * size._1)
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
    val tileCopy = new Tile(tileList, pos, size)
    return tileCopy
  }

}

object MapMenu extends Menu {
  
  val font = Font("src/main/resources/fonts/Castforce.ttf")

  def openInventory() = {
    this.setActive(false)
    PlayerInventoryMenu.setActive(true)
  }
  val inventoryButton = new Button(ButtonTextures.GenericMenu, (1280 - 200, 100), (170, 80))
  inventoryButton.setOnClick(openInventory)
  inventoryButton.setText("Inventory", 80, font)
  inventoryButton.setVisible(true)
  inventoryButton.setActive(true)

  def returnOnClick() = {
    this.setActive(false)
    TestCombat.setActive(true)
  }

  val returnButton = new Button(ButtonTextures.GenericMenu, (1280 - 200, 250), (170, 80))
  returnButton.setOnClick(returnOnClick)
  returnButton.setText("Fight", 63, font)
  returnButton.setVisible(true)
  returnButton.setActive(true)
  
  val map = new Map()
  map.setActive(true)
  val tileArr = Array(TileTypes.SeaTile, TileTypes.GrassTile)
  val mapTile = new Tile(tileArr, (50, 50), (50, 50))
  

  val rawMapArray = Source.fromFile("src/main/resources/levels/levelMap_1.txt").getLines.toArray
  val rawMapMatrix = rawMapArray.map(_.split(" "))
  val mapArr = rawMapMatrix.flatMap(_.toList).map(_.toInt)
  map.initMap(mapTile, (rawMapMatrix(0).size, rawMapArray.size), mapArr)

  val buttons = Array[GraphicObj](inventoryButton, returnButton)


  def getGraphicObjects() : Array[GraphicObj] = {
    return buttons ++ map.getGraphicObjects()
  }

  def getDisplayedObjects() : Array[GraphicObj] = {
    return getGraphicObjects()
  }

  def getUpdateable() : Array[Updateable] = {
    return Array(map)
  }

}

class Map extends Displayable, Updateable {
  
  var mapActive = false
  var tileTypes = Array[Tile]()
  
  var mapTiles = Array[Tile]()
  var mapSize = (0, 0)

  val player = new PlayerCharacter(Texture("src/main/resources/char_sprites/player_icon.jpeg"), (50 + 50/2 - 36/2 + 50*2, 50 + 50/2 - 36/2 + 50*2), (36, 36))
  var playerMapCoords = (2, 2)

  var tileSize = (0, 0)

  def initMap(tile: Tile, sizeOfMap: (Int, Int), mapArr: Array[Int]) = {
    tileSize = tile.tileSize
    mapTiles = mapArr.map(_ => tile.copy())
    mapSize = sizeOfMap
    //mapTiles.foreach(_.setVisible(true))
    for (i <- 0 to mapSize._2 - 1) {
      for (j<- 0 to mapSize._1 - 1) {
        mapTiles(i*mapSize._1 + j).setIndex(mapArr(i*mapSize._1 + j))
        mapTiles(i*mapSize._1 + j).setMapCoord(i, j)
      }
    }
  }
  
  def mapIndex(i: Int, j: Int, mapSize: (Int, Int)): Int = {
    if (0 <= i && i < mapSize._2 && 0 <= j && j < mapSize._1) {
      return i*mapSize._1 + j
    }
    else {
      return -1
    }
  }

  var graphicObjects = Array[GraphicObj]()
  def convertTile(tile: Tile): GraphicObj = {
    return tile
  }
  
  
  def getGraphicObjects() : Array[GraphicObj] = {
    graphicObjects = mapTiles.map(convertTile(_))
    return graphicObjects ++ Array(player)
  }

  def getDisplayedObjects() : Array[GraphicObj] = {
    return getGraphicObjects()
  }

  def setActive(active: Boolean) = {
    mapActive = active
    getGraphicObjects().foreach(_.setVisible(active))
  }

  
  def handleEvent(event: Event) = {
    var j = playerMapCoords._1
    var i = playerMapCoords._2
    event match
      case Event.KeyPressed(Keyboard.Key.KeyRight, _, _, _, _) =>
        var playerMapIndex = mapIndex(i, j+1, mapSize)
        if (playerMapIndex != -1 && mapTiles(playerMapIndex).tileInfo.walkable) {
          player.movePlayer(tileSize._1, 0)
          playerMapCoords = (playerMapCoords._1 + 1, playerMapCoords._2)
        }
      case Event.KeyPressed(Keyboard.Key.KeyLeft, _, _, _, _) =>
        var playerMapIndex = mapIndex(i, j-1, mapSize)
        if (playerMapIndex != -1 && mapTiles(playerMapIndex).tileInfo.walkable) {
          player.movePlayer(-tileSize._1, 0)
          playerMapCoords = (playerMapCoords._1 - 1, playerMapCoords._2)
        }
      case Event.KeyPressed(Keyboard.Key.KeyUp, _, _, _, _) =>
        var playerMapIndex = mapIndex(i-1, j, mapSize)
        if (playerMapIndex != -1 && mapTiles(playerMapIndex).tileInfo.walkable) {
          player.movePlayer(0, -tileSize._2)
          playerMapCoords = (playerMapCoords._1, playerMapCoords._2 - 1)
        }
      case Event.KeyPressed(Keyboard.Key.KeyDown, _, _, _, _) =>
        var playerMapIndex = mapIndex(i+1, j, mapSize)
        if (playerMapIndex != -1 && mapTiles(playerMapIndex).tileInfo.walkable) {
          player.movePlayer(0, tileSize._2)
          playerMapCoords = (playerMapCoords._1, playerMapCoords._2 + 1)
        }
      case _ => {}
  }

}

