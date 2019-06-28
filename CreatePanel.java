/*
*
*
*	Password Keeper app
*	Skelen - 19/10/2018
*
*	Create Panel
*	Panel for adding new entry
*
*
*/

// For components
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

// For actions
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// For layout
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

// For Debugging
import java.awt.Color;


public class CreatePanel extends JPanel{

	//Initiate components
	JButton save_button = new JButton("Save");

	JTextField sname_txt_field = new JTextField(22);
	JTextField login_id_txt_field = new JTextField(22);
	JTextField pword_txt_field = new JTextField(22);
	JTextField slink_txt_field = new JTextField(22);

	JLabel sname_label = new JLabel("Site Name");
	JLabel slink_label = new JLabel("Site Link");
	JLabel pword_label = new JLabel("Password");
	JLabel login_id_label = new JLabel("Username");
	JLabel action_status_label = new JLabel("Display Action Here");
	
	JPanel sname_panel = new JPanel();
	JPanel login_id_panel = new JPanel();
	JPanel pword_panel = new JPanel();
	JPanel slink_panel = new JPanel();

	CreatePanel(int login_user_id, boolean action_create){
		//Constructor for Creating entry

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		setButtonsLayout();	// Method for setting Buttons layout
		setLabelLayout(action_create); // Method for setting label layout; Labels that are not inside JPanels
		setPanelLayout();	// Method for setting panels/containers layout
		setTextFieldLayout(); // Method for setting Textfield layou
		addComponentsToPanels(); // Method for adding the components to their separate panels
		setButtonFunction(login_user_id, action_create); // Method for setting the funciton of the button - pass the login user ID and the type of action(create/edit entry)

		addPanelsToMainPanel(); // Method for adding the separate panels to the main panel
	}

	CreatePanel(int data_id, String user_name, String password, String site, String link, boolean action_create){
		// Constructor for Editing entry

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		setButtonsLayout();	// Method for setting Buttons layout
		setLabelLayout(false); // setting this to false so it would say "Edit entry" in the panel rather than create
		setPanelLayout();	// Method for setting panels/containers layout
		setTextFieldLayout(); // Method for setting Textfield layou
		addComponentsToPanels(); // Method for adding the components to their separate panels
		setTFieldForEdit(user_name, password, site, link); //This will set the textfields to the current value in the database for the entry
		setButtonFunction(data_id, action_create); // since action_create is false this will go to the edit mode

		addPanelsToMainPanel(); // Method for adding the separate panels to the main panel
	}

	private void setTFieldForEdit(String user_name, String password, String site, String link){
		// This method will set the textfields to the current value of the entry from the DB
		// the current values that the user wanted to change
		sname_txt_field.setText(site);
		login_id_txt_field.setText(user_name);
		pword_txt_field.setText(password);
		slink_txt_field.setText(link);

	}

	private void setPanelLayout(){

		sname_panel.setLayout(new BoxLayout(sname_panel, BoxLayout.LINE_AXIS));
		slink_panel.setLayout(new BoxLayout(slink_panel, BoxLayout.LINE_AXIS));
		pword_panel.setLayout(new BoxLayout(pword_panel, BoxLayout.LINE_AXIS));
		login_id_panel.setLayout(new BoxLayout(login_id_panel, BoxLayout.LINE_AXIS));

		sname_panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		slink_panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		pword_panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		login_id_panel.setAlignmentX(Component.CENTER_ALIGNMENT);

		sname_panel.setMaximumSize(new Dimension(314,20));
		slink_panel.setMaximumSize(new Dimension(314,20));
		pword_panel.setMaximumSize(new Dimension(314,20));
		login_id_panel.setMaximumSize(new Dimension(314,20));
	}

