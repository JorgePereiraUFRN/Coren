package br.ufrn.contextanalyzer.demos.roomapp;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.WebResource;

import br.ufrn.contextanalyzer.api.entities.AttributeEntity;
import br.ufrn.contextanalyzer.api.entities.EnactorEntity;
import br.ufrn.contextanalyzer.api.entities.OutcomeEntity;
import br.ufrn.contextanalyzer.api.entities.QueryEntity;
import br.ufrn.contextanalyzer.api.entities.ReferenceEntity;
import br.ufrn.contextanalyzer.api.entities.WidgetEntity;


public class Room {
	
	public Color roomColor;
	public BufferedImage fireImage;
	public BufferedImage presenceImage;
	public String room; 
	public RoomUI ui;
	public Air air;
	public Light light;
	
	public WidgetEntity widget;
	public EnactorEntity encator;
	
	public Room(String room) {
		this.room = room;
		this.widget = createWidget();
		
		createEnactorAir();
		createEnactorLight();
		
		this.air = new Air(room);
		this.light = new Light(room);
		
		this.ui = new RoomUI();
	}
	
	private WidgetEntity createWidget() {
		WidgetEntity widget = new WidgetEntity();
		widget.setName(room);
		List<AttributeEntity> attributes = new ArrayList<AttributeEntity>();
		AttributeEntity presence = new AttributeEntity();
		presence.setName("presence");
		presence.setType("boolean");
		attributes.add(presence);
		AttributeEntity temperature = new AttributeEntity();
		temperature.setName("temperature");
		temperature.setType("int");
		attributes.add(temperature);
		AttributeEntity brightness = new AttributeEntity();
		brightness.setName("brightness");
		brightness.setType("int");
		attributes.add(brightness);
		AttributeEntity fire = new AttributeEntity();
		fire.setName("fire");
		fire.setType("boolean");
		attributes.add(fire);
		widget.setAttributes(attributes);
		
		WebResource resource = RoomAPP.client.resource("http://" + RoomAPP.SERVER_IP + ":8080/ContextAnalyzer/api/widget/create");
		resource.type(MediaType.APPLICATION_JSON).post(String.class, widget);
		
		return widget;
	}
	
	private void createEnactorAir() {
		
		EnactorEntity enactor = new EnactorEntity();
		enactor.setName("RoomEnactorAir" + room);
		List<String> widgets = new ArrayList<String>();
		widgets.add(room);
		enactor.setWidgets(widgets);
		OutcomeEntity outcome = new OutcomeEntity();
		outcome.setName(room + "air");
		outcome.setType("int");
		outcome.setDescription(room + " air");
		enactor.setOutcome(outcome);
		List<ReferenceEntity> references = new ArrayList<ReferenceEntity>();
		ReferenceEntity off = new ReferenceEntity();
		off.setName("Off");
		OutcomeEntity outcomeOff = new OutcomeEntity();
		outcomeOff.setName(room + "air");
		outcomeOff.setType("int");
		outcomeOff.setDescription(room + " air");
		outcomeOff.setValue("0");
		off.setOutcome(outcomeOff);
		QueryEntity airOff = new QueryEntity();
		airOff.setNome("airOff");
		airOff.setValue("(OR (EQUAL presence FALSE) (LESS temperature 20) )");
		off.setQuery(airOff);
		references.add(off);
		ReferenceEntity on = new ReferenceEntity();
		on.setName("On");
		OutcomeEntity outcomeOn = new OutcomeEntity();
		outcomeOn.setName(room + "air");
		outcomeOn.setType("int");
		outcomeOn.setDescription(room + " air");
		outcomeOn.setValue("1");
		on.setOutcome(outcomeOn);
		QueryEntity airOn = new QueryEntity();
		airOn.setNome("airOn");
		airOn.setValue("(ELSE (QUERY airOff) )");
		on.setQuery(airOn);
		references.add(on);
		enactor.setReferences(references);
		
		WebResource resource = RoomAPP.client.resource("http://" + RoomAPP.SERVER_IP + ":8080/ContextAnalyzer/api/enactor/create");
		resource.type(MediaType.APPLICATION_JSON).post(String.class, enactor);
		
	}
	
	private void createEnactorLight() {
		
		EnactorEntity enactor = new EnactorEntity();
		enactor.setName("RoomEnactorLight" + room);
		List<String> widgets = new ArrayList<String>();
		widgets.add(room);
		enactor.setWidgets(widgets);
		OutcomeEntity outcome = new OutcomeEntity();
		outcome.setName(room + "light");
		outcome.setType("int");
		outcome.setDescription(room + " light");
		enactor.setOutcome(outcome);
		List<ReferenceEntity> references = new ArrayList<ReferenceEntity>();
		ReferenceEntity off = new ReferenceEntity();
		off.setName("Off");
		OutcomeEntity outcomeOff = new OutcomeEntity();
		outcomeOff.setName(room + "light");
		outcomeOff.setType("int");
		outcomeOff.setDescription(room + " light");
		outcomeOff.setValue("0");
		off.setOutcome(outcomeOff);
		QueryEntity lightOff = new QueryEntity();
		lightOff.setNome("lightOff");
		lightOff.setValue("(OR (EQUAL presence FALSE) (GREATER brightness 100) )");
		off.setQuery(lightOff); 
		references.add(off);
		ReferenceEntity on = new ReferenceEntity();
		on.setName("On");
		OutcomeEntity outcomeOn = new OutcomeEntity();
		outcomeOn.setName(room + "light");
		outcomeOn.setType("int");
		outcomeOn.setDescription(room + " light");
		outcomeOn.setValue("7 * Math.pow(128 - brightness, 0.33) / Math.pow(128, 0.33)");
		on.setOutcome(outcomeOn);
		QueryEntity lightOn = new QueryEntity();
		lightOn.setNome("lightOn");
		lightOn.setValue("(ELSE (QUERY lightOff) )");
		on.setQuery(lightOn);
		references.add(on);
		enactor.setReferences(references);
		
		WebResource resource = RoomAPP.client.resource("http://" + RoomAPP.SERVER_IP + ":8080/ContextAnalyzer/api/enactor/create");
		resource.type(MediaType.APPLICATION_JSON).post(String.class, enactor);;
		
	}
	
