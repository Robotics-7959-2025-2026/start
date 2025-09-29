//if you see this locally then git pull or git fetch worked
package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.teamcode.robot.Motors;
import static org.firstinspires.ftc.teamcode.robot.Motors.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Teleop 7959 (Duo)")
public class teleop extends LinearOpMode {

    private final double ctrlPow = 2;
    private final double yTime = 0.5;
    private final double gTime = 0.4;
    private double rx;
/*  private final double rx;
    private PIDController controller;
    public static double p = 0, i = 0, d = 0;
    public static double f = 0;
 */

    public static int target = 2800;
    int liftPos;
    double positionR, positionL;
    double R = 70/(255*2.5);
    public final double ticks_in_degree = 380;

    ElapsedTime time = new ElapsedTime();
    public boolean intakeToggle, outToggle, moveOutWrist, readyToOutGrip, gripBackDown, eviltog = false;

    // TEAM 7959 TELEOP - CREATED WITH LOVE BY K. SAMPSON and J. SHAPIRO and J. Kamen
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //intake = hardwareMap.crservo.get("inServo");

        //elbowR = hardwareMap.servo.get("elbowR") ;
        Motors.init(hardwareMap);
        Motors.runWithoutEncoder();

        //controller = new PIDController(p, i, d);
        Gamepad currentGamepad1 = new Gamepad();
        Gamepad previousGamepad1 = new Gamepad();
        Gamepad outPrev = new Gamepad();
        Gamepad outNow = new Gamepad();

        //test commit1

        //picks outtake and intake either off the ground or into zero pos respectively
        //for auto run BoxDown and then
        outL.setPosition((0));
        outR.setPosition(1);
        outWrist.setPosition(0);

        //then run OpemOutClaw
        outClaw.setPosition(0.35);
        eviltog = true;
        outToggle = true;

        waitForStart();

        liftL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //wrist.setPosition(0.9);
        elbowR.setPosition(0.67);
        elbowL.setPosition(0.33);

        time.reset();
        while (opModeIsActive()) {

            telemetry.addData("Time:", time.toString());
            telemetry.addData("Lift" ,liftL.getCurrentPosition());
            telemetry.addData("Right Axon pos:", wristR.getPosition());
            telemetry.addData("Left Axon pos:", wristL.getPosition());
            telemetry.update();
            /*
            controller.setPID(p, i, d);
            liftPos = liftL.getCurrentPosition();
            double pid = controller.calculate(liftPos, target);
            double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;
            double power = pid + ff;
             */



            liftL.setPower((gamepad2.left_trigger - 0.6 * gamepad2.right_trigger));

            if (gamepad2.left_trigger == 0 && gamepad2.right_trigger == 0) {
                liftL.setPower(-0.02);
            }
            if(gamepad1.left_trigger > 0){
                //works fine
                wristL.setPosition(gamepad1.left_trigger);
            }
            if(gamepad1.right_trigger > 0){
                //is not recieviing power
                wristR.setPosition(1-gamepad1.right_trigger);
            }
            outPrev.copy(outNow);
            outNow.copy(gamepad2);

            previousGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);

            rx = (gamepad1.right_stick_x / 4) * 3 ;

            rightFront.setPower(Math.pow(gamepad1.left_stick_y + gamepad1.left_stick_x + rx, ctrlPow) * Math.signum(gamepad1.left_stick_y + gamepad1.left_stick_x + rx));
            leftFront.setPower(Math.pow(gamepad1.left_stick_y - gamepad1.left_stick_x - rx, ctrlPow) * Math.signum(gamepad1.left_stick_y - gamepad1.left_stick_x - rx));
            rightBack.setPower(Math.pow(gamepad1.left_stick_y - gamepad1.left_stick_x + rx, ctrlPow) * Math.signum(gamepad1.left_stick_y - gamepad1.left_stick_x + rx));
            leftBack.setPower(Math.pow(gamepad1.left_stick_y + gamepad1.left_stick_x  - rx, ctrlPow) * Math.signum(gamepad1.left_stick_y + gamepad1.left_stick_x - rx));

            if(gamepad2.left_trigger > 0){
                claw.setPosition(0);
                intakeToggle = false;
            }

            // Moves the outtake servo
            extendo.setPower(-gamepad1.right_stick_y);

            //moves to bucket to dump*************
            //moves outtake servo to dump positionb
            if (gamepad2.right_bumper) {
                //intake claw opens
                intakeToggle = false;
                claw.setPosition(0);
                //intake comes back down
                //wrist.setPosition(0.9);
                elbowR.setPosition(0.68);
                elbowL.setPosition(0.32);
                //dump for the arm
                outL.setPosition((0.6));
                outR.setPosition(0.4);

                outWrist.setPosition(0);

            }

