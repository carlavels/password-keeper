/*
*
*
*	Password Keeper app
*	Skelen - 26/04/2018
*
*	Login Panel
*
*
*
*
*
*/

// Components
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

// For the login image
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

// Listeners
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginPanel extends JPanel
{	
	ImageIcon image;
	JLabel image_labl;
	JTextField username_fld;
	JPasswordField password_fld;
	JButton login_btn, signup_btn;
	JPanel button_panel;

	// For Layout
	final Dimension HEAD_SPACE = new Dimension(20, 45);
	final Dimension USRNAME_SPACE = new Dimension(20, 15);
	final Dimension PASS_SPACE = new Dimension(20, 2);
	final Dimension BTN_SPACE = new Dimension(20, 5);

	LoginPanel()
	{
		// Instantiate Image
		createTheImage();
		image_labl = new JLabel(image);

		// Instantiate textfields
		username_fld = new JTextField("admin_01", 22);
		username_fld.setEditable(true);

		// Instantiate Password field
		password_fld = new JPasswordField("superadmin011",22);

		// Instantiate buttons JPanel
		button_panel = new JPanel();

		// Create Login button
		createLoginButton(); 

		// Create Sign up Button
		createSignupButton();

		// Setting the Layout of our Jpanel
		setTheLayout();

		// Adding components
		add(Box.createRigidArea(HEAD_SPACE));
		add(image_labl);
		add(Box.createRigidArea(USRNAME_SPACE));
		add(username_fld);
		add(Box.createRigidArea(PASS_SPACE));
		add(password_fld);
		add(Box.createRigidArea(BTN_SPACE));
		button_panel.add(login_btn);
		button_panel.add(signup_btn);
		add(button_panel);

	}

	private void setTheLayout()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		image_labl.setAlignmentX(Component.CENTER_ALIGNMENT);
		username_fld.setAlignmentX(Component.CENTER_ALIGNMENT);
		password_fld.setAlignmentX(Component.CENTER_ALIGNMENT);
		button_panel.setAlignmentX(Component.CENTER_ALIGNMENT);

		username_fld.setMaximumSize( username_fld.getPreferredSize());
		password_fld.setMaximumSize( password_fld.getPreferredSize());

		// set maximum size for JButtons
		login_btn.setPreferredSize(new Dimension(77,27));
		signup_btn.setPreferredSize(new Dimension(77,27));
	}

    private void createTheImage()
    {
  		// Creating image as Buffered image to be able to rescale the size
  		BufferedImage img = null;
  		try
  		{
  			img = ImageIO.read(new File ("rsc/key_img.png"));
  		}catch(IOException e)
  		{
  			e.printStackTrace();
  		}

  		Image dimg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
  		image = new ImageIcon(dimg);  	
    }

    private void createLoginButton() 
    {
    	// Method for initializing login button and the aciton
    	login_btn = new JButton("Login");

    	// Adding the action for Login button
		login_btn.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					/**
					*	Pass the username and password entered to the DataConnector clas 
					*	to check if the user is existing in the data base
					*	DataConnector will return -1 if the user does not exist
					*	If user exist, DataConnector will return the ID # of the user
					**/
					DataConnector dc_obj = null; // Need to initialize the object, it will throw a compilation error if not initialized
					dc_obj = new DataConnector();
										
					String username = username_fld.getText();
					String password = String.valueOf(password_fld.getPassword());

					int does_user_exist = dc_obj.checkLoginUser(username, password);

					//this just for testing delete after working
					if(does_user_exist >= 0)
						System.out.println("User ID is " + does_user_exist);
					else
						System.out.println("User does not exist: " + does_user_exist);

					//if the user is existing, we go to the DataPanel passing the user ID
					if(does_user_exist >= 0)
						MainController.GoDataPanel(does_user_exist);
				}
			});
    }// End of createLoginButton

    private void createSignupButton()
    {
    	// initialize component first
    	signup_btn = new JButton("Sign up");

    	// Add actoin for Sign-up button
    	signup_btn.addActionListener(new ActionListener()
    		{
    			@Override
    			public void actionPerformed(ActionEvent e)
    			{
    				/**
					* Action will send us to new Panel
					* Creation of user for PassKeep app
    				**/
    				System.out.println("I go now create new passkeep user page");
    				MainController.GoCreatePkUser();
    			}
    		});
    }// End of createSignupButton Method
}