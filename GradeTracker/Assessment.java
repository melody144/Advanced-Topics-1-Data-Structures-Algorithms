import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;

/**
 * The class for assessments. Has assessment name, category corresponding to course, score
 * earned, the total possible points, and the due date. Can compare 2 courses based on
 * due date.
 */

public class Assessment{
	private String name;
	public int category; // int corresponds to index
	private double scoreEarned;
	private double totalPoints;
	private int dueYear;
	private int dueMonth;
	private int dueDay;

	public Assessment(String name, int category, double scoreEarned, double totalPoints, int dueYear, int dueMonth, int dueDay){
		this.name = name;
		this.category = category;
		this.scoreEarned = scoreEarned;
		this.totalPoints = totalPoints;
		this.dueYear = dueYear;
		this.dueMonth = dueMonth;
		this.dueDay = dueDay;
	}

	// two assessments are the same if the have the same name, regardless of case
	// method returns true if they're the same, false otherwise
	public boolean equals(Assessment assessment){
		if(name.toLowerCase().equals(assessment.getName().toLowerCase())){
			return true;
		}
		return false;
	}

	// return true if this assessment is due before assessment 2, false otherwise
	public boolean isDueBefore(Assessment assessment2){
		if(this.getDueYear() < assessment2.getDueYear()){
			return true;
		} else if (this.getDueYear() == assessment2.getDueYear()){
			if(this.getDueMonth() < assessment2.getDueMonth()){
				return true;
			} else if (this.getDueMonth() == assessment2.getDueMonth()){
				if(this.getDueDay() < assessment2.getDueDay()){
					return true;
				}
			}
		}

		return false;
	}

	// setters
	public void setName(String name){
		this.name = name;
	}
	public void setCategory(int category){
		this.category = category;
	}
	public void setScoreEarned(double scoreEarned){
		this.scoreEarned = scoreEarned;
	}
	public void setTotalPoints(double totalPoints){
		this.totalPoints = totalPoints;
	}
	public void setDueYear(int dueYear){
		this.dueYear = dueYear;
	}
	public void setDueMonth(int dueMonth){
		this.dueMonth = dueMonth;
	}
	public void setDueDay(int dueDay){
		this.dueDay = dueDay;
	}

	// getters
	public String getName(){
		return name;
	}
	public int getCategory(){
		return category;
	}
	public double getScoreEarned(){
		return scoreEarned;
	}
	public double getTotalPoints(){
		return totalPoints;
	}
	public int getDueYear(){
		return dueYear;
	}
	public int getDueMonth(){
		return dueMonth;
	}
	public int getDueDay(){
		return dueDay;
	}
}