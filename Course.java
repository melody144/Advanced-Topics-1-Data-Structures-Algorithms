import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;

/**
 * The class for a regular course. Contains the course name, assessment categories with weights,
 * a list of assessments, the grade average in this course + letter grade, as well as GPA
 * in this class. Contains methods to sort assessment list by due date, search assessment
 * list by name, add, delete, and edit assessment list, compute weighted average, letter grade,
 * and GPA, as well as relevant getters and setters.
 */

public class Course{
	private Scanner scan = new Scanner(System.in);

	private String name;

	private String[] categories;
	private int[] weights;

	private ArrayList<Assessment> assessments;

	private double[] averages; // array of the averages in each category
	private double[] totals; // total number of points possible in each category
	private double[] earned; // array of total points earned in each category
	private double average;

	private String letter; // letter grade
	private double gpa;

	public Course(String name, String[] categories, int[] weights, ArrayList<Assessment> assessments){
		this.name = name;

		this.categories = categories;
		this.weights = weights;

		this.assessments = assessments;

		averages = new double[categories.length];
		totals = new double[categories.length];
		earned = new double[categories.length];

		sortAssessment();
		processAssessments(0);
		average = computeWeightedAverage(0);
		calculateLetter();
		calculateGPA();
	}

	/**
	 * sorts the arraylist assessment by due date - assessments that are due first appear first
	 * 
	 * insertion sort
	 */

	public void sortAssessment(){
		for(int i=1; i<assessments.size(); i++){
			Assessment key = assessments.get(i);
			int j = i-1;

			// inserts the assessment at index i to where it should be + fixes array
			while(j>=0 && key.isDueBefore(assessments.get(j))){
				assessments.set(j+1, assessments.get(j));
				j--;
			}

			assessments.set(j+1, key);
		}
	}

	/** 
	 * searches the assessment by name, ignoring case (i.e. if you input THING and the
	 * assessment is called thing, it will return that index)
	 * 
	 * sequential search
	 * 
	 * @param name of assessment you are searching for
	 * @return index of assessment with that name, -1 if not found
	 * 
	 */

	public int searchAssessment(String thing){
		for(int i=0; i<assessments.size(); i++){
			if(assessments.get(i).getName().toLowerCase().equals(thing.toLowerCase())){
				return i;
			}
		}

		return -1;
	}

	// adding an assessment to the course unless it's already there
	public void addAssessment(Assessment assessment){
		for(Assessment a: assessments){
			if(a.equals(assessment)){
				System.out.println("Your course already has this assessment, edit it instead");
				return;
			}
		}
		assessments.add(assessment);
		sortAssessment();
	}

	// deletes an assessment from the course
	public void deleteAssessment(String name){
		int index = searchAssessment(name);
		if(index == -1){
			System.out.println("Assessment not found");
		} else {
			assessments.remove(index);
		}
	}

