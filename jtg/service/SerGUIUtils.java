

package service;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.Window;

public class SerGUIUtils {
	
	private final static Toolkit tk = Toolkit.getDefaultToolkit();
  	
	public static void center(Window w) {
		Dimension screenSize = tk.getScreenSize();
		Dimension wSize = w.getSize();
	
		int x = (screenSize.width - wSize.width) / 2;
		int y = (screenSize.height - wSize.height) / 2;
	
		w.setLocation(x, y);
	}
	
	public static void addComponent(Container cont,
			   GridBagLayout gbl,
			   Component c,
			   int x, int y,
			   int width, int heigt,
			   double weightx, double weighty )
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = x; gbc.gridy = y;
		gbc.gridwidth = width; gbc.gridheight = heigt;
		gbc.weightx = weightx; gbc.weighty = weighty;
		gbl.setConstraints( c, gbc );
		cont.add( c );
	}
}
