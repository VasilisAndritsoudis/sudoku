package Counter;

import java.util.Timer;
import java.util.TimerTask;

public class DigitalClockTimer implements Counter{

    private int minutes;
    private int seconds;

    public DigitalClockTimer(int m, int s){
        seconds = s;
        minutes = m;
    }

    @Override
    public String getTime(){
        String s = (seconds<10?"0":"") + seconds;
        String m = (minutes<10?"0":"") + minutes;
        return m + ":" + s;
    }

    @Override
    public void increase(){
        if (++seconds == 60) {
            seconds = 0;
            if (++minutes == 60)
                minutes = 0;
        }
    }

    @Override
    public void decrease(){
        if (--seconds == -1) {
            if (--minutes == -1)
                seconds = minutes = 0;
            else
                seconds = 59;
        }
    }

    @Override
    public boolean isOver() {
        return seconds == 0 && minutes == 0;
    }

    @Override
    public boolean isLowerThan(int m, int s){
        if (minutes < m)
            return true;
        else if (minutes == m)
            if (seconds < s)
                return true;
            else
                return false;
        return false;
    }
}
