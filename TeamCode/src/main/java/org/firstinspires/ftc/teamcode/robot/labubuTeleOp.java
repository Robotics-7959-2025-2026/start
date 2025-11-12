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

    // Counted boolean
    //   - False if <= 0
    //   - True if >= 1
    //   - Set to true by incrementing
    //   - Set to false by decrementing
    private int intakeUsers = 0;

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

        enableIntake();

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

    // Ensure the right type of ball is ready to shoot
    private boolean alignBall(BallType select) {
        // Already where we want it
        if (spindexSlots[0] == select) {
            return true;
        }

        // In intake
        if (spindexSlots[2] == select) {
            // Rotate 120
            cycleSpindexSlots();
            return true;
        }

        if (spindexSlots[1] == select) {
            // Rotate 240
            cycleSpindexSlots();
            cycleSpindexSlots();
            return true;
        }

        // Not there
        return false;
    }

    private void shoot() {
        disableIntake();
        rotate60();
        // Turn on spinner
        // Wait for spin up
        // Lift ball
        // Wait for ball to launch
        // Turn off spinner
        rotate60();

        // Update state
        cycleSpindexSlots();
        spindexSlots[1] = BallType.EMPTY;

        // Rotate so intake is empty
        cycleSpindexSlots();
        rotate120();

        enableIntake();
    }

    private void fire(BallType select) {
        disableIntake();

        if (!alignBall(select)) {
            // Error: Ball not found
            return;
        }

        shoot();

        enableIntake();
    }

    private void onIntake(BallType color) {
        rotate120();
        spindexSlots[INTAKE_POSITION] = color;
        cycleSpindexSlots();
    }

    private void cycleSpindexSlots() {
        BallType z0 = spindexSlots[2];
        spindexSlots[2] = spindexSlots[1];
        spindexSlots[1] = spindexSlots[0];
        spindexSlots[0] = z0;
    }

    private void rotate120() {
        disableIntake();
        // TODO
        enableIntake();
    }

    private void rotate60() {
        disableIntake();
        // TODO
        enableIntake();
    }

    private void disableIntake() {
        intakeUsers -= 1;
        if (intakeUsers < 1) return;
        // TODO
    }

    private void enableIntake() {
        intakeUsers += 1;
        if (intakeUsers > 0) return;
        // TODO
    }
}
