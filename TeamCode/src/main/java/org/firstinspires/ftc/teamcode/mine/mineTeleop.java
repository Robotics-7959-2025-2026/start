package org.firstinspires.ftc.teamcode.mine;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "mine")
public class mineTeleop extends LinearOpMode {
    private DcMotor lfMotor = null;
    private DcMotor rfMotor = null;
    private DcMotor lbMotor = null;
    private DcMotor rbMotor = null;

    private double ctrlPow = 1.0;

    public void runOpMode() {
        lfMotor = hardwareMap.get(DcMotor.class, "front_left_drive");
        rfMotor = hardwareMap.get(DcMotor.class, "front_right_drive");
        rbMotor = hardwareMap.get(DcMotor.class, "back_left_drive");
        lbMotor = hardwareMap.get(DcMotor.class, "back_right_drive");

        lfMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rfMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        lbMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rbMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x * 0.85;

            lfMotor.setPower(Math.pow(y + x + rx, ctrlPow) * Math.signum(y + x + rx));
            rfMotor.setPower(Math.pow(y - x - rx, ctrlPow) * Math.signum(y - x - rx));
            lbMotor.setPower(Math.pow(y - x + rx, ctrlPow) * Math.signum(y - x + rx));
            rbMotor.setPower(Math.pow(y + x - rx, ctrlPow) * Math.signum(y + x - rx));
        }
    }
}
