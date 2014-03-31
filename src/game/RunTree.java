package game;

import Run.Tree;

public class RunTree implements Runnable {
	   private Thread t;
	   private int level;
	   private PlayingField pf;
	   private double res;
	   
	   public RunTree(PlayingField pf, int level){
	       this.level = level;
	       this.pf = pf;
	       this.t=null;
	       System.out.println("Creating " +  "t1" );
	   }
	   public void run() {
		  res = -1;
	      System.out.println("Running " +  "t1" );
	      res = Tree.bruteForce(pf, level, 1, 2, level);
	      System.out.println("Thread " +  "t1" + " exiting.");
	   }
	   
	   public double getResult(){
		   return res;
	   }
	   
	   public void start ()
	   {
	      if (t == null)
	      {
	         t = new Thread (this, "name" + "t1");
	         t.start ();
	      }
	   }
	   
	   public Boolean isAlive(){
		   if(t==null) return false;
		   
		   if(t.isAlive()) return true;
		   return false;
	   }

	}