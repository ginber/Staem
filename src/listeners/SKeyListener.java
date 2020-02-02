package listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SKeyListener implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyChar() == 'q' || e.getKeyChar() == 'Q')
			System.exit(0);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
	
	

}
