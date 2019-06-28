/*
*
*
*	Password Keeper app
*	Skelen - 30/04/2018
*
*	Data Strip
*	A panel that contains all the components of the data.
*	For dynamically adding strips
*
*/

// For debugging or layouting delete later
import javax.swing.border.Border;
import java.awt.Color;
import javax.swing.BorderFactory;

// For Components
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

// For actions
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// For layout
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;

// For Icons
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import javax.swing.ImageIcon;

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


	public DataStrip(int id, String usr_name, String pass, String site)
	{
		
		//initialize DataConnector object = for queries in the database
		dc_obj = new DataConnector();

		// initialize some value
		setUserField(usr_name);
		setPassField(pass);
		setSiteLabel(site);

		// Initialize the buttons
		setGoButton();
		setModifyButton();
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
			}
		});	
	}// End of SetDeleteButton method

	private void setModifyButton()
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
				// Do something to modify
			}
		});	
	}// End of SetModifyButton method

	private void setGoButton()
	{
		// Method for setting up the Go to the site button
		go_btn = new JButton();
		go_btn.setPreferredSize(btn_dims);
		go_btn.setMaximumSize(go_btn.getPreferredSize());
		go_btn.setVisible(true);

		// Setting Icon
		bufferTheImage("rsc/go_icon.png");
		go_btn.setIcon(image);

		// Action
		go_btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed( ActionEvent e)
			{
				// Do something to go to site
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
