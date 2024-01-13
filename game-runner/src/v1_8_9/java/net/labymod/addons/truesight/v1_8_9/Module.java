package net.labymod.addons.truesight.v1_8_9;

public class Module {
    private boolean state = false;
    private final String name;
    private int key;

    public Module(String name, int key) {
        this.name = name;
        this.key = key;
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void toggle() {
        this.setState(!getState());
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return this.name;
    }
}