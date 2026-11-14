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