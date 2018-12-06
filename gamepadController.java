package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;


@TeleOp(name="gamepadController", group="Iterative Opmode")
//@Disabled
public class gamepadController extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor wenchDrive = null;
    private Servo hook = null;
    private double servoPosition = 0;
    
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
        wenchDrive = hardwareMap.get(DcMotor.class, "wench");
        hook = hardwareMap.get(Servo.class, "hookServo");
        
        
          //wench motorDrive encoder enabled
        wenchDrive.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS)


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // Setup a variable for each part of the robot controlled by the gamepad
        double leftPower;
        double rightPower;
        boolean wenchPowerUp;
        boolean wenchPowerDown;
        boolean hookLeft;
        boolean hookRight;
        double mPulse;

        //Sets up the gamepad buttons for each robot part variable
        leftPower  = gamepad1.left_stick_y;
        rightPower = gamepad1.right_stick_y;
        wenchPowerUp = gamepad1.y;
        wenchPowerDown = gamepad1.a;
        hookRight = gamepad1.b;
        hookLeft = gamepad1.x;
        
        //track motor pulses on wench
        mPulse = wenchDrive.getCurrentPosition();
        
        // Send calculated power to wheels
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);

        //sets calculated power to the wench motor/HEX motor
        if (wenchPowerUp == true) {
            wenchDrive.setPower(0.5);
        }
        if (wenchPowerDown == true) {
            wenchDrive.setPower(-0.5);
        }
        if (wenchPowerUp == false && wenchPowerDown == false) {
            wenchDrive.setPower(0);
        }

        //conditionals for the servo
        if (hookRight == true) {
            servoPosition = 1;
            hook.setPosition(servoPosition);
        }
        if (hookLeft == true) {
            servoPosition = 0;
        }
        if (hookLeft == false && hookRight == false) {
            servoPosition = 0.5;
        }

        //sets the position data to the servo at this moment
        hook.setPosition(servoPosition);


        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        telemetry.addData("Servo Position:", "(%.2f)", servoPosition);
        //track wench pulse
        telemetry,addData("wench distance:" "(%.2f)", mPulse;
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}



//TO CONNECT THE CONTROLLER TO THE PHONE POWER THE CONTROLLER ON AND PRESS "START" + "A" ON THE CONTROLLER TO USE
//THE NAME "gamepad1" AND "START" + "B" FOR THE NAME "gamepad2"