	// edits an assessment in the course
	public void editAssessment(String name){
		int index = searchAssessment(name);
		if(index == -1){
			System.out.println("Assessment not found");
			return;
		}

		Assessment a = assessments.get(index);

		System.out.println("What would you like to edit? (0 for assessment name, 1 for assessment category, 2 for score earned, 3 for total points, 4 for due year, 5 for due month, and 6 for due day)");
		String editL = scan.nextLine();
		while(!(editL.equals("0") || editL.equals("1") || editL.equals("2") || editL.equals("3") || editL.equals("4") || editL.equals("5") || editL.equals("6"))){
			System.out.println("Invalid input (not an integer between 0 and 6 inclusive), try again");
			editL = scan.nextLine();
		}
		int edit = Integer.parseInt(editL);

		// renaming assignment, unless an assignment already has that name (ignoring case)
		if(edit == 0){
			System.out.println("What would you like to rename your assessment to? (ignoring case)");
			String newName = scan.nextLine();
			boolean goodName = true;
			for(Assessment ax: assessments){
				if(ax.getName().toLowerCase().equals(newName.toLowerCase())){
					goodName = false;
					System.out.println("There already exists an assessment called that");
				}
			}
			if(goodName){
				a.setName(newName);
			}
		// editing category
		} else if (edit == 1){
			System.out.println("What's the new category?");
			for(int i=0; i<categories.length; i++){
				System.out.print(i+" for "+categories[i]+", ");
			}
			boolean badInput = true;
			int newCat = -1;
			while(badInput){
				try{
					newCat = scan.nextInt();
					scan.nextLine();
					if(newCat<0 || newCat >= categories.length){
						System.out.println("Invalid input (not an integer between 0 and " + (categories.length-1) +" inclusive), try again");
					} else {
					badInput = false;
					}
				}
				catch(Exception e){
					System.out.println("Invalid input (not an integer), try again");
					scan.nextLine();
				}
			}
			a.setCategory(newCat);
		// editing score earned
		} else if (edit == 2){
			System.out.println("What score did you actually earn? (enter a nonnegative double)");
			double newScore = -1;
			boolean badInput = true;
			while(badInput){
				try{
					newScore = scan.nextDouble();
					scan.nextLine();
					if(newScore<0){
						System.out.println("Invalid input (not nonnegative), try again");
					} else {
						badInput = false;
					}
				}
				catch(Exception e){
					System.out.println("Invalid input (not a double), try again");
					scan.nextLine();
				}
			}
			a.setScoreEarned(newScore);
		// editing total number of points
		} else if (edit == 3){
			System.out.println("What was the actual total number of points? (enter a positive double)");
			double newTotal = -1;
			boolean badInput = true;
			while(badInput){
				try{
					newTotal = scan.nextDouble();
					scan.nextLine();
					if(newTotal<=0){
						System.out.println("Invalid input (not positive), try again");
					} else {
						badInput = false;
					}
				}
				catch(Exception e){
					System.out.println("Invalid input (not a double), try again");
					scan.nextLine();
				}
			}
			a.setTotalPoints(newTotal);
		// editing year due
		} else if (edit == 4){
			System.out.println("What's the new year it's due? (enter a positive integer)");
			int newYear = -1;
			boolean badInput = true;
			while(badInput){
				try{
					newYear = scan.nextInt();
					scan.nextLine();
					if(newYear<=0){
						System.out.println("Invalid input (not positive), try again");
					} else {
						badInput = false;
					}
				}
				catch(Exception e){
					System.out.println("Invalid input (not an integer), try again");
					scan.nextLine();
				}
			}
			a.setDueYear(newYear);
		// editing month due
		} else if (edit == 5){
			System.out.println("What's the new month it's due? (enter a positive integer less than 13)");
			int newMonth = -1;
			boolean badInput = true;
			while(badInput){
				try{
					newMonth = scan.nextInt();
					scan.nextLine();
					if(newMonth<=0 || newMonth>12){
						System.out.println("Invalid input (not an integer between 1 and 12 inclusive), try again");
					} else {
						badInput = false;
					}
				}
				catch(Exception e){
					System.out.println("Invalid input (not an integer), try again");
					scan.nextLine();
				}
			}
			a.setDueMonth(newMonth);
		// editing day due
		} else if (edit == 6){
			int newDay = 0;
			int[] m31 = {1, 3, 5, 7, 8, 10, 12}; // months w 31 days
			int[] m30 = {4, 6, 9, 11}; // months w 30 days
			// testing if the user entered a valid day
			boolean validDay = false;
			while(!validDay){
				System.out.println("What's the new day it's due? (enter a positive integer)");
				try{
					newDay = scan.nextInt();
					scan.nextLine();
					for(int x: m31){
						if(a.getDueMonth() == x && newDay>0 && newDay <= 31){
							validDay = true;
						}
					}
					for(int x: m30){
						if(a.getDueMonth() == x && newDay>0 && newDay <= 30){
							validDay = true;
						}
					}
					if(a.getDueMonth() == 2){
						if(isLeapYear(a.getDueYear())){
							if(newDay>0 && newDay<=29){
								validDay = true;
							}
						} else {
							if(newDay>0 && newDay<=28){
								validDay = true;
							}
						}
					}
					if(!validDay){
						System.out.println("Invalid input (not within range), try again");
					}
				}
				catch(Exception e){
					System.out.println("Invalid input (not an integer), try again");
					scan.nextLine();
				}
			}
			a.setDueDay(newDay);
		}
	}

	/**
	 * recursively calculates the total points possible and the points earned in each category
	 * 
	 * @param the integer index that we're at
	 */

