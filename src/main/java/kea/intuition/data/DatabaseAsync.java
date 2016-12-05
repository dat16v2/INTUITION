package kea.intuition.data;

import kea.intuition.Intuition;

public class DatabaseAsync implements Runnable{
    @Override
    public void run() {
        Intuition.Config.setDb(new Database());
    }

}