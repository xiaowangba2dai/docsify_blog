/**
 * 模拟龟兔赛跑
 */

public class Race implements Runnable{

    private static String winner;

    @Override
    public void run() {
        // 判断比赛是否结束
        for (int i=0; i<=20; i++) {
            if (isGameOver(i)) {
                break;
            }
            System.out.println(Thread.currentThread().getName() + "跑了第" + i + "步");
        }
    }

    // 判断比赛是否结束
    private boolean isGameOver (int step) {
        if (winner != null) {
            return true;
        }

        // 如果当前步数大于等于10，说明比赛结束
        if (step >= 20) {
            winner = Thread.currentThread().getName();
            System.out.println(winner + "赢了");
            return true;
        }
        else {
            return false;
        }
    }

    public static void main(String[] args) {
        Race race = new Race();
        new Thread(race, "乌龟").start();
        new Thread(race, "兔子").start();
    }

}
