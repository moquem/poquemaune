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
 * - Factoriser code et mettre tour au propre (à terminer)
 * - Refaire fenêtre
 *    - refaire menus qui ont besoins d'être retravaillés (+ tard)
 *    - retravailler les menus pour que ce soit beau (+ tard)
 * - Menu acceuil
 *    x start
 *    - settings
 *    - quit
 * - Animations 1 (+ tard)
 *    - animations basique
 *    - animation combats fonctionnelles de base
 * - Système monde (en priorité)
 * - Ajouter joueur
 * - Ajouter combat depuis map
 */



trait Tile extends JButton {
  def updateTile(new_img:ImageIcon) : Unit
}

class MapTile (image:ImageIcon) extends Tile {
  this.setVisible(true)

 def updateTile (new_img:ImageIcon){
    if (new_img != this.getIcon()){
      this.setIcon(new_img)
    }
  }
}

// Tile representing the player
class PlayerTile () extends Tile {
  this.setVisible(true)
  def updateTile (new_img:ImageIcon){
  }
}

sealed trait Direction
  case object Right extends Direction
  case object Left extends Direction
  case object Up extends Direction
  case object Down extends Direction

class Map (self:JPanel, size_x:Int, size_y:Int, mapImages:Array[ImageIcon], mapLayout:Array[Array[Int]], backImage:ImageIcon, playerImage:ImageIcon, playerTileCoord:(Int, Int)) extends JPanel {
  
  var curr_x_pos = 0
  var curr_y_pos = 0

  var tiles = Array.fill[Array[Tile]](size_x)(Array.fill[Tile](size_y)(new MapTile(backImage)))
 
  // this means that the player will start at position 0, 0, should probably be passed as parameter
  tiles(0)(0) = new PlayerTile()

  def in_bounds (x_pos:Int, y_pos:Int) : Boolean = {
    return !(x_pos >= size_x || x_pos < 0) && !(y_pos>=size_y || y_pos < 0)
  }
  
  def resizeImage (imgIcon:ImageIcon, new_x:Int, new_y:Int) : ImageIcon = {
    val imgToResize = imgIcon.getImage()
    val resizedImg = imgToResize.getScaledInstance(new_x, new_y, Image.SCALE_SMOOTH)
    return new ImageIcon(resizedImg)
  }

  // adds all the tiles to the panel
  def init (panel:JPanel) {
    
    val map_columns = Array.fill[Double](size_y)((1).toDouble/size_y)
    val map_rows = Array.fill[Double](size_x)((1.toDouble)/size_x)
    val map_table = Array(map_rows, map_columns)
    panel.setLayout(new TableLayout(map_table))
    panel.setVisible(true)
    
    for (i<-0 until size_x){
      for (j<-0 until size_y){
        tiles(i)(j) = new MapTile(mapImages(mapLayout(i)(j)))
        val tile = tiles(i)(j)
        this.add(tile, i.toString + ", " + j.toString + ", " + i.toString + ", " + j.toString)
        tile.setVisible(true)
        //tile.setBorderPainted(false)
      }
    }

    // sets up the player
    tiles(playerTileCoord._1)(playerTileCoord._2) = new PlayerTile()
    val playerTile = tiles(playerTileCoord._1)(playerTileCoord._2)
    this.add(playerTile, playerTileCoord._1.toString + ", " + playerTileCoord._2.toString + ", " + playerTileCoord._1.toString + ", " + playerTileCoord._2.toString)
  }

