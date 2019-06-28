/*
*
*
*	Password Keeper app
*	Skelen - 30/04/2018
*
*	Data Panel
*	Where all the data is obviously
*
*
*/

// For components
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;

// For actions
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// For layout
import javax.swing.BoxLayout;
import java.util.ArrayList;
import java.awt.Component;
import java.awt.FlowLayout;

/* Additional import class for the components of the before 
 different class DataStrip */

// For components
import javax.swing.JTextField;
import javax.swing.JLabel;

// For layout
import java.awt.Dimension;
import javax.swing.Box;

// For Icons
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import javax.swing.ImageIcon;

// For debugging or layouting delete later
import javax.swing.border.Border;
import java.awt.Color;
import javax.swing.BorderFactory;

import java.awt.Container;

// Class for opening default web browser
import java.awt.Desktop;
import java.net.URI;

public class DataPanel extends JPanel
{

	JButton add_item_btn; // Button for adding new items
	JScrollPane sbar_mainpane; // Scroll bar for main panel in this class
	private ArrayList <String> uname = new ArrayList<String>(); // variables for the queried data of user 
	private ArrayList <String> pword = new ArrayList<String>(); // variables for the queried data of user
	private ArrayList <String> slink = new ArrayList<String>(); // variables for the queried data of user
	private ArrayList <String> sname = new ArrayList<String>(); // variables for the queried data of user
	private ArrayList <Integer> uid = new ArrayList<Integer>(); // variables for the queried data of user

	int login_user; // Where we store the ID of the login user

	DataConnector dc_obj = null; // object for DataConnector class, Need to initialize the object else error will occur

	ArrayList <JPanel> data_strip; // variable for the datastrip class

	JPanel main_data_panel;

	/* Variables below this are from the before 
		different class DataStrip
	
	// For layout and design
	Dimension btn_dims = new Dimension(38, 38);
	ImageIcon image;

	// Components of the Data Strip
	JButton del_btn, mod_btn, go_btn;
	JTextField usr_nme_fld, psw_fld;
	JLabel site_name;
	*/

	DataPanel(int login_user_id)
	{	
		// This line is to set the vertical gap of the JPANEL to 0
		super(new FlowLayout ( FlowLayout.LEFT, 0, 0 ));

		// Creating a panel that would contain the actual data
		// And then we will add this panel to the scrollbar pane
		main_data_panel = new JPanel();
		main_data_panel.setLayout(new BoxLayout(main_data_panel, BoxLayout.Y_AXIS));
		//main_data_panel.setBackground(Color.BLACK); // For testing to know the bounds of the panel

		data_strip = new ArrayList<JPanel>();
		login_user = login_user_id;
		createScrollbar(); // create scroll bar and add to the pannel in this class
		createAddItemButton(login_user); // Button should be initialized first
		createDataConnectorObject(); // Create connection to database
		boolean shouldWeDrawAll = checkExistingData(login_user); // check if the login user has existing data
		drawTheItems(shouldWeDrawAll); // draw the components to the main_data_panel	
	}

	private void createScrollbar()
	{	// method for initializing and creating the scroll bar
		// This is where we add the main_panel to the scrollbar pane
		// and then the scrollbar pane will be added to the panel of the actual window
		sbar_mainpane = new JScrollPane();
		sbar_mainpane.setPreferredSize(new Dimension(845,672)); // Sadly these value has is a product of trial and error
		sbar_mainpane.setViewportView(main_data_panel);
		add(sbar_mainpane);

	} // End of createScrollbar class

	private void createDataConnectorObject()
	{
		// Method for initializing the DataConnector object
		dc_obj = new DataConnector();
		
	}

	private boolean checkExistingData(int login_user_id)
	{
		// Method will check if theres an existing data for the user in database
		// if yes, we will execute addItemsExisting method to add the data
		boolean data_exist = false;
		data_exist = dc_obj.checkDataUser(login_user_id);

		if(data_exist)
			addItemsExisting(login_user_id);

		// return false if no data else true
		return data_exist;
	}

	private void getLatestData()
	{	// Method for getting the latest data of array list in the DataConnector class
		// Steps for getting data
		//	1 - clear existing arraylist data
		//	2 - copy the latest data

		//Clear
		uname.clear();
		pword.clear();
		slink.clear();
		sname.clear();
		uid.clear();

		//Copy
		uname.addAll(dc_obj.getUname());
		pword.addAll(dc_obj.getPword());
		slink.addAll(dc_obj.getSlink());
		sname.addAll(dc_obj.getSname());
		uid.addAll(dc_obj.getUid());
	}

