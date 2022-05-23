import sfml.graphics.*
import sfml.window.*
import sfml.system.*

@main def main =
  // the main window
  val window = RenderWindow(VideoMode(1280, 720, 32), "Poquemaune", Window.WindowStyle.DefaultStyle)

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
    
    
    window.display()
