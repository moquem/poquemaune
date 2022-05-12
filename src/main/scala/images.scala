import sfml.window.*
import sfml.graphics.*
import sfml.system.*

class Image(texture: Texture, position: (Int, Int), size: (Int, Int)) extends GraphicObj {
  
  var pos = position
  var imageVisible = false
  val text = Text()
  var sprite = Sprite(texture)
  sprite.position = pos
 
  def handleInputs(event: Event) = {
  }

  def updateObj() = {
    sprite.scale = new Vector2f(size._1.toFloat/sprite.textureRect._3.toFloat, size._2.toFloat/sprite.textureRect._4.toFloat)
  }
  
  def currentTexture(): Texture = {
    return texture
  }
  
  def getSprite(): Sprite = {
    return sprite
  }

  def setVisible(visible: Boolean) = {
    imageVisible = visible
  }

  def isVisible(): Boolean = {
    return imageVisible
  }

}
