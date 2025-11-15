package org.firstinspires.ftc.teamcode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name="shootingautoStrafeLeft")
public class shootingautoStrafeLeft extends LinearOpMode {

    public void runOpMode() throws InterruptedException {

        telemetry.addData("Status", "Started");
        telemetry.update();

        //init the necessary objects
        Movement karel = new Movement(this);
        //view the webcam now, you cant check camera stream once the game is initialized.
        waitForStart();
        karel.backwards(18, 0.5);
        //karel.turnOnMotor();
        karel.setMotorSpeed(0.95);
        sleep(1500);
        karel.shoot();
        karel.setMotorSpeed(0.88);
        sleep(500);
        sleep(500);
        karel.shoot();
        karel.setMotorSpeed(0.92);
        sleep(500);
        karel.backwards(3, 0.5);
        sleep(1000);
        karel.shoot();
        sleep(500);
        karel.turnOffMotor();
        karel.strafeLeft(20, 0.5);

        telemetry.addData("Status", "Done");
        telemetry.update();

    }
}