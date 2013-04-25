import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class GUI extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	// Panel Objects
	private static final int ADDRESS	= 1;
	private static final int TYPE		= 3;
	private static final int DURATION	= 5;

	private String[] panelOptions = { "Address", "Connection Type", "Duration" };
	private String[] connectionStrings = { "TCP", "UDP" };

	private String ipErrorString =	"IP Format:\n" + 
									"xxx.xxx.xxx.xxx\n" +
									"     OR\n" +
									"xxx.xxx.xxx.xxx:pppp\n\n" + 
									"(Default Port: 8080 [If Unspecified])";
	// GUI Panel
	JPanel mainPanel = new JPanel();

	// Generate Traffic and Clear Buttons
	JButton generateTrafficButton = new JButton("Fire!");
	JButton resetButton = new JButton("Reset");
	
	GUI()
	{
		super("ECE 369 - JGenerator-Socket (" + Resource.VERSION_NUMBER + " - " + Resource.VERSION_CODENAME + ")");
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		setLayout(fl);
		
		createPanel();
		
		add(mainPanel);
	}
	
	public void createPanel()
	{
		mainPanel.setPreferredSize(new Dimension(425,500));
		mainPanel.add(new JLabel(panelOptions[0] + ":"));
		mainPanel.add(new JTextField());
		mainPanel.add(new JLabel(panelOptions[1] + ":"));
		mainPanel.add(new JComboBox(connectionStrings));
		mainPanel.add(new JLabel(panelOptions[2] + ":"));
		mainPanel.add(new JSpinner(new SpinnerNumberModel(10, 1, 1000000, 1)));
		
		generateTrafficButton.addActionListener(this);
		resetButton.addActionListener(this);
		mainPanel.add(generateTrafficButton);
		mainPanel.add(resetButton);
		
		for(int i = 0; i < mainPanel.getComponentCount(); i++)
			mainPanel.getComponent(i).setPreferredSize(new Dimension(200, 25));
		
		defaultFields();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == generateTrafficButton)
		{
			Object[] parameters = new Object[4];
			parameters[0] = ((JTextField)mainPanel.getComponent(ADDRESS)).getText();
			if(!validIP((String)parameters[0]))
				JOptionPane.showMessageDialog(this, ipErrorString);
			else
			{
				parameters[1] = (String)((JComboBox)mainPanel.getComponent(TYPE)).getSelectedItem();
				parameters[2] = Integer.parseInt(((JSpinner)mainPanel.getComponent(DURATION)).getValue().toString());

				try { JGenerator.GUIExecute(parameters); }
				catch (IOException exception) { exception.printStackTrace(); }
			}
		}
		else if(e.getSource() == resetButton)
			defaultFields();
	}
	
	public void defaultFields()
	{
		((JTextField)mainPanel.getComponent(ADDRESS)).setText(Resource.IP + ":" + Resource.PORT);
		((JComboBox)mainPanel.getComponent(TYPE)).setSelectedIndex(0);
		((JSpinner)mainPanel.getComponent(DURATION)).setValue(Resource.DURATION);
	}
	
	public boolean validIP(String ip)
	{
		String port = null;
		try
		{
	        if (ip == null || ip.isEmpty())
	            return false;

	        String[] parts = ip.split( "\\." );
	        if (parts.length != 4)
	            return false;

	        if(parts[3].contains(":"))
	        {
	        	port = parts[3].split(":")[1];
	        	parts[3] = parts[3].split(":")[0];
	        }
	        
	        for (String s : parts)
	        {
	            int i = Integer.parseInt(s);
	            if ((i < 0) || (i > 255))
	                return false;
	        }
	        
	        if(!port.equals(null))
	        {
	        	int i = Integer.parseInt(port);
	        	if ((i < 0) || (i > 65535))
	                return false;
	        }

	        return true;
	    }
		catch (NumberFormatException nfe) { return false; }
	}
}