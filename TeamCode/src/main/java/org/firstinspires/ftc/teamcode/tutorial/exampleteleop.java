package org.firstinspires.ftc.teamcode;
//judy tu was here
//so was z :3


//Left stick is used for movement amd strafing
//Right stick is used for turning
//B is the killswitch - DONT PRESS IT UNLESS YOU NEED TO!
//Left trigger moves the arm down; Right trigger moves the arm up
//DPad is used for percise strafing
//X is used for launching the drone
//Unused: Y and A, Bumpers


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="Tutorial")
public class exampleteleop extends LinearOpMode {
    //motors (view from back in terms of L & R)
    private DcMotor RightFront = null;

    public void runOpMode() throws InterruptedException {


        RightFront = hardwareMap.dcMotor.get("RightFront");
        RightFront.setDirection(DcMotor.Direction.REVERSE);


        waitForStart();
        while (opModeIsActive()) {
            telemetry.update();

            // sets power after certain deadzone (meant to test motors and dynamic power)
                if (gamepad1.left_stick_x > 0.2 || gamepad1.left_stick_x < -0.2 || gamepad1.left_stick_y > 0.2 || gamepad1.left_stick_y < -0.2){
                    RightFront.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x);
                }
        }
    }
}