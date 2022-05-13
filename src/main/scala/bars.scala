import sfml.graphics.*
import sfml.system.*
import sfml.window.*

class DisplayBar(maxVal: Int, border: Texture, borderOffset: (Double, Double), inside: Texture, pos: (Int, Int), size: (Int, Int)) extends GraphicObj {
  
  var barVisible = false

  var currVal = maxVal
  
  val insideSprite = Sprite(inside)
  val borderSprite = Sprite(border)

  borderSprite.position = pos

  borderSprite.scale = new Vector2f (size._1 / borderSprite.textureRect._3, size._2 / borderSprite.textureRect._4)
 
  val barStartPixelOffset = borderOffset._1 * borderSprite.globalBounds._3
  val barEndPixelOffset = borderOffset._2 * borderSprite.globalBounds._3
  insideSprite.position = ((pos._1 + barStartPixelOffset).toInt, pos._2)
  

  def updateObj() = {
    var insideLengthScale = (currVal.toFloat / maxVal.toFloat) * (1 - borderOffset._1 - borderOffset._2) * size._1 / insideSprite.textureRect._3
    insideSprite.scale = new Vector2f (insideLengthScale.toFloat, borderSprite.globalBounds._4 / insideSprite.textureRect._4)
  }
  
  def setVal(value: Int) = {
    currVal = value
  }

  def handleInputs(event: Event) = {}

  def getSprites() : Array[Sprite] = {
    if (barVisible) {
      return Array(insideSprite, borderSprite)
    }
    else {
      return Array()
    }
  }

  def getTexts() : Array[Text] = {return Array()}

  def isVisible() : Boolean = {
    return barVisible
  }
  def setVisible(visible: Boolean) = {
    barVisible = visible
  }
}
