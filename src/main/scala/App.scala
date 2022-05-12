import sfml.graphics.*
import sfml.window.*
import sfml.system.*

@main def main =
  // the main window
  val window = RenderWindow(VideoMode(1280, 720, 32), "SFML", Window.WindowStyle.DefaultStyle)

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
  

  var displayable = Array[Displayable](MainMenu, TestCombat)
  MainMenu.setActive(true)
  TestCombat.setActive(false)

  var graphicObjects = Array[GraphicObj]()
  graphicObjects = displayable.map(_.getGraphicObjects()).flatMap(_.toList)

  // handling the inputs
  var left_button_pressed = false
  var right_button_pressed = true
  var mousePos = (0, 0)

  // main loop
  while window.isOpen() do
    //println(Mouse.Button.Left.isPressed())
    for event <- window.pollEvent() do
      //println(event)
      
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
    

    window.clear(new Color(50, 50, 50, 255.toByte))
    
    graphicObjects.foreach(_.updateObj())
    for (obj <- graphicObjects) {if (obj.isVisible()){window.draw(obj.getSprite())}}
    for (obj <- graphicObjects) {if (obj.isVisible()) {window.draw(obj.text)}}
    sprite.position = (100, 200)
    //window.draw(sprite)
    
    text.position = (200, 400)
    //window.draw(text)
    
    window.display()
