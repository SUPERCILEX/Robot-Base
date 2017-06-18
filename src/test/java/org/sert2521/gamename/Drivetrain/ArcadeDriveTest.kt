package org.sert2521.gamename.Drivetrain

import org.junit.Before
import org.junit.Test
import org.sert2521.gamename.Drivetrain.commands.ArcadeDrive
import org.strongback.command.CommandTester
import org.strongback.components.Motor
import org.strongback.components.Motor.compose
import org.strongback.drive.TankDrive
import org.strongback.mock.Mock

/**
 * Created by AMD on 6/17/17.
 */
class ArcadeDriveTest() {
    private lateinit var frontLeft: Motor
    private lateinit var frontRight: Motor
    private lateinit var rearLeft: Motor
    private lateinit var rearRight: Motor

    private lateinit var left: Motor
    private lateinit var right: Motor

    private lateinit var motors: List<Motor>

    private lateinit var drive: TankDrive

    @Before
    fun beforeEach() {
        frontLeft = Mock.stoppedMotor()
        frontRight = Mock.stoppedMotor()
        rearLeft = Mock.stoppedMotor()
        rearRight = Mock.stoppedMotor()

        left = compose(frontLeft, rearLeft)
        right = compose(frontRight, rearRight)

        motors = listOf(left, right)

        drive = TankDrive(left, right)
    }

    @Test
    fun shouldDriveForward() {
        val pitch = ContinuousRangeTester()
        val roll = ContinuousRangeTester()

        val tester = CommandTester(ArcadeDrive(drive, pitch.continuous, roll.continuous))

        var speed: Double

        // Make sure the motors are not moving when the test begins

        speed = 0.0
        pitch.value = speed

        tester.step(1)

        assertMotorSpeeds(speed, motors, true)

        // Make sure the motors correctly begin to move

        speed = 0.5
        pitch.value = speed

        tester.step(1)

        assertMotorSpeeds(speed, motors, true)

        // Make sure the motors correctly change speeds

        speed = 0.25
        pitch.value = speed

        tester.step(1)

        assertMotorSpeeds(speed, motors, true)
    }

    @Test
    fun shouldDriveForwardAndStopWhenCanceled() {
        val pitch = ContinuousRangeTester()
        val roll = ContinuousRangeTester()

        val tester = CommandTester(ArcadeDrive(drive, pitch.continuous, roll.continuous))

        var speed: Double

        // Make sure the motors are not moving when the test begins

        speed = 0.0
        pitch.value = speed

        tester.step(1)

        assertMotorSpeeds(speed, motors, true)

        // Make sure the motors correctly begin to move

        speed = 0.5
        pitch.value = speed

        tester.step(1)

        assertMotorSpeeds(speed, motors, true)

        // Make sure the motors stop when canceled

        tester.cancel()
        tester.step(1)

        assertMotorSpeeds(0.0, motors, true)
    }
}
