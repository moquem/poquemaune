import scala.io.Source


class PokTyp (typName: String, weakAgainst:Array[PokTyp], strongAgainst: Array[PokTyp]){
  val name = typName
  def isWeak (otherTyp :PokTyp):Boolean = {
    weakAgainst.contains(otherTyp)
  }
  def isStrong (otherTyp : PokTyp):Boolean = {
    strongAgainst.contains(otherTyp)
  }
}

class Attack(name: String) {
    var attackName: String = name
    var pokTyp = new PokTyp("TODO", Array(), Array())
    var base_damage: Int = -1
    val atkDescritpion: String = "Insert attack description here"
    var PP_max: Int = -1
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
      (base_damage*typ_bonus*atkPok.statAtt*defPok.statDef).toInt
    }
}

class Pokemon(pokName: String, spritePath: String, pokemonType: PokTyp) {
    // Info
    val pokemonName = pokName
    val lien = spritePath
    
    // Stats
    val pokTyp = pokemonType
    var maxHP: Int = 50
    var statAtt = 0.0
    var statDef = 0.0
    var maxPP = 0
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
       defPok.loss_PV(atk.damage_dealt(defPok, this)) 
      }
    }

    def increase_PP(pp_increase: Int) = {
        currPP = (currPP+pp_increase).min(maxPP)
    }

    def loss_PV(damage_dealt: Int) = {
        currHP = (currHP-damage_dealt).max(0)
        if (currHP == 0) { alive = false }
    }

    def heal_PV(heal: Int): Boolean = {
        if (alive){
            currHP = (currHP+heal).min(maxHP)
        }
        alive
    }

    def ressurect(heal: Int): Boolean = {
        if (!alive){
            currHP = heal
            alive = true
        }
        !alive
    }


// the pokemon info files must be given as the following :
// Name
// Path to Image
// HP
// Atk Stat
// Def Stat
//

// initialises a pokemon with the stats provided by the file given in the path

    def generatePokemon(path:String): Pokemon = {
        val pokFile = Source.fromFile(path)
        val pokInfo = pokFile.getLines.toArray
        val newPok = new Pokemon("", "", new PokTyp("", Array(), Array()))
        newPok.maxHP = pokInfo(2).toInt
        newPok.statAtt = pokInfo(3).toDouble
        newPok.statDef = pokInfo(4).toDouble
        pokFile.close()
        return newPok
    }
}
/*
class Aeroqueen extends Pokemon {
    override val pokemonName = "Aeroqueen"
    override val lien = "src/main/resources/sprite/Aeroqueen.png"
    PVmax = 100
    override val typ = "Pierre"
    statAtt = 0.8
    statDef = 0.5
}

class Alaslash extends Pokemon {
    override val pokemonName = "Alaslash"
    override val lien = "src/main/resources/sprite/Alaslash.png"
    PVmax = 70
    override val typ = "Ciseaux"
    statAtt = 1.3
    statDef = 0.9
}

class Arcaicate extends Pokemon {
    override val pokemonName = "Arcaicate"
    override val lien = "src/main/resources/sprite/Arcaicate.png"
    PVmax = 50
    override val typ = "Ciseaux"
    statAtt = 1.2
    statDef = 0.6
}

class Beeler extends Pokemon {
    override val pokemonName = "Beeler"
    override val lien = "src/main/resources/sprite/Beeler.png"
    PVmax = 90
    override val typ = "Pierre"
    statAtt = 1
    statDef = 0.5
}

class Bellwak extends Pokemon {
    override val pokemonName = "Bellwak"
    override val lien = "src/main/resources/sprite/Bellwak.png"
    PVmax = 70
    override val typ = "Feuille"
    statAtt = 1
    statDef = 0.7
}

class Butterplume extends Pokemon {
    override val pokemonName = "Arcaicate"
    override val lien = "src/main/resources/sprite/Butterplume.png"
    PVmax = 80
    override val typ = "Feuille"
    statAtt = 1.2
    statDef = 1
}

class Drowtres extends Pokemon {
    override val pokemonName = "Drowtres"
    override val lien = "src/main/resources/sprite/Drowtres.png"
    PVmax = 70
    override val typ = "Ciseaux"
    statAtt = 1.3
    statDef = 0.9
}

class Ekdash extends Pokemon {
    override val pokemonName = "Ekdash"
    override val lien = "src/main/resources/sprite/Ekdash.png"
    PVmax = 60
    override val typ = "Feuille"
    statAtt = 1.1
    statDef = 0.6
}

class Geofetchd extends Pokemon {
    override val pokemonName = "Geofetchd"
    override val lien = "src/main/resources/sprite/Geofetchd.png"
    PVmax = 80
    override val typ = "Pierre"
    statAtt = 1
    statDef = 0.7
}

class Golnair extends Pokemon {
    override val pokemonName = "Golnair"
    override val lien = "src/main/resources/sprite/Golnair.png"
    PVmax = 80
    override val typ = "Ciseaux"
    statAtt = 1.4
    statDef = 0.7
}

class Growtoise extends Pokemon {
    override val pokemonName = "Growtoise"
    override val lien = "src/main/resources/sprite/Growtoise.png"
    PVmax = 120
    override val typ = "Feuille"
    statAtt = 0.7
    statDef = 0.4
}

class Kadastar extends Pokemon {
    override val pokemonName = "Kadastar"
    override val lien = "src/main/resources/sprite/Kadastar.png"
    PVmax = 70
    override val typ = "Ciseaux"
    statAtt = 1
    statDef = 0.7
}

class Maglax extends Pokemon {
    override val pokemonName = "Maglax"
    override val lien = "src/main/resources/sprite/Maglax.png"
    PVmax = 90
    override val typ = "Pierre"
    statAtt = 1.1
    statDef = 0.6
}

class Nidolax extends Pokemon {
    override val pokemonName = "Nidolax"
    override val lien = "src/main/resources/sprite/Nidolax.png"
    PVmax = 100
    override val typ = "Feuille"
    statAtt = 1.2
    statDef = 0.7
}

class Omatle extends Pokemon {
    override val pokemonName = "Omatle"
    override val lien = "src/main/resources/sprite/Omatle.png"
    PVmax = 60
    override val typ = "Ciseaux"
    statAtt = 0.9
    statDef = 0.7
}

class Onlee extends Pokemon {
    override val pokemonName = "Onlee"
    override val lien = "src/main/resources/sprite/Onlee.png"
    PVmax = 80
    override val typ = "Pierre"
    statAtt = 1.2
    statDef = 0.8
}

class Paras extends Pokemon {
    override val pokemonName = "Paras"
    override val lien = "src/main/resources/sprite/Paras.png"
    PVmax = 100
    override val typ = "Feuille"
    statAtt = 1
    statDef = 0.6
}


class Perbok extends Pokemon {
    override val pokemonName = "Perbok"
    override val lien = "src/main/resources/sprite/Perbok.png"
    PVmax = 100
    override val typ = "Ciseaux"
    statAtt = 1
    statDef = 0.6
}

class Pikaysaur extends Pokemon {
    override val pokemonName = "Pikaysaur"
    override val lien = "src/main/resources/sprite/Pikaysaur.png"
    PVmax = 70
    override val typ = "Feuille"
    statAtt = 1.5
    statDef = 0.8
}

class Pinchamp extends Pokemon {
    override val pokemonName = "Pinchamp"
    override val lien = "src/main/resources/sprite/Pinchamp.png"
    PVmax = 110
    override val typ = "Pierre"
    statAtt = 1.2
    statDef = 0.8
}

class Ponyduck extends Pokemon {
    override val pokemonName = "Ponyduck"
    override val lien = "src/main/resources/sprite/Ponyduck.png"
    PVmax = 80
    override val typ = "Ciseaux"
    statAtt = 1
    statDef = 0.7
}

class Poryizard extends Pokemon {
    override val pokemonName = "Poryizard"
    override val lien = "src/main/resources/sprite/Poryizard.png"
    PVmax = 90
    override val typ = "Pierre"
    statAtt = 1
    statDef = 0.5
}

class Raicruel extends Pokemon {
    override val pokemonName = "Raicruel"
    override val lien = "src/main/resources/sprite/Raicruel.png"
    PVmax = 100
    override val typ = "Feuille"
    statAtt = 1.5
    statDef = 0.9
}

class Rhybat extends Pokemon {
    override val pokemonName = "Rhybat"
    override val lien = "src/main/resources/sprite/Rhybat.png"
    PVmax = 70
    override val typ = "Pierre"
    statAtt = 1.2
    statDef = 0.8
}

class Searino extends Pokemon {
    override val pokemonName = "Searino"
    override val lien = "src/main/resources/sprite/Searino.png"
    PVmax = 80
    override val typ = "Ciseaux"
    statAtt = 1.2
    statDef = 0.7
}

class Shelleel extends Pokemon {
    override val pokemonName = "Shelleel"
    override val lien = "src/main/resources/sprite/Shelleel.png"
    PVmax = 90
    override val typ = "Feuille"
    statAtt = 1
    statDef = 0.6
}*/

