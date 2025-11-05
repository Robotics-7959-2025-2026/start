package org.firstinspires.ftc.teamcode.auton;


import static org.firstinspires.ftc.teamcode.auton.Motors.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;


//no claw
//shyskill poggers dont steal this 4102
public class Movement extends LinearOpMode {

    private final double tick_count = 382.5;
    //give in inches (change mm to inches)
    private static final double circumference = calculateCircumference(3.779);
    private final Telemetry telemetry;
    // constructor
    public Movement(OpMode opmode){
        // set up wheels
        hardwareMap = opmode.hardwareMap;
        telemetry = opmode.telemetry;

        Motors.init(hardwareMap);
        Motors.encoderPrep();

    }
/*
    public void goToRadian(double desiredHeading, double power) {
        double currentHeading;
        double toleranceLevel = 0.1;

        desiredHeading = normalizeAngle(desiredHeading);

        while (opModeIsActive()) {
            odo.update();
            currentHeading = odo.getHeading();
            currentHeading = normalizeAngle(currentHeading);

            double distance = normalizeAngle(desiredHeading - currentHeading);

            telemetry.addData("Current Heading", currentHeading);
            telemetry.addData("Desired Heading", desiredHeading);
            telemetry.addData("distance", distance);
            telemetry.update();

            if (Math.abs(distance) <= toleranceLevel) {
                break;
            }

            if (distance > 0) {

                setMotorPower(power, -power, power, -power);
            } else {

                setMotorPower(-power, power, -power, power);
            }
        }

        setAllPower(0); // Stop motors
    }
 */

    /*
        public double normalizeAngle(double angle) {
        while (angle < -Math.PI) angle += 2 * Math.PI;
        while (angle > Math.PI) angle -= 2 * Math.PI;
        return angle;
    }
     */
/*
    public void goToAngle(double desiredHeading, double power){
        double currentHeading;
        double toleranceLevel = 10;

        //if we want to be facing 90 degrees after facing 0 degrees 0-90 -->
        //difference between the two headings and makes it smaller until its less than the tolerance
        while (Math.abs(getHeading() - desiredHeading) > toleranceLevel) {
            odo.update();
            //convert the heading to degrees
            currentHeading = odo.getHeading() * (180/Math.PI);
            telemetry.addData("head ing:", currentHeading);
            telemetry.update();

            //if 180 < 270, go clockwise (right)
            //else, go counterclockwise (left)
            //this figures out if we need to turn clockwise or coutner clockwise
            if(desiredHeading < currentHeading){
                //right
                setMotorPower(-power, power, -power, power);
            }else{
                //left
                setMotorPower(power, -power, power, -power);
            }

        }
        setAllPower(0);
    }
 */

    public void calculateAndSetTarPos(double inch){
        int calculatedPos = calculateEncoderTicks(inch);
        Motors.setTargetPosition(calculatedPos);
    }

    public void calculateAndSetTarPoses(double LF, double RF, double LB, double RB){

        int LFI = calculateEncoderTicks(LF);
        int RFI = calculateEncoderTicks(RF);
        int LBI = calculateEncoderTicks(LB);
        int RBI = calculateEncoderTicks(RB);

        Motors.setTargetPositions(LFI, RFI, LBI, RBI);
    }

    public void backwards(double inches, double power){
        resetEncoders();
        calculateAndSetTarPos(inches);
        setRunToPosition();
        setAllPower(power);

        //if the current pos isnt there yet
        while(Math.abs(leftFront.getCurrentPosition()) < Math.abs(calculateEncoderTicks(inches))){
            telemetry.addData("Moving", "Forward");
            telemetry.update();
        }
        setAllPower(0);
        sleep(300);
    }

    public void holdIntake(){

        elbowR.setPosition(1);
        elbowL.setPosition(0);
    }
    public void intakeDown(){

        wrist.setPosition(0.9);
        elbowR.setPosition(0.67);
        elbowL.setPosition(0.33);
    }

    public void dumpFromIntake(){
        outClaw.setPosition(0.35);

        claw.setPosition(0);
        //intake comes back down
        wrist.setPosition(0.9);
        elbowR.setPosition(0.67);
        elbowL.setPosition(0.33);
        //dump for the arm

    }


    public void intakeUp(){

        wrist.setPosition(0.25);
        //originally 0.3, per req its going to 0.7
        elbowR.setPosition(1);
        elbowL.setPosition(0);
        claw.setPosition(0.35);

    }

    public void forwards(double inches, double power) {
        resetEncoders();
        calculateAndSetTarPos(-inches);
        setRunToPosition();
        setAllPower(power);
        while(Math.abs(leftFront.getCurrentPosition()) < Math.abs(calculateEncoderTicks(inches))){
            telemetry.addData("forward",leftFront.getCurrentPosition());
            telemetry.update();
        }
        setAllPower(0);
        encoderPrep();
    }

    //for testing only
    public void turnRight(double inches, double power){
        resetEncoders();
        calculateAndSetTarPoses(-inches, inches, -inches, inches);
        setAllPower(power);
        setRunToPosition();
        while(Math.abs(leftFront.getCurrentPosition()) < Math.abs(calculateEncoderTicks(inches))){
            telemetry.addData("Turning", "Right");
            telemetry.update();
            sleep(5);
        }
        sleep(300);

    }

