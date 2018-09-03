package cn.igame.sanguoheros.model;

public abstract class TurnActor extends Actor implements ActionTurner {
    private int duration;
    private int leaveTime;

    TurnActor(String name) {
        super(name);
    }

    @Override
    public int getLeaveTime() {
        return leaveTime;
    }

    @Override
    public int leave(int time) {
        int cTime = leaveTime -= time;
        if (cTime <= 0) {
            leaveTime = duration;
        }
        return cTime;
    }

    void setSpeed(int speed) {
        duration = (int) (1750 - 2.5 * speed);
    }
}
