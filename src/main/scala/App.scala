import sfml.graphics.*
import sfml.window.*
import sfml.system.*

@main def main =
  // the main window
  val window = RenderWindow(VideoMode(1280, 720, 32), "Poquemaune", Window.WindowStyle.DefaultStyle)

  // example
  val texture = Texture("src/main/resources/player_icon.jpeg")
  val sprite = Sprite(texture)
  val font = Font("src/main/resources/fonts/test_font.ttf")
  val text = Text()
  text.string = "babebibobu"
  text.font = font
  
  // test for button
  /*val buttonTexture = ButtonTextures.MainMenu
  val testButton = Button(buttonTexture, (300, 300), (100, 100))
  val testButtonSprite = Sprite(testButton.currentTexture())
  testButton.setActive()
  testButtonSprite.scale = new Vector2f(testButton.rect._3.toFloat/testButtonSprite.textureRect._3.toFloat, testButton.rect._4.toFloat/testButtonSprite.textureRect._4.toFloat)*/
  

  var displayable = Array[Displayable](MainMenu, TestCombat, MapMenu, PlayerInventoryMenu)
  var updateable = MapMenu.getUpdateable()
  var frameUpdateable = TestCombat.getFrameUpdateable()
  MainMenu.setActive(true)
  PlayerInventoryMenu.setActive(false)
  TestCombat.setActive(false)
  MapMenu.setActive(false)

  var graphicObjects = Array[GraphicObj]()

  // handling the inputs
  var left_button_pressed = false
  var right_button_pressed = true
  var mousePos = (0, 0)

  // main loop
  while window.isOpen() do
    
    graphicObjects = displayable.map(_.getGraphicObjects()).flatMap(_.toList)
    
    for event <- window.pollEvent() do
      //println(event)
      
      updateable.foreach(_.handleEvent(event))
      graphicObjects.foreach(_.handleInputs(event))
      
      event match
        // inputs
        case Event.MouseButtonPressed(Mouse.Button.Left, x, y) => 
          left_button_pressed = true
          mousePos = (x, y)
        case Event.MouseButtonReleased(Mouse.Button.Left, x, y) =>
          left_button_pressed = false
          mousePos = (x, y)
        case Event.MouseMoved(x, y) =>
          mousePos = (x, y)
        
        case Event.Closed() => window.close()
        case _ => ()
    

    window.clear(new Color(150.toByte, 150.toByte, 150.toByte, 255.toByte))
    
    frameUpdateable.foreach(_.frameUpdate())
    graphicObjects.foreach(_.updateObj())
    
    var spriteArray = graphicObjects.filter(_.isVisible()).map(_.getSprites()).flatMap(_.toList)
    var textArray = graphicObjects.filter(_.isVisible()).map(_.getTexts()).flatMap(_.toList)
    
    for (sprite <- spriteArray) {window.draw(sprite)}
    for (text <- textArray) {window.draw(text)}
    sprite.position = (100, 200)
    //window.draw(sprite)
    
    text.position = (200, 400)
    //window.draw(text)
    
    window.display()