            if(gamepad2.y){
                outL.setPosition((0.17));
                outR.setPosition(1-0.17);
                outWrist.setPosition(0.18);
                elbowR.setPosition(0.35);
                elbowL.setPosition(1-0.35);
                wristL.setPosition(((135)/(255*2.5)));
                wristR.setPosition(1-((135)/(255*2.5)));
            }
            //moves to grab the sample from intake
            //0.9 is too far down, 0.8 is too far up
            if (gamepad2.left_bumper) {
                //put this jawn down
                outL.setPosition((0));
                outR.setPosition(1);
                //opens the claw
                outToggle = false;
                outClaw.setPosition(0);


                //wrist.setPosition(0.9);
                elbowR.setPosition(0.68);
                elbowL.setPosition(0.32);
                claw.setPosition(0);
                intakeToggle = false;

                telemetry.addData("current pos", outL.getPosition());
                telemetry.update();
            }

            //bruings the entire mechanism up

            //moves to pre-grabbing position
            if(gamepad2.dpad_left){
                outWrist.setPosition(0.3);

            }

            //moves to grabbing position
            if(gamepad2.dpad_right){
                outWrist.setPosition(0);
            }


            if(gamepad2.dpad_up){
                outL.setPosition((1));
                outR.setPosition(0);
            }
            //moves the entire mechanism down
            //ready for pickup
            if(gamepad1.a)
            {
                //180/300 is 0.6
                wristL.setPosition(((135)/(255*2.5)));
                wristR.setPosition(1-((135)/(255*2.5)));
                elbowR.setPosition(0.98);
                elbowL.setPosition(0.02);
                claw.setPosition(0);
                intakeToggle = false;
            }

            //flips the claw 90
            if(gamepad1.right_bumper){
                wristR.setPosition(1-((135)/(255*2.5))+R);
                wristL.setPosition(((135)/(255*2.5))+R);
            }
            //flips it back to normal (180)
            if(gamepad1.left_bumper){
                wristR.setPosition(1-((135)/(255*2.5)));
                wristL.setPosition(((135)/(255*2.5)));
            }

            //moves the entire mechanism up
            //ready for outtake grab

            if(gamepad1.b){
                //wrist.setPosition(0.25);
                //67 is too low, and 92 is too high so probably like 80
                elbowR.setPosition(0.75);
                elbowL.setPosition(0.25);
            }

            if (gamepad1.y) {
                time.reset();
                telemetry.addData("time:", time.toString());
                telemetry.update();
                claw.setPosition(0.75);
                outWrist.setPosition(0.3);
                wristR.setPosition(1);
                wristL.setPosition(0);
                elbowR.setPosition(0.66);
                elbowL.setPosition(0.34);

                outToggle = false;
                outClaw.setPosition(0);

                moveOutWrist = true;
            }




            if (moveOutWrist && time.seconds() >= yTime) {
                claw.setPosition(0.68);
                outWrist.setPosition(0);
                moveOutWrist = false;
                outToggle = false;
                //outclaw is now ready to grip, wait 0.5 seconds for the out regrip
                time.reset();
                readyToOutGrip = true;
            }

            //from here, the intake is still gripping and the outtake aswell, intake needs to let go and move back down
            if(readyToOutGrip && time.seconds() >= gTime){
                outClaw.setPosition(0.35);
                outToggle = true;
                readyToOutGrip = false;
                time.reset();
                gripBackDown = true;
            }

            //atm it just lets go
//            if(gripBackDown && time.seconds() >= yTime){
//                claw.setPosition(0);
//                intakeToggle = false;
//                /*
//                wrist.setPosition(0.9);
//                elbowR.setPosition(0.68);
//                elbowL.setPosition(0.32);
//                 */
//                gripBackDown = false;
//            }




            if (currentGamepad1.x && !previousGamepad1.x) {
                intakeToggle = !intakeToggle;
            }
            if (outNow.x && !outPrev.x) {
                outToggle = !outToggle;
            }
            if (outNow.a && !outPrev.a) {
                eviltog = !eviltog;
            }


// Using the toggle variable to control the robot.
            //closes the claw
            if(gamepad1.x){
                if (intakeToggle) {
                    claw.setPosition(0.75);
                }else{
                    claw.setPosition(0);
                }
            }
            if(outToggle){
                //closes the claw
                outClaw.setPosition(0.35);
            }else{
                //opens the claw
                outClaw.setPosition(0);
            }

        }
    }
}