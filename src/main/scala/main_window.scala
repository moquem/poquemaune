import java.awt.Color
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
import java.awt.GridBagLayout
import java.awt._
import javax.swing.border.Border
import javax.swing.BorderFactory
import java.awt.GraphicsDevice
import java.awt.GraphicsEnvironment
import java.awt.event._
//import scala.swing.event._



class CombatMenu (fight:Fight) {

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
  
  // Text to display messages to the palyer
  val messageTextLabel = new JLabel("this is a test for now !")
  messageTextLabel.setBounds(300, 50, 200, 200)


  // adding action listers to the buttons so we can know when they are clicked

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
  pokemonImgPanel.add(messageTextLabel)
  //pokemonImgPanel.setBorder(BorderFactory.createLineBorder(Color.black))







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
  // creates border around the panel (mainly for test purposes)
  //actionMenuPanel.setBorder(BorderFactory.createLineBorder(Color.black))
  

  /*
   *
   *  Attack menu, has 5 buttons, 4 for the various attacks and one for returning to the main menu
   *
   * */






  // Panel for the attack menu, is activated when the attack button is pressed
  val attackMenuPanel = new JPanel
  attackMenuPanel.setVisible(false)
  attackMenuPanel.setBounds(vLim, hLim, 1920-vLim-borderSize, 1080-hLim-borderSize)
  // Grid layout is good here
  attackMenuPanel.setLayout(new GridLayout(2,2))
  
  // Attack buttons
  val attackButton1 = new JButton("attack 1")
  attackMenuPanel.add(attackButton1)
  attackButton1.setVisible(true)
  //attackButton1.setBounds(borderSize+300, borderSize + 300, 300, 100)
  val attackButton2 = new JButton("attack 2")
  attackButton2.setVisible(true)  
  attackMenuPanel.add(attackButton2)
  //attackButton2.setBounds(1920-borderSize-300, borderSize + 300, 300, 100)
  val attackButton3 = new JButton("attack 3")
  attackButton3.setVisible(true)
  attackMenuPanel.add(attackButton3)
  //attackButton3.setBounds(borderSize+300, 1080-borderSize-100, 300, 100)
  val attackButton4 = new JButton("attack 4")
  attackButton4.setVisible(true)
  attackMenuPanel.add(attackButton4)
  //attackButton4.setBounds(1920-borderSize-300, 1080-borderSize-100, 300, 100)
  // Return button
  val returnButton = new JButton("return")
  returnButton.setVisible(true)
  //attackMenuPanel.add(returnButton)
  // returnButton.setBounds(1920-200, 1080-200, 100, 100)


  // buttons
  attackMenuPanel.add(attackButton2)
  attackMenuPanel.add(attackButton3)
  attackMenuPanel.add(attackButton4)
  attackMenuPanel.add(returnButton)




  // Team panel
  val pok1SelectionButton = new JButton(fight.team_1.team(0).pokemonName)
  pok1SelectionButton.setVisible(true)

  val pok2SelectionButton = new JButton(fight.team_1.team(1).pokemonName)
  pok2SelectionButton.setVisible(true)

  val pok3SelectionButton = new JButton(fight.team_1.team(2).pokemonName)
  pok3SelectionButton.setVisible(true)

  val pok4SelectionButton = new JButton(fight.team_1.team(3).pokemonName)
  pok4SelectionButton.setVisible(true)

  val pok5SelectionButton = new JButton(fight.team_1.team(4).pokemonName)
  pok5SelectionButton.setVisible(true)

  val pok6SelectionButton = new JButton(fight.team_1.team(5).pokemonName)
  pok6SelectionButton.setVisible(true)

  val returnButtonPokSelection = new JButton("Retour")
  returnButton.setVisible(true)


  val teamMenuPanel = new JPanel
  teamMenuPanel.setVisible(true)
  teamMenuPanel.setBounds(vLim, hLim, 1920-vLim-borderSize, 1080-hLim-borderSize)
  // panel layout, a 2x3 grid is what we want here
  teamMenuPanel.setLayout(new GridLayout(2, 3))

