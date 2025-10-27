package org.firstinspires.ftc.teamcode.robot;
//judy tu was here
//so was z :3




import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="SPINNER ")
public class SPINNER extends LinearOpMode {
    //motors (view from back in terms of L & R)
    private DcMotor RightFront = null;
    public boolean intakeToggle, outToggle, moveOutWrist, readyToOutGrip, gripBackDown, eviltog = false;

    public void runOpMode() throws InterruptedException {


        RightFront = hardwareMap.dcMotor.get("spinner");
        RightFront.setDirection(DcMotor.Direction.REVERSE);

        //controller = new PIDController(p, i, d);
        Gamepad currentGamepad1 = new Gamepad();
        Gamepad previousGamepad1 = new Gamepad();
        waitForStart();
        while (opModeIsActive()) {
            telemetry.update();

            previousGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);

            // sets power after certain deadzone (meant to test motors and dynamic power)
            if (gamepad1.left_stick_x > 0.2 || gamepad1.left_stick_x < -0.2 || gamepad1.left_stick_y > 0.2 || gamepad1.left_stick_y < -0.2){
                RightFront.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x);
            }


            if (currentGamepad1.x && !previousGamepad1.x) {
                intakeToggle = !intakeToggle;
            }

            if(gamepad1.x){
                if (intakeToggle) {
                    RightFront.setPower(1);
                }else{
                    RightFront.setPower(0);
                }
            }

        }
    }
}
