import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Title: gui calculator (problem set 8.1)
 * Description: creating a calculator using gui
 * Created: 2026-05-19
 * Last Updated: 2026-05-19
 * Author(s): Melody Wu
*/

// TODO: add decimals

public class Main{
	public static void main(String[]args){
		ArithmeticExpressionValidator validator = new ArithmeticExpressionValidator();
		ArithmeticExpressionSolver solver = new ArithmeticExpressionSolver();

		JFrame calc = new JFrame();
		calc.setTitle("CALC (short for calculator)");
		calc.setSize(600, 300);
		calc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// calculator history
		JTextArea history = new JTextArea();
		history.setEditable(false);
		history.setLineWrap(false);
		history.setWrapStyleWord(false);


		JScrollPane scrollPane = new JScrollPane(history);
		scrollPane.setPreferredSize(new Dimension(200, 300));
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		calc.add(scrollPane, BorderLayout.EAST);

		// BASIC CALCULATOR VIEW

		JPanel basic = new JPanel(); // basic calc view
		basic.setLayout(new GridBagLayout());

		// equation inputted into the calc
		basic.add(new JLabel("equation: "), newGBC(0, 0, 1, 1, GridBagConstraints.LINE_START));
		JLabel eqb = new JLabel();
		basic.add(eqb, newGBC(1, 0, 2, 1, GridBagConstraints.LINE_END));

		// answer of the equation inputted
		basic.add(new JLabel("answer: "), newGBC(0, 1, 1, 1, GridBagConstraints.LINE_START));
		JLabel ansb = new JLabel();
		basic.add(ansb, newGBC(1, 1, 2, 1, GridBagConstraints.LINE_END));

		// equal sign that when pressed, computes the equation inputted
		JButton equalb = new JButton("=");
		basic.add(equalb, newGBC(3, 0, 1, 1, GridBagConstraints.CENTER));
		equalb.addActionListener( // adding listener to button
			new ActionListener() {
				public void actionPerformed(ActionEvent e) { // handler
					try{
						validator.validation(eqb.getText());
						try{
							ansb.setText(""+solver.solve(eqb.getText())); // printing the result

							// save result to history
							history.insert(eqb.getText()+" = "+ansb.getText()+"\n", 0);
						} catch(Exception er){
							ansb.setText("Error"); // printing error + popup box
							JOptionPane.showMessageDialog(null, "Error: "+er.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
						}
					} catch(Exception er){
						ansb.setText("Error"); // printing error + popup box
						JOptionPane.showMessageDialog(null, "Error: "+er.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		);

		// delete button
		JButton deleteb = new JButton("\u232B");
		basic.add(deleteb, newGBC(0, 2, 1, 1, GridBagConstraints.CENTER));
		deleteb.addActionListener( // adding listener to button
			new ActionListener() {
				public void actionPerformed(ActionEvent e) { // handler
					String text = eqb.getText();
					if(text.length()!=0){
						eqb.setText(text.substring(0, text.length()-1));
					}
				}
			}
		);

		// all clear button
		JButton clearb = new JButton("AC");
		basic.add(clearb, newGBC(1, 2, 1, 1, GridBagConstraints.CENTER));
		clearb.addActionListener( // adding listener to button
			new ActionListener() {
				public void actionPerformed(ActionEvent e) { // handler
					eqb.setText("");
					ansb.setText("");
				}
			}
		);

		newButton(eqb, basic, "(", 0, 6);
		newButton(eqb, basic, ")", 2, 6);
		newButton(eqb, basic, "%", 2, 2);
		newButton(eqb, basic, "^", 3, 1);
		newButton(eqb, basic, "\u00F7", 3, 2);
		newButton(eqb, basic, "\u00D7", 3, 3);
		newButton(eqb, basic, "-", 3, 4);
		newButton(eqb, basic, "+", 3, 5);
		newButton(eqb, basic, "0", 1, 6);
		newButton(eqb, basic, "1", 0, 5);
		newButton(eqb, basic, "2", 1, 5);
		newButton(eqb, basic, "3", 2, 5);
		newButton(eqb, basic, "4", 0, 4);
		newButton(eqb, basic, "5", 1, 4);
		newButton(eqb, basic, "6", 2, 4);
		newButton(eqb, basic, "7", 0, 3);
		newButton(eqb, basic, "8", 1, 3);
		newButton(eqb, basic, "9", 2, 3);
		newButton(eqb, basic, ".", 3, 6);

		basic.add(new JLabel("basic calculator view"), newGBC(0, 7, 4, 1, GridBagConstraints.LAST_LINE_START));

		// SCIENTIFIC CALC VIEW

		JPanel scientific = new JPanel(); // scientific calc view
		scientific.setLayout(new GridBagLayout());

		// equation inputted into the calc
		scientific.add(new JLabel("equation: "), newGBC(0, 0, 1, 1, GridBagConstraints.LINE_START));
		JLabel eqs = new JLabel();
		scientific.add(eqs, newGBC(1, 0, 3, 1, GridBagConstraints.LINE_END));

		// answer of the equation inputted
		scientific.add(new JLabel("answer: "), newGBC(0, 1, 1, 1, GridBagConstraints.LINE_START));
		JLabel anss = new JLabel();
		scientific.add(anss, newGBC(1, 1, 2, 1, GridBagConstraints.LINE_END));

		// equal sign that when pressed, computes the equation inputted
		JButton equals = new JButton("=");
		scientific.add(equals, newGBC(4, 6, 1, 1, GridBagConstraints.CENTER));
		equals.addActionListener( // adding listener to button
			new ActionListener() {
				public void actionPerformed(ActionEvent e) { // handler
					try{
						validator.validation(eqs.getText());
						try{
							anss.setText(""+solver.solve(eqs.getText())); // printing the result

							// save result to history
							history.insert(eqs.getText()+" = "+anss.getText()+"\n", 0);
						} catch(Exception er){
							anss.setText("Error"); // printing error + popup box
							JOptionPane.showMessageDialog(null, "Error: "+er.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
						}
					} catch(Exception er){
						anss.setText("Error"); // printing error + popup box
						JOptionPane.showMessageDialog(null, "Error: "+er.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		);

		// delete button
		JButton deletes = new JButton("\u232B");
		scientific.add(deletes, newGBC(0, 2, 1, 1, GridBagConstraints.CENTER));
		deletes.addActionListener( // adding listener to button
			new ActionListener() {
				public void actionPerformed(ActionEvent e) { // handler
					String text = eqs.getText();
					if(text.length()!=0){
						eqs.setText(text.substring(0, text.length()-1));
					}
				}
			}
		);

		// all clear button
		JButton clears = new JButton("AC");
		scientific.add(clears, newGBC(1, 2, 1, 1, GridBagConstraints.CENTER));
		clears.addActionListener( // adding listener to button
			new ActionListener() {
				public void actionPerformed(ActionEvent e) { // handler
					eqs.setText("");
					anss.setText("");
				}
			}
		);

		newButton(eqs, scientific, "(", 0, 6);
		newButton(eqs, scientific, ")", 2, 6);
		newButton(eqs, scientific, "%", 2, 2);
		newButton(eqs, scientific, "^", 3, 1);
		newButton(eqs, scientific, "\u00F7", 3, 2); // divide
		newButton(eqs, scientific, "\u00D7", 3, 3); // multiply
		newButton(eqs, scientific, "-", 3, 4);
		newButton(eqs, scientific, "+", 3, 5);
		JButton sqrt = new JButton("\u221Ax");
		scientific.add(sqrt, newGBC(4, 0, 1, 1, GridBagConstraints.CENTER));
		sqrt.addActionListener( // adding listener to button
			new ActionListener() {
				public void actionPerformed(ActionEvent e) { // handler
					eqs.setText(""+eqs.getText()+"\u221A(");
				}
			}
		);
		JButton square = new JButton("x\u00B2");
		scientific.add(square, newGBC(4, 1, 1, 1, GridBagConstraints.CENTER));
		square.addActionListener( // adding listener to button
			new ActionListener() {
				public void actionPerformed(ActionEvent e) { // handler
					eqs.setText(""+eqs.getText()+"^2");
				}
			}
		);
		JButton cube = new JButton("x\u00B3");
		scientific.add(cube, newGBC(4, 2, 1, 1, GridBagConstraints.CENTER));
		cube.addActionListener( // adding listener to button
			new ActionListener() {
				public void actionPerformed(ActionEvent e) { // handler
					eqs.setText(""+eqs.getText()+"^3");
				}
			}
		);
		newButton(eqs, scientific, "log(", 4, 3);
		newButton(eqs, scientific, "ln(", 4, 4);
		newButton(eqs, scientific, "!", 4, 5);
		newButton(eqs, scientific, "0", 1, 6);
		newButton(eqs, scientific, "1", 0, 5);
		newButton(eqs, scientific, "2", 1, 5);
		newButton(eqs, scientific, "3", 2, 5);
		newButton(eqs, scientific, "4", 0, 4);
		newButton(eqs, scientific, "5", 1, 4);
		newButton(eqs, scientific, "6", 2, 4);
		newButton(eqs, scientific, "7", 0, 3);
		newButton(eqs, scientific, "8", 1, 3);
		newButton(eqs, scientific, "9", 2, 3);
		newButton(eqs, scientific, ".", 3, 6);

		scientific.add(new JLabel("scientific calculator view"), newGBC(0, 7, 4, 1, GridBagConstraints.LAST_LINE_START));

		// CONVERSION CALC VIEW

		JPanel convert = new JPanel();
		convert.setLayout(new GridBagLayout());

		// decimal number inputted into the converter
		convert.add(new JLabel("decimal: "), newGBC(0, 0, 1, 1, GridBagConstraints.LINE_START));
		JLabel dec = new JLabel();
		convert.add(dec, newGBC(1, 0, 3, 1, GridBagConstraints.LINE_END));

		// binary / hexadecimal value of the decimal number inputted
		JLabel to = new JLabel("binary: ");
		convert.add(to, newGBC(0, 1, 1, 1, GridBagConstraints.LINE_START));
		JLabel ansc = new JLabel();
		convert.add(ansc, newGBC(1, 1, 4, 1, GridBagConstraints.LINE_END));

		// delete button
		JButton deletec = new JButton("\u232B");
		convert.add(deletec, newGBC(3, 3, 1, 1, GridBagConstraints.CENTER));
		deletec.addActionListener( // adding listener to button
			new ActionListener() {
				public void actionPerformed(ActionEvent e) { // handler
					String text = dec.getText();
					if(text.length()!=0){
						dec.setText(text.substring(0, text.length()-1));
					}
				}
			}
		);

		// all clear button
		JButton clearc = new JButton("AC");
		convert.add(clearc, newGBC(3, 4, 1, 1, GridBagConstraints.CENTER));
		clearc.addActionListener( // adding listener to button
			new ActionListener() {
				public void actionPerformed(ActionEvent e) { // handler
					dec.setText("");
					ansc.setText("");
				}
			}
		);

		newButton(dec, convert, "0", 1, 5);
		newButton(dec, convert, "1", 0, 4);
		newButton(dec, convert, "2", 1, 4);
		newButton(dec, convert, "3", 2, 4);
		newButton(dec, convert, "4", 0, 3);
		newButton(dec, convert, "5", 1, 3);
		newButton(dec, convert, "6", 2, 3);
		newButton(dec, convert, "7", 0, 2);
		newButton(dec, convert, "8", 1, 2);
		newButton(dec, convert, "9", 2, 2);

		// button that lets you change from converting to binary to converting to hexadecimal
		JButton change = new JButton("\u21C4");
		convert.add(change, newGBC(3, 2, 1, 1, GridBagConstraints.CENTER));
		final int[] conv = {0}; // 0 = binary, 1 = hexadecimal
		change.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					conv[0] = (conv[0]+1)%2;
					if(conv[0] == 0){
						to.setText("binary:");
					} else if(conv[0] == 1){
						to.setText("hexadecimal: ");
					}
				}
			}
		);


		// equal sign that when pressed, converts the decimal inputted
		JButton equalc = new JButton("=");
		convert.add(equalc, newGBC(3, 5, 1, 1, GridBagConstraints.CENTER));
		equalc.addActionListener( // adding listener to button
			new ActionListener() {
				public void actionPerformed(ActionEvent e) { // handler
					if(conv[0] ==0){
						ansc.setText(""+toBinary(dec.getText()));
						// save result to history
						history.insert(dec.getText()+" = "+ansc.getText()+"\u2082\n", 0);
					} else if(conv[0] ==1){
						ansc.setText(""+toHexa(dec.getText()));
						history.insert(dec.getText()+" = "+ansc.getText()+"\u2081\u2086\n", 0);
					}
				}
			}
		);

		convert.add(new JLabel("convert calculator view"), newGBC(0, 6, 4, 1, GridBagConstraints.LAST_LINE_START));

		// MENU BAR

		JMenuBar menuBar = new JMenuBar();
		calc.setJMenuBar(menuBar);
		JMenu view = new JMenu("view"); // actual dropdown
		menuBar.add(view);
		JMenuItem b = new JMenuItem("basic calculator view");
		JMenuItem s = new JMenuItem("scientific calculator view");
		JMenuItem c = new JMenuItem("conversion calculator view");
		view.add(b);
		view.add(s);
		view.add(c);

		final JPanel[] currentPanel = {basic};

		b.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					calc.remove(currentPanel[0]);
					calc.add(basic, BorderLayout.CENTER);
					currentPanel[0] = basic;

					calc.revalidate();
					calc.repaint();
				}
			}
		);

		s.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					calc.remove(currentPanel[0]);
					calc.add(scientific, BorderLayout.CENTER);
					currentPanel[0] = scientific;

					calc.revalidate();
					calc.repaint();
				}
			}
		);

		c.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					calc.remove(currentPanel[0]);
					calc.add(convert, BorderLayout.CENTER);
					currentPanel[0] = convert;

					calc.revalidate();
					calc.repaint();
				}
			}
		);

		JMenu hist = new JMenu("history");
		menuBar.add(hist);
		JMenuItem clear = new JMenuItem("clear calculation history");
		JMenuItem save = new JMenuItem("save calcuation history to file");
		hist.add(clear);
		hist.add(save);

		clear.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					history.setText("");
				}
			}
		);

		save.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					FileWriter fWriter = null;
					PrintWriter out = null;

					try{
						fWriter = new FileWriter("history.txt", true);
						out = new PrintWriter(fWriter);

						out.println(history.getText());

						out.close();
					} catch(IOException er){
						JOptionPane.showMessageDialog(null, "Error opening output file: "+er.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		);

		calc.add(basic, BorderLayout.CENTER);
		calc.setVisible(true);

	}

	// creating a new grid bag constraint
	private static GridBagConstraints newGBC(int x, int y, int w, int h, int anchor){
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = x;
		c.gridy = y;
		c.gridwidth = w;
		c.gridheight = h;
		c.anchor = anchor;

		return c;
	}

	/** creates the buttons that you can press on a basic calculator which add to the inputted equation
	 * @param eq the equation that gets modified after pressing the button
	 * @param panel the jpanel that the button is added to
	 * @param label of the button
	 * @param x for the grid bag constraint
	 * @param y for the grid bag constraint
	 */
	private static void newButton(JLabel eq, JPanel panel, String label, int x, int y){
		JButton button = new JButton(label);
		panel.add(button, newGBC(x, y, 1, 1, GridBagConstraints.CENTER));
		button.addActionListener( // adding listener to button
			new ActionListener() {
				public void actionPerformed(ActionEvent e) { // handler
					eq.setText(""+eq.getText()+label);
				}
			}
		);
	}

	public static String toBinary(String x){
		if(x.equals("0")) return "0";
		if(x.isEmpty()) return "";

		// turn x into an int
		long num = 0;
		for(int i=0; i<x.length(); i++){
			num = num*10+x.charAt(i)-'0';
		}

		String binary = "";
		while(num!=0){
			binary=(num%2)+binary;
			num/=2;
		}

		return binary;
	}

	public static String toHexa(String x){
		if(x.equals("0")) return "0";
		if(x.isEmpty()) return "";

		String[] hex = {"A", "B", "C", "D", "E", "F"};
		// turn x into an int
		long num = 0;
		for(int i=0; i<x.length(); i++){
			num = num*10+x.charAt(i)-'0';
		}

		String hexa = "";
		while(num!=0){
			if(num%16 < 10){
				hexa=(num%16)+hexa;
			} else {
				hexa=hex[(int)(num%16-10)]+hexa;
			}
			num /= 16;
		}

		return hexa;
	}
}