  // moves the map
  def move_player_dir (dir:Direction){
    // rescales the images, must be done repeatedly because the player can change window size at any time
    val resizedMapImages = mapImages
    val ex_tile = tiles(0)(0)
    // resizes player
    tiles(playerTileCoord._1)(playerTileCoord._2).setIcon(resizeImage(playerImage, ex_tile.getWidth(), ex_tile.getHeight()))
    //println(tiles(playerTileCoord._1)(playerTileCoord._2).getWidth())
    for (k<-0 until mapImages.size){
      resizedMapImages(k) = resizeImage(mapImages(k), ex_tile.getWidth(), ex_tile.getHeight())
    }

    var new_pos_x = 0
    var new_pos_y = 0
    dir match{
      case Right =>
        new_pos_x = curr_x_pos+1;
        new_pos_y = curr_y_pos;
      case Left =>
        new_pos_x = curr_x_pos-1;
        new_pos_y = curr_y_pos;
      case Up =>
        new_pos_x = curr_x_pos;
        new_pos_y = curr_y_pos-1;
      case Down =>
        new_pos_x = curr_x_pos;
        new_pos_y = curr_y_pos+1;
    }
    
    val i = 0
    val j = 0
    for (i<-0 until size_x){
      for (j<-0 until size_y){
        val tile = tiles(i)(j)
        if (in_bounds(new_pos_x+i, new_pos_y+j)){
          val newTileImage = resizedMapImages(mapLayout(new_pos_x+i)(new_pos_y+j))
          tile.updateTile(newTileImage)
        }
        else {
          tile.updateTile(backImage)
        }
      }
    }

    curr_x_pos = new_pos_x
    curr_y_pos = new_pos_y
  }
}


class MapArrow (direction:Direction, map:Map) extends JButton{
  this.setVisible(true)
  this.addActionListener(
    new ActionListener{
      def actionPerformed(e:ActionEvent){
        map.move_player_dir(direction) 
      }
    }
  ) 
}


class CombatMenu (fight:Fight) {
  
  var myTurn = true
  var currentPokDead = false

  // Images for the player's and oponent's pokemon
  val playerPokemonImg = new ImageIcon("src/main/resources/ho-ho-garlic-bread-ho-ho.png")
  val greenSquare = new ImageIcon("src/main/resources/green_square.png")
  val oppPokemonImg = new ImageIcon("src/main/resources/purple_square.png")

  val playerIcon = new ImageIcon("src/main/resources/player_icon.jpeg")

  val fightIcon = new ImageIcon("src/main/resources/fight_icon.png")
  
  // Text to display messages to the palyer
  val messageTextLabel = new JLabel("")
  messageTextLabel.setVisible(true)
  messageTextLabel.setHorizontalAlignment(SwingConstants.CENTER)

  val playerHPTextLabel = new JLabel("hp. 100/100")
  playerHPTextLabel.setVisible(true)
  playerHPTextLabel.setHorizontalAlignment(SwingConstants.CENTER)
  
  val enemyHPTextLabel = new JLabel ("hp. 100/100")
  enemyHPTextLabel.setVisible(true)
  enemyHPTextLabel.setHorizontalAlignment(SwingConstants.CENTER)

  /*val playerPPTextLabel = new JLabel("pp. 100/100")
  playerPPTextLabel.setVisible(true)
  playerPPTextLabel.setBounds(400, 430, 150, 30)*/

  def updateStatText() : Unit = {
    playerHPTextLabel.setText("hp. " + fight.current_pok_ally.PV + "/" + fight.current_pok_ally.PVMax)
    enemyHPTextLabel.setText("hp. " + fight.current_pok_enemy.PV + "/" + fight.current_pok_enemy.PVMax)
    //playerPPTextLabel.text = "pp. " + fight.team_ally.team(0).PV + "/" + fight.team_ally.team(0).PVMax
  }

