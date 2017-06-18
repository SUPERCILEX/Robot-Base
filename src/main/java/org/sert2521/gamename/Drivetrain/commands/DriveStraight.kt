package org.sert2521.gamename.Drivetrain.commands

import org.strongback.command.Command
import org.strongback.drive.TankDrive

/**
 * This command will drive at a constant speed for a set duration of time.
 */
class DriveStraight(val drive: TankDrive, val speed: Double, duration: Double) : Command(duration, drive) {
    override fun execute(): Boolean {
        this.drive.tank(speed, speed)
        return false
    }

    override fun interrupted() {
        this.drive.stop()
    }

    override fun end() {
        this.drive.stop()
    }
}