	private void processAssessments(int index) {
    	if (index == assessments.size()){ 
    		return;
    	}

    	Assessment a = assessments.get(index);
    	int cat = a.getCategory();
    	earned[cat] += a.getScoreEarned();
    	totals[cat] += a.getTotalPoints();

    	processAssessments(index + 1);
	}

	/**
	 * recursively calculates the weighted average in this course - if a section has 0 assignments, the average is 0
	 * 
	 * @param integer index that we're at
	 */

	private double computeWeightedAverage(int index) {
    	if (index == averages.length){
    		return 0;
    	}

    	averages[index] = 0;
    	if (totals[index] != 0) {
        	averages[index] = (double) earned[index] / totals[index] * 100;
    	} else {
    		averages[index] = 0;
    	}

    	return averages[index] * weights[index] / 100.0 + computeWeightedAverage(index + 1);
	}

	// calculate letter grade
	public void calculateLetter(){
		if(average >= 97){
        	letter = "A+";
    	} else if (average >= 93){
    		letter = "A";
    	} else if(average >= 90){
    	    letter = "A-";
    	} else if(average >= 87){
    	    letter = "B+";
    	} else if(average >= 83){
        	letter = "B";
    	} else if(average >= 80){
    	    letter = "B-";
    	} else if(average >= 77){
    	    letter = "C+";
    	} else if(average >= 73){
    	    letter = "C";
    	} else if(average >= 70){
    	    letter = "C-";
    	} else if(average >= 67){
    	    letter = "D+";
    	} else if(average >= 63){
    	    letter = "D";
    	} else if(average >= 60){
    	    letter = "D-";
    	} else {
    	    letter = "F";
    	}
	}

	// calculate gpa of this course
	public void calculateGPA(){
		if(average >= 93){
        	gpa = 4;
    	} else if(average >= 90){
    	    gpa = 3.7;
    	} else if(average >= 87){
    	    gpa = 3.3;
    	} else if(average >= 83){
        	gpa = 3;
    	} else if(average >= 80){
    	    gpa = 2.7;
    	} else if(average >= 77){
    	    gpa = 2.3;
    	} else if(average >= 73){
    	    gpa = 2;
    	} else if(average >= 70){
    	    gpa = 1.7;
    	} else if(average >= 67){
    	    gpa = 1.3;
    	} else if(average >= 63){
    	    gpa = 1;
    	} else if(average >= 60){
    	    gpa = 0.7;
    	} else {
    	    gpa = 0;
    	}
	}

	// setters
	public void setName(String name){
		this.name = name;
	}
	public void setAverage(double average){
		this.average = average;
	}
	public void setLetter(String letter){
		this.letter = letter;
	}
	public void setGPA(double gpa){
		this.gpa = gpa;
	}

	// getters
	public String getName(){
		return name;
	}
	public String[] getCategories(){
		return categories;
	}
	public int[] getWeights(){
		return weights;
	}
	public double getAverage(){
		return average;
	}
	public String getLetter(){
		return letter;
	}
	public double getGPA(){
		return gpa;
	}
	public ArrayList<Assessment> getAssessments(){
		return assessments;
	}

	public String getType(){
		return "Regular";
	}

	/**
	 * tests if this year is a leap year
	 * 
	 * @param year that we want to test
	 * @return true if it's a leap year, false if it's not
	 */
	public static boolean isLeapYear(int year){
		if(year%100 == 0){
			if(year%400 == 0){
				return true;
			} else {
				return false;
			}
		} else {
			if(year%4 == 0){
				return true;
			}
		}
		return false;
	}
}

// subclass of course. does all the same stuff except adds 0.3 to gpa.
class AdvancedCourse extends Course{
	public AdvancedCourse(String name, String[] categories, int[] weights, ArrayList<Assessment> assessments){
		super(name, categories, weights, assessments);
	}

	public String getType(){
		return "Advanced";
	}

	// @Override
	public void calculateGPA(){
		super.calculateGPA();
		setGPA(getGPA()+0.3);
	}
}

// subclass of course. does all the same stuff except adds 0.5 to gpa.
class HonorsCourse extends Course{
	public HonorsCourse(String name, String[] categories, int[] weights, ArrayList<Assessment> assessments){
		super(name, categories, weights, assessments);
	}

	public String getType(){
		return "Honors";
	}

	// @Override
	public void calculateGPA(){
		super.calculateGPA();
		setGPA(getGPA()+0.5);
	}
}