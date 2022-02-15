import java.awt.event._



class Fight (team1:Team, team2:Team) {

    var current_pok_1 = team1.team(0)
    var current_pok_2 = team2.team(0)

    /* Permet de changer de Pokemon, pour l'instant possible seulement quand le pokemon en frontline meurt */
    def switch_pokemon (team:Team) : Unit = { // TODO used to return a bool but now returns Unit in order to fix typing issues

        // TODO affichage de l'équipe

        var new_pok = new Pokemon("poketruc") // TODO trouver une solution permanente pour nommer le pokemon
        var fail_switch = true

        while (fail_switch) {

            /*case event.ButtonClicked(pok0_team) => new_pok = team1.team(0)
            case event.ButtonClicked(pok1_team) => new_pok = team1.team(1)
            case event.ButtonClicked(pok2_team) => new_pok = team1.team(2)
            case event.ButtonClicked(pok3_team) => new_pok = team1.team(3)
            case event.ButtonClicked(pok4_team) => new_pok = team1.team(4)
            case event.ButtonClicked(pok5_team) => new_pok = team1.team(5)*/

            fail_switch = new_pok.alive

            if (!(fail_switch)) { 
                // TODO affichage message d'erreur "Le pokemon n'a plus de PV"
            }
        }

    }

    /* A lancer au moment où c'est au joueur d'attaquer */
    def attack_ally () {

        // TODO affichage set d'attaques

        var fail_attack = true
        var att = new Attack("fulguropoing") // temporary name

        while (fail_attack) {

            /*case event.ButtonClicked(attack0) => 
            att = current_pok_1.set_attack(0)

            case event.ButtonClicked(attack1) => 
            att = current_pok_1.set_attack(1)

            case event.ButtonClicked(attack2) => 
            att = current_pok_1.set_attack(2)

            case event.ButtonClicked(attack3) => 
            att = current_pok_1.set_attack(3)*/

            fail_attack = att.use_attack()
            if (fail_attack) {
                // TODO affichage message d'erreur "Cette attaque ne peut plus être utilisée"
            } else {
                current_pok_2.loss_PV(att.damage)
            }
        }
    }
    
    /* A lancer quand c'est au moment de l'ennemi d'attaquer */
    def attack_enemy () {
        val r = scala.util.Random
        var att = new Attack("turbo-défoncage") // temporary attack name

        var fail_attack = true

        while (fail_attack) {
            att = current_pok_2.set_attack(r.nextInt(4))
            fail_attack = att.use_attack()
            if (!(fail_attack)) { 
                current_pok_1.loss_PV(att.damage)
            }
        }
    }

class Fight_processing (team1:Team, team2:Team) {

    var fight_in_progress = Fight(team1,team2)

    def fight (team1:Team, team2:Team) : Boolean {
        // TODO déplacer les boutons ici 
        var step = 0 
        
        while (team_ally.team_alive() && team_enemy.team_alive()) {
            if (atkSelectionButton.isPressed()) {
                fight_in_progress.attack_ally()
            }
        }
    }
}
