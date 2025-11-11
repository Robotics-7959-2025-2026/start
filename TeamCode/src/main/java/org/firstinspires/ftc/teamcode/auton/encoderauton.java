package org.firstinspires.ftc.teamcode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auton.Movement;

@Autonomous(name="auton")
public class encoderauton extends LinearOpMode{

    public void runOpMode(){
        Movement karel = new Movement(this);
        waitForStart();
        karel.backwards(10, 0.5);
        int count = 0;
        while (count < 3) {
            karel.shoot();
            count++;
        }


    }
}