package facialgestures;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.*;

import Core.Publisher;

public class Gui extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static Model model;
	private final int PORT = 1598;
	private JRadioButton radioButton;
	protected JLabel labelPublishPort;
	private final JButton buttonConnect = new JButton("run");
	static String engageIcon = "Engagement";
	static String shortTermIcon = "Short Term Excitement";
	static String longTermIcon = "Long Term Excitement";
	static String meditateIcon = "Meditation";
	static String frustrateIcon = "Frustration";

	static String blinkTrue = "Blink_0";
	static String leftWinkTrue = "LWink_0";
	static String rightWinkTrue = "RWink_0";
	static String lookLeftTrue = "LookL_0";
	static String lookRightTrue = "LookR_0";

	static String leftSmirk = "Left Smirk";
	static String rightSmirk = "Right Smirk";
	static String raiseBrow = "Raise Brow";
	static String furrowBrow = "Furrow Brow";
	static String smile = "Smile";
	static String laugh = "Laugh";
	static String clench = "Clench";
	JLabel gifIcon;
	HashMap<String, Integer> listOfExpressions = new HashMap<>();
	ArrayList<Double> arrayList = new ArrayList<Double>();
	JSlider slider;

	private Eye leftOfEye = new Eye(300 - 50, 100, 50, 20);
	private Eye rightOfEye = new Eye(300 + 58, 100, 50, 20);
	private VectorForEye position = new VectorForEye(300, 0);
	public int mode = 0;// 1, 2, 3, 4, 5
	public JPanel expressive_bin;

	private Component createPanelSouth() {
		JPanel labels = new JPanel();
		labels.setBackground(Color.GRAY);
		labels.add(new JLabel("  Publishing at port: "));
		labelPublishPort = new JLabel("" + PORT);
		labels.add(labelPublishPort);
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.CYAN);
		panel.add(labels, BorderLayout.WEST);
		panel.add(buttonConnect, BorderLayout.EAST);
		buttonConnect.addActionListener(this);
		buttonConnect.setEnabled(true);
		return panel;
	}

	public JRadioButton radio_button(String name, int y_axis) {
		JRadioButton radioButton = new JRadioButton(name);
		radioButton.setBounds(158, y_axis, 21, 23);
		return radioButton;
	}

	public JLabel label(String name, int x_axis, int y_axis) {
		JLabel label = new JLabel(name);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(x_axis, y_axis, 100, 25);
		return label;
	}

	public JSlider addSlider(JLabel labelNum, int x_axis, int y_axis, int val) {
		JSlider slider = new JSlider();
		slider.setBounds(x_axis, y_axis, 120, 50);
		slider.setMaximum(10);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				double value = ((JSlider) e.getSource()).getValue() * 0.1;
				System.out.println(labelNum.getText());
				if (((JSlider) e.getSource()).getValue() >= 5) {
					labelNum.setIcon(createImageIcon(labelNum.getText() + ".gif"));
				} else {
					labelNum.setIcon(createImageIcon("Neutral" + ".gif"));
				}
				labelNum.setVisible(true);
				labelNum.setBounds(x_axis + 116, y_axis + 10, 40, 30);
				arrayList.set(5, value);
			}
		});
		return slider;
	}

	public Gui() {
		expressionToIndexMapping();
		for(int i=0;i<17;i++)
			arrayList.add(0.0);
    model = new Model(new FacialDataGenerator(), new Publisher(PORT));
    this.setBackground(Color.WHITE);
    this.setLayout(new BorderLayout());
    this.add(createPanelSouth(), BorderLayout.SOUTH);
    Dimension screen = getToolkit().getScreenSize();
    this.setSize(screen.width / 2, 3 * screen.height / 4);
    this.setLocation((screen.width - getSize().width) / 2, (screen.height - getSize().height) / 2);
   
    expressive_bin = new JPanel()
    		 {
    		    	@Override
    		        public void paintComponent(Graphics g) {
    		            super.paintComponent(g);
    		            if (mode == 1) {
    		            	leftOfEye.draw(g, position, mode, false, Color.YELLOW);
    		                rightOfEye.draw(g, position, mode, false, Color.YELLOW);
    		            }
    		            else if (mode == 2) {
    		            	leftOfEye.draw(g, position, mode, false, Color.YELLOW);
    		                rightOfEye.draw(g, new VectorForEye(357, 100), mode, true, Color.WHITE);
    		            }
    		            else if (mode == 3) {
    		            	leftOfEye.draw(g, new VectorForEye(249, 100), mode, true, Color.WHITE);
    		                rightOfEye.draw(g, position, mode, false, Color.YELLOW);
    		            }
    		            else if(mode == 4){
    		            	leftOfEye.draw(g, new VectorForEye(0, 0), mode, true, Color.WHITE);
    		                rightOfEye.draw(g, new VectorForEye(0, 0), mode, true, Color.WHITE);
    		            }
    		            else if(mode == 5){
    		            	leftOfEye.draw(g, new VectorForEye(700, 0), mode, true, Color.WHITE);
    		                rightOfEye.draw(g, new VectorForEye(700, 0), mode, true, Color.WHITE);
    		            }
    		            else {
    		            	leftOfEye.draw(g, new VectorForEye(249.5, 99), mode, true, Color.WHITE);
    		                rightOfEye.draw(g, new VectorForEye(358, 99), mode, true, Color.WHITE);
    		            }
    		        }
    		    };
    expressive_bin.setBounds(0, 28, 331, 497);
    expressive_bin.setLayout(null);
    expressive_bin.setBackground(Color.RED);
    
    JLabel lblBlink = label("Blink", 16, 31);
    expressive_bin.add(lblBlink);
    JLabel lblLeftWink = label("Left Wink", 16, 67);
    expressive_bin.add(lblLeftWink);
    JLabel lblRightWink = label("Right Wink", 16, 103);
    expressive_bin.add(lblRightWink);
    JLabel lblLookLeft = label("Look Left", 16, 139);
    expressive_bin.add(lblLookLeft);
    JLabel lblLookRight = label("Look Right", 16, 175);
    expressive_bin.add(lblLookRight);
 
    JRadioButton radioButtonBlink = radio_button("Blink_0", 33);
    expressive_bin.add(radioButtonBlink);
    
    JRadioButton radioButtonLWink = radio_button("LWink_0", 68);
    expressive_bin.add(radioButtonLWink);
    
    JRadioButton radioButtonRwink = radio_button("RWink_0", 104);
    expressive_bin.add(radioButtonRwink);
    
    JRadioButton radioButtonLLeft = radio_button("LookL_0", 140);
    expressive_bin.add(radioButtonLLeft);
    
    JRadioButton radioButtonLRight = radio_button("LookR_0", 176);
    expressive_bin.add(radioButtonLRight);
    
    ButtonGroup group1 = new ButtonGroup();
    group1.add(radioButtonBlink);
    group1.add(radioButtonLWink);
    group1.add(radioButtonRwink);
    group1.add(radioButtonLLeft);
    group1.add(radioButtonLRight);
    
    radioButtonBlink.addActionListener(this);
    radioButtonLWink.addActionListener(this);
    radioButtonRwink.addActionListener(this);
    radioButtonLLeft.addActionListener(this);
    radioButtonLRight.addActionListener(this);
    
    JPanel expressive_cont = new JPanel();
    expressive_cont.setBounds(334, 28, 339, 497);
    expressive_cont.setLayout(null);
    
    JLabel lblLeftSmirk = label("Left Smirk", 16, 25);
    expressive_cont.add(lblLeftSmirk);
    JLabel lblRightSmirk = label("Right Smirk", 16, 61);
    expressive_cont.add(lblRightSmirk);
    JLabel lblRaiseBurrow = label("Raise Brow", 16, 97);
    expressive_cont.add(lblRaiseBurrow);
    JLabel lblFurrowBrow = label("Furrow Brow", 16, 137);
    expressive_cont.add(lblFurrowBrow);
    JLabel lblSmile = label("Smile", 16, 173);
    expressive_cont.add(lblSmile);
    JLabel lblLaugh = label("Laugh", 16, 209);
    expressive_cont.add(lblLaugh);
    JLabel lblClench = label("Clench", 16, 243);
    expressive_cont.add(lblClench);
    
    JLabel num = label("",215, 25);
    num.setText("Left Smirk");
    num.setVisible(false);
    expressive_cont.add(num);
    JLabel num1 = label("", 215, 65);
    num1.setText("Right Smirk");
    num1.setVisible(false);
    expressive_cont.add(num1);
    JLabel num2 = label("", 215, 100);
    num2.setText("Raise Brow");
    num2.setVisible(false);
    expressive_cont.add(num2);
    JLabel num3 = label("", 215, 135);
    num3.setText("Furrow Brow");
    num3.setVisible(false);
    expressive_cont.add(num3);
    JLabel num4 = label("", 215, 170);
    num4.setText("Smile");
    num4.setVisible(false);
    expressive_cont.add(num4);
    JLabel num5 = label("", 215, 200);
    num5.setText("Laugh");
    num5.setVisible(false);
    expressive_cont.add(num5);
    JLabel num6 = label("", 215, 240);
    num6.setText("Clench");
    num6.setVisible(false);
    expressive_cont.add(num6);
    
//  Adding Sliders
    
    JSlider slider = addSlider(num, 107, 15, 5);
    JSlider slider_1 = addSlider(num1, 107, 55, 6);
    JSlider slider_2 = addSlider(num2, 107, 90, 7);
    JSlider slider_3 = addSlider(num3, 107, 125, 8);
    JSlider slider_4 = addSlider(num4, 107, 160, 9);
    JSlider slider_5 = addSlider(num5, 107, 195, 10);
    JSlider slider_6 = addSlider(num6, 107, 230, 11);

    expressive_cont.add(slider);
    expressive_cont.add(slider_1);
    expressive_cont.add(slider_2);    
    expressive_cont.add(slider_3);
    expressive_cont.add(slider_4);
    expressive_cont.add(slider_5);
    expressive_cont.add(slider_6);
	
//    Panel 3 : Affective Panel starts from here
    JPanel affective = new JPanel();
    affective.setBounds(334, 28, 339, 497);
    affective.setLayout(null);
    setBorder(BorderFactory.createEmptyBorder(15,15,15,15));  
    
    JLabel engage = label("Engagement", 16, 40);
    affective.add(engage);
    JLabel sExcite = label("Short Term Excitement", 16, 75);
    affective.add(sExcite);
    JLabel lExcite = label("Long Term Excitement", 16, 110);
    affective.add(lExcite);
    JLabel med = label("Meditation", 16, 145);
    affective.add(med);
    JLabel fru = label("Frustration", 16, 180);
    affective.add(fru);
    
    JRadioButton radioButtonEngage = radio_button(engageIcon, 40);
    radioButtonEngage.setActionCommand(engageIcon);
    JRadioButton radioButtonST = radio_button(shortTermIcon, 75);
    radioButtonST.setActionCommand(shortTermIcon);
    JRadioButton radioButtonLT = radio_button(longTermIcon, 110);
    radioButtonLT.setActionCommand(longTermIcon);
    JRadioButton radioButtonMed = radio_button(meditateIcon, 145);
    radioButtonMed.setActionCommand(meditateIcon);
    JRadioButton radioButtonFrus = radio_button(frustrateIcon, 180);
    radioButtonFrus.setActionCommand(frustrateIcon);
    affective.add(radioButtonEngage);
    affective.add(radioButtonST);
    affective.add(radioButtonLT);
    affective.add(radioButtonMed);
    affective.add(radioButtonFrus);
    
    ButtonGroup group = new ButtonGroup();
    group.add(radioButtonEngage);
    group.add(radioButtonST);
    group.add(radioButtonLT);
    group.add(radioButtonMed);
    group.add(radioButtonFrus);
    
    radioButtonEngage.addActionListener(this);
	radioButtonST.addActionListener(this);
	radioButtonLT.addActionListener(this);
	radioButtonMed.addActionListener(this);
	radioButtonFrus.addActionListener(this);  

	gifIcon = new JLabel(createImageIcon("" + ".gif"));
	gifIcon.setBounds(197, 30, 280, 200);
	affective.add(gifIcon, BorderLayout.CENTER);
   
    JTabbedPane tp=new JTabbedPane();  
    tp.setBounds(50,50,200,200);  
    tp.add("Expressive_binary",expressive_bin);  
    tp.add("Expressive_continuous",expressive_cont);  
    tp.add("Affective",affective);  
    add(tp);
    System.out.println("gui done");
  }

	private void expressionToIndexMapping() {
		listOfExpressions.put(blinkTrue, 0);
		listOfExpressions.put(leftWinkTrue, 1);
		listOfExpressions.put(rightWinkTrue, 2);
		listOfExpressions.put(lookLeftTrue, 3);
		listOfExpressions.put(lookRightTrue, 4);
		listOfExpressions.put(leftSmirk, 5);
		listOfExpressions.put(rightSmirk, 6);
		listOfExpressions.put(raiseBrow, 7);
		listOfExpressions.put(furrowBrow, 8);
		listOfExpressions.put(smile, 9);
		listOfExpressions.put(laugh, 10);
		listOfExpressions.put(clench, 11);
		listOfExpressions.put(engageIcon, 12);
		listOfExpressions.put(shortTermIcon, 13);
		listOfExpressions.put(longTermIcon, 14);
		listOfExpressions.put(meditateIcon, 15);
		listOfExpressions.put(frustrateIcon, 16);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gifIcon.setIcon(createImageIcon(e.getActionCommand() + ".gif"));

		System.out.println(e.getActionCommand());
		System.out.println("listener trigger");

		for (int i = 0; i < 17; i++) {
			arrayList.add(0.0);
		}

		setExpressiveBinary(e, arrayList);
		setAffective(e, arrayList);
		model.setFacialValues(arrayList);

		if (e.getSource() == buttonConnect) {
			if (buttonConnect.getText().compareTo("run") == 0) {
				System.out.println("start");
				model.setFacialValues(arrayList);
				model.start();

				buttonConnect.setText("stop");
			} else if (buttonConnect.getText().compareTo("stop") == 0) {
				System.out.println("stop");
				model.stop();
				buttonConnect.setText("run");
			}
		}
	}

	private void setAffective(ActionEvent e, ArrayList<Double> arrayList) {
		if (e.getActionCommand().equals(engageIcon)) {
			arrayList.set(listOfExpressions.get(engageIcon), (double) 1);
		} else if (e.getActionCommand().equals(shortTermIcon)) {
			arrayList.set(listOfExpressions.get(shortTermIcon), (double) 1);
		} else if (e.getActionCommand().equals(longTermIcon)) {
			arrayList.set(listOfExpressions.get(longTermIcon), (double) 1);
		} else if (e.getActionCommand().equals(meditateIcon)) {
			arrayList.set(listOfExpressions.get(meditateIcon), (double) 1);
		} else if (e.getActionCommand().equals(frustrateIcon)) {
			arrayList.set(listOfExpressions.get(frustrateIcon), (double) 1);
		}
	}

	private void setExpressiveBinary(ActionEvent e, ArrayList<Double> arrayList) {
		if(e.getActionCommand().equals(blinkTrue))
		{
			this.mode = 1;
			arrayList.set(listOfExpressions.get(blinkTrue), (double) 1);	
			this.expressive_bin.repaint();
			new Thread(new Runnable() {
				public void run() {
					if(Gui.this.mode != 1) {
						return;
					}
					while(true) {
						if(Gui.this.mode != 1) {
							return;
						}
						try {
							
							Thread.sleep(2000);
							if(Gui.this.mode != 1) {
								return;
							}
							Gui.this.mode = 0;
							Gui.this.expressive_bin.repaint();
							Thread.sleep(2000);
							if(Gui.this.mode > 1) {
								return;
							}
							Gui.this.mode = 1;
							Gui.this.expressive_bin.repaint();
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						}
						
					}
				}
			}).start();
		}
		if(e.getActionCommand().equals(leftWinkTrue))
		{
			this.mode = 2;
			arrayList.set(listOfExpressions.get(leftWinkTrue), (double) 1);
			this.expressive_bin.repaint();
		}
		if(e.getActionCommand().equals(rightWinkTrue))
		{
			this.mode = 3;
			arrayList.set(listOfExpressions.get(rightWinkTrue), (double) 1);
			this.expressive_bin.repaint();
		}
		if(e.getActionCommand().equals(lookLeftTrue))
		{
			this.mode = 4;
			arrayList.set(listOfExpressions.get(lookLeftTrue), (double) 1);
			this.expressive_bin.repaint();
		}
		if(e.getActionCommand().equals(lookRightTrue))
		{
			this.mode = 5;
			arrayList.set(listOfExpressions.get(lookRightTrue), (double) 1);
			this.expressive_bin.repaint();
		}
	}

	protected static ImageIcon createImageIcon(String path) {
		java.net.URL gifURL = Gui.class.getResource(path);
		if (gifURL != null) {
			return new ImageIcon(gifURL);
		} else {
			System.err.println("");
			return null;
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Simulator");
		frame.setLayout(new GridLayout(1, 1));
		frame.add(new Gui());
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				model.shutdown();
				System.exit(0);
			}
		});
		frame.pack();
		frame.setSize(500, 400);
		frame.setVisible(true);
	}
}
