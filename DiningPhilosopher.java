import java.util.Random;


public class DiningPhilosopher {

    public static void main(String[] args) {
        Semaphore chopsticks[];
        Philosopher philosopher[];

        chopsticks=new Semaphore[5];

        for (int i=0; i<5;i++){
            chopsticks[i] =new Semaphore(1);

        }
        philosopher = new Philosopher[5];

        for(int i=0;i<5;i++)
        {
            philosopher[i] = new Philosopher(i,chopsticks);
            philosopher[i].start();
        }
    }
}
class Semaphore{
    private int value;

    public Semaphore(int value)
    {
        this.value=value;

    }

    public synchronized void p()
    {
        while(value==0)
        {
            try {
                System.out.println("chopStick in use");
                wait();
            }catch (InterruptedException e){}
        }
        value=value-1;
    }
    public synchronized void v()
    {
        value=value+1;
        notify();
    }
}
class Philosopher extends Thread
{
    private int myName;
    private Semaphore chopsticks[];

    public Philosopher(int myName,Semaphore chopsticks[]){
        this.myName=myName;
        this.chopsticks=chopsticks;
    }
    public void run()
    {
        while (true)
        {
            System.out.println("philosopher "+ myName+" thinking");
            try{
                Thread.sleep(new Random().nextInt(100)+50);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Philosopher "+myName+" hungry.");

            chopsticks[myName].p();
            System.out.println("Philosopher takes the chopstick: "+myName);

            chopsticks[(myName+1)%5].p();
            System.out.println("Philosopher takes the chopstick: "+(myName+1));

            System.out.println("Philosophers "+myName+" eating");
            try{
                Thread.sleep(new Random().nextInt(100)+50);
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            chopsticks[myName].v();
            System.out.println("Philosopher releases the chopstick: "+myName);

            chopsticks[(myName+1)%5].v();
            System.out.println("Philosopher releases the chopstick: "+(myName+1));

        }
    }
}
