class Attack (name:String) {
    var attackName:String = name
    var typ:String = "TODO"
    var damage:Int = -1

<<<<<<< HEAD
    var PP_max:Int = -1
    var PP:Int = -1
=======
    var PP_max:Int = 1

    var PP:Int = PP_max
    var PP_cost:Int = 1
>>>>>>> 34643e52d4542c1be6f263d4ac37c86214c23a76

    def use_attack () : Boolean = { 
        if (PP == 0) {
            false
        } else {
            PP -= 1
            true
        }
    }

    def restore_PP (restore:Int) = { 
        PP += restore 
        PP.min(PP_max) }

}

class Pokemon (name:String) {
    var pokemonName:String = name
    var lien:String = "TODO"
    var PVMax:Int = 0
    var typ:String = "TODO"

    var PV:Int = PVMax
    var alive : Boolean = true
    if (name == ""){
      alive = false
    } else {
      alive = true
    }

    var set_attack:Array[Attack] = new Array[Attack](4)

    def loss_PV (damage:Int) = { 
        PV -= damage
        PV = PV.max(0)
        if ( PV == 0 ) { alive = false } }

    def heal_PV (heal:Int) : Boolean = { 
        if (alive) { 
            PV += heal
            PV = PV.min(PVMax)
            true
        } else {
            false
        }
    }

    def ressurect (heal:Int) : Boolean = {
        if (alive) { 
            false
        } else {
            PV = heal
            alive = true
            true
        }
    }

}


class Team {
    var team:Array[Pokemon] = new Array[Pokemon](6)

    def team_alive () : Boolean = {
        var nb_alive = 0
        val i = 0
        for (i <- 0 to 5) {
            if (team(i).alive) {
                nb_alive += 1
            }
        }
        if (nb_alive != 0) {
            return true
        } else {
            return false 
        }
    }
    
}
