class Attack (name:String) {
    var typ:String = "TODO"
    var damage:Int = -1

    var PP_max:Int = -1

    var PP_cost:Int = PP_max

    def use_attack () : Boolean = { 
        if (PP_cost == 0) {
            false
        } else {
            PP_cost -= 1
            true
        }
    }

    def restore_PP (restore:Int) = { 
        PP_cost += restore 
        PP_cost.min(PP_max) }

}

class Pokemon (name:String) {
    var lien:String = "TODO"
    var PVMax:Int = 0
    var typ:String = "TODO"

    var PV:Int = PVMax
    var alive = true

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
