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


object CombatMenu {
  
  val playerPokemonImg = new ImageIcon("src/main/resources/green_square.png")
  val oppPokemonImg = new ImageIcon("src/main/resources/purple_square.png")


  val atkSelectionButton = new JButton
  atkSelectionButton.setVisible(true)
  atkSelectionButton.text = "Attack"

  val pokSelectionButton = new JButton
  pokSelectionButton.setVisible(true)
  atkSelectionButton.setText("Change Pokemon")

  val actionSelectionButton = new Button
  actionSelectionButton.setVisible(true)
  actionSelectionButton.setText("Action")

  val itemSelectionButton = new Button
  itemSelectionButton.setVisible(true)
  itemSelectionButton.settext("Item")

  // Label for the image of player's pokemon
  val playerPokLabel = new JLabel(playerPokemonImg)
  playerPokLabel.setVisible(true)
  playerPokLabel.setBounds(250, 400, 100, 100)
  
  // Label for the image of opponent's pokemon
  val oppPokLabel = new JLabel(oppPokemonImg)
  oppPokLabel.setVisible(true)
  oppPokLabel.setBounds(1600, 200, 100, 100)


  // Panel where the pokemons will be shown
  val pokemonImgPanel = new JPanel
  pokemonImgPanel.setLayout(null)
  pokemonImgPanel.setVisible(true)
  pokemonImgPanel.setBounds(0, 0, 1920, 1080)
  pokemonImgPanel.add(playerPokLabel)
  pokemonImgPanel.add(oppPokLabel)

  // Panel for the various action buttons
  val actionMenuPanel = new JPanel
  actionMenuPanel.setVisible(true)
  actionMenuPanel.setLayout(new GridLayout(2, 2))
  actionMenuPanel.setBounds(0, 0, 1920, 1080)
  actionMenuPanel.add(atkSelectionButton)
  actionMenuPanel.add(pokSelectionButton)
  actionMenuPanel.add(actionSelectionButton)
  actionMenuPanel.add(itemSelectionButton)
  
  val mainFrame = new JFrame
  mainFrame.setLayout(null)
  mainFrame.setVisible(true)
  mainFrame.add(pokemonImgPanel)
  mainFrame.setPreferredSize(new Dimension(1920, 1080))
  mainFrame.pack()
  mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

  

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
