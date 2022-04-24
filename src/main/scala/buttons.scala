//import sfml.window.*
import sfml.graphics.*

trait GraphicObj {
  def updateObj() : Unit
}

enum ButtonTextures(activeTexture:Texture,
                     hoveredTexture:Texture, 
                     clickedTexture:Texture, 
                     inactiveTexture:Texture){

  case Example extends ButtonTextures(Texture("src/main/resources/player_icon.jpeg"),
                                      Texture("src/main/resources/player_icon.jpeg"),
                                      Texture("src/main/resources/player_icon.jpeg"),
                                      Texture("src/main/resources/player_icon.jpeg"))

}

type Position = (Int, Int)
type Size = (Int, Int)

class Button (buttonTextures:ButtonTextures, buttonPos:Position, buttonSize:Size) extends GraphicObj {
 
  // what the button will do when pressed
  def onClick() = {
    // what the button will do when it is pressed
  }
  
  // is called once a frame, updates the image, checks for a click ...
  def updateObj() = {
    // set the approriate sprite if the button is :
    //  - inactive
    //  - being clicked
    //  - being hovered
    //  - in its default state
  }
  
}
