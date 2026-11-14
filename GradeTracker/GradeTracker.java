import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;

/**
 * Title: Grade Tracker
 * Description: This is a personal grade tracker for a student. It allows the student 
 * to create and manage multiple courses, each with its own grading schema (assessment 
 * categories and weights). The tracker calculates weighted averages for each course, 
 * assign letter grades, and compute the student’s overall GPA based on the courses they 
 * have entered. The application is robust, supports record editing, deletion, and reports
 * generation. Data is saved so that the tracker restores to its last state when reopened.
 * Created: 2025-09-18
 * Last Updated: 2025-09-18
 * Author(s): Melody Wu
 */

public class GradeTracker{
	public static Scanner scan = new Scanner(System.in);
	public static void main(String[]args){
		FileReader fReader=null;
		Scanner inData=null;
		FileWriter fWriter = null;
		PrintWriter out = null;

		System.out.println("What student's gradebook do you want to open?");
		String studentName = scan.nextLine();

		// making a file if it doesn't exist already
		try{
			fWriter= new FileWriter(studentName+".gdtr", true); 
			out = new PrintWriter(fWriter);
		}
		catch(IOException e){
			System.out.println("Error opening output file: " + e);
			System.exit(1);
		}

		try{
			fWriter.close();
		}
		catch(IOException e){
			System.out.println("Error closing files: " + e);
			System.exit(1);
		}

		//file reader
		try{
			fReader = new FileReader(studentName+".gdtr");
			inData = new Scanner(fReader);
		}
		catch(IOException e){
			System.out.println("Error opening input file: " +e);
			System.exit(1);
		}

		ArrayList<String> lines = new ArrayList<>();

		// copies down all the lines in the original file
		while(inData.hasNext()){
			String line = inData.nextLine();
			lines.add(line);
		}

		// file closer
		try{
			fReader.close();
		}
		catch(IOException e){
			System.out.println("Error closing files: " + e);
			System.exit(1);
		}

		ArrayList<Course> courses = new ArrayList<>(); // ArrayList of the student's courses

		String name = "";
		int type = -1;
        String[] categories = new String[0];
        int[] weights = new int[0];
        ArrayList<Assessment> assessments = new ArrayList<>();
        double average = 0;
        String letter = "";
        double gpa = 0;

        boolean isAssessment = false;

        // transferring the information from the file to the ArrayList
		for(String string: lines){
			if(string.isEmpty()){
				continue;
			}

			// sets the variables to create a new course based on what the line says
			// course name
			if(string.startsWith("COURSE:")){
				name = string.substring(7).trim();
			// course type
			} else if(string.startsWith("TYPE:")){
				String kind = string.substring(5).trim();

				if(kind.toLowerCase().equals("regular")){
					type = 0;
				} else if (kind.toLowerCase().equals("advanced")){
					type = 1;
				} else if (kind.toLowerCase().equals("honors")){
					type = 2;
				}
			// assessment categories
			} else if(string.startsWith("CATEGORIES:")){
				String[] data = string.substring(12).split(", "); // splits the string into each individual piece fo data
				categories = new String[data.length];
				weights = new int[data.length];

				for(int i=0; i<data.length; i++){
					int index = data[i].indexOf("|");
					// if the string doesn't contain |, or has an empty string before it, then set the category name to be an empty string
					if(index <= 0){
						categories[i] = "";
					// if the string after | is empty, then set the weight to equal 0
					} else if (index == data[i].length()){
						weights[i] = 0;
					// otherwise, separate the string into the category name and weight
					} else {
						categories[i] = data[i].substring(0, index);
						weights[i] = Integer.parseInt(data[i].substring(index+1));
					}
				}
			// if the string says ASSESSMENTS:, then we will continue to the next line and start reading the assessments
			} else if(string.startsWith("ASSESSMENTS:")){
				assessments = new ArrayList<>();
				isAssessment = true;
			// course average
			} else if(string.startsWith("AVERAGE:")){
				average = Double.parseDouble(string.substring(8).trim());
				isAssessment = false; // ends reading assessments
			// course letter grade
			} else if(string.startsWith("LETTER GRADE:")){
				letter = string.substring(13).trim();
			// course gpa
			} else if(string.startsWith("GPA:")){
				gpa = Double.parseDouble(string.substring(4).trim());
			// making the course
			} else if(string.startsWith("ENDCOURSE")){
				// setting the right type for the course
				Course course = null;
				if(type == 0){
					course = new Course(name, categories, weights, assessments);
				} else if (type == 1){
					course = new AdvancedCourse(name, categories, weights, assessments);
				} else if (type == 2){
					course = new HonorsCourse(name, categories, weights, assessments);
				} else {
					course = new Course(name, categories, weights, assessments); // makes it a regular class by default
				}
				courses.add(course);

				// reset variables
				name = "";
				type = -1;
        		categories = new String[0];
        		weights = new int[0];
        		assessments = new ArrayList<>();
        		average = 0;
        		letter = "";
        		gpa = 0;

        	// reading all the assessments
			} else if(isAssessment){
				// set default variables
				String[] data = string.split("; "); // splits the string into an array with each individual info
				String aName = "";
				String dueDate = "";
				double scoreEarned = 0;
				double totalPoints = 0;
				String category = "";
				String[] date = null;
				// default day is 2025-09-19
				int dueYear = 2025;
				int dueMonth = 9;
				int dueDay = 19;

				// sets variables based on the array - checking for errors
				if(data.length > 0){
					aName = data[0].trim();
					if(data.length > 1){
						dueDate = data[1].trim();
						date = dueDate.split("-");
						if(date.length > 0){
							dueYear = Integer.parseInt(date[0].trim());
							if(date.length > 1){
								dueMonth = Integer.parseInt(date[1].trim());
								if(date.length > 2){
									dueDay = Integer.parseInt(date[2].trim());
								}
							}
						}
						if(data.length > 2){
							scoreEarned = Double.parseDouble(data[2].trim());
							if(data.length > 3){
								totalPoints = Double.parseDouble(data[3].trim());
								if(data.length > 4){
									category = data[4].trim();
								}
							}
						}
					}
				}

				// sets the category of the assessment
				int cat = -1;
				for(int i=0; i<categories.length; i++){
					if(category.equals(categories[i])){
						cat = i;
						break;
					}
				}

				// make the assessment and add it to the list
				Assessment a = new Assessment(aName, cat, scoreEarned, totalPoints, dueYear, dueMonth, dueDay);
				assessments.add(a);
			}
		}

		// while loop so you can edit your course list for however long you want
		boolean cont = true;
		while(cont){
			System.out.println("What do you want to do? (-1 for nothing, 0 to add course, 1 to delete course, 2 to edit course)");
			String xL = scan.nextLine();
			while(!(xL.equals("-1") || xL.equals("0") || xL.equals("1") || xL.equals("2"))){
				System.out.println("Invalid input (not an integer between -1 and 2 inclusive), try again");
				xL = scan.nextLine();
			}
			int x = Integer.parseInt(xL);
			// add a course
			if(x==0){
				addCourse(courses);
			// delete a course
			} else if (x==1){
				System.out.println("Name of course you want to delete:");
				String del = scan.nextLine();
				deleteCourse(del, courses);
			// edit a course
			} else if (x==2){
				System.out.print("Name of course you want to edit:");
				String ed = scan.nextLine();
				int ind = searchCourses(ed, courses);
				if(ind == -1){
					System.out.println("Course not found.");
				} else {
					Course co = courses.get(ind); // course we are editing
					System.out.println("What do you want to do? (0 to add assessment, 1 to delete assessment, 2 to edit assessment, 3 to change course name) (if you want to edit course type, delete the course and make it again)");
					String x1L = scan.nextLine();
					while(!(x1L.equals("0") || x1L.equals("1") || x1L.equals("2") || x1L.equals("3"))){
						System.out.println("Invalid input (not an integer between 0 and 3 inclusive), try again");
						x1L = scan.nextLine();
					}
					int x1 = Integer.parseInt(x1L);
					// add an assignment
					if(x1==0){
						co.addAssessment(makeAses(co.getCategories().length));
					// delete an assignment
					} else if (x==1){
						System.out.println("Name of assessment you want to delete:");
						String del = scan.nextLine();
						co.deleteAssessment(del);
					// edit an assignment
					} else if (x==2){
						System.out.print("Name of assessment you want to edit:");
						String edi = scan.nextLine();
						co.editAssessment(edi);
					// renaming the course
					} else if (x==3){
						System.out.println("What do you want to rename the course to? (ignoring case)");
						String s = scan.nextLine();
						co.setName(s);
					}
				}
			}

			// asking user if they want to do this again
			System.out.println("Enter 0 to edit some more and 1 to stop");
			String endL = scan.nextLine();
			while(!(endL.equals("0") || endL.equals("1"))){
				System.out.println("Invalid input (not an integer between 0 and 1 inclusive), try again");
				endL = scan.nextLine();
			}
			int end = Integer.parseInt(endL);
			if(end==1){
				cont = false;
			}
		}

		// file writer - rewrites the courses into the same file
		try{
			fWriter= new FileWriter(studentName+".gdtr"); 
			out = new PrintWriter(fWriter);
		}
		catch(IOException e){
			System.out.println("Error opening output file: " + e);
			System.exit(1);
		}

		// writing the end file - course report
		for(Course cour: courses){
			out.println("COURSE: " + cour.getName());

			out.println("TYPE: " + cour.getType());

			out.print("CATEGORIES: ");
			String[] cats = cour.getCategories();
			int[] distribs = cour.getWeights();
			for(int i=0; i<cats.length-1; i++){
				out.print(cats[i]+"|"+distribs[i]+", ");
			}
			out.println(cats[cats.length-1]+"|"+distribs[cats.length-1]);

			out.println("ASSESSMENTS:");
			ArrayList<Assessment> assess = cour.getAssessments();
			for(int i=0; i<assess.size(); i++){
				Assessment as = assess.get(i);
				out.println(as.getName()+"; "+as.getDueYear()+"-"+as.getDueMonth()+"-"+as.getDueDay()+"; "+as.getScoreEarned()+"; "+as.getTotalPoints()+"; "+cats[as.getCategory()]);
			}

			out.println("AVERAGE: " + cour.getAverage());
			out.println("LETTER GRADE: " + cour.getLetter());
			out.println("GPA: " + cour.getGPA());
			out.println("ENDCOURSE");

			out.println();
		}

		// file closer
		try{
			fWriter.close();
		}
		catch(IOException e){
			System.out.println("Error closing files: " + e);
			System.exit(1);
		}

		// file writer - writes the summary file
		try{
			fWriter = new FileWriter(studentName+"summary.gdtr");
			out = new PrintWriter(fWriter);
		}
		catch(IOException e){
			System.out.println("Error opening output file: " + e);
			System.exit(1);
		}

		out.println("STUDENT SUMMARY");
		for(Course c: courses){
			out.println("COURSE: " + c.getName() + "; " + c.getAverage() + "; " + c.getLetter());
		}
		out.println("OVERALL GPA: " + calculateGPA(0, courses));
		out.println("ENDSUMMARY");

		try{
			fWriter.close();
		}
		catch(IOException e){
			System.out.println("Error closing files: " + e);
			System.exit(1);
		}
	}

