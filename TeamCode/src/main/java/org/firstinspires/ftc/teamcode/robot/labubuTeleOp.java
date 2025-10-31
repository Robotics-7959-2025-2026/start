package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "balubu")
public class labubuTeleOp extends LinearOpMode {
    private DcMotor lfMotor = null;
    private DcMotor rfMotor = null;
    private DcMotor lbMotor = null;
    private DcMotor rbMotor = null;

    private DcMotor shooterMotor = null;
    private Servo shooterServo = null;

    private double servoStart = 0.0;

    private double ctrlPow = 1.0;

    @Override
    public void runOpMode() {
        lfMotor = hardwareMap.get(DcMotor.class, "front_left_drive");
        rfMotor = hardwareMap.get(DcMotor.class, "front_right_drive");
        lbMotor = hardwareMap.get(DcMotor.class, "front_left_drive");
        rbMotor = hardwareMap.get(DcMotor.class, "back_right_drive");
        shooterMotor = hardwareMap.get(DcMotor.class, "shooterMotor");
        shooterServo = hardwareMap.get(Servo.class, "shooterServo");

        lfMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rfMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        lbMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rbMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterServo.setDirection(Servo.Direction.REVERSE);

        servoStart = shooterServo.getPosition();

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Debug", "Balulu2");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.update();

            if (Math.abs(gamepad1.left_stick_x) + Math.abs(gamepad1.left_stick_y) > 0.05
                    || Math.abs(gamepad1.right_stick_x) > 0.03
            ) {
                double x = gamepad1.left_stick_x;
                double y = gamepad1.left_stick_y;
                double rx = gamepad1.right_stick_x * 0.85;

                lfMotor.setPower(Math.pow(y + x + rx, ctrlPow) * Math.signum(y + x + rx));
                rfMotor.setPower(Math.pow(y - x - rx, ctrlPow) * Math.signum(y - x - rx));
                lbMotor.setPower(Math.pow(y - x + rx, ctrlPow) * Math.signum(y - x + rx));
                rbMotor.setPower(Math.pow(y + x - rx, ctrlPow) * Math.signum(y + x - rx));
            } else {
                lfMotor.setPower(0.0);
                rfMotor.setPower(0.0);
                lbMotor.setPower(0.0);
                rbMotor.setPower(0.0);
            }

            double pos = 0.0;
            // Hold left bumper to spin, then press the right bumper to shoot
            if (gamepad1.left_bumper) {
                shooterMotor.setPower(1.0);

                if (gamepad1.right_bumper) {
                    // 1.0 = 300deg
                    pos = 51.0 / 300.0;
                }
            } else {
                shooterMotor.setPower(0.0);
            }

            shooterServo.setPosition(pos);
        }
    }
}
