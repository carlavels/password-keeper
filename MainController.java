/*
*
*
*	Password Keeper app
*	Skelen - 26/04/2018
*
*	Main class
*
*
*
*
*
*/

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;

public class MainController extends JFrame
{

	final static String GO_LOGIN_PNL = "login_pnl";
	final static String GO_DATA_PNL = "data_pnl";
	final static String GO_CREATE_PNL = "create_pnl";
	final static String GO_CREATE_PK_USR = "create_pk_usr";
	DataPanel data_panel;
	static MainController mc;
	static CardLayout cl;

	LoginPanel login_panel = new LoginPanel();
	CreatePkPanel create_pk_panel = new CreatePkPanel();

	static JPanel panel_control = new JPanel( new CardLayout());

	MainController()
	{

		cl = (CardLayout)(panel_control.getLayout());
		panel_control.add(login_panel, GO_LOGIN_PNL);
		panel_control.add(create_pk_panel, GO_CREATE_PK_USR);
		mc.GoLoginPanel();
		//cl.show(panel_control,GO_LOGIN_PNL); // Will show the login Panel first
	}

	private void addPanels()
	{
		// Add the panels for cardlayout
		add(panel_control);
	}

	public static void GoCreatePkUser()
	{
		// Method for going to CreatePK Panel
		cl.show(panel_control, GO_CREATE_PK_USR);
	}

	public static void GoLoginPanel()
	{
		// Method for going to LoginPanel
		cl.show(panel_control, GO_LOGIN_PNL);
	}

	public static void GoDataPanel(int login_user_id)
	{	
		// Adding the dataPanel class
		mc.data_panel = new DataPanel(login_user_id);
		panel_control.add(mc.data_panel, GO_DATA_PNL);
		// Method for going to DataPanel
		cl.show(panel_control, GO_DATA_PNL);
	}

	public static void GoDataPanel()
	{	
		// This method is the one being called to go to DataPanel once it has already been created
		
		// Tells DataPanel to update his self before showing
		mc.data_panel.updatePanel();

		// Method for going to DataPanel
		cl.show(panel_control, GO_DATA_PNL);
	}

	public static void GoCreatePanel(int login_user_id, boolean action)
	{	
		// Instantiate create panel
		CreatePanel create_panel = new CreatePanel(login_user_id,action);
		panel_control.add(create_panel, GO_CREATE_PNL);

		// Go to create panel
		cl.show(panel_control, GO_CREATE_PNL);
	}

	public static void GoCreatePanel(int data_id, String user_name, String password, String site, String link, boolean action)
	{	
		// This method is for the EditPanel
		CreatePanel create_panel = new CreatePanel(data_id, user_name, password, site, link, action);
		panel_control.add(create_panel, GO_CREATE_PNL);

		// Go to create panel
		cl.show(panel_control, GO_CREATE_PNL);
	}

	public static void main(String args[]) 
	{
		mc = new MainController();
		mc.setTitle("Password Keeper");
		mc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mc.addPanels();

		mc.setSize(850, 700);
		mc.setResizable(false);
		mc.setVisible(true);
	}
}