/*class Rhybat extends Pokemon {
    override val pokemonName = "Rhybat"
    override val lien = "src/main/resources/sprite/Rhybat.png"
    PVmax = 70
    override val typ = "Pierre"
    statAtt = 1.2
    statDef = 0.8
}*/
/*
class Tangmime extends Pokemon {
    override val pokemonName = "Tangmime"
    override val lien = "src/main/resources/sprite/Tangmime.png"
    PVmax = 80
    override val typ = "Ciseaux"
    statAtt = 1
    statDef = 0.6
}

class Tentanat extends Pokemon {
    override val pokemonName = "Tentanat"
    override val lien = "src/main/resources/sprite/Tentanat.png"
    PVmax = 60
    override val typ = "Feuille"
    statAtt = 1.1
    statDef = 0.7
}

class Venoysaur extends Pokemon {
    override val pokemonName = "Venoysaur"
    override val lien = "src/main/resources/sprite/Venoysaur.png"
    PVmax = 90
    override val typ = "Feuille"
    statAtt = 1.1
    statDef = 0.8
}

class Venufable extends Pokemon {
    override val pokemonName = "Venufable"
    override val lien = "src/main/resources/sprite/Venufable.png"
    PVmax = 140
    override val typ = "Feuille"
    statAtt = 1.2
    statDef = 0.7
}

class Voltrina extends Pokemon {
    override val pokemonName = "Voltrina"
    override val lien = "src/main/resources/sprite/Voltrina.png"
    PVmax = 70
    override val typ = "Feuille"
    statAtt = 1.2
    statDef = 0.7
}*/

class Empty extends Pokemon("", "", new PokTyp("", Array(), Array())) {
    // override val pokemonName = ""
    // override val lien = ""
    maxHP = 0
    // override val typ = "Feuille"
    statAtt = 0.0
    statDef = 0.0
}

class Team {
    var team: Array[Pokemon] = new Array[Pokemon](6)

    def team_alive(): Boolean = {
        !team.filter(_.alive).isEmpty
    } 
}
