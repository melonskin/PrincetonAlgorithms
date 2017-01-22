package queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
	   public static void main(String[] args)
	   {
		   int k = Integer.parseInt(args[0]);
		   int i2 = 1;
		   RandomizedQueue<String> randq = new RandomizedQueue<String>();
// use reservoir sampling
		   while (!StdIn.isEmpty() && k > 0)
		   {
			   String newString = StdIn.readString();
			   if (i2 <= k ) 
			   {
				   randq.enqueue(newString);  
			   } else {
			   
				   int j = StdRandom.uniform(i2) + 1;
				   if (j <= k)
				   {
					   randq.dequeue(); 
					   randq.enqueue(newString);  
				   }
			   }
			   i2++;
		   }
// use shuffling but with readAllStrings, also works		   
//		   String[] str = StdIn.readAllStrings();
//		   StdRandom.shuffle(str);
//		   for (int i = 0; i < k; i++)
//			   randq.enqueue(str[i]);
		   for (int i = 0; i < k; i++)
		   {
			   System.out.println(randq.dequeue());
		   }
	   }

}
