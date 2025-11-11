package org.firstinspires.ftc.teamcode.auton;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Motors {

    public static DcMotorEx leftFront, leftBack, rightBack, rightFront, liftL, shooterMotor;
    public static Servo elbowR, elbowL, wrist, outL, claw, outClaw, outR, outWrist, shooterServo;

    public static void init(HardwareMap hardwareMap) {
        leftFront = hardwareMap.get(DcMotorEx.class, "front_left_drive");
        rightFront = hardwareMap.get(DcMotorEx.class, "front_right_drive");
        leftBack = hardwareMap.get(DcMotorEx.class, "back_left_drive");
        rightBack = hardwareMap.get(DcMotorEx.class, "back_right_drive");

        shooterMotor = hardwareMap.get(DcMotorEx.class, "shooterMotor");
        shooterServo = hardwareMap.get(Servo.class, "shooterServo");

        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterServo.setDirection(Servo.Direction.REVERSE);

    }

    public static void encoderPrep(){
        rightBack.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        leftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
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
