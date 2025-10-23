package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "labubu")
public class labubuTeleOp extends LinearOpMode {
    private DcMotor lfMotor = null;
    private DcMotor rfMotor = null;
    private DcMotor lbMotor = null;
    private DcMotor rbMotor = null;

    private DcMotor shooterMotor = null;
    private Servo shooterServo = null;

    private double ctrlPow = 1.0;

    public void runOpMode() {
        lfMotor = hardwareMap.get(DcMotor.class, "left");
        rfMotor = hardwareMap.get(DcMotor.class, "right");
        //rbMotor = hardwareMap.get(DcMotor.class, "back_left_drive");
        //lbMotor = hardwareMap.get(DcMotor.class, "back_right_drive");
        shooterMotor = hardwareMap.get(DcMotor.class, "shooterMotor");
        // TODO!: Get shooterMotor & servo from hardwareMap

        lfMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rfMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //lbMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        //rbMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        shooterServo.setDirection(Servo.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.update();

//            if (Math.abs(gamepad1.left_stick_x) + Math.abs(gamepad1.left_stick_y) < 0.1) {
//                continue;
//            }
//
//            double x = gamepad1.left_stick_x;
//            double y = gamepad1.left_stick_y;
//            double rx = gamepad1.right_stick_x * 0.85;
//
//            lfMotor.setPower(Math.pow(y + x + rx, ctrlPow) * Math.signum(y + x + rx));
//            rfMotor.setPower(Math.pow(y - x - rx, ctrlPow) * Math.signum(y - x - rx));
//            lbMotor.setPower(Math.pow(y - x + rx, ctrlPow) * Math.signum(y - x + rx));
//            rbMotor.setPower(Math.pow(y + x - rx, ctrlPow) * Math.signum(y + x - rx));

            double l = gamepad1.right_stick_y;
            double r = gamepad1.left_stick_y;

            lfMotor.setPower(l);
            rfMotor.setPower(r);

            // Hold left bumper to spin, then press the right bumper to shoot
            if (gamepad1.left_bumper) {
                shooterMotor.setPower(1.0);

                if (gamepad1.right_bumper) {
                    // TODO: shooterServo.setPosition();
                } else {
                    // TODO: shooterServo.setPosition();
                }
            } else{
                shooterMotor.setPower(0.0);
            }
        }
    }
}
