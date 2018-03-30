package jp.maceration.component.mascott.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;

import jp.maceration.component.mascott.data.MascottMoveData;
import jp.maceration.util.StringUtil;

/**
 * Mascott Dialog Class.
 * 
 * @author Masatoshi.H
 *
 */
public class MascottView extends JDialog {
	
	private static final long serialVersionUID = 2223255734500452313L;

	public Map<String, List<MascottMoveData>>	actions;
	
	public String		action;
	public int			indexImage;
	public String		prevAction;
	public int			prevIndexImage;
	
	public MouseEvent	startDrag;
	public int			x;
	public int			y;
	public int			prevX;
	public int			prevY;
	public boolean		isDraggable;
	
	public double		scale;
	
	/**
	 * Constructor Method.
	 */
	public MascottView() {}
	
	/**
	 * Initialize Method.
	 */
	public void init() {
		
		this.actions = new LinkedHashMap<String, List<MascottMoveData>>();
		
		this.action = "INIT";
		this.indexImage = 0;
		
		this.prevAction = this.action;
		this.prevIndexImage = this.indexImage;
		
		this.scale = 1.0;

		setIgnoreRepaint(true);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));		// Transparent.
		setAlwaysOnTop(true);
		setFocusable(true);
				
		addMouseListener(new DragWindowListener());
		addMouseMotionListener(new DragWindowListener());
	}
	
	/**
	 * Initial Paint Method.
	 */
	public void initPaint() {
		
		// Check Mascott Actions.
		if (this.actions == null || this.actions.size() == 0) return;
		
		// init image.
		MascottMoveData moveData = this.actions.get(this.action).get(0);
		BufferedImage image = moveData.image;
		int width = image.getWidth();
		int height = image.getHeight();
		
		// init display.
		setBounds(0, 0, width, height);
		setVisible(true);
	}
	
	/**
	 * Draw Method.
	 * @param g
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		// Check Mascott Actions.
		if (this.actions == null || this.actions.size() == 0) return;
		
		// get next image index.
		if (this.action.equals(this.prevAction)) {
			// same action.
			this.indexImage++;
			List<MascottMoveData> listImage = this.actions.get(this.action);
			if (listImage == null) return;
			if (listImage.size() <= this.indexImage) this.indexImage = 0;
		} else {
			// different action.
			this.indexImage = 0;
		}
		
		// Get image.
		MascottMoveData moveData = this.actions.get(this.action).get(indexImage);
		BufferedImage image = moveData.image;
		int width = (int)(image.getWidth() * this.scale);
		int height = (int)(image.getHeight() * this.scale);
		
		// move x, y.
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		int screenWidth = ge.getDefaultScreenDevice().getDisplayMode().getWidth();
		int screenHeight = ge.getDefaultScreenDevice().getDisplayMode().getHeight();
		if (StringUtil.isNotEmpty(moveData.moveX)) {
			this.x = this.x + Integer.parseInt(moveData.moveX);
			if (this.x < (width * -1)) this.x = (width * -1);
			if ((screenWidth + width) < this.x) this.x = (screenWidth + width);
		}
		if (StringUtil.isNotEmpty(moveData.moveY)) {
			this.y = this.y + Integer.parseInt(moveData.moveY);
			if (this.y < (height * -1)) this.y = (height * -1);
			if ((screenHeight + height) < this.y) this.y = (screenHeight + height);
		}
		
		// draw image.
		g.drawImage(image, 0, 0, width, height, null);
		setLocation(this.x, this.y);
		
		this.prevX = this.x;
		this.prevY = this.y;
		this.prevAction = this.action;
		this.prevIndexImage = this.indexImage;
	}
	
	/**
	 * Update Method.
	 * @param g
	 */
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	/**
	 * Drag Window Listener Class.
	 * 
	 * @author Masatoshi.H
	 */
	private class DragWindowListener implements MouseMotionListener, MouseListener {
		private Point	point;
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
			// set small scale at Click.
			scale = scale - 0.05;
			if (scale < 0) scale = 0.0;
		}

		@Override
		public void mousePressed(MouseEvent me) {
			startDrag = me;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			isDraggable = false;
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mouseDragged(MouseEvent me) {
			isDraggable = true;
			
			point = getLocation();
			x = point.x - startDrag.getX() + me.getX();
			y = point.y - startDrag.getY() + me.getY();
			setLocation(x, y);
		}

		@Override
		public void mouseMoved(MouseEvent e) {}
	}
}
