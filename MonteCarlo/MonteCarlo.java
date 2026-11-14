/* 
This program will estimate the value of pi using a technique called Monte Carlo.
First, we will assume that the radius of the circle is 1 and generate a series of random points as simple (x, y) coordinates that falls within the square circumscribed about the circle.
Of the total number of random points that are generated, some will occur within the circle. We estimate π by performing the following calculation: 
π = 4*(number of points in circle) / total number of points 
*/


import java.util.Scanner;
public class MonteCarlo{
  public static void main(String[]args){
    Scanner scan = new Scanner(System.in);
    boolean cont = true;
    while(cont){ // will repeat this process until user says no
      System.out.println("How many points do you want to be generated?");
      int points = scan.nextInt(); // number of points that will be generated
    
      double[] xCords = new double[points]; // array of the x coordinates
      double[] yCords = new double[points]; // array of the y coordinates
      
      int count = 0; // count of the number of points inside the circle
      
      System.out.print("Here are the list of all generated points: ");
      for(int i=0; i<points; i++){
        xCords[i] = Math.random()*2-1; // generates x coordinate in (-1, 1)
        yCords[i] = Math.random()*2-1; // generates y coordinate in (-1, 1)
        
        System.out.println(i+": ("+xCords[i]+", "+yCords[i]+"), "); // prints the coordinates
        
        if(xCords[i]*xCords[i]+yCords[i]*yCords[i]<=1){ // tests if its inside the circle using formula: (x-a)^2+(y-b)^2=r^2, where (a,b) is center and r is the radius
          count++; // increases number of points inside circle
        }
      }
      
      System.out.println(count+" of the "+ points +" points lie inside the circle."); // prints number of points inside circle
      System.out.println("\u03C0 \u2248 " + 4.0*count/points); // use formula to estimate pi; pi=4*(number of points inside circle)/(total number of points)
      
      System.out.println("Do you want to estimate again? (type 0 for no, anything else for yes)"); // asks user if they want to run the program again
      int stop = scan.nextInt(); // if input 0, then stop program, otherwise repeat
      if(stop==0){
        cont=false;
      }
    }
  }
}