  def updatePokImg (oldImg:JLabel, newImg:JLabel) : Unit = {
    println("to be implemented")
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
  
  // ends the player's current turn
  val endOfTurnButton = new JButton("End turn")
  endOfTurnButton.setVisible(true)

  // shows the map
  val openMapButton = new JButton("Open map")
  openMapButton.setVisible(true)
  

  // Label for the image of player's pokemon
  val playerPokLabel = new JLabel(playerPokemonImg)
  // makes the label visible
  playerPokLabel.setVisible(true)
  // positioning, the size of the label is exactly the size of the image
  
  // Label for the image of opponent's pokemon
  val oppPokLabel = new JLabel(oppPokemonImg)
  oppPokLabel.setVisible(true)


  val pokemonImgPanel2 = new JPanel()
  pokemonImgPanel2.setVisible(true)
  
  val pokemon_img_panel_columns = Array(0.2, 0.2, 0.2, 0.2, 0.2)
  val pokemon_img_panel_rows = Array(0.16, 0.16, 0.16, 0.16, 0.16, 0.2)
  val pokemon_img_table = Array(pokemon_img_panel_columns, pokemon_img_panel_rows)
  pokemonImgPanel2.setLayout(new TableLayout(pokemon_img_table))
  
  pokemonImgPanel2.add(playerPokLabel, "1, 3, 1, 3")
  pokemonImgPanel2.add(oppPokLabel, "3, 1, 3, 1")
  pokemonImgPanel2.add(playerHPTextLabel, "1, 2, 1, 2")
  pokemonImgPanel2.add(enemyHPTextLabel, "3, 0, 3, 0")
  //pokemonImgPanel2.add(new JButton("test"), "0, 5, 4, 5")
  pokemonImgPanel2.add(messageTextLabel, "0, 5, 4, 5")
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
  actionMenuPanel.add(openMapButton)
  // creates border around the panel (mainly for test purposes)
  // actionMenuPanel.setBorder(BorderFactory.createLineBorder(Color.black))

  // Map
  val test_map_layout_columns = Array.fill[Int](20)(0)
  val map_x = 30
  val map_y = 20
  val test_map_layout = Array.fill[Array[Int]](map_x)(Array.fill[Int](map_y)(0))
  test_map_layout(10)(15) = 2
  val tilesIcons = Array(greenSquare, oppPokemonImg, fightIcon)
  val mainMap = new Map(new JPanel(), map_x, map_y, tilesIcons, test_map_layout, oppPokemonImg, playerPokemonImg, (15, 10))
  mainMap.init(mainMap)  
 


   // Map side panel
  val mapSidePanel = new JPanel()
  mapSidePanel.setVisible(true)
  val mapSidePanel_columns = Array(0.33, 0.33, 0.33)
  val mapSidePanel_rows = Array(0.07, 0.07, 0.07, 0.19, 0.2, 0.2, 0.2)
  val mapSidePanelLayout = Array(mapSidePanel_columns, mapSidePanel_rows)
  mapSidePanel.setLayout(new TableLayout(mapSidePanelLayout))
  mapSidePanel.setVisible(true)

  // Map return button
  val mapReturnButton = new JButton("return")
  mapReturnButton.setVisible(true)
  mapSidePanel.add(mapReturnButton, "0, 6, 2, 6")

  // Map movement keys
  val mapUpButton = new MapArrow(Up, mainMap)
  mapUpButton.setLabel("up")
  val mapDownButton = new MapArrow(Down, mainMap)
  mapDownButton.setLabel("down")
  val mapRightButton = new MapArrow(Right, mainMap)
  mapRightButton.setLabel("right")
  val mapLeftButton = new MapArrow(Left, mainMap)
  mapLeftButton.setLabel("left")

  mapSidePanel.add(mapUpButton, "1, 0, 1, 0")
  mapSidePanel.add(mapDownButton, "1, 2, 1, 2")
  mapSidePanel.add(mapRightButton, "2, 1, 2, 1")
  mapSidePanel.add(mapLeftButton, "0, 1, 0, 1")



  val fightMenuPanels = Array(pokemonImgPanel2, sidePanel, actionMenuPanel)
  val mapPanels = Array(mainMap, mapSidePanel)
  
  def deactivateAllPanels() {
    for (k<-0 until fightMenuPanels.size){
      fightMenuPanels(k).setVisible(false)
    }
    for (k<-0 until mapPanels.size){
      mapPanels(k).setVisible(false)
    }
  }

  // Panel end of game
  
  // End game screen
  val endScreenImage = new ImageIcon("src/main/resources/end_img.png")

  val endScreenImgLabel = new JLabel(endScreenImage)
  endScreenImgLabel.setVisible(true)
  endScreenImgLabel.setBounds(0, 0, 1920, 1080)

  val endScreenText = new JLabel("You win ;-)")
  endScreenText.setVisible(true)
  endScreenText.setBounds(1920/2 - 250, 1080/2 - 50, 500, 100)

  val endFightPanel = new JPanel
  endFightPanel.setLayout(new GridLayout(1, 1))
  endFightPanel.setVisible(false)
  endFightPanel.add(endScreenImgLabel)
  endFightPanel.add(endScreenText)


  // openMapButton
  openMapButton.addActionListener(
    new ActionListener{
      def actionPerformed (e:ActionEvent){
        deactivateAllPanels()
        for (k<-0 until mapPanels.size){
          mapPanels(k).setVisible(true)
        }
      }
    }
  )
  
  // Map return button
  mapReturnButton.addActionListener(
    new ActionListener{
      def actionPerformed(e:ActionEvent){
        deactivateAllPanels()
        fightMenuPanels.foreach(_.setVisible(true))
      }
    }
  )
  

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
  
  // looks at all the conditions for various buttons and activates or deactivates them as needed
  def updateActive(){
    // Attack button
    atkSelectionButton.setEnabled(true)
    if (!myTurn){
      atkSelectionButton.setEnabled(false)
    }
    var no_available_attacks = true
    for (k<-0 until 4){
      if (attackButtonList(k).isEnabled()){
        no_available_attacks = false
        if (fight.current_pok_ally.set_attack(k).PP_cost>fight.current_pok_ally.set_attack(k).PP){
          attackButtonList(k).setEnabled(false)
        }
        else {
          attackButtonList(k).setEnabled(true)
        }
      }
    }
    if (no_available_attacks){
      atkSelectionButton.setEnabled(false)
    }

    // Pokemon selection button
    for (k<-0 until pokSelectButtonList.size){
      if (fight.team_player.team(k).alive){
        pokSelectButtonList(k).setEnabled(true)
      }
      else {
        pokSelectButtonList(k).setEnabled(false)
      }
    }
    // The map arrow keys, need to add a bool to see if in fight
  }

  var panelArray = new Array[JPanel](6)
  panelArray(0)=pokemonImgPanel2; panelArray(1)=sidePanel; panelArray(2)=actionMenuPanel;

  def errorMessage() {
    messageTextLabel.setText("this action cannot be taken")
  }
 
  // Activate attack menu
  atkSelectionButton.addActionListener(
    new ActionListener{
      def actionPerformed(e:ActionEvent) {
        if (myTurn) {
          pokemonImgPanel2.setVisible(true)
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
      updateActive()
    }
  )


  // End of turn

  endOfTurnButton.addActionListener(
    new ActionListener{
      def actionPerformed(e:ActionEvent) {
        if (! fight.team_opp.team_alive() ) {
          pokemonImgPanel2.setVisible(false)
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
            pokemonImgPanel2.setVisible(false)
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
        updateActive()
      }
    }
  )

  // Switch pokemon

  def switch_pok(nb_pok:Int) {
    if (fight.team_player.team(nb_pok).alive) {
        fight.current_pok_ally = fight.team_player.team(nb_pok)
        pokemonImgPanel2.setVisible(true)
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
        pokemonImgPanel2.setVisible(true)
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
        updateActive()
      }
    }
  )
  
 
  for (k<-0 until 5){
    pokSelectButtonList(k).addActionListener(
      new ActionListener{
        def actionPerformed(e:ActionEvent){
          switch_pok(k)
          updateActive()
        }
      }
    )
  }


  // Return button

  returnButton.addActionListener(
    new ActionListener{
      def actionPerformed (e:ActionEvent) {
        pokemonImgPanel2.setVisible(true)
        // side panel
        actionMenuPanel.setVisible(true)
        attackMenuPanel.setVisible(false)
        teamMenuPanel.setVisible(false)
        // end panel
        // [1, 1, 1, 0, 0, 0]
        updateActive()
      }
    }
  )

  // Attack menu

  def attack_processing (nb_attack:Int) {
    var att = new Attack("")
    att = fight.current_pok_ally.set_attack(nb_attack)
    if (att.use_attack()) {

      var typ1 = current_pok_enemy.typ
      var typ2 = current_pok_ally.typ
      
      var bonus_typ:Int

      pokemonImgPanel2.setVisible(true)
      // side panel
      actionMenuPanel.setVisible(true)
      attackMenuPanel.setVisible(false)
      // team menu
      // end panel
      // [1, 1, 1, 0, 0, 0]
      messageTextLabel.setText(fight.current_pok_ally.pokemonName + " used " + fight.current_pok_ally.set_attack(nb_attack).attackName + "")

      if ((typ1 == "Feuille" && typ2 == "Pierre") || (typ1 == "Pierre" && typ2 == "Ciseaux") || (typ1 == "Ciseaux" && typ2 == "Feuille")) {
            bonus_typ = 0.2
        } else if ((typ1 == "Pierre" && typ2 == "Feuille") || (typ1 == "Ciseaux" && typ2 == "Pierre") || (typ1 == "Feuille" && typ2 == "Ciseaux")) {
            bonus_typ = -0.2
        }

      fight.current_pok_enemy.loss_PV(att.damage*(current_pok_enemy.statDef+bonus_typ)*current_pok_ally.statAtt)
      updateStatText()
      myTurn = false
    }
    else {
      messageTextLabel.setText("not enough pp")
    }

  }
  
  for (k<-0 until 4){
    attackButtonList(k).addActionListener(
      new ActionListener {
        def actionPerformed(e:ActionEvent) {
          attack_processing(k)
          updateActive()
        }
      }
    )
  }

  // Main menu
  
  def startGame (panels_to_activate:Array[JPanel]){
    val k = 0
    for (k<-0 until panels_to_activate.size){
      panels_to_activate(k).setVisible(true)
    }
    mainMenuPanel.setVisible(false)
  }

  val mainMenuPanel = new JPanel
  mainMenuPanel.setVisible(true)
  val main_menu_columns = Array(0.33, 0.06, 0.21, 0.06,0.33)
  val main_menu_rows = Array(0.33, 0.11, 0.11, 0.11, 0.33)
  val main_menu_table = Array(main_menu_columns, main_menu_rows)
  mainMenuPanel.setLayout(new TableLayout(main_menu_table))

  val startGameButton = new JButton("Start Game")
  val settingsButton = new JButton("Settings")
  val quitButton = new JButton("Quit")
  
  mainMenuPanel.add(startGameButton, "1, 1, 3, 1")
  startGameButton.setVisible(true)
  startGameButton.addActionListener(
    new ActionListener {
      def actionPerformed(e:ActionEvent) {
        startGame(Array(pokemonImgPanel2, sidePanel, actionMenuPanel))
      }
    }
  )

  mainMenuPanel.add(settingsButton, "2, 2, 2, 2")
  settingsButton.setVisible(true)
  mainMenuPanel.add(quitButton, "2, 3, 2, 3")
  quitButton.setVisible(true)


  val columns = Array(0.13, 0.87)
  val rows = Array(0.55, 0.45)
  val cells_size_mainFrame = Array(columns, rows)
  
  
  val mainFrame2 = new JFrame
  mainFrame2.setVisible(true)
  mainFrame2.setLayout(new TableLayout(cells_size_mainFrame))
  mainFrame2.setPreferredSize(new Dimension(1920, 1080))
  mainFrame2.pack()


  mainFrame2.add(mainMap, "1, 0, 1, 1")
  mainFrame2.add(mapSidePanel, "0, 0, 0, 1")
  
  mainFrame2.add(mainMenuPanel, "0, 0, 1, 1")

  mainFrame2.add(sidePanel, "0, 0, 0, 1")
 
  mainFrame2.add(pokemonImgPanel2, "1, 0, 1, 0")

  mainFrame2.add(actionMenuPanel, "1, 1, 1, 1")
  mainFrame2.add(attackMenuPanel, "1, 1, 1, 1")
  mainFrame2.add(teamMenuPanel, "1, 1, 1, 1")

  mainFrame2.add(endFightPanel, "0, 0, 1, 1")

  
  val fight_menu_panels = Array(pokemonImgPanel2, sidePanel, actionMenuPanel, attackMenuPanel, teamMenuPanel)
  val all_panels = Array(pokemonImgPanel2, sidePanel, actionMenuPanel, attackMenuPanel, teamMenuPanel, endFightPanel, mainMenuPanel, mainMap, mapSidePanel)
 
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
    
    // activates the main menu, deactivates all other panels, the menu system handles the ui from then on
    combatInterface.all_panels.foreach(_.setVisible(false))
    combatInterface.mainMenuPanel.setVisible(true)
    
    Thread.sleep(Int.MaxValue)
  }
  
}

