import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;

// For actions
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestFrame extends JPanel{

	TestFrame(){

		//Instantiate components
		JButton go_create_panel_button = new JButton("Save");

		go_create_panel_button.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{	
					MainController.GoDataPanel();
					
				}
		});

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(go_create_panel_button);
	}

	/*
	public static void main(String args[]) 
	{
		CreatePanel cp_obj = new CreatePanel(1,true);
		TestFrame tf = new TestFrame();
		tf.add(cp_obj);

		tf.setTitle("Testing of Create Panel");
		tf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tf.setSize(850, 700);
		tf.setResizable(false);
		tf.setVisible(true);
	}
	*/
}