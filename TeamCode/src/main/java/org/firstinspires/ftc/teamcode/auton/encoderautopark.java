package org.firstinspires.ftc.teamcode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auton.Movement;


@Autonomous(name="Parking auto - Keir")
@Disabled
public class encoderautopark extends LinearOpMode{

    public void runOpMode() throws InterruptedException{
        //init the necessary objects
        Movement karel = new Movement(this);
        //view the webcam now, you cant check camera stream once the game is initialized.
        waitForStart();
        karel.forwards(10, 5);
        sleep(1000);


        telemetry.addData("Status", "Running now");
        telemetry.update();

    }
}