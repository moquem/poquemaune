import sfml.window.*
import sfml.graphics.*
import sfml.system.*

class TextBox(background: Texture, pos: (Int, Int), size:(Int, Int), textString: String, textSize: Int) extends GraphicObj {
  val font = Font("src/main/resources/fonts/Castforce.ttf") 
  var overlayVisible = false
  
  val bg = new Image(background, pos, size)
  bg.setVisible(true)
  val text = Text()
  text.string = textString
  text.font = font
  text.characterSize = textSize
  text.position = ((pos._1 + size._1 * 0.17).toFloat, (pos._2 + size._2 * 0.1).toFloat)

  def updateObj() = {bg.updateObj()}
  def handleInputs(event: Event) = {}
  def getSprites(): Array[Sprite] = {return bg.getSprites()}
  def getTexts(): Array[Text] = {return Array(text)}
  def setVisible(visible: Boolean) = {overlayVisible = visible; bg.setVisible(visible)}
  def isVisible(): Boolean = {return overlayVisible}
}

