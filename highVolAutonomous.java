package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;
import java.util.Date;
import java.lang.*;

@Autonomous(name="highVolAutonomous", group="Iterative Opmode")
//@Disabled
public class highVolAutonomous extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor wenchDrive = null;
    private Servo hook = null;
    private double wenchUpPower = 0.5;
    private double wenchDownPower = -0.5;
    private Date date = new Date();
    private long startTime;
    private long end;
    private boolean isLowered = false;
    private boolean isUnlatched = false;
    private modernRoboticsI2colorSensor colorTest = null;



    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        hook = hardwareMap.get(Servo.class, "hookServo");
        wenchDrive = hardwareMap.get(DcMotor.class, "wench");
        colorTest = hwMap.get(modernRoboticsI2colorSensor.class,"color_sensor");

        //run the wench motor using the encoders in the wench motor
        wenchDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //turn on the led for color sensor
        colorTest.enableled(true);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        //print color number
        telemety.addData("color sensor output: ", colorTest.readUsingByte(modernRoboticsI2colorSensor.Register.Color_Number));
        
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        //create variables
        int wenchRaiseOne = 2085;
        startTime = System.currentTimeMillis();
        //zero drive motors
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //zero wench
        wenchDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //start of movement

        //lower robot(raise wench)
        wenchDrive.setTargetPosition(wenchRaiseOne);
        wenchDrive.setPower(-1);
        wenchDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        //unlatch hook
        end = startTime - 250;
        for(int x=0;x<=60000;x++) {
            if (startTime - end < 0.25) {
                hook.setPosition(0);
                end = System.currentTimeMillis();
            } else {
                hook.setPosition(0.5);
                break;                   //break
            }
        }
        hook.setPosition(0.5);


        //collapse wench back into place
        //wenchDrive.RunMode.RUN_TO_POSITION(wenchRaiseTwo);


        //runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        //lower robot
        if (isLowered = false) {
            wenchDrive.setTargetPosition(wenchRaiseOne);
            wenchDrive.setPower(-1);
            wenchDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            isLowered = true;
        }

        //unlatch hook
        if (isUnlatched == false) {
            for(int x=0;x<=60000;x++) {
                if (startTime - end < 0.25) {
                    hook.setPosition(0);
                    end = System.currentTimeMillis();
                } else {
                    hook.setPosition(0.5);
                    break;                   //break
                }

            }
            isUnlatched = true;
        }


    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}

