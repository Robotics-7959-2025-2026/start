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

    static int INTAKE_POSITION = 2;
    static int SHOOTING_POSITION = 0;
    private BallType[] spindexSlots = {BallType.EMPTY, BallType.EMPTY, BallType.EMPTY};

    boolean firePressed = false;

    private double ctrlPow = 1.0;

    public void runOpMode() {
        // NOTE: Update driver station after 1st comp
        lfMotor = hardwareMap.get(DcMotor.class, "front_left_drive");
        rfMotor = hardwareMap.get(DcMotor.class, "front_right_drive");
        rbMotor = hardwareMap.get(DcMotor.class, "back_left_drive");
        lbMotor = hardwareMap.get(DcMotor.class, "back_right_drive");
        shooterMotor = hardwareMap.get(DcMotor.class, "shooterMotor");
        shooterServo = hardwareMap.get(Servo.class, "shooterServo");

        lfMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rfMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        lbMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rbMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterServo.setDirection(Servo.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
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
            }

            if (firePressed) {
                if (gamepad1.x) {
                    fire(BallType.PURPLE);
                    firePressed = true;
                } else if (gamepad1.a) {
                    fire(BallType.GREEN);
                    firePressed = true;
                }
            } else if (!gamepad1.x && !gamepad1.a) {
                firePressed = false;
            }
        }
    }

    private void fire(BallType select) {
        // Disable intake

        int index = -1;
        for (int i = 0; i < 2; ++i) {
            if (spindexSlots[i] == select) {
                index = i;
                break;
            }
        }

        // Error! We don't have that ball loaded. Indicate to user?
        if (index == -1) return;

        for (int i = 0; i < index; ++i) {
            cycleSpindexSlots();
            // Rotate 120
        }

        // Rotate 60
        // Shoot
        // Rotate 60

        // Move the newly-empty slot to [2]
        spindexSlots[SHOOTING_POSITION] = BallType.EMPTY;
        cycleSpindexSlots();
        cycleSpindexSlots();
        // Rotate 120

        // Enable intake (guaranteed to work)
    }

    private void onIntake(BallType color) {
        // Rotate 120
        cycleSpindexSlots();
        // Index 2 is now index 0
        // Hithertofore, use shooting position instead
        spindexSlots[SHOOTING_POSITION] = color;
    }

    private void cycleSpindexSlots() {
        BallType z0 = spindexSlots[2];
        spindexSlots[2] = spindexSlots[1];
        spindexSlots[1] = spindexSlots[0];
        spindexSlots[0] = z0;
    }
}
