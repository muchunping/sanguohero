package cn.igame.sanguoheros.model;

public abstract class TurnActor extends Actor implements ActionTurner {
    protected int duration;

    protected TurnActor(String name) {
        super(name);
    }


    @Override
    public int getLeaveTime() {
        return 0;
    }

    @Override
    public int leave(int time) {
        return 0;
    }
}
