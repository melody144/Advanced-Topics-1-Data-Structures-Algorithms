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