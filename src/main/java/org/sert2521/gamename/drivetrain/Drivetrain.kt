package org.sert2521.gamename.drivetrain

import edu.wpi.first.wpilibj.command.Subsystem
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import org.sert2521.gamename.util.LEFT_FRONT_MOTOR
import org.sert2521.gamename.util.LEFT_REAR_MOTOR
import org.sert2521.gamename.util.RIGHT_FRONT_MOTOR
import org.sert2521.gamename.util.RIGHT_REAR_MOTOR
import org.sert2521.gamename.util.leftJoystick
import org.sertain.RobotLifecycle
import org.sertain.hardware.Talon
import org.sertain.hardware.autoBreak
import org.sertain.hardware.inverted
import org.sertain.hardware.plus
import org.sertain.hardware.resetEncoder

object Drivetrain : Subsystem(), RobotLifecycle {
    private val frontLeft = Talon(LEFT_FRONT_MOTOR) + Talon(LEFT_REAR_MOTOR)
    private val frontRight =
            Talon(RIGHT_FRONT_MOTOR).inverted() + Talon(RIGHT_REAR_MOTOR).inverted()

    private val drive = DifferentialDrive(frontLeft, frontRight)

    init {
        RobotLifecycle.addListener(this)
        frontLeft.autoBreak()
        frontRight.autoBreak()
    }

    override fun onStart() {
        stop()

        frontLeft.resetEncoder()
        frontRight.resetEncoder()
    }

    fun arcade() {
        drive.arcadeDrive(leftJoystick.x, -leftJoystick.y)
    }

    fun stop() {
        drive.stopMotor()
    }

    override fun initDefaultCommand() {
        ArcadeDrive().start()
    }
}