	private void setButtonFunction(int id, boolean action_create){
		
		save_button.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{	
					// Get all the inputs - trim to remove excess white space
					String username = login_id_txt_field.getText().trim();
					String password = pword_txt_field.getText().trim();
					String sitename = sname_txt_field.getText().trim();
					String sitelink = slink_txt_field.getText().trim();

					// Input checking first
					boolean all_inputs_good = checkInputs();

					// If all inputs are good save entry to DB and go back to Data panel
					// Else we stay in the Create Panel
					if(all_inputs_good){
						if(action_create){
							// Create instance of DataConnector class
							DataConnector dc_obj = new DataConnector();

							// Pass the inputs of the user to the create method in DataConnector
							dc_obj.createEntryInDB(id, sitename, username, password, sitelink);

						}else{
							// Create instance of DataConnector class
							DataConnector dc_obj = new DataConnector();

							// Pass the inputs of the user to edit the selected input
							dc_obj.editEntryInDB(id, sitename, username, password, sitelink);
						}

						// Go back to datapanel
						MainController.GoDataPanel();
					}
				}
		});
	}

	private boolean checkInputs(){
		// method for the checking of the inputs 
		/*
		Get the inputs, trim to remove excess whitepsace
		*/
		String username = login_id_txt_field.getText();
		String password = pword_txt_field.getText();
		String sitename = sname_txt_field.getText().trim();
		String sitelink = slink_txt_field.getText().trim();


		// REGEX pattern for checking
		String pattern1 = "((.*)([!#%&'=`{}~;\"\\$\\*\\+\\-\\/\\?\\^\\|\\s\\\\])(.*))";	// REGEX: checking for special characters
		String pattern2 = "(^[a-zA-Z].*)(\\w+)";	// REGEX: checking for string starting with letter
		String pattern3 = "((\\w+)@(\\w+)\\.(\\w+))";	// REGEX: checking for format - carl@gmail.com
		String pattern4 = "((.*)([\"'{};\\s])(.*))";	// REGEX: checking for certain special characters - for password

		// Checking for login ID
		if(username.matches(pattern1)){
			JOptionPane.showMessageDialog(this, "Login ID: Space and Special Characters not allowed.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if (!username.matches(pattern2)){
			JOptionPane.showMessageDialog(this, "Login ID: Must start with a letter.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if (!username.matches(pattern3)){
			JOptionPane.showMessageDialog(this, "Login ID: Invalid format.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Checking for password
		if(password.matches(pattern4)){
			JOptionPane.showMessageDialog(this, "Password: Space and some Special Characters are not allowed.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if (password.length() < 8){
			JOptionPane.showMessageDialog(this, "Password: Must be 8 characters or more.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
			return false;		
		}

		// Checking for sitename
		if(sitename.equals("")){
			JOptionPane.showMessageDialog(this, "Site Name: Please fill out all fields.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Checking for sitelink
		if(sitelink.equals("")){
			JOptionPane.showMessageDialog(this, "Site Link: Please fill out all fields.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
			return false;
		}


		// if nothing is wrong - return true to signal the creation in DB and return in DataPanel
		return true;
	}

	private void setTextFieldLayout(){
		
		sname_txt_field.setAlignmentX(Component.RIGHT_ALIGNMENT);
		login_id_txt_field.setAlignmentX(Component.RIGHT_ALIGNMENT);
		pword_txt_field.setAlignmentX(Component.RIGHT_ALIGNMENT);
		slink_txt_field.setAlignmentX(Component.RIGHT_ALIGNMENT); 

		sname_txt_field.setMaximumSize(sname_txt_field.getPreferredSize());
		login_id_txt_field.setMaximumSize(login_id_txt_field.getPreferredSize());
		pword_txt_field.setMaximumSize(pword_txt_field.getPreferredSize());
		slink_txt_field.setMaximumSize(slink_txt_field.getPreferredSize());
	}

	private void setButtonsLayout(){
		save_button.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	private void setLabelLayout(boolean action_create){
		if(action_create){
			action_status_label.setText("Create New Entry");
		}else{
			action_status_label.setText("Edit Selected Entry");
		}

		action_status_label.setAlignmentX(Component.CENTER_ALIGNMENT);
		action_status_label.setFont(new Font("Dialog Bold", Font.BOLD, 32));
	}

	private void addComponentsToPanels(){
		// Method for adding the components in the separate panels
		// And also the setup of their layout inide those panels		

		sname_panel.add(sname_label);
		sname_panel.add(Box.createHorizontalGlue());
		sname_panel.add(sname_txt_field);
		
		login_id_panel.add(login_id_label);
		login_id_panel.add(Box.createHorizontalGlue());		
		login_id_panel.add(login_id_txt_field);

		pword_panel.add(pword_label);
		pword_panel.add(Box.createHorizontalGlue());
		pword_panel.add(pword_txt_field);
		
		slink_panel.add(slink_label);
		slink_panel.add(Box.createHorizontalGlue());
		slink_panel.add(slink_txt_field);
	}

	private void addPanelsToMainPanel(){
		// Method for adding the separate panels in to the main panels
		// Here we will also add the Button
		// layout for the separate panel and buttons will be handled here as well.

		add(Box.createRigidArea(new Dimension(0,25))); // Add space a blank  - HEADER
		add(action_status_label);
		add(Box.createRigidArea(new Dimension(0,25))); // Add space a blank  - Text Component Space
		add(sname_panel);
		add(Box.createRigidArea(new Dimension(0,5))); // Add space a blank space - Component SPACE
		add(login_id_panel);
		add(Box.createRigidArea(new Dimension(0,5))); // Add space a blank space - Component SPACE
		add(pword_panel);
		add(Box.createRigidArea(new Dimension(0,5))); // Add space a blank space - Component SPACE
		add(slink_panel);
		add(Box.createRigidArea(new Dimension(0,10))); // Add space a blank space - Button SPACE
		add(save_button);
	}
}