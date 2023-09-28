package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ctr.BookingController;
import ctr.DataAccessException;
import model.Booking;
import model.Member;
import model.Range;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTable;

public class BookingUI_1 extends JFrame {

	private JPanel contentPane;
	
	//Controller
	private BookingController bookingController;
	
	//input variabler
	private JTextField memberID_Input;
	private JTextField dato_Input;
	
	//Buttons
	JButton saveBooking_Button;
	JButton addDate_Button;
	
	//ComboBox
	JComboBox<String> weaponType_ComboBox;
	JComboBox<String> tidspunkt_ComboBox;
	JComboBox<String> bane_ComboBox;
	
	//IndexSelection
	int prevTimeSelction;
	
	//Member
	private Member member;
	
	//Booking
	private Booking booking;
	
	//Range
	private Range range;
	
	
	//liste med tider
	ArrayList<String> availableTimes;
	
	
	//liste med baner
	ArrayList<Integer> findAvailableRanges;
	
	
	//liste med tider og baner
	HashMap<Integer, List<String>> availableTimesAndRanges;
	private JPanel panel_1;
	private JLabel memberID_Text_1;
	private JTextField textField;
	private JButton addMember_button_1;
	private JButton saveBooking_Button_1;
	private JTable table;
	private JButton saveBooking_Button_1_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookingUI_1 frame = new BookingUI_1();
					ThreadDB threadDB = new ThreadDB();
					ThreadUpdateUI threadUpdateUI = new ThreadUpdateUI(frame);
					threadDB.start();
					threadUpdateUI.start();
					
					
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws DataAccessException 
	 */
	public BookingUI_1() throws DataAccessException {
		//instansiere controller
		bookingController = new BookingController();
		
		
	//#######################################################################################################################################################################
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 545, 533);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Opret booking", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 509, 209);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel memberID_Text = new JLabel("Skyttenummer");
		memberID_Text.setBounds(25, 29, 150, 14);
		panel.add(memberID_Text);
		
		JLabel weaponType_Text = new JLabel("V\u00E5bentype");
		weaponType_Text.setBounds(25, 54, 150, 14);
		panel.add(weaponType_Text);
		
		JLabel dato_Text = new JLabel("Dato");
		dato_Text.setBounds(25, 79, 150, 14);
		panel.add(dato_Text);
		
		JLabel tidspunkt_Text = new JLabel("Tidspunkt");
		tidspunkt_Text.setBounds(25, 104, 150, 14);
		panel.add(tidspunkt_Text);
		
		JLabel bane_Text = new JLabel("Bane");
		bane_Text.setBounds(25, 129, 150, 14);
		panel.add(bane_Text);
	//######################################################################################################################################################################
		
		//MEMBER INPUT FRA BRUGER													<---- MEMBER INPUT FRA BRUGER
		memberID_Input = new JTextField();
		memberID_Input.setBounds(185, 26, 186, 20);
		panel.add(memberID_Input);
		memberID_Input.setColumns(10);
		
	
	
		//V�BEN TYPE CONBOBOX													 	<----- V�BEN COMBOBOX
		weaponType_ComboBox = new JComboBox();
		weaponType_ComboBox.setBounds(185, 50, 186, 22);
		panel.add(weaponType_ComboBox);
		
		
		
		
		//DATO INPUT FRA BRUGER													<----- DATO INPUT FRA BRUGER
		dato_Input = new JTextField();
		dato_Input.setBounds(185, 75, 186, 22);
		panel.add(dato_Input);
		dato_Input.setColumns(10);
		
		
		//TIDSPUNKT COMBOBOX													<----- TIDSPUNKT COMBOBOX
		tidspunkt_ComboBox = new JComboBox();
		tidspunkt_ComboBox.setBounds(185, 100, 186, 22);
		panel.add(tidspunkt_ComboBox);
		
		
		
		
		
		//BANE COMBO BOX														<----- BANE COMBOBOX
		bane_ComboBox = new JComboBox();
		bane_ComboBox.setBounds(185, 125, 186, 22);
		panel.add(bane_ComboBox);
		
		
		
		
		//Tilf�j member til booking											<------ ADD MEMBER BUTTON
		JButton addMember_button = new JButton("Tilf\u00F8j skytte");
		addMember_button.setBounds(381, 25, 106, 23);
		panel.add(addMember_button);
		
		
		
	
		//tilf�j dato til booking											<----- ADD DATO BUTTON
		addDate_Button = new JButton("Tilf\u00F8j dato");
		addDate_Button.setBounds(381, 75, 106, 23);
		panel.add(addDate_Button);
		
		
		
		
		//gem booking i databasen											<----- ADD BOOKING TIL DATABASE BUTTON
		saveBooking_Button = new JButton("Opret booking");
		saveBooking_Button.setBounds(25, 167, 462, 23);
		panel.add(saveBooking_Button);
		
		panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "S\u00F8g booking", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 231, 509, 209);
		contentPane.add(panel_1);
		
		memberID_Text_1 = new JLabel("Skyttenummer");
		memberID_Text_1.setBounds(25, 29, 150, 14);
		panel_1.add(memberID_Text_1);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(185, 26, 186, 20);
		panel_1.add(textField);
		
		addMember_button_1 = new JButton("S\u00F8g");
		addMember_button_1.setBounds(381, 25, 106, 23);
		panel_1.add(addMember_button_1);
		
		saveBooking_Button_1 = new JButton("\u00E6".toUpperCase() + "ndre booking");
		saveBooking_Button_1.setBounds(266, 167, 221, 23);
		panel_1.add(saveBooking_Button_1);
		
		JButton saveBooking_Button_1_1 = new JButton("Slet booking");
		saveBooking_Button_1_1.setBounds(25, 167, 221, 23);
		panel_1.add(saveBooking_Button_1_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 59, 461, 97);
		panel_1.add(scrollPane);
		
		table = new JTable();
		table.setBounds(0, 0, 1, 1);
		panel_1.add(table);
		
		saveBooking_Button_1_2 = new JButton("Tilbage");
		saveBooking_Button_1_2.setBounds(10, 455, 509, 23);
		contentPane.add(saveBooking_Button_1_2);
		saveBooking_Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String dialog = null;
				if(bookingController.getBooking().getRange() == null) {
					dialog = "er ikke blevet valgt!";
				} else {
					dialog = Integer.toString(bookingController.getBooking().getRange().getRangeID());
				}
				
				
				int chosenOption = JOptionPane.showConfirmDialog(contentPane, "Booking\n" + "Medlem: " + bookingController.getBooking().getMember().getName() + "\n" + "Vaabentype: " + bookingController.getBooking().getWeaponType() + "\n" + "Dato og tidspunkt: " + bookingController.getBooking().getDate() + "-" + bookingController.getBooking().getTime() + "\n" + "Bane: " + dialog);
				switch(chosenOption) {
				case 0: 
					try {
						
						if(bookingController.getBooking().getRange() == null) {
							JOptionPane.showMessageDialog(new JFrame(), "Du har ikke valgt en bane", "Ugyldig bane", JOptionPane.INFORMATION_MESSAGE);
						} else {
							dialog = Integer.toString(bookingController.getBooking().getRange().getRangeID());
							bookingController.saveBooking();
							bookingController.setBookingToNull();
							reset();
							memberID_Input.setText("");
							lockAll();
						}
					} catch (DataAccessException e1) {}
					break;
				case 1:
					//code
					break;
				case 2:
					//kode
					break;
				}
			}
		});
		addDate_Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//F�r tid fra bruger
				String selectedDato = dato_Input.getText();
				weaponType_ComboBox.setActionCommand("DateSelected");
				
				try {
					//Finder alle ledige tider
					findAvailableTimes(selectedDato);
					//DEBUG
					System.out.println("Booking tilh\u00F8rere: " +  bookingController.getBooking().getMember().getName());
					System.out.println("Tiderne fundet fra denne dato: " + selectedDato);
					System.out.println("HashMap af alle baner og tider tilg\u00E6ngelige: " + availableTimesAndRanges);
					System.out.println("Liste af alle tider: " + availableTimes);
					
					
					//G�R TIDSPUNKT KLAR TIL BRUG
					tidspunkt_ComboBox.setEnabled(true);
					bane_ComboBox.setEnabled(false);
					bane_ComboBox.setModel(new DefaultComboBoxModel());
				} catch (DataAccessException e1) {} catch (SQLException e1) {}
			
		
				
				
				//Something med dato tilf�jelse
			}
		});
		addMember_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				reset();
				//Gets the memberID text user has written
				int getSelectedMember = Integer.parseInt(memberID_Input.getText());
				try {
					addMember(getSelectedMember);
					//DEBUG
					//System.out.println("Medlem du har valgt: " + bookingController.getBooking().getMember().getName());
				} catch (DataAccessException e1) {}}
		});
		bane_ComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int selectedRange = (int) bane_ComboBox.getSelectedItem();
				try {
					bookingController.addRange(selectedRange);
					saveBooking_Button.setEnabled(true);
				} catch (DataAccessException e1) {}
				//Something med bane
			}
		});
		tidspunkt_ComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					String selectedTime = tidspunkt_ComboBox.getSelectedItem().toString();
					bookingController.addTime(selectedTime);
					findAvailableRangesForTime(selectedTime);
					bane_ComboBox.setEnabled(true);
				}
			}
		});
		
		//Tilf�jelse af hardcoded v�ben
		weaponType_ComboBox.addItem("Pistol");
		weaponType_ComboBox.addItem("Rifle");
		
		//Event handler
		weaponType_ComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					
					String selectedWeapon = weaponType_ComboBox.getSelectedItem().toString();
					
					try {
						bookingController.addWeaponType(selectedWeapon);
					} catch (DataAccessException e1) {}
					//G�R DATO KLAR TIL INDTASTNING
					dato_Input.setEnabled(true);
					addDate_Button.setEnabled(true);
					
					//DEBUG
					System.out.println(" Vaaben du har valgt: " + bookingController.getBooking().getWeaponType());
					}
				
				if(weaponType_ComboBox.getActionCommand().equals("DateSelected") && e.getStateChange() == ItemEvent.SELECTED) 
				{
					System.out.println("Reselct");
					try {
						bane_ComboBox.setEnabled(false);
						findAvailableTimes(bookingController.getBooking().getDate());
					} catch (DataAccessException e1) {} catch (SQLException e1) {}
				}
		}});
		
		
		lockAll();
	}//END CONSTUCTOR
	
	
	
	
	
	
	
	
	
	//----------------------------------------------------------------------METODER--------------------------------------------------------------------------------------------
	
	//Tilf�j en medlem til vores booking og check om medlemmet er ledigt. 
	public void addMember(int memberID) throws DataAccessException 
	{
		bookingController.addMember(memberID);
		//booking = bookingController.getBooking();
		
		if(bookingController.getBooking().getMember() == null) 
		{
			JOptionPane.showMessageDialog(new JFrame(), "Skyttenummeret er ikke i vores database\nfor medlemmer.", "Ugyldigt skyttenummer", JOptionPane.INFORMATION_MESSAGE);
			weaponType_ComboBox.setActionCommand("Disable");
		} else 
			{
			memberID_Input.setText(bookingController.getBooking().getMember().getName() + " er added.");
			weaponType_ComboBox.setActionCommand("Enabled");
			weaponType_ComboBox.setEnabled(true);
			}
	}
	
	//Finder alle tilg�ngelige tider og baner
	public void findAvailableTimes(String dato) throws DataAccessException, SQLException {
		//Finder alle tider og baner 
		availableTimesAndRanges = bookingController.findAvailableTimes(dato);
		
		availableTimes = getAvailableTimeStrings();
		Object[] data = availableTimes.toArray();
		tidspunkt_ComboBox.setModel(new DefaultComboBoxModel(data));
	}
	
	public void findAvailableRangesForTime(String time) {
		findAvailableRanges = getRangesFromAvailableTimeStrings(time);
		
		Object[] data = findAvailableRanges.toArray();
		bane_ComboBox.setModel(new DefaultComboBoxModel(data));
		
		
	}
	
	
	public void update() throws DataAccessException, SQLException {
		if(bookingController.getBooking() != null && bookingController.getBooking().getDate() != null) {
			findAvailableTimes(bookingController.getBooking().getDate());
			bane_ComboBox.setEnabled(false);
			bane_ComboBox.setModel(new DefaultComboBoxModel());
			bookingController.getBooking().addRange(null);
			
			System.out.println("Update has been made");
			System.out.println("New hashMap: " + availableTimesAndRanges);
			System.out.println("New set of avialeTimes " + availableTimes);
			
		}
	}
	
	
	
	//L�ser alle componentes
	public void lockAll() {
		weaponType_ComboBox.setEnabled(false);
		tidspunkt_ComboBox.setEnabled(false);
		bane_ComboBox.setEnabled(false);
		dato_Input.setEnabled(false);
		addDate_Button.setEnabled(false);
		saveBooking_Button.setEnabled(false);
	
		
	}
	
	public void reset() {
		bane_ComboBox.setModel(new DefaultComboBoxModel());
		tidspunkt_ComboBox.setModel(new DefaultComboBoxModel());
		dato_Input.setText("");
		
		//Enable
		lockAll();
		
	}
	
	public ArrayList<Integer> getRangesFromAvailableTimeStrings(String time) {

		ArrayList<Integer> availableRanges = new ArrayList<>();
		
		availableRanges.removeAll(availableRanges);
	
		for(int Key : availableTimesAndRanges.keySet()) {
			int size = availableTimesAndRanges.get(Key).size() -1 ;
			for(int i = 0; i <= size; i++) {
				String timeInList = availableTimesAndRanges.get(Key).get(i);
				String timeGot = time;
				if(timeInList.equals(time)) {
					availableRanges.add(Key);
				}
			}
		}
		
		//System.out.println(availableRanges.size());
		//System.out.println(availableRanges);
		return availableRanges;
	}
	
	
	public ArrayList<String> getAvailableTimeStrings() {
		
		int at17 = 0;
		int at18 = 0;
		int at19 = 0;
		int at20 = 0;
				
		for(int Key : availableTimesAndRanges.keySet()) {
			for(int i = 0; i < availableTimesAndRanges.get(Key).size(); i++) {
				if(availableTimesAndRanges.get(Key).get(i) == "17:00") {
					at17++;
				}
				if(availableTimesAndRanges.get(Key).get(i) == "18:00") {
					at18++;
				}
				if(availableTimesAndRanges.get(Key).get(i) == "19:00") {
					at19++;
				}
				if(availableTimesAndRanges.get(Key).get(i) == "20:00") {
					at20++;
				}
			}
		}
		
		ArrayList<String> availableStringTimes = new ArrayList<>();
		
		if(at17 != 0) {
			availableStringTimes.add("17:00");
		}
		if(at18 != 0) {
			availableStringTimes.add("18:00");
		}
		if(at19 != 0) {
			availableStringTimes.add("19:00");
		}
		if(at20 != 0) {
			availableStringTimes.add("20:00");
		}
		
		return availableStringTimes;
	}
}//END CLASS
