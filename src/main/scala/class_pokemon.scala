import scala.io.Source
import reflect.Selectable.reflectiveSelectable


enum PokTyp (typName: String, weakAgainst:Array[PokTyp], strongAgainst: Array[PokTyp]){
  def isWeak (otherTyp :PokTyp):Boolean = {
    weakAgainst.contains(otherTyp)
  }
  def isStrong (otherTyp : PokTyp):Boolean = {
    strongAgainst.contains(otherTyp)
  }
  case Empty extends PokTyp("", Array(), Array())
}


class Attack(name: String, atk_damage: Int, atk_description: String) {
    var attackName: String = name
    var pokTyp = PokTyp.Empty
    var base_damage: Int = -1
    val atkDescritpion: String = "Insert attack description here"
    var PP: Int = -1

    var PP_cost: Int = 1
    
    def damage_dealt (defPok:Pokemon, atkPok:Pokemon) : Int = {
      var typ_bonus = 1.0
      
      // bonus damage if the pokemon is the same type as the attack
      if (this.pokTyp == atkPok.pokTyp){
        typ_bonus += 0.2
      }
      // additional damage dealt (or removed) based on typ relations (strong/weak against)
      if (defPok.pokTyp.isWeak(this.pokTyp)){
        typ_bonus += 0.2
      }
      if (defPok.pokTyp.isWeak(atkPok.pokTyp)){
        typ_bonus += 0.2
      }
      if (defPok.pokTyp.isStrong(pokTyp)){
        typ_bonus -= 0.2
      }
      
      // more modifiers based on the atk and def stats for the pokemon 
      (base_damage*typ_bonus*atkPok.statAtt/defPok.statDef).toInt
    }
}

type PokInfo = {
              val pokName:String
              val pokTyp:PokTyp
              val maxHP:Int
              val maxPP:Int
              // the default atk and defense stats of the pokemon
              val statAtk:Double
              val statDef:Double}

class Pokemon private (spritePath: String, pokInfo: PokInfo) {
    // Info
    val pokemonName = pokInfo.pokName
    val lien = spritePath
    
    // Stats
    val pokTyp = pokInfo.pokTyp
    var maxHP: Int = pokInfo.maxHP
    var statAtt = pokInfo.statAtk
    var statDef = pokInfo.statDef
    var maxPP = pokInfo.maxPP

    // These change
    var currPP = maxPP
    var currHP: Int = maxHP

    // Bools and state
    var hasAttacked : Boolean = false
    var alive: Boolean = true
    alive  = pokemonName != ""

    var atk_set: Array[Attack] = new Array[Attack](4)
   
    def can_attack (atk_nb:Int) : Boolean = {
      currPP >= atk_set(atk_nb).PP_cost && !hasAttacked
    }

    def attack_pok(atk_nb:Int, defPok:Pokemon) = {
      val atk = atk_set(atk_nb)
      if (!can_attack(atk_nb)){
        println("you cannot attack")
      }
      else {
       defPok.decrease_HP(atk.damage_dealt(defPok, this)) 
      }
    }

    def increase_PP(pp_increase: Int) = {
        currPP = (currPP+pp_increase).min(maxPP)
    }

    def decrease_HP(damage_dealt: Int) = {
        currHP = (currHP-damage_dealt).max(0)
        if (currHP == 0) { alive = false }
    }

    def heal_HP(heal: Int) = {
        if (alive){
            currHP = (currHP+heal).min(maxHP)
        }
    }

    def ressurect(heal: Int) = {
        if (!alive){
            currHP = heal
            alive = true
        }
    }


// the pokemon info files must be given as the following :
// Name
// Type
// maxHP
// maxPP
// Atk Stat
// Def Stat
// Path to Image
//

}

object Pokemon {
    
  // initialises a pokemon with the stats provided by the file given in the path
    def apply(path:String): Option[Pokemon] = {
        val pokFile = Source.fromFile(path)
        val pokInfoArr = pokFile.getLines.toArray
        val pokName = pokInfoArr(0)
        val pokTyp = PokTyp.valueOf(pokInfoArr(1))
        val maxHP = pokInfoArr(2).toInt
        val maxPP = pokInfoArr(3).toInt
        val atkStat = pokInfoArr(4).toDouble
        val defStat = pokInfoArr(5).toDouble
        val pokInfo = (pokName, pokTyp, maxHP, maxPP, atkStat, defStat).asInstanceOf[PokInfo]
        val newPok = new Pokemon(pokInfoArr(6), pokInfo)
        pokFile.close()
        return Some(newPok)
    }
}


class Team {
    var team: Array[Pokemon] = new Array[Pokemon](6)

    def team_alive(): Boolean = {
        !team.filter(_.alive).isEmpty
    } 
}
