import sfml.graphics.*
import sfml.window.*

@main def main =
  val window = RenderWindow(VideoMode(800, 600, 32), "SFML", Window.WindowStyle.DefaultStyle)
  val texture = Texture("src/main/resources/player_icon.jpeg")
  val sprite = Sprite(texture)
  val font = Font("src/main/resources/test_font.ttf")
  val text = Text()
  text.string = "babebibobu"
  text.font = font
  while window.isOpen() do
    //println(Mouse.position)
    for event <- window.pollEvent() do
      //println(event)
      event match
        case Event.Closed() => window.close()
        case _ => ()
    
    window.clear(Color.Magenta())
    sprite.position = (100, 200)
    window.draw(sprite)
    text.position = (200, 400)
    window.draw(text)
    window.display()