	public class RoomUI extends JPanel {
		
		private BufferedImage roomImage;
		public Dashboard dashboard;
		
		public RoomUI() {
			try {
				switch (room) {
					case "1":
						roomImage = ImageIO.read(new File("img/room1.png"));
						break;
					case "2":
						roomImage = ImageIO.read(new File("img/room2.png"));
						break;
					case "3":
						roomImage = ImageIO.read(new File("img/room3.png"));
						break;
					case "4":
						roomImage = ImageIO.read(new File("img/room4.png"));
						break;
				}
				roomColor = Color.BLACK;
				fireImage = ImageIO.read(new File("img/fire_0.png"));
				setSize(roomImage.getWidth(), roomImage.getHeight());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			dashboard = new Dashboard();
			
			new Thread() {
				@Override
				public void run() {
					while(true) {
						repaint();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D graphics = (Graphics2D) g;
			int w = roomImage.getWidth();   
	    	int h = roomImage.getHeight();   
			
			setBackground(roomColor);
	        
	    	graphics.drawImage(roomImage, 0, 0, w, h, null);
	    	graphics.drawImage(fireImage, 0, 0, w, h, null);
	    	graphics.drawImage(presenceImage, 0, 0, w, h, null);
	    	graphics.drawImage(air.airImage, 0, 0, w, h, null);
	    	graphics.drawImage(light.lightImage, 0, 0, w, h, null);
	    	graphics.dispose();
	    }
	}
	
	public class Dashboard extends JPanel {
		
		private float fontSize = 10f;
		private JCheckBox presenceCheckBox;
		private JCheckBox fireCheckBox;
		private JSpinner temperatureSpinner;
		private JSlider brightnessSlider;
		
		public Dashboard() {			
			setBorder(BorderFactory.createTitledBorder("Dashboard - Room " + room));
		    setLayout(new GridLayout(4, 2));
		    add(new JLabel("Presence") {{ setFont(getFont().deriveFont(fontSize)); }});
		    add(presenceCheckBox = new JCheckBox() {{
				addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent evt) {
						try {
							String position = "_botton_";
			    			if (room.equals("3") || room.equals("4")) {
			    				position = "_top_";
			    			}
			    			if (presenceCheckBox.isSelected()) {
			    				presenceImage = ImageIO.read(new File("img/presence" + position + "1.png"));
							} else {
								presenceImage = ImageIO.read(new File("img/presence" + position + "0.png"));
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						
						WebResource resource = RoomAPP.client.resource("http://" + RoomAPP.SERVER_IP + ":8080/ContextAnalyzer/api/widget/update/" + room);
						AttributeEntity att = new AttributeEntity();
						att.setName("presence");
						att.setValue(String.valueOf(presenceCheckBox.isSelected()));
						att.setType("boolean");
						resource.type(MediaType.APPLICATION_JSON).put(att);
					}
				});
			}});
		    add(new JLabel("Fire") {{ setFont(getFont().deriveFont(fontSize)); }});
		    add(fireCheckBox = new JCheckBox() {{
				addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent evt) {
						try {
							if (fireCheckBox.isSelected()) {
								fireImage = ImageIO.read(new File("img/fire_1.png"));
							} else {
								fireImage = ImageIO.read(new File("img/fire_0.png"));
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						
						WebResource resource = RoomAPP.client.resource("http://" + RoomAPP.SERVER_IP + ":8080/ContextAnalyzer/api/widget/update/" + room);
						AttributeEntity att = new AttributeEntity();
						att.setName("fire");
						att.setValue(String.valueOf(fireCheckBox.isSelected()));
						att.setType("boolean");
						resource.type(MediaType.APPLICATION_JSON).put(att);
					}
				});
			}});
			add(new JLabel("Temperature") {{ setFont(getFont().deriveFont(fontSize)); }});
			add(temperatureSpinner = new JSpinner(new SpinnerNumberModel(24, 18, 30, 1)) {{
				setFont(getFont().deriveFont(fontSize));
				addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent evt) {
						WebResource resource = RoomAPP.client.resource("http://" + RoomAPP.SERVER_IP + ":8080/ContextAnalyzer/api/widget/update/" + room);
						AttributeEntity att = new AttributeEntity();
						att.setName("temperature");
						att.setValue(String.valueOf(temperatureSpinner.getValue()));
						att.setType("int");
						resource.type(MediaType.APPLICATION_JSON).put(att);
					}
				});
			}});
			add(new JLabel("Brightness") {{ setFont(getFont().deriveFont(fontSize)); }});
			add(brightnessSlider = new JSlider(new DefaultBoundedRangeModel(0, 0, 0, 128)) {{
				addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent evt) {
						int brightness = brightnessSlider.getValue();
						roomColor = new Color(brightness*2, brightness*2, brightness*2);
						WebResource resource = RoomAPP.client.resource("http://" + RoomAPP.SERVER_IP + ":8080/ContextAnalyzer/api/widget/update/" + room);
						AttributeEntity att = new AttributeEntity();
						att.setName("brightness");
						att.setValue(String.valueOf(brightness));
						att.setType("int");
						resource.type(MediaType.APPLICATION_JSON).put(att);
					}
				});
			}});
		}
	}

}
