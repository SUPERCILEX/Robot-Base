package org.sert2521.gamename.Drivetrain

import org.fest.assertions.Assertions.assertThat
import org.fest.assertions.Delta
import org.junit.Before
import org.junit.Test
import org.sert2521.gamename.Drivetrain.commands.DriveStraight
import org.strongback.command.CommandTester
import org.strongback.components.Motor
import org.strongback.components.Motor.compose
import org.strongback.drive.TankDrive
import org.strongback.mock.Mock

/**
 * Created by AMD on 6/17/17.
 */
class DriveStraightTest() {
    private val TOLERANCE = Delta.delta(0.001)
    private val START_TIME_MS: Long = 1000

    private var frontLeft: Motor = Mock.stoppedMotor()
    private var frontRight: Motor = Mock.stoppedMotor()
    private var rearLeft: Motor = Mock.stoppedMotor()
    private var rearRight: Motor = Mock.stoppedMotor()

    private var left: Motor = compose(frontLeft, rearLeft)
    private var right: Motor = compose(frontRight, rearRight)

    private var drive: TankDrive = TankDrive(left, right)

    @Before
    fun beforeEach() {
        frontLeft = Mock.stoppedMotor()
        frontRight = Mock.stoppedMotor()
        rearLeft = Mock.stoppedMotor()
        rearRight = Mock.stoppedMotor()

        left = compose(frontLeft, rearLeft)
        right = compose(frontRight, rearRight)

        drive = TankDrive(left, right)
    }

    @Test
    fun shouldDriveForwardAndStopAfterDuration() {
        val tester = CommandTester(DriveStraight(drive, 0.5, 5.0))

        assertThat(left.speed).isEqualTo(0.0, TOLERANCE)
        assertThat(right.speed).isEqualTo(0.0, TOLERANCE)

        tester.step(START_TIME_MS)

        tester.step(START_TIME_MS + 2000)

        assertThat(left.speed).isEqualTo(0.5, TOLERANCE)
        assertThat(right.speed).isEqualTo(0.5, TOLERANCE)

        tester.step(START_TIME_MS + 5000)

        assertThat(left.speed).isEqualTo(0.0, TOLERANCE)
        assertThat(right.speed).isEqualTo(0.0, TOLERANCE)
    }

    @Test
    fun shouldDriveForwardAndStopAfterCanceled() {
        val tester = CommandTester(DriveStraight(drive, 0.5, 5.0))

        assertThat(left.speed).isEqualTo(0.0, TOLERANCE)
        assertThat(right.speed).isEqualTo(0.0, TOLERANCE)

        tester.step(START_TIME_MS)

        tester.step(START_TIME_MS + 1999)

        assertThat(left.speed).isEqualTo(0.5, TOLERANCE)
        assertThat(right.speed).isEqualTo(0.5, TOLERANCE)

        tester.cancel()
        tester.step(START_TIME_MS + 1)

        assertThat(left.speed).isEqualTo(0.0, TOLERANCE)
        assertThat(right.speed).isEqualTo(0.0, TOLERANCE)
    }
}