import sfml.graphics.*
import sfml.system.*


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
  val testPok1 = Pokemon("src/main/resources/pokemons/testPokemon.txt")
  val testPok2 = Pokemon("src/main/resources/pokemons/testPokemon.txt")
  val testPok3 = Pokemon("src/main/resources/pokemons/testPokemon.txt")
  val testPok4 = Pokemon("src/main/resources/pokemons/testPokemon.txt")
  val testPok5 = Pokemon("src/main/resources/pokemons/testPokemon.txt")
  val testPok6 = Pokemon("src/main/resources/pokemons/testPokemon.txt")
  var team = Array[Pokemon](testPok1, testPok2, testPok3, testPok4, testPok5, testPok6)
}

object testFight extends Fight(testTeam1, testTeam2) {}

object TestCombat extends CombatMenu(testFight) {}

class CombatMenu(fight: Fight) extends Menu {
  
  val font = Font("src/main/resources/fonts/Castforce.ttf")

  val mainPlayerPok = fight.current_pok_ally
  val mainEnemyPok  = fight.current_pok_enemy
  
  val playerPokImg = new Image(Texture(mainPlayerPok.pokSpritePath), (270 - 250/2, 100), (250, 250))
  val oppPokImg = new Image(Texture(mainEnemyPok.pokSpritePath), (1280 - (270 + 250/2), 100), (250, 250))
 
  val images = Array[GraphicObj](playerPokImg, oppPokImg)
  images.foreach(_.setVisible(true))


  def testOnClick() = {
    println("attack button clicked")
  }
  val attack_1 = new AttackButton(((1280-200)/2, 500), (200, 80), ButtonTextures.GenericMenu)
  val attack_2 = new AttackButton(((1280-200)/2, 600), (200, 80), ButtonTextures.GenericMenu)
  val attack_3 = new AttackButton(((1280-200)/2-220, 500), (200, 80), ButtonTextures.GenericMenu)
  val attack_4 = new AttackButton(((1280-200)/2-220, 600), (200, 80), ButtonTextures.GenericMenu)
  val attack_5 = new AttackButton(((1280-200)/2+220, 500), (200, 80), ButtonTextures.GenericMenu)
  val attack_6 = new AttackButton(((1280-200)/2+220, 600), (200, 80), ButtonTextures.GenericMenu)

  val atkButtons = Array[AttackButton](attack_1, attack_2, attack_3, attack_4, attack_5, attack_6)
  for (i <- 0 to 5){
    def atkOnClick() = {
      mainPlayerPok.attack_pok(i, mainEnemyPok)
    }
    atkButtons(i).setAtk(mainPlayerPok.atk_set(i))
    atkButtons(i).setOnClick(atkOnClick)
  }
  
  val buttons = Array[GraphicObj](attack_1, attack_2, attack_3, attack_4, attack_5, attack_6)
  val graphicObjects = buttons ++ images

  def getGraphicObjects() : Array[GraphicObj] = {
    return graphicObjects
  }
}

