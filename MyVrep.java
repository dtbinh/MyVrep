import coppelia.FloatW;
import coppelia.IntW;
import coppelia.IntWA;
import coppelia.remoteApi;

public class MyVrep {

    remoteApi vrep;
    int clientID;

    public MyVrep(String ip, int port) throws Exception {
        System.out.println("connecting " + ip + " on " + port + " port");
        vrep = new remoteApi();
        vrep.simxFinish(-1); // just in case, close all opened connections
        clientID = vrep.simxStart(ip, port, true, true, 5000, 5);
        if (clientID == -1) {
//            System.out.println("Failed!");
            throw new Exception("Cant' Connect");
        }
        System.out.println("success: connected");
    }

    public void startSimulation() {
        vrep.simxStartSimulation(clientID, vrep.simx_opmode_blocking);
    }

    public IntW getObjHandle(String name) {
        IntW handle = new IntW(0);

        int code = vrep.simxGetObjectHandle(clientID, name, handle, vrep.simx_opmode_blocking);
        if (code == 0) {
            return handle;
        }
        return null;
    }

    public void sendMsg(String msg) {
        vrep.simxAddStatusbarMessage(clientID, msg, vrep.simx_opmode_oneshot);
    }

    public int setJointPos(IntW handle, int deg) {
        sendMsg("move to " + deg);
        int code = vrep.simxSetJointTargetPosition(clientID, handle.getValue(), deg, vrep.simx_opmode_streaming);
        return code;
    }

    public int setJointPosDeg(IntW handle, int d) {
        sendMsg("move to " + d);
        float deg = (float) (d * Math.PI / 180);
//        System.out.println("rad=" + deg);
        int code = vrep.simxSetJointTargetPosition(clientID, handle.getValue(), deg, vrep.simx_opmode_streaming);
        return code;
    }

    public int getJointPosDeg(IntW handle) {
        FloatW p = new FloatW(2.1f);
      
        int code = vrep.simxGetJointPosition(clientID, handle.getValue(), p, vrep.simx_opmode_oneshot_wait);
        double ps=p.getValue()*180/Math.PI;
//        System.out.println("get: code="+code+" p="+ps +" deg="+ps*180/Math.PI);
        
        
        return (int)ps;
    }

    public int setJointVel(IntW handle, float vel) {
        int code = vrep.simxSetJointTargetVelocity(clientID, handle.getValue(), vel, vrep.simx_opmode_blocking);
        return code;
    }

    public void close() {
        // stop the simulation:
        vrep.simxStopSimulation(clientID, vrep.simx_opmode_blocking);
        // Now close the connection to V-REP:	
        vrep.simxFinish(clientID);
    }
}
