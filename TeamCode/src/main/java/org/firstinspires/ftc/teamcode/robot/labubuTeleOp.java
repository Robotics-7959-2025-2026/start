/*
TLDR:

Left Stick is mechina drive.
Right Stick rotates the robot
Left bumper spins the shooter motor to shoot the john
Right bumper rotates the servo that rotates the platform that lifts the john into position to be shot

 */
package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "mine")
public class labubuTeleOp extends LinearOpMode {
    private DcMotor lfMotor = null;
    private DcMotor rfMotor = null;
    private DcMotor lbMotor = null;
    private DcMotor rbMotor = null;

    private DcMotor shooterMotor = null;
    private Servo shooterServo = null;

    private double ctrlPow = 1.0;

    public void runOpMode() {

        // Defines motors to the hardwareMap (in order words, the driver station)
        lfMotor = hardwareMap.get(DcMotor.class, "front_left_drive");
        rfMotor = hardwareMap.get(DcMotor.class, "front_right_drive");
        rbMotor = hardwareMap.get(DcMotor.class, "back_left_drive");
        lbMotor = hardwareMap.get(DcMotor.class, "back_right_drive");

        // Sets the directions of the motors for the driving.
        lfMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rfMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        lbMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rbMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        shooterMotor = hardwareMap.get(DcMotor.class, "shooter_motor");
        shooterServo = hardwareMap.get(Servo.class, "shooter_servo");

        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        shooterServo.setDirection(Servo.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.update();

            if (Math.abs(gamepad1.left_stick_x) + Math.abs(gamepad1.left_stick_y) < 0.1) {
                continue;
            }

            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x * 0.85;

            lfMotor.setPower(Math.pow(y + x + rx, ctrlPow) * Math.signum(y + x + rx));
            rfMotor.setPower(Math.pow(y - x - rx, ctrlPow) * Math.signum(y - x - rx));
            lbMotor.setPower(Math.pow(y - x + rx, ctrlPow) * Math.signum(y - x + rx));
            rbMotor.setPower(Math.pow(y + x - rx, ctrlPow) * Math.signum(y + x - rx));

            // Press D-Up to spin the launcher, then D-Down (while still holding) to lift the servo
            /*if (gamepad1.dpad_up) {
                shooterMotor.setPower(1.0);

                if (gamepad1.dpad_down) {
                    // TODO: shooterServo.setPosition();
                } else {
                    // TODO: shooterServo.setPosition();
                }
            } else{
                shooterMotor.setPower(0.0);
            }*/

            // Note: above code is impossible. You can't press D-Up AND D-Down at the same time.

            if (gamepad1.left_bumper){

                shooterMotor.setPower(1.0);

            }else{

                shooterMotor.setPower(0);

            }

            if (gamepad1.right_bumper) {

                shooterServo.setPosition(0.05);

            }else{

                shooterServo.setPosition(0);

            }
        }
    }
}
