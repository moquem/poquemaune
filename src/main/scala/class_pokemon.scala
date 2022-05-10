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
    var currPP: Int = maxPP
    var currHP: Int = maxHP
    var pokExp: Int = 0
    var pokLvl: Int = 1

    // Allow the improvement of the competences. 
    var nbPoint : Int = 0 

    // Bools and state
    var alive: Boolean = true
    var hasAttacked = false
    alive  = pokemonName != ""

    var atk_set: Array[Attack] = new Array[Attack](4)
   
    def can_attack (atk_nb:Int) : Boolean = {
      currPP >= atk_set(atk_nb).cost_PP && !hasAttacked
    }

    def attack_pok(atk_nb:Int, defPok:Pokemon) = {
      val atk = atk_set(atk_nb)
      if (!can_attack(atk_nb)){
        println("you cannot attack")
      }
      else {
       defPok.decrease_HP(atk.damage_dealt(defPok, this))
       currPP = (currPP - atk.cost_PP).max(0)
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

    def new_point(level: Int) = {
        if (level%5==0) {
            nbPoint += 2
        } else {
            nbPoint += 1
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

trait Buff (name: String,
            costPP: Int,
            description: String,
            buffMult: Double,
            buffAdd: Double,
            val target: BuffTarget)
extends AffectPok {
    // applies the buff (or debuff) to the pokemon passed as an argument
    // note that the target is a single pokemon and doesn't correspond to
    // the BuffTarget
    def applyBuffToPok (target: Pokemon): Unit
}



trait AffectPok (val name:String, 
                 val costPP: Int,
                 val description: String){

}

sealed trait BuffTarget
case object Self extends BuffTarget
case object Opponent extends BuffTarget
case object SelfTeam extends BuffTarget
case object OppTeam extends BuffTarget


class Attack (name:String,
              costPP: Int,
              description: String,
              atk_damage: Int,
              atk_typ: PokTyp,
              // all the buffs and debuffs the attack will incur
              atk_effects: Array[Buff])
extends AffectPok (name, costPP, description) {
    var attackName: String = name
    var pokTyp = atk_typ
    var base_damage: Int = atk_damage
    val atkDescritpion: String = description

    var cost_PP: Int = costPP
    
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
      if (defPok.pokTyp.isStrong(atkPok.pokTyp)){
        typ_bonus -= 0.2
      }
     
      // if for some reason the typ_bonus becomes negative (eg : stacking debufs)
      typ_bonus = typ_bonus.max(0)
      // more modifiers based on the atk and def stats for the pokemon 
      (base_damage*typ_bonus*atkPok.statAtt*defPok.statDef).toInt
    }
    
    def applyBuff (buff: Buff, defTeam: Array[Pokemon], atkTeam: Array[Pokemon], defPok: Pokemon, atkPok: Pokemon) = {
      buff.target match {
        case Self => buff.applyBuffToPok(atkPok)
        case Opponent => buff.applyBuffToPok(defPok)
        // TODO test the foreach
        case SelfTeam => atkTeam.foreach(buff.applyBuffToPok(_))
        case OppTeam => defTeam.foreach(buff.applyBuffToPok(_))
      }
    }
    

    def applyBuffs (defTeam: Array[Pokemon], atkTeam: Array[Pokemon], defPok: Pokemon, atkPok: Pokemon) = {
      atk_effects.foreach(applyBuff(_, defTeam, atkTeam, defPok, atkPok)) 
    }
}

class DebuffAtk (name: String,
                costPP: Int,
                description: String,
                buffMult: Double,
                buffAdd: Double,
                target: BuffTarget)
extends Buff (name, costPP, description, buffMult, buffAdd, target)
with AffectPok (name, costPP, description){
  
    def applyBuffToPok (target: Pokemon) : Unit = {
      target.statAtt = (target.statAtt + buffAdd) * buffMult
    }
}

class BuffAtk (name: String,
                costPP: Int,
                description: String,
                buffMult: Double,
                buffAdd: Double,
                target: BuffTarget)
extends Buff (name, costPP, description, buffMult, buffAdd, target)
with AffectPok (name, costPP, description){
    def applyBuffToPok (target: Pokemon) : Unit = {
      println("nvm for now")
    }
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

