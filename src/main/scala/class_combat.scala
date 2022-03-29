import java.awt.event._



class Fight (team1:Team, team2:Team) {
  
    var team_player = new Team 
    team_player = team1
    var team_opp = new Team
    team_opp = team2

    var current_pok_ally = team1.team(0)
    var current_pok_enemy = team2.team(0)

    var currentActionPoints = 1

    /* Permet de changer de Pokemon, pour l'instant possible seulement quand le pokemon en frontline meurt */

    
    /* A lancer quand c'est au moment de l'ennemi d'attaquer */
    def attack_enemy ():Int = {
        val r = scala.util.Random

        var fail_attack = true
        var nb_attack:Int = 0
        var att:Attack = new Attack("")
        var bonus_typ:Double = 0.0

        var typ1:String = current_pok_ally.typ
        var typ2:String = current_pok_enemy.typ

        if ((typ1 == "Feuille" && typ2 == "Pierre") || (typ1 == "Pierre" && typ2 == "Ciseaux") || (typ1 == "Ciseaux" && typ2 == "Feuille")) {
            bonus_typ = 0.2
        } else if ((typ1 == "Pierre" && typ2 == "Feuille") || (typ1 == "Ciseaux" && typ2 == "Pierre") || (typ1 == "Feuille" && typ2 == "Ciseaux")) {
            bonus_typ = -0.2
        }

        while (fail_attack) {
            nb_attack = r.nextInt(4)
            att = current_pok_enemy.set_attack(nb_attack)
            fail_attack = att.use_attack()
            if (!(fail_attack)) { 
                current_pok_ally.loss_PV((att.damage*(current_pok_ally.statDef+bonus_typ)*current_pok_enemy.statAtt).toInt)
            }
        }
        nb_attack
    }

    def new_pok_enemy () {
        val r = scala.util.Random

        var dead_pok = !current_pok_enemy.alive
        var nb_pok:Int = 0

        while (dead_pok) {
            nb_pok = r.nextInt(6)
            if (team2.team(nb_pok).alive) {
                current_pok_enemy = team2.team(nb_pok)
                dead_pok = false
            }
        }
  }

    
} 
