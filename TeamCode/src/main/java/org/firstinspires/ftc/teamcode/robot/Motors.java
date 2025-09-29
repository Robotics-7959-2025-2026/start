package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Motors {

    public static DcMotorEx leftFront, leftBack, rightBack, rightFront, liftL, extendo;
    public static Servo elbowR, elbowL, claw, outL, outClaw, outR, outWrist;
    public static Servo wristR, wristL;
    public static AnalogInput axonright, axonleft;

    public static void init(HardwareMap hardwareMap) {
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");

        extendo = hardwareMap.get(DcMotorEx.class, "extendo");

        axonright = hardwareMap.get(AnalogInput.class, "axonright");
        axonleft = hardwareMap.get(AnalogInput.class, "axonleft");

        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        extendo.setDirection(DcMotorEx.Direction.REVERSE);
        extendo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        liftL = hardwareMap.get(DcMotorEx.class, "liftL");
        liftL.setDirection(DcMotor.Direction.REVERSE);

        wristL = hardwareMap.servo.get("wristL");
        wristR = hardwareMap.servo.get("wristR");
        claw = hardwareMap.servo.get("claw");

        //control hub ---> 0 is intake, 1 is elbowL, 2 is wristL
        //expansion hub ---> 0 is outtake , 1 is elbowR, 2 is wristR

        outL = hardwareMap.servo.get("outL");
        elbowR = hardwareMap.servo.get("elbowR");
        elbowL = hardwareMap.servo.get("elbowL");

        outR = hardwareMap.servo.get("outR");
        outWrist = hardwareMap.servo.get("outWrist");
        outClaw = hardwareMap.servo.get("outClaw");

    }

    public static void encoderPrep(){
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extendo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public static void runToPosition(){
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public static void resetEncoders(){
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public static void runEncoders(){
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    /*
        public static void getCurrentPos(String motor){
        switch(motor){
            case(motor.equals("Lf"))

        }
    }
     */

    public static void setTargetPosition(int pos){
        rightBack.setTargetPosition(pos);
        rightFront.setTargetPosition(pos);
        leftFront.setTargetPosition(pos);
        leftBack.setTargetPosition(pos);
    }
    public static void setTargetPositions(int LF, int RF, int LB, int RB){
        rightBack.setTargetPosition(RB);
        rightFront.setTargetPosition(RF);
        leftFront.setTargetPosition(LF);
        leftBack.setTargetPosition(LB);
    }
    public static void runWithoutEncoder(){
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}