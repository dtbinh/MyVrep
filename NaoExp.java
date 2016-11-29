package myvrepexp;

import abstractrobot.IdManager;
import abstractrobot.JointHelper;
import coppelia.IntW;
import java.util.HashMap;
import java.util.Map;

public class NaoExp {

    MyVrep robot;

    public NaoExp() {
        System.out.println("Connecting with vrep...");
        try {
            robot = new MyVrep("127.0.0.1", 19997);
        } catch (Exception ex) {
            System.out.println("Error=" + ex.getMessage());
            return;
        }
        robot.startSimulation();

        IntW handle = robot.getObjHandle("HeadYaw");
        if (handle == null) {
            System.out.println("Can't get handle");
            return;
        }
        int code = robot.setJointVel(handle, 1.0f);

        robot.sendMsg("ready");
        delay(5000);

        code = robot.setJointPosDeg(handle, 30);
        System.out.println("code=" + code);

        delay(100);
        int val =  robot.getJointPosDeg(handle);
        System.out.println("pos="+val);
//        delay(5000);
//        code = robot.setJointPos(handle, -30);
//        System.out.println("code=" + code);
//
//        delay(5000);
//        code = robot.setJointPos(handle, 0);
//        System.out.println("code=" + code);

        System.out.println("closing...");
        delay(5000);
        robot.sendMsg("Stopping...");
        robot.close();
        System.out.println("done");
    }

    private void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception ex) {

        }
    }

    public static void main(String[] args) {
        new NaoExp();
    }

}
