import java.util.Random;

//the main program
public class DiningPhilosopher {

    public static void main(String[] args) {
        Semaphore chopsticks[];
        Philosopher philosopher[];

        //Create an array of five Semaphores object reference Handles
        chopsticks=new Semaphore[5];

        //Create five Semaphore objects and assign to the array
        for (int i=0; i<5;i++){
            chopsticks[i] =new Semaphore(1); //Semaphore initial value=1

        }
        //Create an array of five philosopher thread object reference handles
        philosopher = new Philosopher[5];

        //Create and initiate five philosopher Thread objects
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
                wait(); //The calling thread waits until semaphore becomes free
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

    //Executes when a philosopher thread us first created
    public Philosopher(int myName,Semaphore chopsticks[]){
        this.myName=myName;
        this.chopsticks=chopsticks;
    }

    //This is what each philosopher thread executes
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

            chopsticks[myName].p(); //Acquire right chopstick
            System.out.println("Philosopher takes the chopstick: "+myName);

            chopsticks[(myName+1)%5].p(); //Acquire left chopstick
            System.out.println("Philosopher takes the chopstick: "+(myName+1)%5);

            System.out.println("Philosophers "+myName+" eating");
            try{
                Thread.sleep(new Random().nextInt(100)+50);
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            chopsticks[myName].v();// release right chopstick
            System.out.println("Philosopher releases the chopstick: "+myName);

            chopsticks[(myName+1)%5].v();//release left chopstick
            System.out.println("Philosopher releases the chopstick: "+(myName+1)%)5;

        }
    }
}