	/**
	 * recursively calculates the student's gpa over all courses
	 * 
	 * @param the integer index that we're at, and the ArrayList of courses
	 * @return the weighted GPA - returns 4 if there are no courses
	 */

	public static double calculateGPA(int index, ArrayList<Course> courses){
		if(courses.size() == 0){
			return 0;
		}

		if (index == courses.size()){
    		return 0;
    	}

    	return (double) courses.get(index).getGPA() / courses.size() + calculateGPA(index + 1, courses);
	}

	/**
	 * this method allows you to add a course to the ArrayList courses
	 * 
	 * @param ArrayList courses
	 */

	public static void addCourse(ArrayList<Course> courses){
		System.out.println("Insert necessary information to add a course");

		// making the course name
		System.out.println("Course name (ignoring case):");
		String cName = scan.nextLine();

		// if the course already exists, exits - compares name disregarding case
		for(Course co: courses){
			if(cName.toLowerCase().equals(co.getName().toLowerCase())){
				System.out.println("This course already exists, edit it instead");
				return;
			}
		}

		// making the course type
		System.out.println("Course type (0 for regular, 1 for advanced, 2 for honors):");
		String cTypeL = scan.nextLine();
		while(!(cTypeL.equals("0") || cTypeL.equals("1") || cTypeL.equals("2"))){
			System.out.println("Invalid input (not an integer between 0 and 2 inclusive), try again");
			cTypeL = scan.nextLine();
		}
		int cType = Integer.parseInt(cTypeL);

		// making the categories
		System.out.println("Assessment categories: ");
		System.out.println("Insert #0 category name:");
		String cat = scan.nextLine();
		ArrayList<String> cCats = new ArrayList<>(); // ArrayList of assessment categories
		cCats.add(cat);
		int count = 1;

		// lets you add as many categories as you want
		System.out.println("Want to add more categories? (0 for yes, 1 for no)");
		String yL = scan.nextLine();
		while(!(yL.equals("0") || yL.equals("1"))){
			System.out.println("Invalid input (not an integer between 0 and 1 inclusive), try again");
			yL = scan.nextLine();
		}
		int y = Integer.parseInt(yL);
		boolean add = true;
		if(y==0){
			add = true;
		} else if (y==1){
			add = false;
		}
		// adding more categories
		while(add){
			System.out.println("Insert #" + count + " category name:");
			cat = scan.nextLine();
			cCats.add(cat);
			System.out.println("Want to add more categories? (0 for yes, 1 for no)");
			yL = scan.nextLine();
			while(!(yL.equals("0") || yL.equals("1"))){
				System.out.println("Invalid input (not an integer between 0 and 1 inclusive), try again");
				yL = scan.nextLine();
			}
			y = Integer.parseInt(yL);
			if(y==0){
				add = true;
				count++;
			} else if (y==1){
				add = false;
			}
		}

		// gets the weights of each category
		int[] cWeights = new int[cCats.size()];
		System.out.println("Insert " + cCats.size() + " positive integers that sum to 100 for the weights");
		int weightSum = 0;
		// repeating until user gives the weights so that total sum = 100
		while(weightSum != 100){
			weightSum=0;
			for(int i=0; i<cCats.size(); i++){
				int z = -1; // weight of the category
				boolean badInput = true; // repeating until user inputs positive integer
				while(badInput){
					try{
						System.out.print("#" + i+": ");
						z = scan.nextInt();
						scan.nextLine();
						if(z<=0){
							System.out.println("That's not positive, try again");
						} else {
							badInput = false;
						}
					}
					catch(Exception e){
						System.out.println("Invalid input (not an integer), try again");
						scan.nextLine();
					}
				}
				cWeights[i] = z;
				weightSum+=z;
				System.out.println("");
			}
			if(weightSum != 100){
				System.out.println("Total doesn't equal 100, input the weights again");
			}
		}

		// list of assessment of the courses
		ArrayList<Assessment> ases = new ArrayList<>();
		System.out.println("Assessments:");
		ases.add(makeAses(cCats.size()));

		// lets you add as many assessments as you want
		System.out.println("Want to add more assessments? (0 for yes, 1 for no)");
		yL = scan.nextLine();
		while(!(yL.equals("0") || yL.equals("1"))){
			System.out.println("Invalid input (not an integer between 0 and 1 inclusive), try again");
			yL = scan.nextLine();
		}
		y = Integer.parseInt(yL);
		boolean more = true;
		if(y==0){
			more = true;
		} else if (y==1){
			more = false;
		}
		// adding more assessments
		while(more){
			ases.add(makeAses(cCats.size()));
			System.out.println("Want to add more assessments? (0 for yes, 1 for no)");
			yL = scan.nextLine();
			while(!(yL.equals("0") || yL.equals("1"))){
				System.out.println("Invalid input (not an integer between 0 and 1 inclusive), try again");
				yL = scan.nextLine();
			}
			y = Integer.parseInt(yL);
			if(y==0){
				more = true;
			} else if (y==1){
				more = false;
			}
		}

		// turning the ArrayList of categories into an array
		String[] categories = new String[cCats.size()];
		for(int i=0; i<cCats.size(); i++){
			categories[i] = cCats.get(i);
		}

		// making the right course type
		Course course=null;
		if(cType == 0){
			course = new Course(cName, categories, cWeights, ases);
		} else if (cType == 1){
			course = new AdvancedCourse(cName, categories, cWeights, ases);
		} else if (cType ==2){
			course = new HonorsCourse(cName, categories, cWeights, ases);
		}
		courses.add(course);
	}

