package myvrepexp;

import hardware.MyVrep;
import coppelia.IntW;

public class AstiExp {

    MyVrep robot;

    public AstiExp() {
        System.out.println("Connecting with vrep...");
        try {
            robot = new MyVrep("127.0.0.1", 19997);
        } catch (Exception ex) {
            System.out.println("Error=" + ex.getMessage());
            return;
        }
        robot.startSimulation();
        
        IntW handle = robot.getObjHandle("neckJoint0");
        if (handle == null) {
            System.out.println("Can't get handle");
            return;
        }
        int code = robot.setJointVel(handle, 1.0f);

        robot.sendMsg("ready");
        delay(5000);

        code = robot.setJointPos(handle, 30);
        System.out.println("code=" + code);

        delay(5000);
        code = robot.setJointPos(handle, -30);
        System.out.println("code=" + code);

        delay(5000);
        code = robot.setJointPos(handle, 0);
        System.out.println("code=" + code);

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
        new AstiExp();
    }

}
