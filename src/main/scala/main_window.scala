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
import java.awt.LayoutManager
import java.awt._
import javax.swing.border.Border
import javax.swing.BorderFactory
import java.awt.GraphicsDevice
import java.awt.GraphicsEnvironment
import java.awt.event._
import layout.TableLayout


/*
 * TODO :
 * - Factoriser code et mettre tour au propre 
 * - Refaire fenêtre
 *    - refaire menu qui ont besoins d'être retravaillés
 *    - retravailler les menus pour que ce soit beau
 * - Menu acceuil
 *    - palceholder menu
 * - Animations 1
 *    - animations basique
 *    - animation combats fonctionnelles de base
 * - Système monde
 *    - base avec placeholder
 *    - petit mais propre
 *    - étendre par morceaux
 */

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
  var myTurn = true
  var currentPokDead = false

  // Images for the player's and oponent's pokemon
  val playerPokemonImg = new ImageIcon("src/main/resources/green_square.png")
  val oppPokemonImg = new ImageIcon("src/main/resources/purple_square.png")
  
  // Text to display messages to the palyer
  val messageTextLabel = new JLabel("")
  messageTextLabel.setVisible(true)
  messageTextLabel.setBounds(250, 300, 800, 100)

  val playerHPTextLabel = new JLabel("hp. 100/100")
  playerHPTextLabel.setVisible(true)
  playerHPTextLabel.setBounds(400, 400, 150, 30)
  
  val enemyHPTextLabel = new JLabel ("hp. 100/100")
  enemyHPTextLabel.setVisible(true)
  enemyHPTextLabel.setBounds(1270, 200, 150, 30)

  /*val playerPPTextLabel = new JLabel("pp. 100/100")
  playerPPTextLabel.setVisible(true)
  playerPPTextLabel.setBounds(400, 430, 150, 30)*/

  def updateStatText() : Unit = {
    playerHPTextLabel.setText("hp. " + fight.current_pok_ally.PV + "/" + fight.current_pok_ally.PVMax)
    enemyHPTextLabel.setText("hp. " + fight.current_pok_enemy.PV + "/" + fight.current_pok_enemy.PVMax)
    //playerPPTextLabel.text = "pp. " + fight.team_ally.team(0).PV + "/" + fight.team_ally.team(0).PVMax
  }


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

  val endOfTurnButton = new JButton("End turn")
  endOfTurnButton.setVisible(true)
  

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


  val pokemonImgPanel2 = new JPanel
  pokemonImgPanel2.setVisible(true)
  pokemonImgPanel2.setBounds(vLim, borderSize, 1920-vLim-borderSize, hLim-borderSize)
  pokemonImgPanel2.setLayout(new GridBagLayout())
  val c = new GridBagConstraints()
  c.gridwidth = 1
  c.gridheight = 1
  c.fill = GridBagConstraints.NONE
  c.weightx = 0.5
  c.weighty = 0.5
  c.gridx = 1
  c.gridy = 1
  pokemonImgPanel2.add(playerPokLabel, c)
  pokemonImgPanel2.add(new JButton("Test1"), c)
  c.gridx = 5
  c.gridy = 5
  pokemonImgPanel2.add(oppPokLabel, c)
  pokemonImgPanel2.add(new JButton("Test2"), c)


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
  pokemonImgPanel.add(playerHPTextLabel)
  pokemonImgPanel.add(enemyHPTextLabel)
  //pokemonImgPanel.setBorder(BorderFactory.createLineBorder(Color.black))


  // Side panel
  val sidePanel = new JPanel
  sidePanel.setVisible(true)
  // will change according to needs of the side bar
  sidePanel.setLayout(new GridLayout (2, 1))

  // Return button
  val returnButton = new JButton("return")
  returnButton.setVisible(true)
  sidePanel.add(new JButton(""))
  sidePanel.add(returnButton)




  // Panel for the various action buttons 

  val actionMenuPanel = new JPanel
  actionMenuPanel.setVisible(true)
  actionMenuPanel.setLayout(new GridLayout(2, 3))
  // add the buttons to the panel
  actionMenuPanel.add(atkSelectionButton)
  actionMenuPanel.add(pokSelectionButton)
  actionMenuPanel.add(actionSelectionButton)
  actionMenuPanel.add(itemSelectionButton)
  actionMenuPanel.add(endOfTurnButton) 
  // creates border around the panel (mainly for test purposes)
  // actionMenuPanel.setBorder(BorderFactory.createLineBorder(Color.black))
  


  // Panel end of game
  
  // End game screen
  val endScreenImage = new ImageIcon("src/main/resources/end_img.png")

  val endScreenImgLabel = new JLabel(endScreenImage)
  endScreenImgLabel.setVisible(true)
  //endScreenImgLabel.setBounds(0, 0, 1920, 1080)

  val endScreenText = new JLabel("You win ;-)")
  endScreenText.setVisible(true)
  endScreenText.setBounds(1920/2 - 250, 1080/2 - 50, 500, 100)

  val endFightPanel = new JPanel
  endFightPanel.setLayout(null)
  endFightPanel.setVisible(false)
  endFightPanel.setBounds(0, 0, 1920, 1080)
  endFightPanel.add(endScreenImgLabel)
  endFightPanel.add(endScreenText)




  // Panel for the attack menu, is activated when the attack button is pressed
  val attackMenuPanel = new JPanel
  attackMenuPanel.setVisible(false)
  attackMenuPanel.setLayout(new GridLayout(2,2))
  
  // Attack buttons

  val attackButtonList = new Array[JButton](4)

  val attackButton1 = new JButton("attack 1")
  attackButtonList(0) = attackButton1;
  val attackButton2 = new JButton("attack 2")
  attackButtonList(1) = attackButton2;
  val attackButton3 = new JButton("attack 3")
  attackButtonList(2) = attackButton3;
  val attackButton4 = new JButton("attack 4")
  attackButtonList(3) = attackButton4;

  for (k<-0 until 3){
    attackMenuPanel.add(attackButtonList(k));
    attackButtonList(k).setVisible(true);
  }

  


  // Team panel
  

  // pokemon selection buttons
  
  val pokSelectButtonList = new Array[JButton](6);

  val pok1SelectionButton = new JButton(fight.team_player.team(0).pokemonName)
  pokSelectButtonList(0)=pok1SelectionButton;
  val pok2SelectionButton = new JButton(fight.team_player.team(1).pokemonName)
  pokSelectButtonList(1)=pok2SelectionButton;
  val pok3SelectionButton = new JButton(fight.team_player.team(2).pokemonName)
  pokSelectButtonList(2)=pok3SelectionButton; 
  val pok4SelectionButton = new JButton(fight.team_player.team(3).pokemonName)
  pokSelectButtonList(3)=pok4SelectionButton;
  val pok5SelectionButton = new JButton(fight.team_player.team(4).pokemonName)
  pokSelectButtonList(4)=pok5SelectionButton;
  val pok6SelectionButton = new JButton(fight.team_player.team(5).pokemonName)
  pokSelectButtonList(5)=pok6SelectionButton;
 


  val teamMenuPanel = new JPanel
  teamMenuPanel.setVisible(true)
  teamMenuPanel.setLayout(new GridLayout(2, 3))
  // add buttons to the panel
  var k = 0
  for (k<-0 until 5){
    teamMenuPanel.add(pokSelectButtonList(k));
    pokSelectButtonList(k).setVisible(true)
  }
  

  // Button actions
  

  var panelArray = new Array[JPanel](6)
  panelArray(0)=pokemonImgPanel; panelArray(1)=sidePanel; panelArray(2)=actionMenuPanel;

  def errorMessage() {
    messageTextLabel.setText("this action cannot be taken")
  }
 
  // Activate attack menu
  atkSelectionButton.addActionListener(
    new ActionListener{
      def actionPerformed(e:ActionEvent) {
        if (myTurn) {
          pokemonImgPanel.setVisible(true)
          // side panel
          actionMenuPanel.setVisible(false)
          teamMenuPanel.setVisible(false)
          attackMenuPanel.setVisible(true)
          // end panel
          // [1, 1, 0, 0, 1, 0]

          val attackButtonList = new Array[JButton](4)
          attackButtonList(0) = attackButton1; attackButtonList(1) =  attackButton2;  attackButtonList(2) = attackButton3; attackButtonList(3) = attackButton4
        
          for (k<-0 until 4){
            // Set the button to the name of the pokemon attack
            attackButtonList(k).setText(fight.current_pok_ally.set_attack(k).attackName) 
          }
        } else {
          errorMessage()
        }
      }
    }
  )



  // End of turn

  endOfTurnButton.addActionListener(
    new ActionListener{
      def actionPerformed(e:ActionEvent) {
        if (! fight.team_opp.team_alive() ) {
          pokemonImgPanel.setVisible(false)
          sidePanel.setVisible(false)
          actionMenuPanel.setVisible(false)
          // attack menu panel
          teamMenuPanel.setVisible(false)
          endFightPanel.setVisible(true)
          //Array(endFightPanel).iter(setVisible(true))
          //pannelArray.iter(setVisible(false))
          // [0, 0, 0, 0, 0, 1]
        } else {
          myTurn = true
          fight.new_pok_enemy()
          var nb_attack:Int = 0
          nb_attack = fight.attack_enemy()
          messageTextLabel.setText( fight.current_pok_enemy.pokemonName + " used " + fight.current_pok_enemy.set_attack(nb_attack).attackName)
          if (!fight.team_player.team_alive()) {
            actionMenuPanel.setVisible(false)
            sidePanel.setVisible(false)
            teamMenuPanel.setVisible(false)
            pokemonImgPanel.setVisible(false)
            endFightPanel.setVisible(true)
            // [0, 0, 0, 0, 0, 1]
          } else {
            if (!fight.current_pok_ally.alive) {
              currentPokDead = true
              // image panel
              actionMenuPanel.setVisible(false)
              // side panel
              // action menu panel
              // attack menu panel
              teamMenuPanel.setVisible(true)
              // end panel
              // [1, 0, 0, 0, 1, 0]
              messageTextLabel.setText("choose a new pokemon")
              updateStatText()
            }
          }
        }
        updateStatText()
      }
    }
  )



  // Switch pokemon

  def switch_pok(nb_pok:Int) {
    if (fight.team_player.team(nb_pok).alive) {
        fight.current_pok_ally = fight.team_player.team(nb_pok)
        pokemonImgPanel.setVisible(true)
        // side panel
        attackMenuPanel.setVisible(false)
        actionMenuPanel.setVisible(true)
        teamMenuPanel.setVisible(false)
        // end panel
        // [1, 1, 0, 1, 0, 0]
        if (!currentPokDead) {
          myTurn = false
        } else {
          currentPokDead = false
        }
      }
    updateStatText()
  }

  pokSelectionButton.addActionListener(
    new ActionListener{
      def actionPerformed(e:ActionEvent) {
        if (myTurn) {
        pokemonImgPanel.setVisible(true)
        // side panel
        attackMenuPanel.setVisible(false)
        actionMenuPanel.setVisible(false)
        teamMenuPanel.setVisible(true)
        // end panel
        // [1, 1, 0, 0, 1, 0]
        updateStatText()
        } else {
          errorMessage()
          updateStatText()
        }
      }
    }
  )
  
 
  for (k<-0 until 5){
    pokSelectButtonList(k).addActionListener(
      new ActionListener{
        def actionPerformed(e:ActionEvent){
          switch_pok(k)
        }
      }
    )
  }


  // Return button

  returnButton.addActionListener(
    new ActionListener{
      def actionPerformed (e:ActionEvent) {
        pokemonImgPanel.setVisible(true)
        // side panel
        actionMenuPanel.setVisible(true)
        attackMenuPanel.setVisible(false)
        teamMenuPanel.setVisible(false)
        // end panel
        // [1, 1, 1, 0, 0, 0]
      }
    }
  )

  // Attack menu

  def attack_processing (nb_attack:Int) {
    var att = new Attack("")
    att = fight.current_pok_ally.set_attack(nb_attack)
    if (att.use_attack()) {
      pokemonImgPanel.setVisible(true)
      // side panel
      actionMenuPanel.setVisible(true)
      attackMenuPanel.setVisible(false)
      // team menu
      // end panel
      // [1, 1, 1, 0, 0, 0]
      messageTextLabel.setText(fight.current_pok_ally.pokemonName + " used " + fight.current_pok_ally.set_attack(nb_attack).attackName + "")
      fight.current_pok_enemy.loss_PV(att.damage)
      updateStatText()
      myTurn = false
    }
    else {
      messageTextLabel.setText("not enough pp")
    }

  }
  
  for (k<-0 until 3){
    attackButtonList(k).addActionListener(
      new ActionListener {
        def actionPerformed(e:ActionEvent) {
          attack_processing(k)
        }
      }
    )
  }


  // enables us to set the window to full screen, i have no idea what it does 
  val graphics = GraphicsEnvironment.getLocalGraphicsEnvironment()
  val device = graphics.getDefaultScreenDevice()

  /*// Main Frame, where everything is displayed
  val mainFrame = new JFrame
  // makes sure that the 1920x1080 dimensions show fully on screen and are not obscured by additional stuff
  mainFrame.setUndecorated(true)
  // enables us to position panels absolutely
  mainFrame.setLayout(null)
  // makes the current frame visible
  mainFrame.setVisible(true)
  // adds the panels to the frame
  mainFrame.add(pokemonImgPanel2)
  mainFrame.add(sidePanel)
  mainFrame.add(actionMenuPanel)
  mainFrame.add(attackMenuPanel)
  mainFrame.add(teamMenuPanel)
  mainFrame.add(endFightPanel)
  // size of the window
  mainFrame.setPreferredSize(new Dimension(1920, 1080))
  mainFrame.pack()
  // makes the window close when the cross is pressed
  mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  // makes the frame full screen
  device.setFullScreenWindow(mainFrame)
  mainFrame.setVisible(false)*/

  val columns = Array(0.13, 0.87)
  val rows = Array(0.55, 0.45)
  val cells_size_mainFrame = Array(columns, rows)

  val mainFrame2 = new JFrame
  mainFrame2.setVisible(true)
  mainFrame2.setLayout(new TableLayout(cells_size_mainFrame))
  mainFrame2.setPreferredSize(new Dimension(1920, 1080))
  mainFrame2.pack()
  
  
  val test = new JFrame
  test.setVisible(false)
  test.setLayout(new TableLayout(cells_size_mainFrame))

  val c2 = new GridBagConstraints()
  c2.gridwidth = 1
  c2.gridheight = 20
  c2.fill = GridBagConstraints.BOTH
  c2.weightx = 0.13
  c2.weighty = 1
  c2.gridx = 0
  c2.gridy = 0
  mainFrame2.add(sidePanel, "0, 0, 0, 1")
 
  c2.gridwidth = 7
  c2.gridheight = 11
  c2.fill = GridBagConstraints.BOTH
  c2.weightx = 0.87
  c2.weighty = 0.55
  c2.gridx = 1
  c2.gridy = 0
  mainFrame2.add(pokemonImgPanel2, "1, 0, 1, 0")

  c2.gridwidth = 7
  c2.gridheight = 9
  c2.fill = GridBagConstraints.BOTH
  c2.weightx = 0.87
  c2.weighty = 0.45
  c2.gridx = 1
  c2.gridy = 11
  mainFrame2.add(actionMenuPanel, "1, 1, 1, 1")
  mainFrame2.add(attackMenuPanel, "1, 1, 1, 1")
  mainFrame2.add(teamMenuPanel, "1, 1, 1, 1")
  

  mainFrame2.add(endFightPanel, "0, 0, 0, 0")
 
}

