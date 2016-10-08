import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

import javax.swing.*;

public class Puzzle extends JPanel {
	Segment[] segments;
	Image img;
    Timer t;
    JLabel label;
    boolean timer = false;
	public boolean started = false;
	public boolean mixing = false;
	
	public Puzzle(Image img,JLabel label) {
        this.label=label;
		this.img = img;
		segments = new Segment[9];
		int segmentSize = img.getWidth(null)/3;
		for (int i = 0; i != segments.length; i++) {
			segments[i] = new Segment(this, i, segmentSize);
		}
	}
	
	public void start() {
		started = true;
		segments[8].isEmpty = true;
		mix.start();
	}

    public void startTimer() {
        timer = true;
        t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                int sec = Integer.parseInt(label.getText().split(" ")[0]);
                sec++;
                label.setText(String.valueOf(sec)+" Sec");
            }
        }, 0, 1000);
    }
	
	Thread mix = new Thread(new Runnable() {
		public void run() {
			mixing = true;
            long t= System.currentTimeMillis();
            long end = t+5000;
			while (System.currentTimeMillis() < end) {
				ArrayList<Integer> possibleMovements = new ArrayList<Integer>();
				for (Segment s : segments) {
					if (s.getPosition().x == segments[8].getPosition().x+1 && s.getPosition().y == segments[8].getPosition().y) possibleMovements.add(s.getID());
					if (s.getPosition().x == segments[8].getPosition().x-1 && s.getPosition().y == segments[8].getPosition().y) possibleMovements.add(s.getID());
					if (s.getPosition().x == segments[8].getPosition().x && s.getPosition().y == segments[8].getPosition().y-1) possibleMovements.add(s.getID());
					if (s.getPosition().x == segments[8].getPosition().x && s.getPosition().y == segments[8].getPosition().y+1) possibleMovements.add(s.getID());
				}
				
				int ind = (int) ((Math.random()*possibleMovements.size()));
				try {
					Point tmp = segments[possibleMovements.get(ind)].getPosition();
					segments[possibleMovements.get(ind)].setPosition(segments[8].getPosition());
					segments[8].setPosition(tmp);
				} catch (Exception e) {}
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
            mixing = false;
		}
	});
	
	public void onClick(MouseEvent e) {
        if(!timer && !mixing){
            startTimer();
        }
		for (Segment s : segments) {
			if (s.hitten(e.getPoint())) {
				Point tmp = s.getPosition();
				if (s.move(segments[8].getPosition())) {
					segments[8].setPosition(tmp);
					
					
					boolean done = true;
					for (int i = 0; i != 9; i++) {
						if (segments[i].getPosition().x == ((i <= 2)? i:(i <= 5)? (i-3):(i-6)) && segments[i].getPosition().y == (int) Math.ceil((i/3))) {
							//System.out.println(i+": :)");
						} else {
							//System.out.println(i+": :(");
							done = false;
						}
					}
					
					if (done) {
                        t.cancel();
						started = false;
						segments[8].isEmpty = false;
                        JOptionPane.showMessageDialog(null,"Well done!\n" +
                                "Your time was "+label.getText().split(" ")[0]+" seconds");
					}
				}
			}
		}
		repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		for (int i = 0; i != segments.length; i++) {
			//System.out.print(segments[i].getID()+((i == 2 || i == 5 || i == 8)? "\n-----\n":"|"));
			segments[i].paint(g);
		}
	}
	
}
