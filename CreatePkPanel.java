/*
*
*
*	Password Keeper app
*	Skelen - 24/03/2019
*
*	CreatePkPanel class - panel for creating new users that will use the Password Keeper app
*
*
*/

// Components
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

// For the image
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import javax.swing.ImageIcon;

// For Layout
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import java.awt.Insets;
import javax.swing.BorderFactory;


// Listeners
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// For JLabel text
import java.awt.Font;

public class CreatePkPanel extends JPanel
{	
	// Components
	ImageIcon image;
	JLabel image_label, text_label;
	JTextField user_name_field;
	JPasswordField password_field;
	JButton save_button, back_button;
	JPanel back_button_panel;

	// For Layout
	final Dimension TEXT_SPACE = new Dimension(20, 10);
	final Dimension FIELD_SPACE = new Dimension(20, 2);
	final Dimension BUTTON_SPACE = new Dimension(20, 15);

	CreatePkPanel()
	{
		// instantiate user name and password field
		InstantiateComponents();

		// instantiate image for JLabel
		createTheImage("rsc/new_user1.png", 300); // send directory and size for image to be used
		image_label = new JLabel(image);

		// instantiate text JLabel
		createTextJLabel();

		// instantiate back button
		createTheImage("rsc/back_btn3.png", 40); // send directory and size for image to be used
		back_button = new JButton(image);
		back_button.setMargin(new Insets(0,0,0,0)); // this is to wrap the image in the JButton
		createBackButton(); // method for action of back button

		// instantiate the panel that will hold back button
		back_button_panel = new JPanel();
		back_button_panel.setLayout(new BoxLayout(back_button_panel, BoxLayout.X_AXIS));
		back_button_panel.setBorder(BorderFactory.createEmptyBorder(5,5,10,15)); // border for panel holding back_button (top, left, right, top)
		back_button_panel.add(back_button);
		back_button_panel.add(Box.createHorizontalGlue());

		// instantiate save button
		save_button = new JButton("Create");
		createSaveButton(); //method for action of save button

		// this method will set the layout of our panel and components
		setTheLayout();


		// Adding the components to the Panel
		add(back_button_panel);
		add(image_label);
		add(text_label);
		add(Box.createRigidArea(TEXT_SPACE));
		add(user_name_field);
		add(Box.createRigidArea(FIELD_SPACE));
		add(password_field);
		add(Box.createRigidArea(BUTTON_SPACE));
		add(save_button);

	}

	private void createSaveButton()
	{
		// Adding actions:
		// 1. check the inputs
		// 2. if all inputs are okay, create in database
		// 3. if inputs are incorrect prompt user
		// checking of inputs
		// 1. username should not exist in the database
		// 2. password should be confirmed twice 2nd via JOptionPane
		// 3. username and password should not contain special characters

		
	}// End of createSaveButton

	private void createBackButton(){
		// This method will just bring us back to login panel
		back_button.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				MainController.GoLoginPanel();
			}
		});
	}// end of createBackButton method

	private void setTheLayout()
	{	
		// this method handles the layout of panel and components
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		image_label.setAlignmentX(Component.CENTER_ALIGNMENT);
		text_label.setAlignmentX(Component.CENTER_ALIGNMENT);
		user_name_field.setAlignmentX(Component.CENTER_ALIGNMENT);
		password_field.setAlignmentX(Component.CENTER_ALIGNMENT);
		save_button.setAlignmentX(Component.CENTER_ALIGNMENT);

		user_name_field.setMaximumSize(user_name_field.getPreferredSize());
		password_field.setMaximumSize(password_field.getPreferredSize());

	}// end of setTheLayout method

	private void InstantiateComponents()
	{
		// This method is for instantiating components such as username field and password field
		// will be used during the first creation of the CreatePkPanel
		// and everytime the program is about to exit the panel
		// to make sure that the next time the panel shows every component is instantiated to the initial value

		user_name_field = new JTextField("User Name", 22);
		user_name_field.setEditable(true);

		password_field = new JPasswordField("Password", 22);

	}// end of InstantiateComponents method

	private void createTheImage(String img_name, int size)
	{	
		// this method is for instantiating the image that we are going to use in this panel
		// Creating image as Buffered image to be able to rescale the size
  		BufferedImage img = null;
  		try
  		{
  			img = ImageIO.read(new File (img_name));
  		}catch(IOException e)
  		{
  			e.printStackTrace();
  		}

  		Image dimg = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
  		image = new ImageIcon(dimg);

	}// end of createTheImage method

	private void createTextJLabel()
	{	
		// method for creating and setting Test JLabel
		text_label = new JLabel("Create new App User");
		text_label.setAlignmentX(Component.CENTER_ALIGNMENT);
		text_label.setFont(new Font("Dialog Bold", Font.BOLD, 28));

	}// end of createTextJLabel
}