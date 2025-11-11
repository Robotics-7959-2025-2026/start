package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

@TeleOp(name = "TeleOp for November 15th Comp")
public class OfficialTeleop extends LinearOpMode {
    private DcMotor lfMotor = null;
    private DcMotor rfMotor = null;
    private DcMotor lbMotor = null;
    private DcMotor rbMotor = null;
    private double nominalVoltage = 12.5;
    private double desiredPower = 1.0;
    private double batteryVoltage, correctedPower;
    private DcMotor shooterMotor = null;
    private Servo shooterServo = null;

    private double servoStart = 0.0;

    private double ctrlPow = 1.0;

    @Override
    public void runOpMode() {
        lfMotor = hardwareMap.get(DcMotor.class, "front_left_drive");
        rfMotor = hardwareMap.get(DcMotor.class, "front_right_drive");
        lbMotor = hardwareMap.get(DcMotor.class, "back_left_drive");
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
        telemetry.addData("Debug", "Balulu3");
        telemetry.update();

        VoltageSensor battery = hardwareMap.voltageSensor.iterator().next();
        batteryVoltage = battery.getVoltage();


        waitForStart();

        while (opModeIsActive()) {
            telemetry.update();

            if (Math.abs(gamepad1.left_stick_x) + Math.abs(gamepad1.left_stick_y) > 0.05
                    || Math.abs(gamepad1.right_stick_x) > 0.03
            ) {
                double x = gamepad1.left_stick_x;
                double y = gamepad1.left_stick_y;
                double rx = gamepad1.right_stick_x * 0.75;

                telemetry.addData("drive/x", x + "");
                telemetry.addData("drive/y", y + "");
                telemetry.addData("drive/rx", rx + "");

                //lfMotor.setPower(Math.pow(y - x - rx, ctrlPow) * Math.signum(y - x - rx));
                //rfMotor.setPower(Math.pow(y + x + rx, ctrlPow) * Math.signum(y + x + rx));
                //lbMotor.setPower(Math.pow(y + x - rx, ctrlPow) * Math.signum(y + x - rx));
                //rbMotor.setPower(Math.pow(y - x + rx, ctrlPow) * Math.signum(y - x + rx));

                lfMotor.setPower(y - x - rx);
                rfMotor.setPower(y + x + rx);
                lbMotor.setPower(y + x - rx);
                rbMotor.setPower(y - x + rx);
            } else {
                lfMotor.setPower(0.0);
                rfMotor.setPower(0.0);
                lbMotor.setPower(0.0);
                rbMotor.setPower(0.0);
            }

            double pos = 0.0;
            // Hold left bumper to spin, then press the right bumper to shoot
            if (gamepad1.left_bumper) {
                correctedPower = desiredPower * (nominalVoltage / Math.max(batteryVoltage, 1.0));
                correctedPower = Math.max(-1.0, Math.min(correctedPower, 1.0));
                shooterMotor.setPower(correctedPower);

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
