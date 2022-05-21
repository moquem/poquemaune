import java.awt.event._

class Fight(team1: Team, team2: Team) {

    var team_player = team1
    var team_opp = team2

    var current_pok_ally = team1.team(0)
    var current_pok_enemy = team2.team(0)

    var currentActionPoints = 1

  
    def endPlayerTurn() = {
      println("ended player turn")
      println("enemy attacks")
      // create some AI for this part
      val randInt = scala.util.Random.nextInt(6)
      current_pok_enemy.attack_pok(randInt, current_pok_ally)
      current_pok_ally.resetAttack()
    }

    def new_pok_enemy(): Unit = {
        val r = scala.util.Random

        var dead_pok = !current_pok_enemy.alive
        var nb_pok: Int = 0

        while (dead_pok) {
            nb_pok = r.nextInt(6)
            if (team2.team(nb_pok).alive) {
                current_pok_enemy = team2.team(nb_pok)
                dead_pok = false
            }
        }
    }

}
