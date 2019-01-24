package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;


@Autonomous(name="highVolAutonomous", group="Iterative Opmode")
public class highVolAutonomous extends OpMode {
    DcMotor MotorL;
    DcMotor MotorR;
    DcMotor wench;
    Servo hook;
    // ColorSensor colSens;


    // Code to run ONCE when the driver hits INIT

    public void init() {
        //init hardwear
        MotorL = hardwareMap.dcMotor.get("mL");
        MotorR = hardwareMap.dcMotor.get("mR");
        wench = hardwareMap.dcMotor.get("wench");
        hook = hardwareMap.servo.get("hookServo");
        // colSens = hardwareMap.colorSensor.get("colSens");



        //MotorL
        MotorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorL.setDirection(DcMotor.Direction.FORWARD);
        //MotorR
        MotorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorR.setDirection(DcMotor.Direction.REVERSE);
        //Wench
        wench.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        //init telemitry
        telemetry.addData("status", "initialized");

    }

    public void init_loop() {


    }

    public void start() {


    }


    public void loop() {
        int wenchRaiseOne = 1600;
        boolean isLowered;
        int move1 = 10000;


        //set servo pos
        hook.setPosition(0);


        //is the bot lowered
        if (wenchRaiseOne <= (wench.getCurrentPosition())-10) {
            isLowered = true;
        } else {
            isLowered = false;
        }

        //raises wench

        if (isLowered == false) {
            wench.setTargetPosition(wenchRaiseOne);
            wench.setPower(-1);
            wench.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }else{
            hook.setPosition(.5);
        }

       //move
        if (isLowered == true) {
            MotorL.setTargetPosition(move1);
            MotorL.setPower(0.5);
            MotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            MotorR.setTargetPosition(move1);
            MotorR.setPower(0.5);
            MotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }


        //pulse positions
        telemetry.addData("encoder ", wench.getCurrentPosition());
        telemetry.addData("time",getRuntime());
    }
}