	/**
	 * this method lets you create a new assessment
	 * 
	 * @param number of categories that the user can choose from
	 */
	public static Assessment makeAses(int catSize){
		// making assessment name
		System.out.println("Assessment name (ignoring case):");
		String asName = scan.nextLine();

		// making the category of the assessment
		int cat = -1;
		boolean badInput = true;
		System.out.println("Insert integer between 0 and " + (catSize-1) + " inclusive for the category (the numbers correspond to the order you entered your categories in)");
		// repeating until user inputs integer within range
		while(badInput){
			try{
				cat = scan.nextInt();
				scan.nextLine();
				if(cat<0 || cat >= catSize){
					System.out.println("Invalid input (not an integer between 0 and " + (catSize-1) + " inclusive), try again");
				} else {
					badInput = false;
				}
			}
			catch(Exception e){
				System.out.println("Invalid input (not an integer), try again");
				scan.nextLine();
			}
		}

		// making the score earned
		double score = -1;
		badInput = true;
		System.out.println("Insert nonnegative double for score earned:");
		// repeating until user inputs nonnegative double
		while(badInput){
			try{
				score = scan.nextDouble();
				scan.nextLine();
				if(score<0){
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

		// making the total number of points
		double points = -1;
		badInput = true;
		System.out.println("Insert positive double for total points:");
		// repeating until user inputs positive double
		while(badInput){
			try{
				points = scan.nextDouble();
				scan.nextLine();
				if(points<=0){
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

		// making the year due
		int year = -1;
		badInput = true;
		System.out.println("Insert postive integer for year due:");
		while(badInput){
			try{
				year = scan.nextInt();
				scan.nextLine();
				if(year<=0){
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

		// making the month due
		int month = -1;
		badInput = true;
		System.out.println("Insert postive integer for month due (1 = January, 2 = February, etc.):");
		while(badInput){
			try{
				month = scan.nextInt();
				scan.nextLine();
				if(month<1 || month>12){
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

		// making the day due
		int day = 0;
		int[] m31 = {1, 3, 5, 7, 8, 10, 12}; // months w 31 days
		int[] m30 = {4, 6, 9, 11}; // months w 30 days
		boolean validDay = false; // making sure user inputs a valid day for the month
		System.out.println("Insert postive integer for day due: ");
		while(!validDay){
			try{
				day = scan.nextInt();
				scan.nextLine();

				// testing if it's a valid day
				for(int x: m31){
					if(month == x && day>0 && day <= 31){
						validDay = true;
					}
				}
				for(int x: m30){
					if(month == x && day>0 && day <= 30){
						validDay = true;
					}
				}
				if(month == 2){
					if(isLeapYear(year)){
						if(day>0 && day<=29){
							validDay = true;
						}
					} else {
						if(day>0 && day<=28){
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
		
		// making + returning new assessment
		Assessment a = new Assessment(asName, cat, score, points, year, month, day);
		return a;
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

	/**
	 * searches the ArrayList courses for a course with the name thing
	 * 
	 * ignores case, so if you input THING and the name of the course is thing, it will
	 * return that index
	 * 
	 * @param name of course we're searching for and the ArrayList that we're searching in
	 * @return index of where that course is in the ArrayList (-1 if it's not in it)
	 */
	public static int searchCourses(String thing, ArrayList<Course> courses){
		for(int i=0; i<courses.size(); i++){
			if(courses.get(i).getName().toLowerCase().equals(thing.toLowerCase())){
				return i;
			}
		}

		return -1;
	}

	/**
	 * method to delete a course from the ArrayList courses
	 * 
	 * @param name of course we want to delete and ArrayList that we're deleting it from
	 */
	public static void deleteCourse(String name, ArrayList<Course> courses){
		int index = searchCourses(name, courses);
		if(index == -1){
			System.out.println("Assessment not found");
		} else {
			courses.remove(index);
		}
	}
}