	private void addToDataStrip()
	{	// This method will add the data into data strip class
		
		// Clear the arraylist first to make sure only updated data are in the arraylist
		data_strip.clear();

		// Then include all the latest data that we queried
		for(int i = 0; i < uname.size(); i++)
		{
			// Create an object of Data strip and enter our database data 1 by 1
			// (data_id, data_username, data_paword, data_sitename) - data_sitelink to be added
			DataStrip ds = new DataStrip(uid.get(i), uname.get(i), pword.get(i), sname.get(i), slink.get(i));

			// then add the ds to our data_strip arraylist which i the collection of all the data
			data_strip.add(ds);
		}
	}

	private void addItemsExisting(int login_user_id)
	{
		// Query for the latest data
		dc_obj.queryForUserID(login_user_id);

		// Call method to assign the latest data to the arraylist variables in this class
		getLatestData();

		// Call method to add the data in the datastrip class
		addToDataStrip();

	}

	private void createAddItemButton(int login_user_id)
	{
		add_item_btn = new JButton("Add");
		add_item_btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		// Adding the action for this button
		add_item_btn.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// Go to create panel
					// Communiacate to create panel and set it to Create new user
					// Pass the user ID where the new item to be created should be saved
					// the true variable is to set the CreatePanel to create new user rather than edit existing
					MainController.GoCreatePanel(login_user_id, true);
				}
		});
	}

	private void drawTheItems(boolean drawAll)
	{	
		removeAllTheItems(); // Clean the slate first before drawing all items

		if(drawAll)
		{
			// if drawAll is false means there is no data and
			// only the additem button should be drawn
			for(JPanel x :  data_strip)
			{
				x.setAlignmentX(Component.CENTER_ALIGNMENT);
				x.setVisible(true);
				x.setAlignmentX(Component.CENTER_ALIGNMENT);
				main_data_panel.add(x);
			}
		}
			
		main_data_panel.add(add_item_btn); // Line for drawing the add item button
		main_data_panel.add(Box.createRigidArea(new Dimension(0,5))); // Just a space for the add item button and the border
		
		main_data_panel.revalidate();
		main_data_panel.repaint();
	} // End of drawTheItems method

	public void removeAllTheItems()
	{
		// Method for clearing all the components in our JPANEL
		main_data_panel.removeAll();
	}

	public void updatePanel(){
		System.out.println("I updated my Datapanel before showing!!");
		
		// Check for the latest data in DB
		boolean shouldWeDrawAll = checkExistingData(login_user);

		// Then redraw base on the new data
		drawTheItems(shouldWeDrawAll);
	}	

	/*
		Inner class for the datastrip
		Accepts the database data and then creates a Panel with the components for the Datastrip
	*/
	private class DataStrip extends JPanel
	{
		
		// For layout and design
		Dimension btn_dims = new Dimension(38, 38);
		ImageIcon image;

		// Components of the Data Strip
		JButton del_btn, mod_btn, go_btn;
		JTextField usr_nme_fld, psw_fld;
		JLabel site_name;

		// object for DataConnector class, Need to initialize the object else error will occur
		DataConnector dc_obj = null; 


		public DataStrip(int id, String usr_name, String pass, String site, String link)
		{
			
			//initialize DataConnector object = for queries in the database
			dc_obj = new DataConnector();

			// initialize some value
			setUserField(usr_name);
			setPassField(pass);
			setSiteLabel(site);

			// Initialize the buttons
			setGoButton(link);
			setModifyButton(id, usr_name, pass, site, link);
			setDeleteButton(id);

			// Display all components
			displayComponents();

		}// End of Constructor

		private void setSiteLabel(String input)
		{
			/* Method for setting up site name label
			*/

			site_name = new JLabel(input);
			site_name.setVisible(true);

			//Border border = BorderFactory.createLineBorder(Color.BLACK); // FOR LAYOUTING DELETE LATER
			//site_name.setBorder(border);

			// Setting layout
			site_name.setPreferredSize(new Dimension(150, 72));
			site_name.setMaximumSize(site_name.getPreferredSize());
			site_name.setHorizontalAlignment(JLabel.CENTER);
			site_name.setVerticalAlignment(JLabel.CENTER);
			
		}// End of setSiteLabel method

		private void setUserField(String input)
		{
			/* Method for setting up Username filed
			*/

			usr_nme_fld = new JTextField(input, 15);
			usr_nme_fld.setEditable(false);
			usr_nme_fld.setVisible(true);
			usr_nme_fld.setMaximumSize(usr_nme_fld.getPreferredSize());
		}// End of setUserFiled method

		private void setPassField(String input)
		{
			/* Method for setting up Password filed
			*/

			psw_fld = new JTextField(input, 15);
			psw_fld.setEditable(false);
			psw_fld.setVisible(true);
			psw_fld.setMaximumSize(psw_fld.getPreferredSize());
		}// End of setPassFiled method

		private void setDeleteButton(int db_id)
		{
			/* Method for setting up the Delete button
			*/

			del_btn = new JButton();
			del_btn.setPreferredSize(btn_dims);
			del_btn.setMaximumSize(del_btn.getPreferredSize());
			del_btn.setVisible(true);

			// Setting Icon
			bufferTheImage("rsc/delete_icon.png");
			del_btn.setIcon(image);

			// Action
			del_btn.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed( ActionEvent e)
				{
					System.out.println("Im deleting database ID:" + db_id);

					// Pass the ID of the data in DB to be deleted
					dc_obj.deleteDataInDB(db_id);

					// Check for the latest data in DB
					boolean shouldWeDrawAll = checkExistingData(login_user);

					// Then redraw base on the data
					drawTheItems(shouldWeDrawAll);
				}
			});	
		}// End of SetDeleteButton method

		private void setModifyButton(int id, String usr_name, String pass, String site, String link)
		{
			/* Method for setting up the Modify button
			*/
			mod_btn = new JButton();
			mod_btn.setPreferredSize(btn_dims);
			mod_btn.setMaximumSize(mod_btn.getPreferredSize());
			mod_btn.setVisible(true);

			// Setting Icon
			bufferTheImage("rsc/edit_icon.png");
			mod_btn.setIcon(image);

			// Action
			mod_btn.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed( ActionEvent e)
				{
					// Go to create panel with the parameter
					// LoginId, Username, password, sitename, sitelink
					// the false value is to notify the method that this is edit instead of create new user
					MainController.GoCreatePanel(id, usr_name, pass, site, link, false);
				}
			});	
		}// End of SetModifyButton method

		private void setGoButton(String link)
		{
			// Method for setting up the Go to the site button
			go_btn = new JButton();
			go_btn.setPreferredSize(btn_dims);
			go_btn.setMaximumSize(go_btn.getPreferredSize());
			go_btn.setVisible(true);

			// Setting Icon
			bufferTheImage("rsc/go_icon.png");
			go_btn.setIcon(image);

			// Checking the provided link, it should start with "https://" or "www."
			// Prefix will be added if it is not included in the link
			if(!link.toLowerCase().startsWith("https://") && !link.toLowerCase().startsWith("http://") && !link.toLowerCase().startsWith("www."))
			{
				link = "www." + link;
			}
			System.out.println(link);
			// Now set the final link - somehow the URI class only accepts final variables
			final String final_link = link;

			// Action
			go_btn.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed( ActionEvent e)
				{
					// This code block is for the opening of the link using default browser

					// First check if the desktop is supported - if not set to null
					Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;

					// Evaluate the result of checking the desktop
					if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
					{
						// If supported we try to open the link using the default browser
						try
						{	
							URI goURI = new URI(final_link);
							desktop.browse(goURI);	

						}catch(Exception err){
							err.printStackTrace();
						}
					}else
					{
						System.out.println("Desktop is not supported: setGoButton method");
					}
				}
			});
		}// End of SetGoButton method

		private void displayComponents()
		{
			/* Method for displaying components and their layouts
			*/

			// Set the layout of this Jpanel
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

			site_name.setAlignmentY(Component.CENTER_ALIGNMENT);
			usr_nme_fld.setAlignmentY(Component.CENTER_ALIGNMENT);
			psw_fld.setAlignmentY(Component.CENTER_ALIGNMENT);
			go_btn.setAlignmentY(Component.CENTER_ALIGNMENT);
			mod_btn.setAlignmentY(Component.CENTER_ALIGNMENT);
			del_btn.setAlignmentY(Component.CENTER_ALIGNMENT);

			add(site_name);
			add(Box.createRigidArea(new Dimension(25, 10))); // Space
			add(usr_nme_fld);
			add(Box.createRigidArea(new Dimension(5, 10))); // Space
			add(psw_fld);
			add(Box.createRigidArea(new Dimension(25, 10))); // Space
			add(go_btn);
			add(Box.createRigidArea(new Dimension(8, 10))); // Space
			add(mod_btn);
			add(Box.createRigidArea(new Dimension(8, 10))); // Space
			add(del_btn);

		}// End of displayComponents method

		private void bufferTheImage(String path)
	    {
	  		/* Method for setting up our image for the button icons
	  		*/

	  		BufferedImage img = null;
	  		try
	  		{
	  			img = ImageIO.read(new File (path));
	  		}catch(IOException e)
	  		{
	  			e.printStackTrace();
	  		}

	  		Image scaled_img = img.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
	  		image = new ImageIcon(scaled_img);  	
	    
	    }// End of bufferTheImage method
	} 
}