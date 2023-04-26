package org.example.student.battleshipgame
import sun.jvm.hotspot.oops.CellTypeState.bottom
import sun.jvm.hotspot.oops.CellTypeState.top
import uk.ac.bournemouth.ap.battleshiplogic.StudentShip
import uk.ac.bournemouth.ap.battleshiplib.*
import uk.ac.bournemouth.ap.battleshiplib.test.BattleshipTest
import uk.ac.bournemouth.ap.battleshiplogic.MyBattleShipOpponent
import uk.ac.bournemouth.ap.battleshiplogic.StudentBattleShipGrid
import uk.ac.bournemouth.ap.lib.matrix.boolean.BooleanMatrix
import kotlin.random.Random

class StudentBattleshipTest : BattleshipTest</*YourShipType*/StudentShip>() {
    override fun createOpponent(
        columns: Int,
        rows: Int,
        ships: List</*YourShipType*/StudentShip>
    ): /*YourOpponentType*/MyBattleShipOpponent{
        return MyBattleShipOpponent(columns, rows, ships)
    }

    override fun transformShip(sourceShip: Ship): /*YourShipType*/StudentShip {
        return StudentShip(sourceShip.top, sourceShip.left, sourceShip.bottom, sourceShip.right)
    }

    override fun createOpponent(
        columns: Int,
        rows: Int,
        shipSizes: IntArray,
        random: Random
    ): /* TODO YourOpponentType*/MyBattleShipOpponent {
        // Note that the passing of random allows for repeatable testing
        return MyBattleShipOpponent(rows, columns,shipSizes,random)
    }

    override fun createGrid(
        grid: BooleanMatrix,
        opponent: BattleshipOpponent
    ): /* TODO YourGridType*/BattleshipGrid {
        // If the opponent is not a StudentBattleshipOpponent, create it based upon the passed in data
        val studentOpponent =
            opponent as? /*YourOpponentType*/BattleshipOpponent
                ?: createOpponent(opponent.columns, opponent.rows, opponent.ships.map { it as? /*YourShipType*/StudentShip ?: transformShip(it) })

        return TODO("Create a new grid instance with the opponent. E.g. <YourGridTypeBattleshipGrid>(studentOpponent)")
    }
}