object MainGame {
  var pok_empty = new Pokemon(" ")
  pok_empty.alive = false

  var atk1 = new Attack("Griffe acier")
  atk1.damage = 7
  atk1.PP_max = 10
  atk1.PP = 10

  var atk2 = new Attack("Fulguropoing")
  atk2.damage = 5
  atk2.PP_max = 20
  atk2.PP = 20

  var atk3 = new Attack("Inutile")
  atk3.damage = 0
  atk3.PP_max = 30
  atk3.PP = 30

  var atk4 = new Attack("Roulade Tactique")
  atk4.damage = 5
  atk4.PP_max = 10
  atk4.PP = 10

  var atk5 = new Attack("Griffe acier")
  atk5.damage = 7
  atk5.PP_max = 10
  atk5.PP = 10

  var atk6 = new Attack("Fulguropoing")
  atk6.damage = 5
  atk6.PP_max = 20
  atk6.PP = 20

  var atk7 = new Attack("Inutile")
  atk7.damage = 0
  atk7.PP_max = 30
  atk7.PP = 30

  var atk8 = new Attack("Roulade Tactique")
  atk8.damage = 5
  atk8.PP_max = 10
  atk8.PP = 10

  var atk9 = new Attack("Griffe acier")
  atk9.damage = 7
  atk9.PP_max = 10
  atk9.PP = 10

