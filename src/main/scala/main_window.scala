import java.awt.Color
import scala.swing._
import scala.swing.Component
import javax.swing.ImageIcon
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing._
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JLabel
import javax.swing.JTextArea
import java.awt.image.BufferedImage
import java.awt.{Graphics2D,Color,Font,BasicStroke}
import javax.imageio.ImageIO
import java.io.InputStream
import java.io.File
import java.awt.GridLayout
import javax.swing.border.Border
import javax.swing.BorderFactory
import java.awt.GraphicsDevice
import java.awt.GraphicsEnvironment



object CombatMenu {

  /*
   * We are using a null layout, this means we have to be precise about how we organise our panels
   * Most importantly on any given pixel there should be at most one panel
   * Otherwise we run the risk of having a components that are invisble
   * This is all handled in the setBounds of all the panels, however, they must respect the following layout
   *
   *
   *  |----|--------------------|
   *  | s  |                    |
   *  | i  |                    |
   *  | d  v      imgPanel      |
   *  | e  L                    |
   *  | B  i--------hLim--------|
   *  | a  m                    |
   *  | r  |   actionMenuPanel  |
   *  |    |                    |
   *  |----|--------------------|
   *
   *  To this we have to add a border so it isn't hideous
   *
   *  It is possible that some single pixel adjusting may have to be done
   *
   *
   */
  
  val hLim = 600
  val vLim = 250
  val borderSize = 15

  // Images for the player's and oponent's pokemon
  val playerPokemonImg = new ImageIcon("src/main/resources/green_square.png")
  val oppPokemonImg = new ImageIcon("src/main/resources/purple_square.png")

  // Button for selecting attacks
  val atkSelectionButton = new JButton("Attack")
  atkSelectionButton.setVisible(true)

  // Button for switching pokemon
  val pokSelectionButton = new JButton("Change Pokemon")
  pokSelectionButton.setVisible(true)

  // Button for selecting an action
  val actionSelectionButton = new JButton("Action")
  actionSelectionButton.setVisible(true)

  // Button for slecting an item
  val itemSelectionButton = new JButton("Item")
  itemSelectionButton.setVisible(true)
  

  // Label for the image of player's pokemon
  val playerPokLabel = new JLabel(playerPokemonImg)
  // makes the label visible
  playerPokLabel.setVisible(true)
  // positioning, the size of the label is exactly the size of the image
  playerPokLabel.setBounds(250, 400, 100, 100)
  
  // Label for the image of opponent's pokemon
  val oppPokLabel = new JLabel(oppPokemonImg)
  oppPokLabel.setVisible(true)
  oppPokLabel.setBounds(1400, 200, 100, 100)


  // Panel where the pokemons will be shown
  val pokemonImgPanel = new JPanel
  // makes panel visible
  pokemonImgPanel.setVisible(true)
  // null layout enables absolute positioning
  pokemonImgPanel.setLayout(null)
  // panel size and position
  pokemonImgPanel.setBounds(vLim, borderSize, 1920-vLim-borderSize, hLim-borderSize)
  // adds the pokemon images to the panel
  pokemonImgPanel.add(playerPokLabel)
  pokemonImgPanel.add(oppPokLabel)
  pokemonImgPanel.setBorder(BorderFactory.createLineBorder(Color.black))

  // Panel for the various action buttons
  val actionMenuPanel = new JPanel
  actionMenuPanel.setVisible(true)
  actionMenuPanel.setBounds(vLim, hLim, 1920-vLim-borderSize, 1080-hLim-borderSize)
  // panel layout, a 2x2 grid is what we want here
  actionMenuPanel.setLayout(new GridLayout(2, 2))
  // add the buttons to the panel
  actionMenuPanel.add(atkSelectionButton)
  actionMenuPanel.add(pokSelectionButton)
  actionMenuPanel.add(actionSelectionButton)
  actionMenuPanel.add(itemSelectionButton)
  actionMenuPanel.setBorder(BorderFactory.createLineBorder(Color.black))
  

