import sfml.window.*
import sfml.graphics.*
import sfml.system.*

trait GraphicObj {
  def updateObj() : Unit
  def handleInputs(event:Event) : Unit
  def getSprites() : Array[Sprite]
  def getTexts() : Array[Text]
  def setVisible(visible: Boolean) : Unit
  def isVisible() : Boolean
}

enum ButtonTextures( val activeTexture:Texture,
                     val hoveredTexture:Texture, 
                     val clickedTexture:Texture, 
                     val inactiveTexture:Texture){

  case MainMenu extends ButtonTextures(Texture("src/main/resources/button_sprites/button_sprite.png"),
                                      Texture("src/main/resources/button_sprites/button_sprite.png"),
                                      Texture("src/main/resources/player_icon.jpeg"),
                                      Texture("src/main/resources/player_icon.jpeg"))
  case GenericMenu extends ButtonTextures(Texture("src/main/resources/button_sprites/button_sprite_2.png"),
                                      Texture("src/main/resources/button_sprites/button_sprite_2.png"),
                                      Texture("src/main/resources/button_sprites/button_sprite_2.png"),
                                      Texture("src/main/resources/player_icon.jpeg"))


}

type Position = (Int, Int)
type Size = (Int, Int)

class Button (buttonTextures:ButtonTextures, buttonPos:Position, buttonSize:Size) extends GraphicObj {
  
  // useful values
  val rect = IntRect(buttonPos._1, buttonPos._2, buttonSize._1, buttonSize._2)
  val position = buttonPos
  val text = Text()
  text.position = (buttonPos._1 + text.position._1, buttonPos._2 + text.position._2)

  // internal state of the button
  var is_being_pressed = false
  var current_texture = buttonTextures.activeTexture
  var isActive = false
  var sprite = Sprite(current_texture)
  var buttonVisible = false
  var buttonReleased = false

  // the variables for the mouse
  var mousePos = (0, 0)
  var mouseLeftButtonPressed = false
  
  // other internal values
  def defaultOnClick : () => Unit = () => {}
  var onClick = defaultOnClick
  
  def setOnClick(func: () => Unit) = {
    onClick = func
  }

  def setVisible(visible: Boolean) = {
      buttonVisible = visible
  }

  def isVisible(): Boolean = {
    return buttonVisible
  }

  def setText(string: String, charSize: Int, font: Font) = {
    text.string = string
    text.characterSize = charSize
    text.font = font
    while ((text.globalBounds._3*1.05 > buttonSize._1 || text.globalBounds._4*1.05 > buttonSize._2) && text.characterSize > 0) {
      text.characterSize -= 1
    }
    text.position = (buttonPos._1 + (buttonSize._1/2 - text.globalBounds._3/2), buttonPos._2 + (buttonSize._2/2 - text.globalBounds._4))
  }

  // is called once a frame, updates the image, checks for a click ...
  def updateObj() = {
    // set the approriate sprite if the button is :
    //  - inactive
    //  - being clicked
    //  - being hovered
    //  - in its default state
    if (buttonVisible) {  
      sprite.texture = current_texture
      sprite.scale = new Vector2f (rect._3.toFloat/sprite.textureRect._3.toFloat, rect._4.toFloat/sprite.textureRect._4.toFloat)
      sprite.position = position
    }

  }

  def setActive(active: Boolean) = {
    if (active){
      current_texture = buttonTextures.activeTexture
    }
    else {
      current_texture = buttonTextures.inactiveTexture
    }
    isActive = active
  }

  def currentTexture() : Texture = {
    return current_texture
  }

  def handleInputs(event:Event) : Unit = {
    if (buttonVisible) {
      buttonReleased = false
      event match
        case Event.MouseMoved(x, y) =>
          mousePos = (x, y)
        case Event.MouseButtonPressed(Mouse.Button.Left, x, y) =>
          mouseLeftButtonPressed = true
          mousePos = (x, y)
          if (rect.contains(mousePos._1, mousePos._2)){
            is_being_pressed = true
          }
        case Event.MouseButtonReleased(Mouse.Button.Left, x, y) =>
          mouseLeftButtonPressed = false
          if (is_being_pressed) {
            buttonReleased = true
          }
          is_being_pressed = false
          mousePos = (x, y)
        case _ => {}
      if (isActive) {
        if (rect.contains(mousePos._1, mousePos._2)) {
          current_texture = buttonTextures.hoveredTexture
          sprite.color = new Color(200.toByte, 200.toByte, 200.toByte, 255.toByte)
        }
        else {
          current_texture = buttonTextures.activeTexture
          sprite.color = new Color(255.toByte, 255.toByte, 255.toByte, 255.toByte)
        }
        if (rect.contains(mousePos._1, mousePos._2) && is_being_pressed) {
          current_texture = buttonTextures.clickedTexture
        }
        if (rect.contains(mousePos._1, mousePos._2) && buttonReleased) {
          is_being_pressed = false
          onClick()
          current_texture = buttonTextures.activeTexture
        }
      }
    }
  }

  def getSprites() : Array[Sprite] = {
    if (buttonVisible) {
      return Array(sprite)
    }
    else {
      return Array()
    }
  }

  def getTexts() : Array[Text] = {
    if (buttonVisible) {
      return Array(text)
    }
    else {
      return Array()
    }
  }


}

trait Displayable () {
  def getGraphicObjects () : Array[GraphicObj]
  def setActive(active: Boolean) : Unit
}