    public void turnLeft(double inches, double power){
        resetEncoders();
        calculateAndSetTarPoses(inches, -inches, inches, -inches);
        setRunToPosition();
        setAllPower(power);
        while(Math.abs(leftFront.getCurrentPosition()) < Math.abs(calculateEncoderTicks(inches))){
            telemetry.addData("Turning", "Left");
            telemetry.update();
            sleep(5);
        }
        sleep(300);

    }
    //lf is forward, rf is reverse, lb is forward, rb is reverse
    //to strafe left, left should get pos power, right should get pos aswell but more, and back left and right should get negative power
    public void strafeLeft(double inches, double power){
        resetEncoders();
        //left front, right front, left back right back
        calculateAndSetTarPoses(inches,-inches,-inches,inches);
        setRunToPosition();
        setAllPower(power);
        while(Math.abs(leftFront.getCurrentPosition()) < Math.abs(calculateEncoderTicks(inches))){
            telemetry.addData("Strafing", "Left");
            telemetry.update();
            sleep(5);
        }
        sleep(300);

    }

    public void strafeRight(double inches, double power){
        resetEncoders();
        calculateAndSetTarPoses(-inches, inches, inches, -inches);
        setRunToPosition();
        setAllPower(power);
        while(Math.abs(leftFront.getCurrentPosition()) < Math.abs(calculateEncoderTicks(inches))){
            telemetry.addData("Strafing", "Right");
            telemetry.update();
        }
        sleep(300);

    }
    public void openClaw(){
        claw.setPosition(0);
        sleep(500);
    }

    public void closeClaw(){
        claw.setPosition(0.35);
        sleep(500);
    }

    public void closeOutClaw(){
        outClaw.setPosition(0.35);
    }

    public void outWristDown(){
        outWrist.setPosition(0);
    }
    public void prepBox(){
        outL.setPosition((0.1));
        outR.setPosition(0.9);
        outWrist.setPosition(0);
    }
    public void outWristUp(){
        outWrist.setPosition(0.5);
    }
    public void outWristUp(double pos){
        outWrist.setPosition(pos);
    }

    public void openOutClaw(){
        outClaw.setPosition(0);
    }

    public void liftUp(){
        resetEncoders();
        int pos = 2800;
        liftL.setTargetPosition(pos);
        liftL.setPower(0.6);
        while(liftL.getCurrentPosition() < pos || liftL.isBusy()){
            liftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            telemetry.addData("Position" ,liftL.getCurrentPosition());
            telemetry.update();
            sleep(5);
        }

        if(liftL.getCurrentPosition() > pos-10 && liftL.getCurrentPosition() < pos+10 ){
            liftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            liftL.setPower(0.1);
        }
        liftL.setPower(0.1);

    }
    public void liftUp(int pos){
        resetEncoders();
        liftL.setTargetPosition(pos);
        liftL.setPower(0.6);
        while(liftL.getCurrentPosition() < pos || liftL.isBusy()){
            liftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            telemetry.addData("Position" ,liftL.getCurrentPosition());
            telemetry.update();
            sleep(5);
        }

        if(liftL.getCurrentPosition() > pos-10 && liftL.getCurrentPosition() < pos+10 ){
            liftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            liftL.setPower(0.1);
        }
        liftL.setPower(0.1);

    }

    public void liftDown(){
        liftL.setTargetPosition(0);
        liftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftL.setPower(0.5);
        while(liftL.getCurrentPosition() > 0 || liftL.isBusy()){
            liftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            telemetry.addData("Position" ,liftL.getCurrentPosition());
            telemetry.update();
            sleep(5);
        }

        liftL.setPower(0);
    }
    public void dumpBox(){
        outL.setPosition((0.6));
        outR.setPosition(0.4);
        sleep(750);
    }
    public void boxDown(){
        outL.setPosition((0));
        outR.setPosition(1);
        outWrist.setPosition(0);
    }

    public boolean isAllBusy(){
        return leftBack.isBusy() && leftFront.isBusy() && rightFront.isBusy() && rightBack.isBusy();
    }

    public boolean isSomeBusy(){
        return leftBack.isBusy() || leftFront.isBusy() || rightFront.isBusy() || rightBack.isBusy();
    }

    public void moveExtendoOut(double ticks){
        extendo.setTargetPosition(-(int) ticks);
        extendo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendo.setPower(0.3);

    }
    public void moveExtendoIn(double ticks){
        extendo.setTargetPosition((int) ticks);
        extendo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extendo.setPower(0.3);

    }


    public void setAllPower(double p) {
        setMotorPower(p,p,p,p);
    }


    // Set power to motors
    public void setMotorPower(double LF, double RF, double LB, double RB) {
        leftFront.setPower(LF);
        rightFront.setPower(RF);
        leftBack.setPower(LB);
        rightBack.setPower(RB);
    }

    public void resetEncoders(){
        Motors.resetEncoders();
    }

    public void setRunToPosition(){
        Motors.runToPosition();
    }

    private static double encoderTicksToInches(double ticks){
        return 1.887 * 2 * Math.PI * ticks / 384.5;
    }
    private int calculateEncoderTicks(double inches) {
        return (int) ((inches / circumference) * tick_count);
    }

    public static double calculateCircumference(double diameter) {
        return Math.PI * diameter;
    }

    @Override
    public void runOpMode() {


    }


}