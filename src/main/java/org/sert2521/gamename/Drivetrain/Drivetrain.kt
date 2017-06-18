package org.sert2521.gamename.Drivetrain

import edu.wpi.first.wpilibj.command.Command
import edu.wpi.first.wpilibj.command.Subsystem
import org.sert2521.gamename.FRONT_LEFT_MOTOR
import org.sert2521.gamename.FRONT_RIGHT_MOTOR
import org.sert2521.gamename.OI
import org.sert2521.gamename.REAR_LEFT_MOTOR
import org.sert2521.gamename.REAR_RIGHT_MOTOR
import org.strongback.components.Motor
import org.strongback.drive.TankDrive
import org.strongback.hardware.Hardware

/**
 * This is the Drivetrain subsystem, which is in charge of controlling the speeds of the individual
 * talons and converting joystick input to movement speeds.
 */
object Drivetrain : Subsystem() {
    private val frontLeft = Hardware.Motors.talon(FRONT_LEFT_MOTOR)
    private val frontRight = Hardware.Motors.talon(FRONT_RIGHT_MOTOR)
    private val rearLeft = Hardware.Motors.talon(REAR_LEFT_MOTOR)
    private val rearRight = Hardware.Motors.talon(REAR_RIGHT_MOTOR)

    private val left = Motor.compose(frontLeft, rearLeft)
    private val right = Motor.compose(frontRight, rearRight)

    private val drive: TankDrive

    init {
        drive = TankDrive(left, right)
    }

    fun teleoperatedDrive() {
        val pitch = OI.left.pitch.read()
        val roll = OI.left.roll.read()

        drive.arcade(pitch, roll)
    }

    override fun initDefaultCommand() {
        defaultCommand = object : Command() {
            init {
                requires(this@Drivetrain)
            }

            override fun execute() = teleoperatedDrive()

            override fun isFinished() = false
        }
    }
}