  teamMenuPanel.add(pok1SelectionButton)
  teamMenuPanel.add(pok2SelectionButton)
  teamMenuPanel.add(pok3SelectionButton)
  teamMenuPanel.add(pok4SelectionButton)
  teamMenuPanel.add(pok5SelectionButton)
  teamMenuPanel.add(pok6SelectionButton





  // Button actions
 
  // Activate attack menu
  atkSelectionButton.addActionListener(
    new ActionListener{
      def actionPerformed(e:ActionEvent) {
          pokemonImgPanel.setVisible(false)
          actionMenuPanel.setVisible(false)
          attackMenuPanel.setVisible(true)

        }
    }
  )

  // Switch pokemon

  pokSelectionButton.addActionListener(
    new ActionListener{
      def actionPerformed(e:ActionEvent) {
        pokemonImgPanel.setVisible(false)
        actionMenuPanel.setVisible(false)
        teamMenuPanel.setVisible(true)
      }
    }
  )



  //Attack menu

  returnButton.addActionListener(
    new ActionListener{
      def actionPerformed (e:ActionEvent) {
        pokemonImgPanel.setVisible(true)
        actionMenuPanel.setVisible(true)
        attackMenuPanel.setVisible(false)
      }
    }
  )

  attackButton1.addActionListener(
    new ActionListener {
      def actionPerformed(e:ActionEvent) {
          pokemonImgPanel.setVisible(true)
          actionMenuPanel.setVisible(true)
          attackMenuPanel.setVisible(false)
          messageTextLabel.setText(fight.current_pok_ally.pokemonName + " used " + fight.current_pok_ally.set_attack(0).attackName + " it's not very effective")
          fight.attack_ally(0)
        }
    }
  )

  attackButton2.addActionListener(
    new ActionListener {
      def actionPerformed(e:ActionEvent) {
          pokemonImgPanel.setVisible(true)
          actionMenuPanel.setVisible(true)
          attackMenuPanel.setVisible(false)
          fight.attack_ally(1)
        }
    }
  )

  attackButton3.addActionListener(
    new ActionListener {
      def actionPerformed(e:ActionEvent) {
          pokemonImgPanel.setVisible(true)
          actionMenuPanel.setVisible(true)
          attackMenuPanel.setVisible(false)
          fight.attack_ally(2)
        }
    }
  )

  attackButton4.addActionListener(
    new ActionListener {
      def actionPerformed(e:ActionEvent) {
          pokemonImgPanel.setVisible(true)
          actionMenuPanel.setVisible(true)
          attackMenuPanel.setVisible(false)
          fight.attack_ally(3)
        }
    }
  )



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
  mainFrame.add(teamMenuPanel)
  // size of the window
  mainFrame.setPreferredSize(new Dimension(1920, 1080))
  mainFrame.pack()
  // makes the window close when the cross is pressed
  mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  // makes the frame full screen
  device.setFullScreenWindow(mainFrame)
  

}

object MainGame {
  var pok_empty = new Pokemon("")
  pok_empty.alive = false

  var atk1 = new Attack("Griffe acier")
  atk1.damage = 7
  atk1.PP_max = 10
  atk1.PP_cost = 10

  var atk2 = new Attack("Fulguropoing")
  atk2.damage = 5
  atk2.PP_max = 20
  atk2.PP_cost = 20

  var atk3 = new Attack("Inutile")
  atk3.damage = 0
  atk3.PP_max = 30
  atk3.PP_cost = 30

  var atk4 = new Attack("Roulade Tactique")
  atk4.damage = 5
  atk4.PP_max = 10
  atk4.PP_cost = 10

  var pok1 = new Pokemon("Noacier")
  pok1.PVMax = 50
  pok1.PV = 50
  pok1.set_attack(0) = atk1
  pok1.set_attack(1) = atk2
  pok1.set_attack(2) = atk3
  pok1.set_attack(3) = atk4

  var pok2 = new Pokemon("Grodrive")
  pok2.PVMax = 50
  pok2.PV = 50
  pok2.set_attack(0) = atk1
  pok2.set_attack(1) = atk2
  pok2.set_attack(2) = atk3
  pok2.set_attack(3) = atk4

  var pok3 = new Pokemon("Cabriolaine")
  pok3.PVMax = 50
  pok3.PV = 50
  pok3.set_attack(0) = atk1
  pok3.set_attack(1) = atk2
  pok3.set_attack(2) = atk3
  pok3.set_attack(3) = atk4

  var pok4 = new Pokemon("Spoink")
  pok4.PVMax = 50
  pok4.PV = 50
  pok4.set_attack(0) = atk1
  pok4.set_attack(1) = atk2
  pok4.set_attack(2) = atk3
  pok4.set_attack(3) = atk4

  var team1 = new Team
  var team2 = new Team

  team1.team(0) = pok1 
  team1.team(1) = pok2
  team1.team(2) = pok_empty
  team1.team(3) = pok_empty
  team1.team(4) = pok_empty
  team1.team(5) = pok_empty

  team2.team(0) = pok3
  team2.team(1) = pok4
  team2.team(2) = pok_empty
  team2.team(3) = pok_empty
  team1.team(4) = pok_empty
  team1.team(5) = pok_empty

  var fight = new Fight(team1,team2)
  var combatInterface = new CombatMenu(fight)

  def main(args: Array[String]) { // we need to keep that argument, otherwise it doesn't count as the main function
    while (true) {}
  }
  
}

