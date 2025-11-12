package org.firstinspires.ftc.teamcode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auton.Movement;


@Autonomous(name="shootingauto")
public class shootingauto extends LinearOpMode{

    public void runOpMode() throws InterruptedException{
        //init the necessary objects
        Movement karel = new Movement(this);
        //view the webcam now, you cant check camera stream once the game is initialized.
        waitForStart();
        karel.backwards(20, 0.5);
        karel.turnOnMotor();
        sleep(1500);
        karel.shoot();
        sleep(500);
        karel.backwards(3, 0.5);
        sleep(500);
        karel.shoot();
        sleep(500);
        karel.backwards(3, 0.5);
        karel.shoot();
        sleep(500);
        karel.turnOffMotor();

        telemetry.addData("Status", "Running now");
        telemetry.update();

    }
}