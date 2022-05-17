import sfml.graphics.*
import sfml.system.*
import sfml.window.*

class AttackButton(pos: (Int, Int), size: (Int, Int), texture: ButtonTextures) extends Button(texture, pos, size) {
  val font = Font("src/main/resources/fonts/Castforce.ttf")
  def setAtk(atk: Attack) = {
    this.setText(atk.attackName, 35, font)
  }
  this.setActive(true)
  this.setVisible(true)
}

object testTeam1 extends Team {
  val testPok1 = Pokemon("src/main/resources/pokemons/testPokemon.txt")
  val testPok2 = Pokemon("src/main/resources/pokemons/testPokemon.txt")
  val testPok3 = Pokemon("src/main/resources/pokemons/testPokemon.txt")
  val testPok4 = Pokemon("src/main/resources/pokemons/testPokemon.txt")
  val testPok5 = Pokemon("src/main/resources/pokemons/testPokemon.txt")
  val testPok6 = Pokemon("src/main/resources/pokemons/testPokemon.txt")
  var team = Array[Pokemon](testPok1, testPok2, testPok3, testPok4, testPok5, testPok6)
}
object testTeam2 extends Team {
  val testPok1 = Pokemon("src/main/resources/pokemons/bestPokemon.txt")
  val testPok2 = Pokemon("src/main/resources/pokemons/bestPokemon.txt")
  val testPok3 = Pokemon("src/main/resources/pokemons/bestPokemon.txt")
  val testPok4 = Pokemon("src/main/resources/pokemons/bestPokemon.txt")
  val testPok5 = Pokemon("src/main/resources/pokemons/bestPokemon.txt")
  val testPok6 = Pokemon("src/main/resources/pokemons/bestPokemon.txt")
  var team = Array[Pokemon](testPok1, testPok2, testPok3, testPok4, testPok5, testPok6)
}

object testFight extends Fight(testTeam1, testTeam2) {}

object TestCombat extends CombatMenu(testFight) {}


trait FrameUpdateable () {
  def frameUpdate(): Unit
}


class CombatMenu(fight: Fight) extends Menu, FrameUpdateable {
  
  val font = Font("src/main/resources/fonts/Castforce.ttf")

  val mainPlayerPok = fight.current_pok_ally
  val mainEnemyPok  = fight.current_pok_enemy
  
  val playerPokImg = new Image(Texture(mainPlayerPok.pokSpritePath), (270 - 250/2, 100), (250, 250))
  val oppPokImg = new Image(Texture(mainEnemyPok.pokSpritePath), (1280 - (270 + 250/2), 100), (250, 250))
 
  val images = Array[GraphicObj](playerPokImg, oppPokImg)
  images.foreach(_.setVisible(true))

 
  val barBorderTexture = Texture("src/main/resources/ui_sprites/bar_border.png")
  val healthBarInsideTexture = Texture("src/main/resources/ui_sprites/health_bar_inside.png")
  val ppBarInsideTexture = Texture("src/main/resources/ui_sprites/pp_bar_inside.png")
  val barBorderOffset = (2.0/50.0, 2.0/50.0)
  val allyHealthBar = new DisplayBar(mainPlayerPok.maxHP, barBorderTexture, barBorderOffset, healthBarInsideTexture, (270 - 300/2, 10), (300, 30))
  val enemyHealthBar = new DisplayBar(mainEnemyPok.maxHP, barBorderTexture, barBorderOffset, healthBarInsideTexture, (1280 - 270 - 300/2, 10), (300, 30))
  val allyPPBar = new DisplayBar(mainPlayerPok.maxPP, barBorderTexture, barBorderOffset, ppBarInsideTexture, (270 - 300/2, 50), (300, 30))
  val enemyPPBar = new DisplayBar(mainEnemyPok.maxPP, barBorderTexture, barBorderOffset, ppBarInsideTexture, (1280 - 270 - 300/2, 50), (300, 30))


  val attack_1 = new AttackButton(((1280-200)/2, 500), (200, 80), ButtonTextures.GenericMenu)
  val attack_2 = new AttackButton(((1280-200)/2, 600), (200, 80), ButtonTextures.GenericMenu)
  val attack_3 = new AttackButton(((1280-200)/2-220, 500), (200, 80), ButtonTextures.GenericMenu)
  val attack_4 = new AttackButton(((1280-200)/2-220, 600), (200, 80), ButtonTextures.GenericMenu)
  val attack_5 = new AttackButton(((1280-200)/2+220, 500), (200, 80), ButtonTextures.GenericMenu)
  val attack_6 = new AttackButton(((1280-200)/2+220, 600), (200, 80), ButtonTextures.GenericMenu)
  
  val mapButton = new Button(ButtonTextures.GenericMenu, (1280 - 200 - 80, 600), (200, 80))
  mapButton.setText("Map", 63, font)
  def mapOnClick() = {
    MainMenu.setActive(false)
    TestCombat.setActive(false)
    MapMenu.setActive(true)
  }
  mapButton.setOnClick(mapOnClick)
  mapButton.setActive(true)
  mapButton.setVisible(true)

  
  val passTurnButton = new Button(ButtonTextures.GenericMenu, (80, 600), (200, 80))
  passTurnButton.setText("End Turn", 70, font)
  def passTurnOnClick() = {
    println("ended turn")
    fight.endPlayerTurn()
  }
  passTurnButton.setOnClick(passTurnOnClick)
  passTurnButton.setActive(true)
  passTurnButton.setVisible(true)


  val atkButtons = Array[AttackButton](attack_1, attack_2, attack_3, attack_4, attack_5, attack_6)
  for (i <- 0 to 5){
    def atkOnClick() = {
      mainPlayerPok.attack_pok(i, mainEnemyPok)
      allyPPBar.setVal(mainPlayerPok.currPP)
      enemyHealthBar.setVal(mainEnemyPok.currHP)
    }
    atkButtons(i).setAtk(mainPlayerPok.atk_set(i))
    atkButtons(i).setOnClick(atkOnClick)
  }
  
  val attackButtons = Array[Button](attack_1, attack_2, attack_3, attack_4, attack_5, attack_6)
  val buttons = Array[GraphicObj](attack_1, attack_2, attack_3, attack_4, attack_5, attack_6, mapButton, passTurnButton)
  val graphicObjects = buttons ++ images ++ Array[GraphicObj](allyHealthBar, allyPPBar, enemyHealthBar, enemyPPBar)
  
  def frameUpdate() = {
    for (i<- 0 to 5) {
      if (mainPlayerPok.can_attack(i)) {
        attackButtons(i).setActive(true)
      }
      else {
        attackButtons(i).setActive(false)
      }
    }
  }

  def getGraphicObjects() : Array[GraphicObj] = {
    
    return graphicObjects
  }

  def getFrameUpdateable() : Array[FrameUpdateable] = {
    return Array[FrameUpdateable](this)
  }
}

