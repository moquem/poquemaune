import sfml.window.*
import sfml.graphics.*
import sfml.system.*

class Character (texture: Texture, pos: (Int, Int), size: (Int, Int)) extends GraphicObj {
  
  var charVisible = false;
  val charSprite = Sprite(texture)
  charSprite.position = pos

  def updateObj() = {
    charSprite.scale = new Vector2f(size._1.toFloat / charSprite.textureRect._3.toFloat, size._2.toFloat / charSprite.textureRect._4.toFloat)
  }

  def handleInputs(event: Event) = {}
  def getSprites(): Array[Sprite] = {
    return Array(charSprite)
  }
  def getTexts (): Array[Text] = {
    return Array()
  }
  def setVisible(visible: Boolean) = {
    charVisible = visible
  }
  def isVisible(): Boolean = {
    return charVisible
  }
}

class Player (texture: Texture, pos: (Int, Int), size: (Int, Int)) extends Character(texture, pos, size) {
  def movePlayer(posDelta: (Int, Int)) = {
    this.charSprite.position = (charSprite.position._1 + posDelta._1, charSprite.position._2 + posDelta._2)
  }
}
