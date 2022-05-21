import sfml.graphics.*
import sfml.system.*

trait Menu extends Displayable{
  val buttons: Array[GraphicObj]
  def setActive(active: Boolean) = {
    this.getDisplayedObjects().foreach(_.setVisible(active))
  }
}

//class ButtonText(string: String, buttonSize: (Int, Int), characterSize: Int, font: Font) extends Text() {
//class ButtonText() extends Text() {
//  this.string = string
//  this.font = font
//  this.characterSize = characterSize
//  this.position = ((buttonSize._1 - this.globalBounds._1)/2, (buttonSize._2/2 - this.globalBounds._2/2)/2)
//}


object MainMenu extends Menu {
  val font = Font("src/main/resources/fonts/Castforce.ttf")

  // start game button
  def startButtonOnClick() : Unit = {
    this.setActive(false)
    TestCombat.setActive(false)
    MapMenu.setActive(true)
  }
  val startButton = new Button(ButtonTextures.MainMenu, ((1280-640)/2, 100), (640, 180))
  startButton.setOnClick(startButtonOnClick)
  startButton.setText("Start Game", 95, font)
  startButton.setActive(true)
  startButton.setVisible(true)
 
  def settingsOnClick(): Unit = {
    println("no settings yet")
  }
  val settingsButton = new Button(ButtonTextures.MainMenu, ((1280-450)/2, 100+180), (450, 150))
  settingsButton.setOnClick(settingsOnClick)
  settingsButton.setText("Settings", 80, font)
  settingsButton.setActive(true)
  settingsButton.setVisible(true)
  
  def quitOnClick() : Unit = {
    println("quit")
  }
  val quitButton = new Button(ButtonTextures.MainMenu, ((1280-450)/2, 100+180+150), (450 ,150))
  quitButton.setOnClick(quitOnClick)
  quitButton.setText("Quit", 80, font)
  quitButton.setActive(true)
  quitButton.setVisible(true)
 
  //val textBox = new TextBox(Texture("src/main/resources/ui_sprites/pok_sheet.png"), (0, 0), (500, 600), "this is a test\nFor now ...", 40)
  //textBox.setVisible(true)

  val buttons = Array[GraphicObj](startButton, settingsButton, quitButton)

  def getDisplayedObjects(): Array[GraphicObj] = {
    return buttons
  }

  def getGraphicObjects(): Array[GraphicObj] = {
    return buttons //++ Array(textBox)
  }
 
}