  var atk10 = new Attack("Fulguropoing")
  atk10.damage = 5
  atk10.PP_max = 20
  atk10.PP = 20

  var atk11 = new Attack("Inutile")
  atk11.damage = 0
  atk11.PP_max = 30
  atk11.PP = 30

  var atk12 = new Attack("Roulade Tactique")
  atk12.damage = 5
  atk12.PP_max = 10
  atk12.PP = 10

  var atk13 = new Attack("Griffe acier")
  atk13.damage = 7
  atk13.PP_max = 10
  atk13.PP = 10

  var atk14 = new Attack("Fulguropoing")
  atk14.damage = 5
  atk14.PP_max = 20
  atk14.PP = 20

  var atk15 = new Attack("Inutile")
  atk3.damage = 0
  atk3.PP_max = 30
  atk3.PP = 30

  var atk16 = new Attack("Roulade Tactique")
  atk4.damage = 5
  atk4.PP_max = 10
  atk4.PP = 10

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
  pok2.set_attack(0) = atk5
  pok2.set_attack(1) = atk6
  pok2.set_attack(2) = atk7
  pok2.set_attack(3) = atk8

  var pok3 = new Pokemon("Cabriolaine")
  pok3.PVMax = 50
  pok3.PV = 50
  pok3.set_attack(0) = atk9
  pok3.set_attack(1) = atk10
  pok3.set_attack(2) = atk11
  pok3.set_attack(3) = atk12

  var pok4 = new Pokemon("Spoink")
  pok4.PVMax = 50
  pok4.PV = 50
  pok4.set_attack(0) = atk13
  pok4.set_attack(1) = atk14
  pok4.set_attack(2) = atk15
  pok4.set_attack(3) = atk16

  var team1 = new Team
  var team2 = new Team

  team1.team(0) = pok1 
  team1.team(1) = pok2
  team1.team(2) = new Pokemon("")
  team1.team(3) = new Pokemon("")
  team1.team(4) = new Pokemon("")
  team1.team(5) = new Pokemon("")

  team2.team(0) = pok3
  team2.team(1) = pok4
  team2.team(2) = new Pokemon("")
  team2.team(3) = new Pokemon("")
  team2.team(4) = new Pokemon("")
  team2.team(5) = new Pokemon("")

  var fight = new Fight(team1, team2)
  var combatInterface = new CombatMenu(fight)

  def main(args: Array[String]) { // we need to keep that argument, otherwise it doesn't count as the main function
    combatInterface.updateStatText()
    Thread.sleep(Int.MaxValue)
  }
  
}