  /*
   *
   *  Attack menu, has 5 buttons, 4 for the various attacks and one for returning to the main menu
   *
   * */
  
  // Attack buttons
  val attackButton1 = new JButton("attack 1")
  attackButton1.setVisible(true)
  attackButton1.setBounds(borderSize, borderSize, 100, 50)
  val attackButton2 = new JButton("attack 2")
  attackButton2.setVisible(true)
  attackButton2.setBounds(1920-borderSize-100, borderSize, 100, 50)
  val attackButton3 = new JButton("attack 3")
  attackButton3.setVisible(true)
  attackButton3.setBounds(borderSize, 1080-borderSize-50, 100, 50)
  val attackButton4 = new JButton("attack 4")
  attackButton4.setVisible(true)
  attackButton4.setBounds(1920-borderSize-100, 1080-borderSize-50, 100, 50)
  // Return button
  val returnButton = new JButton("return")
  returnButton.setVisible(true)
  returnButton.setBounds(1920-200, 1080-200, 100, 100)

  // Panel for the attack menu, is activated when the attack button is pressed
  val attackMenuPanel = new JPanel
  attackMenuPanel.setVisible(false)
  attackMenuPanel.setBounds(0, 0, 1920, 1080)
  // We need a null layout in order to be able to place the return button
  attackMenuPanel.setLayout(null)
  // buttons
  attackMenuPanel.add(attackButton1)
  attackMenuPanel.add(attackButton2)
  attackMenuPanel.add(attackButton3)
  attackMenuPanel.add(attackButton4)
  attackMenuPanel.add(returnButton)





  // enables us to set the window to full screen, i have no idea what it does 
  val graphics = GraphicsEnvironment.getLocalGraphicsEnvironment()
  val device = graphics.getDefaultScreenDevice()

  // Main Frame, where everything is displayed
  val mainFrame = new JFrame
  // makes sure that the 1920x1080 dimensions show fully on screen and are not obscured by additional stuff
  mainFrame.setUndecorated(true)
  // enables us to position panels absolutely
  mainFrame.setLayout(null)
  // makes the current frame visible
  mainFrame.setVisible(true)
  // adds the panels to the frame
  mainFrame.add(pokemonImgPanel)
  mainFrame.add(actionMenuPanel)
  mainFrame.add(attackMenuPanel)
  // size of the window
  mainFrame.setPreferredSize(new Dimension(1920, 1080))
  mainFrame.pack()
  // makes the window close when the cross is pressed
  mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  // makes the frame full screen
  device.setFullScreenWindow(mainFrame)
  
  




  def main(args: Array[String]){
    while (true) {}
  }


}
/*
object SwingExample {
    


    val image = new ImageIcon("src/main/resources/green_square.png")
    val image2 = new ImageIcon("src/main/resources/purple_square.png")


    val picLabel = new JLabel(image)
    picLabel.setVisible(true)
    picLabel.setBounds(300, 200, 100, 100)
    //picLabel.setHorizontalAlignment(SwingConstants.LEFT)
    //picLabel.setVerticalAlignment(SwingConstants.BOTTOM)
    //picLabel.setBounds(300, 100, 100, 100)

    val picLabel2 = new JLabel(image2)
    picLabel2.setVisible(true)
    picLabel2.setBounds(100, 400, 100, 100)

    val picPanel = new JPanel
    picPanel.setLayout(null)
    picPanel.setVisible(true)
    picPanel.add(picLabel)
    picPanel.add(picLabel2)
    picPanel.setBounds(0, 0, 700, 500)

    val mainFrame = new JFrame
    mainFrame.setLayout(null)
    mainFrame.add(picPanel)
    mainFrame.setVisible(true)
    mainFrame.setPreferredSize(new Dimension(700, 500))
    mainFrame.pack()
    mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)


    def main(args: Array[String]){while (true) {}
    }
}
*/
