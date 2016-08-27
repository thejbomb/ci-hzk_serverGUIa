package tool;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

public class Timer {

    private Thread refreshTime;
    private boolean timerRunning;

    public Timer(Label timerLabel, int time, TimerInterface timerInterface, int type) {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                timerRunning = true;
                int timeLeft = time;
                while (timeLeft > 0) {
                    String time;
                    if (type == 0)
                        time = refreshTimer1(timeLeft);
                    else
                        time = Integer.toString(timeLeft);
                    Platform.runLater(() -> timerLabel.setText(time));
                    timeLeft--;
                    if (timeLeft == 0)
                        timerInterface.takeNotice();
                    Thread.sleep(1000);
                }
                return null;
            }
        };
        refreshTime = new Thread(task);
        refreshTime.start();
    }

    private String refreshTimer1(int timeLeft) {
        int minute = timeLeft / 60;
        int second = timeLeft % 60;
        return minute + ":" + (second > 9 ? second : ("0" + second));
    }

    public void stop() {
        refreshTime.interrupt();
        timerRunning = false;
    }

    public boolean isRunning() {
        return timerRunning;
    }

}
