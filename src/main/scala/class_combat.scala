import java.awt.event._



class Fight (team1:Team, team2:Team) {
  
    var team_1 = new Team 
    team_1 = team1
    var team_2 = new Team
    team_2 = team2

    var current_pok_ally = team1.team(0)
    var current_pok_enemy = team2.team(0)

    /* Permet de changer de Pokemon, pour l'instant possible seulement quand le pokemon en frontline meurt */

    /* A lancer au moment où c'est au joueur d'attaquer */
    def attack_ally (nb_attack:Int) {

        // TODO affichage set d'attaques

        var fail_attack = true
        var att = current_pok_ally.set_attack(nb_attack)

        while (fail_attack) {

            fail_attack = att.use_attack()
            if (fail_attack) {
                // TODO affichage message d'erreur "Cette attaque ne peut plus être utilisée"
            } else {
                current_pok_enemy.loss_PV(att.damage)
            }
        }
    }
    
    /* A lancer quand c'est au moment de l'ennemi d'attaquer */
    def attack_enemy ():Int = {
        val r = scala.util.Random

        var fail_attack = true
        var nb_attack:Int = 0
        var att:Attack = new Attack("")

        while (fail_attack) {
            nb_attack = r.nextInt(4)
            att = current_pok_enemy.set_attack(nb_attack)
            fail_attack = att.use_attack()
            if (!(fail_attack)) { 
                current_pok_ally.loss_PV(att.damage)
                nb_attack
            }
        }
    }

    
} 
