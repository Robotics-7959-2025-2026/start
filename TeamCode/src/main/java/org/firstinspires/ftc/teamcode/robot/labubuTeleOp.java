package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/** TODO:
 *
 * Drive system: Done, mechania drive copied from 2024-2025 year
 * Launching system: A[] B[]
 *    - (A) Intake: Not done, hasn't been built yet
 *    - (B) Launcher: Not done, hasn't been built yet
 *
 * Auton: I (Bennett) have to learn how to do it. If anyone else
 *        knows how to do it feel free to contribute
 */
@TeleOp(name="labubu")
public class labubuTeleOp extends LinearOpMode {

    private DcMotor RightFront = null;
    private DcMotor LeftFront = null;
    private DcMotor RightBack = null;
    private DcMotor LeftBack = null;

    public void runOpMode() throws InterruptedException {


        RightFront = hardwareMap.dcMotor.get("RightFront");
        RightFront.setDirection(DcMotor.Direction.REVERSE);

        LeftFront = hardwareMap.dcMotor.get("LeftFront");
        LeftFront.setDirection(DcMotor.Direction.FORWARD);

        RightBack = hardwareMap.dcMotor.get("RightBack");
        RightBack.setDirection(DcMotor.Direction.REVERSE);

        LeftBack = hardwareMap.dcMotor.get("LeftBack");
        LeftBack.setDirection(DcMotor.Direction.FORWARD);


        waitForStart();
        while (opModeIsActive()) {
            telemetry.update();
        }

        if (gamepad1.left_stick_x > 0.2 || gamepad1.left_stick_x < -0.2 || gamepad1.left_stick_y > 0.2 || gamepad1.left_stick_y < -0.2){

            double y = -gamepad1.left_stick_x;
            double x = gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;
            double div = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            LeftFront.setPower(Math.pow((y + x - (rx*.85)),5)/div);
            LeftBack.setPower(Math.pow((y - x + (rx*.85)),5)/div);
            RightFront.setPower(Math.pow((y - x - (rx*.85)),5)/div);
            RightBack.setPower(Math.pow((y + x + (rx*.85)),5)/div);

        }

    }

}
