import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.JTableHeader;

	
	public class supchartmain extends javax.swing.JFrame {
	    /**
		 * 
		 */
		ArrayList<String> strArray = new ArrayList<String>();
		ArrayList<String> BloxArray = new ArrayList<String>();
		public static void main(String args[]) {
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new supchartmain().setVisible(true);
	            }
	        });
	    }
		
		private static final long serialVersionUID = 1L;
		private DefaultTableModel tableModel;
	    private javax.swing.JScrollPane jScrollPane;
	    private javax.swing.JTable jTable;
	    private javax.swing.JTable freezeTable;
	    private JTextField textField0;
	    private JTextField textField1;
	    private JTextField textField2; 
	    private JTextField textField3;
	    private int NumRows = 15;
	    public int NOB = 0;              // NUMBER OF BLOCKS
	    public int  NumOfTrs = 0;
	    private int fixedColumns = 5;    // Number of columns to be frozen
	    
	    public static int[] myNumbers = null;
	    String column_header[] = {"Date", "Day", "Time", "FYJC","SYJC"};
	    String FYJCSubjects[] = {"ENG", "S.LANG", "PHY", "CHEM", "BIO", "MATHS", "E.V.S.", "P.T."};
	    String SYJCSubjects[] = {"ENG", "S.LANG", "PHY", "CHEM", "BIO", "MATHS", "E.V.S.", "P.T."};
	    
	    Object[] headers = {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
	            " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
	            " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ",
	            " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "};    //  new String[17];
	    public String[] splitBlox;
	    private JButton buttonAllot;
	    private JButton buttonBlox;
	
		public String GetData (JTable table, int row_index, int col_index) {  
			return jTable.getModel().getValueAt(row_index, col_index).toString(); 
			}
		
		public String GetData1 (JTable table, int row_index, int col_index) {  
			return freezeTable.getModel().getValueAt(row_index, col_index).toString(); 
			}
		
		public void SetData1(Object obj, int row_index, int col_index) {  
			        freezeTable.getModel().setValueAt(obj,row_index,col_index);   }
		
		public void SetData2(Object obj, int row_index, int col_index) {  
	        jTable.getModel().setValueAt(obj,row_index,col_index);   }
	
		
		public static void show(String msg) {JOptionPane.showMessageDialog(null, msg);}
		public void Show(int msg) {JOptionPane.showMessageDialog(null, msg);}
	  
		public supchartmain() {
	
	    	setTitle("S U P E R V I S I O N  C H A R T FOR J U N I O R C O L L E G E");
	        jScrollPane = new javax.swing.JScrollPane();
	        jTable = new javax.swing.JTable();
	        jScrollPane.setViewportView(jTable);
	        getContentPane().add(jScrollPane);
	        NumRows = 23;
	        setPreferredSize(new Dimension(1360,150+(NumRows*25)));   
	        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	        new JPanel(new GridBagLayout());
	        
	        Object[][] data = new String[NumRows][40];
	 ////      Object[] headers =new String[17];    /////
	        tableModel = new DefaultTableModel(data, headers);
	        jTable.setModel(tableModel);
	      
	        jTable.setAutoCreateColumnsFromModel( true );
	        
	        for (int i=0; i < jTable.getColumnCount(); i++){
	        	if(i == 0){jTable.getColumnModel().getColumn(0).setPreferredWidth(60);}
	        	else if(i == 1){jTable.getColumnModel().getColumn(1).setPreferredWidth(15);}
	        	else if(i == 2){jTable.getColumnModel().getColumn(2).setMinWidth(80);}
	                       jTable.getColumnModel().getColumn(i).setPreferredWidth(55);
	                       jTable.setRowHeight(25);
	        }
	               
	   for (int k = 0; k < column_header.length; k++){
		   jTable.getColumnModel().getColumn(k).setHeaderValue(column_header[k]);
	   }
	        
	        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	        freezeTable = new javax.swing.JTable();
	        freezeTable.setAutoCreateColumnsFromModel(false);
	        freezeTable.setModel(tableModel);
	        freezeTable.setSelectionModel(jTable.getSelectionModel());
	        freezeTable.setFocusable(false);
	      
	        for (int i = 0; i < fixedColumns; i++) {
	            TableColumnModel colModel = jTable.getColumnModel();
	            TableColumn column = colModel.getColumn(0);
	            colModel.removeColumn(column);
	            freezeTable.getColumnModel().addColumn(column);
	            freezeTable.setRowHeight(25);
	        }
	
	        //  Add the fixed table to the scroll pane
	        freezeTable.setPreferredScrollableViewportSize(freezeTable.getPreferredSize());
	        jScrollPane.setRowHeaderView(freezeTable);
	        jScrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, freezeTable.getTableHeader());
	
	        
	        // Synchronize scrolling of the row header with the jTable
	        jScrollPane.getRowHeader().addChangeListener(new ChangeListener() {
	            public void stateChanged(ChangeEvent e) {
	                //  Sync the scroll pane scrollbar with the row header
	                JViewport viewport = (JViewport) e.getSource();
	                jScrollPane.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
	            }
	        });                        
	        pack();
	        ClearTable();
	        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
	    	Date date = new Date();
	    	String TodaysDate = dateFormat.format(date);                // Gives Current Date
	
	    	for(int i=0; i < jTable.getRowCount(); i++)                                 //  To initialize the table with Current Date
	    	     {	 	
	    	    	 SetData2(TodaysDate,i,0);
	    	    	 SetData2( DayOfWeek(TodaysDate).toUpperCase(), i, 1);
	    	    	 TodaysDate = IncrementDate(TodaysDate);
	    	    	 if(DayOfWeek(TodaysDate).contains("Sun")) TodaysDate=IncrementDate(TodaysDate); ///SKIP SUNDAY
	    	    	 SetData2("12:30-3:30",i,2);
	    	    	 if (i == jTable.getRowCount()-2){ 
	    	    		 SetData2(" ",i,0);
	        	    	 SetData2( " ",i,1);
	    	    		 SetData2("Current Count",i,2);}
	    	    	 if (i == jTable.getRowCount()-1){ 
	    	    		 SetData2(" ",i,0);
	        	    	 SetData2( " ",i,1);
	    	    		 SetData2("Final Count",i,2);}
	    	     }
	    	
	    	for(int i=0; i < FYJCSubjects.length; i++) {
                     SetData2(FYJCSubjects[i], i, 3);
//                   SetData2(FYJCSubjects[i], i, 4);
	    	}
	    	
	    	JTableHeader jTableHeader = jTable.getTableHeader();
	        jTableHeader.addMouseListener(new TableHeaderMouseListener(jTable));
	        JTableHeader TableHeader = freezeTable.getTableHeader();
	        TableHeader.addMouseListener(new TableHeaderMouseListener(freezeTable));
	        
	        
	/////    N O R T H   P A N E L  ///////        
	        
	        JPanel northPanel = new JPanel();
	        
	        textField3 = new JTextField("S.I.W.S. Junior College- Wadala - 31");
	        textField3.setColumns(25);
	        JLabel Label3 = new JLabel("Name of College");
	        northPanel.add(Label3);
	        northPanel.add(textField3);
	        
	        textField0 = new JTextField();
	        textField0.setColumns(8);
	        JLabel Label0 = new JLabel("STREAM");
	        northPanel.add(Label0);
	        northPanel.add(textField0);
	        
	        textField1 = new JTextField();
	        textField1.setColumns(8);
	        JLabel Label1 = new JLabel("EXAM TYPE - FYJC");
	      	northPanel.add(Label1);
	        northPanel.add(textField1);
	        
	        textField2 = new JTextField();
	        textField2.setColumns(8);
	        JLabel Label2 = new JLabel("EXAM TYPE - SYJC");
	      	northPanel.add(Label2);
	        northPanel.add(textField2);
//	        ClearTable();
	        buttonAllot = new JButton("Enter Names");
	        northPanel.add(buttonAllot);
	        buttonAllot.addActionListener(new ActionListener() {		
				  public void actionPerformed(ActionEvent arg0) {
					  JOptionPane JOP = new JOptionPane();
					  String NemsOfSups = JOptionPane.showInputDialog("Enter the CODE Names of Supervisors ( Up To 4 characters )");	
					  JOptionPane.showMessageDialog(JOP, NemsOfSups);
					  String[] splitNames = NemsOfSups.split(",");
					   for (int i = 0; i < splitNames.length; i++){
						 JTableHeader TablHdr = jTable.getTableHeader();
						 TableColumnModel tcm = TablHdr.getColumnModel();
						 TableColumn tc = tcm.getColumn(i);
						 tc.setHeaderValue(splitNames[i].toUpperCase());
						 TablHdr.repaint();			   
			           }   
				//	   NumOfTrs = splitNames.length;
			      }	
	       });            
	        
	        buttonBlox = new JButton("Enter Blocks");
	        northPanel.add(buttonBlox);
	        buttonBlox.addActionListener(new ActionListener() {		
				  public void actionPerformed(ActionEvent arg0) {
					  JOptionPane JOP = new JOptionPane();
					  String NemsOfBlox = JOptionPane.showInputDialog("Enter the Block Codes");	
					  JOptionPane.showMessageDialog(JOP, NemsOfBlox);
					  String[] splitBlox = NemsOfBlox.split(",");
					  NumOfTrs = NumberOfTeachers();
					  int start = randInt(0, NumOfTrs);
					  int bloc = 0;
	//				  Show(start);
					  for(int j = start ; j < splitBlox.length + start ; j++){
						  SetData2(splitBlox[bloc],0,(j % NumOfTrs + 5));
						  bloc++;
					  }
					 
			      }		
	     });                
	           
	      	add(northPanel, BorderLayout.NORTH);        
	
		///////    S O U T H   P A N E L  /////		
	        
	      	JPanel southPanel = new JPanel();
	        
	      	JButton buttonDistri = new JButton("Distribute");
	        buttonDistri.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub	
	//				System.exit(0);
//					System.exit(0);
						distribute();
						show("PAUSE");					
						Randomizer();
		            	show("PAUSE");
		            	int NOT =  NumberOfTeachers();
		            	for( int i = 0; i < NOT; i++) {
		        			SwapDuties();	            		
		        		}        
		            	SumOfDuties();
				}		
	        });
	   
	        final JButton buttonLoad = new JButton("Load");        
	        buttonLoad.addActionListener(new ActionListener() {      
	   	      public void actionPerformed(ActionEvent e) {
				String fyle = "";
				JFileChooser chooser = new JFileChooser();
		        chooser.setMultiSelectionEnabled(true);
		        FileNameExtensionFilter filter = new FileNameExtensionFilter(
		                "SupervisionChart", "sup");
		        chooser.setFileFilter(filter);
		        chooser.setCurrentDirectory(new File("E:/SupervisionChart"));
		       
		        int option = chooser.showOpenDialog(buttonLoad);
		        
		        if (option == JFileChooser.APPROVE_OPTION)
		          {
		            File[] sf = chooser.getSelectedFiles();
		            String filelist = "nothing";
		            if (sf.length > 0) filelist = sf[0].getName();
		            for (int i = 1; i < sf.length; i++) 
		            { filelist += ", " + sf[i].getName(); }
		            fyle=sf[0].getPath();
		            if (!fyle.endsWith(".sup")) fyle+= ".sup";
				    LoadFile(fyle);                                    //Load File is called here
				 }  
			  }		
	   }); 
	   
	   final JButton buttonSave = new JButton("Save");
	        buttonSave.addActionListener(new ActionListener(){        
	              public void actionPerformed(ActionEvent e){                         
	  				String fyle="";
	  				JFileChooser chooser = new JFileChooser();
	  	            chooser.setMultiSelectionEnabled(true);
	  	            FileNameExtensionFilter filter = new FileNameExtensionFilter(
	  	                    "SupervisionChart", "sup");
	  	            chooser.setFileFilter(filter);
	  	            chooser.setCurrentDirectory(new File("E:/SupervisionChart"));
	  	            int option = chooser.showSaveDialog(buttonSave);
	
	  	            if (option == JFileChooser.APPROVE_OPTION)
	  	             {
	  	                File[] sf = chooser.getSelectedFiles();
	  	                String filelist = "nothing";
	  	                if (sf.length > 0) filelist = sf[0].getName();
	  	                for (int i = 1; i < sf.length; i++) 
	  	                  {
	  	                    filelist += ", " + sf[i].getName();
	  	                  }
	  	                fyle=sf[0].getPath();
	  	                
	  	                if (!fyle.endsWith(".sup")) fyle+= ".sup";
	
	  	                SaveToFile(fyle);                             //Save to File is called here
	  	             }  			 	              	  
	            }	   
	        });
	        JButton buttonUpdate = new JButton("Update");
	        buttonUpdate.addActionListener(new ActionListener() 
	        {
	        	 public void actionPerformed(ActionEvent arg0) 
		            {
		            	int rows = jTable.getRowCount();
		            	int NOTs =  NumberOfTeachers();
		                for(int j = 5; j < NOTs + 5; j++){
		                	SetData2(TotalOfIndividualDuties(j), rows-2, j);
		                }           	
		                SumOfDuties();
		            }
	        });
	        
	        final int totalpages  = 1;	
	        JButton buttonMastChart = new JButton("Master Print");
//            buttonMastChart.setFont(new Font("Times New Roman", Font.BOLD, 13));
	        buttonMastChart.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent arg0)
	            {   
	       		 try {
	       			 final int NOTs =  NumberOfTeachers();
	   		         PrinterJob pjob = PrinterJob.getPrinterJob();
	   		         PageFormat pf = pjob.pageDialog(pjob.defaultPage());
	   		         pjob.setJobName("Master Supervision Chart");
	   	//		      pjob.setCopies(1);
	   		         pjob.setPrintable(new Printable() {
	   	             public int print(Graphics pg, PageFormat pf, int pageNum)
	   		        {
	   	            	int totalpages=0 , NumberOfPages = NOTs/35;
			        	int Remainder = NOTs % 35;
			        	if (Remainder == 0) 
					    	totalpages = NumberOfPages;
			        	else totalpages = NumberOfPages+1;
	   	            	 
   		              if (pageNum < totalpages) {
   		        	  
//	  	   				pg.drawString("A", 72,  81);
//	  	   				pg.drawString("A", 514, 81);
//	  	   				pg.drawString("A", 72, 770);
//	  	   				pg.drawString("A", 514,770);	   		        	  
//	   		          int NOTs =  NumberOfTeachers();
	   		          int NOD = NODOfExams();
	   		          int StartX = 72, StartY = 82, EndX = 522, EndY = 770, HtDiff = 20 ;        // Border coordinates 
	   		          Font newFont;     int width = 35, ht = 15; 
	   		          int StartPosX = EndX/2 + StartX/2;
	   		          int rows = jTable.getRowCount();
	   				  newFont = new Font("Times New Roman", Font.PLAIN, 14);  
	   				  pg.setFont(newFont);	   				  
	   		          FontMetrics metrics = pg.getFontMetrics(newFont);
	   				  String CollName = textField3.getText().toUpperCase();
	   				  int  ColNameWidth = (int) (metrics.stringWidth(CollName));	   				  
	   				  pg.drawString(CollName, StartPosX-(ColNameWidth)/2, StartY);
	   				  String Title = "MASTER SUPERVISION CHART";
	   				  int TitleWidth = (int) (metrics.stringWidth(Title));				
	   				  pg.drawString(Title, StartPosX-(TitleWidth)/2, StartY+HtDiff);
	   				  
	   				  newFont = new Font("Times New Roman", Font.PLAIN, 11);  
	   				  pg.setFont(newFont);	
	   				  String str0 = textField0.getText().toUpperCase();
	   				  pg.drawString("STREAM : "+ str0, StartX, StartY+(2*HtDiff));
	   				  String str1 = textField1.getText().toUpperCase();
	   				  String str2 = textField2.getText().toUpperCase(); 
	   				  pg.drawString("EXAM  : " + str2 , StartX+150, StartY+(2*HtDiff));
//	   				  String str2 = textField2.getText().toUpperCase();
	   				  pg.drawString("CLASS : "+ "SYJC", StartX+350, StartY+(2*HtDiff));
	   				  pg.drawLine(StartX, StartY+(2*HtDiff)+5 , 550, StartY+(2*HtDiff)+5);

	   				  newFont = new Font("Times New Roman", Font.PLAIN, 9);  
	   				  pg.setFont(newFont);	
	   				  pg.drawString("DATE", StartX+20,  StartY+(3*HtDiff)+1);
	   				  pg.drawString("DAY", StartX+22,  StartY+(3*HtDiff)+16);
	   				  pg.drawString("SUB",  StartX+22, StartY+(3*HtDiff)+31);
	   				  pg.drawString("CODE / NAME", StartX+3, StartY+(3*HtDiff)+46); 	      		
	   				  for(int i = 0; i < 35 + 4; i++){
	   					for(int j = 0; j < NOD + 2; j++){   
	   					  if(j == 0){pg.drawRect(StartX, StartY+(2*HtDiff)+10 + i*15, 65, 15);}	
	   					  else pg.drawRect((StartX+30)+j*35, StartY+(2*HtDiff)+10 + i*15, 35, 15);    // Printing Rectangular grid
	   					}	   					
	   				  }                                  
	  // 				  Show(pageNum);
	   			     for(int i =0; i < NOTs; i++){
				         for(int a = 0; a < 35; a++){	    				     
    				    	 newFont = new Font("Times New Roman", Font.PLAIN, 9);       // Printing Supervisor's Codes
    					   	 pg.setFont(newFont);	
    				   		 JTableHeader jTablHdr = jTable.getTableHeader();
    				   		 TableColumnModel jtcm = jTablHdr.getColumnModel();
    				   		 TableColumn jtc = jtcm.getColumn(a+pageNum*35);
    				   		 String SupNames = (String) jtc.getHeaderValue();
    				   		  if (SupNames.length() > 10){ pg.drawString( SupNames.substring(0, 10), StartX + 2, 203+15*a ); }
    				   		  else pg.drawString( SupNames, StartX + 2, 203+15*a );	
				         }
			         }                                       
	   				  	   				 
	   				    for(int i = 0; i < NOD + 1; i++){
	   					  newFont = new Font("Times New Roman", Font.PLAIN, 7);          // Printing Exam Dates and Subjects
		   				  pg.setFont(newFont);	
	   					  pg.drawString((String)GetData(jTable, i, 0), (StartX+66)+i*35,  StartY+(3*HtDiff)+1);  // Printing Exam Dates 
	   					  
	   					  newFont = new Font("Times New Roman", Font.PLAIN, 9);          // Printing Exam Dates and Subjects
		   				  pg.setFont(newFont);	
	   					  pg.drawString((String)GetData(jTable, i, 1), (StartX+66)+i*35, StartY+(3*HtDiff)+16);  // Printing Exam Days
	   					  pg.drawString((String)GetData(jTable, i, 4), (StartX+66)+i*35, StartY+(3*HtDiff)+31);  // Printing Subjects
	   					  if(i == NOD){ pg.drawString("TOTAL", (StartX+66)+(i*35), StartY+(3*HtDiff)+46);}       // Printing TOTAL
	   					  else pg.drawString("----", (StartX+75)+(i*35), StartY+(3*HtDiff)+46);	                 // Printing ---- Dashes
	   					    for(int j = 0; j < NOTs; j++){	  
	   				        }	   				  
	   				    }	   	      			    

		   			    for(int i = 0; i < NOD; i++){  
		   			         for (int a = 0; a < 35; a++){	    				         
					           String temp = (String) GetData(jTable, i, 5+pageNum*35+a);      
						       pg.drawString(temp, (StartX+70)+(width*i), (StartY+120)+15*a);           // Duty of each teacher
					         }	    				     
					    }                     
	   				       				    
   				     for (int a = 0; a < 35; a++){	    				         
				         for(int i = 0; i < NOD; i++){
					       String temp2 = (String) GetData(jTable, rows-2, 5+pageNum*35+a);  // Total Duty of each teacher
					       pg.drawString(temp2, (StartX+10)+(NOD+2)*width, (StartY+120)+15*a);	    					       
				         }	    				     
				    }  
	   				    
	   				  return Printable.PAGE_EXISTS; // ie., end of job
	   				}
	   			 else
	   				{
	   				 return Printable.NO_SUCH_PAGE;
	   				}
	   			}
	   		});
	   				
	   		    if (pjob.printDialog() == false)                        // choose printer
	   			 return; 
	   			 pjob.print(); 
	   			} catch (PrinterException pe) { pe.printStackTrace(); }	

	            }
	        });
	        
	        
	        JButton buttonIndTT = new JButton("Ind.Duty");
	        buttonIndTT.addActionListener(new ActionListener()
	        {
	        	public void actionPerformed(ActionEvent arg0) {
	    			
	    			try { 	    				 	    			
//	    				  int MidPageY = (EndY + StartY)/2;
	    				  final int NOTs =  NumberOfTeachers(); 	    			      
	    				  PrinterJob pjob = PrinterJob.getPrinterJob();
	    				  PageFormat pf = pjob.pageDialog(pjob.defaultPage());
	    			      pjob.setJobName("Individual Supervision Chart");
	    			      pjob.setPrintable(new Printable() {
	    			        public int print(Graphics pg, PageFormat pf, int pageNum)
	    			        {   
	    			        	int rows = jTable.getRowCount();
	    			        	int NOD = NODOfExams(); 
	    			        	int totalpages=0 , NumberOfPages = NOTs/4;
	    			        	int Remainder = NOTs % 4;
	    			        	if (Remainder == 0) 
	    					    	totalpages = NumberOfPages;
	    			        	else totalpages = NumberOfPages+1;
//	    			        	Show(NOD);
	    			        	
	    			     if (pageNum < totalpages) { 
	    			    	 int StartX = 72, StartY = 82, EndX = 522, EndY = 770, HtDiff = 20 ;        // Border coordinates	 
	    			    	 int MidPageX = 297;        //  (EndX + StartX)/2;
	    			        	Font newFont;
	    				        newFont = new Font("Times New Roman", Font.PLAIN, 14);
	    				        FontMetrics metrics = pg.getFontMetrics(newFont);
	    				     for(int p = 0; p < 4; p++){
	    				    	String CollName = textField3.getText().toUpperCase();
	   	 	   				    int ColNameWidth = (int) (metrics.stringWidth(CollName));
	   	 	   				    int StartPos =  (MidPageX - ColNameWidth/2)+(StartX/2);
	   	 	   	                pg.drawString(CollName, (MidPageX-(ColNameWidth/2))+(StartX/2), StartY+175*p);
	   	 	   	               	newFont = new Font("Times New Roman", Font.PLAIN, 12);
	    				        pg.setFont(newFont);
	    				        String str1 = "INDIVIDUAL SUPERVISION CHART";
	    				        int str1Width = (int) (metrics.stringWidth(str1));
	    				        pg.drawString(str1, (MidPageX-(str1Width/2))+26, (StartY + HtDiff)+175*p);
	    					        pg.drawLine(StartX, (StartY+25)+175*p, EndX, (StartY+25)+175*p);
	    				     }
	    				       
	    				        int width = 39, ht = 15;                       // Height of each cell is fixed to 15
	    				        int k,l,a;
	    				     for(a = 0; a < 4; a++){
	    				       for(k = 0; k < 4; k++){  
	    				         for(l = 0; l < NOD ; l++){
		    				           pg.drawRect(StartX, (StartY+30)+175*a, 100, ht);                   // Rectangle for Names of Teachers
		    				           pg.drawString("Number of Duties", StartX + 110, (StartY+40)+175*a);
	    				               pg.drawRect(StartX + 225, (StartY+30)+175*a, 25, ht);              // Rectangle for Total Number of duties
		    				           pg.drawRect(StartX+width*l, (StartY+50)+ht*k+175*a, width, ht);    // Grid for Duty Chart
	    				         
	    				       }
	    				     }
	    			      }
	    				   
	    				     for(a = 0; a < 4; a++){
	    				         for(l = 0; l < NOD ; l++){
	    				        	 newFont = new Font("Times New Roman", Font.PLAIN, 8);
	 	    				         pg.setFont(newFont);
		    				           pg.drawString(GetData(jTable,l,0), (StartX+1)+width*l, (StartY+60)+175*a);    // Printing Date
		    				           
		    				           newFont = new Font("Times New Roman", Font.PLAIN, 10);
		 	    				         pg.setFont(newFont);
		    				           pg.drawString(GetData(jTable,l,1), (StartX+5)+width*l, (StartY+75)+175*a);	// Printing Day  				          
	    				         }
	    			         }
	    				     
	    				     for(a = 0; a < 4; a++){
	    				         for(l = 0; l < NOD ; l++){	    				   	    				   
	    				           newFont = new Font("Times New Roman", Font.PLAIN, 7);
		    				       pg.setFont(newFont);
	    				           pg.drawString(GetData(jTable,l,2), (StartX+1)+width*l, (StartY+90)+175*a);     // Printing Timings
	    				         }
	    				     }  
	    				     for(int i =0; i < NOTs; i++){
	    				         for(a = 0; a < 4; a++){	    				     
		    				    	 newFont = new Font("Times New Roman", Font.PLAIN, 10);       // Printing Supervisor's Codes
		    					   	 pg.setFont(newFont);	
		    				   		 JTableHeader jTablHdr = jTable.getTableHeader();
		    				   		 TableColumnModel jtcm = jTablHdr.getColumnModel();
		    				   		 TableColumn jtc = jtcm.getColumn(a+pageNum*4);
		    				   		 String SupNames = (String) jtc.getHeaderValue();
		    				   		  if (SupNames.length() > 10){ pg.drawString( SupNames.substring(0, 10), StartX + 2, (StartY+41)+175*a ); }
		    				   		  else pg.drawString( SupNames, StartX + 2, (StartY+41)+175*a );	
	    				         }
	    			         }   
	    				     
	    				     
	    				     for (a = 0; a < 4; a++){	    				         
	    				         for(l = 0; l < NOD; l++){
	    				    	   String temp = (String) GetData(jTable, l, 5+pageNum*4+a);      // Duty of each teacher
	    					       pg.drawString(temp, (StartX+5)+(width*l), (StartY+105)+175*a);
	    				         }	    				     
	    				    }  
	    				     
	    				     for (a = 0; a < 4; a++){	    				         
	    				         for(l = 0; l < NOD; l++){
	    					       String temp2 = (String) GetData(jTable, rows-2, 5+pageNum*4+a);  // Total Duty of each teacher
	    					       pg.drawString(temp2, 305, (StartY+40)+175*a);	    					       
	    				         }	    				     
	    				    }  
 	    					 Graphics2D g2d = (Graphics2D) pg.create();
	    				     Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
	    				     g2d.setStroke(dashed);
	    					 for(int p = 0; p < 3; p++){
	    					        g2d.drawLine(StartX, 235+175*p, EndX, 235+175*p);
	    				     }                                                             
	    				     g2d.dispose();                                             // gets rid of the copy
	    				     return Printable.PAGE_EXISTS;                              // i.e., end of job
	    			       }
	    			          else
	    			          {
	    			           return Printable.NO_SUCH_PAGE;
	    			          }
	    			        }
	    			      });
	    	      if (pjob.printDialog() == false)          // choose printer
	              return; 
	    	      pjob.print(); 
	    	    } catch (PrinterException pe) { pe.printStackTrace(); }	
	    	}
	        });
	        
	        JButton buttonFYTT = new JButton("FYJC TT");
	        buttonFYTT.addActionListener(new ActionListener()
	        {	
	          public void actionPerformed(ActionEvent arg0)
	             
	              {   
	 	       		 try {
	 	       		  PrinterJob pjob = PrinterJob.getPrinterJob();
		   		      PageFormat pf = pjob.pageDialog(pjob.defaultPage());
	 	   		      pjob.setJobName("FYJC - TIME TABLE");
	 	   		      pjob.setPrintable(new Printable() {
	 	   	             public int print(Graphics pg, PageFormat pf, int pageNum)
	 	   		        {
	 	   		        if (pageNum < totalpages) { 
	 	   		          int NOD = NODOfExams();
//	 	   		          int PaperWidth = 595;   int LastX = 0 , LastY = 0;	 	   		          
	 	   		          Font newFont;    
	 	   		          int rows = jTable.getRowCount();	 	   		          	 	   		     
	   		              int StartX = 72, StartY = 82, EndX = 522, EndY = 770, HtDiff = 20 ;        // Border coordinates 	   		          
	   		              int StartPosX = EndX/2 + StartX/2;  int LastY = 0;
	   		          
	   				  newFont = new Font("Times New Roman", Font.PLAIN, 14);  
	   				  pg.setFont(newFont);	   				  
	   		          FontMetrics metrics = pg.getFontMetrics(newFont);
	   				  String CollName = textField3.getText().toUpperCase();
	   				  int  ColNameWidth = (int) (metrics.stringWidth(CollName));	   				  
	   				  pg.drawString(CollName, StartPosX-(ColNameWidth)/2, StartY);
	   				 pg.drawLine(StartX, (StartY+25), EndX, (StartY+25));
	 	   				  newFont = new Font("Times New Roman", Font.PLAIN, 12);  
	 	   				  pg.setFont(newFont);	   				  
	 	   				  String str0 = textField0.getText().toUpperCase();
	 	   				  pg.drawString("STREAM : "+ str0, 72, 105);
	 	   				  String str1 = textField1.getText().toUpperCase();
	 	   				  pg.drawString("EXAM  : " + str1, 240, 105);
	 	   				  pg.drawString("CLASS  :  FYJC", 440, 105);	 	   				  
	 	   				  newFont = new Font("Times New Roman", Font.BOLD, 12);  
	 	   				  pg.setFont(newFont);
	 	   				for(int j = 0; j < 4; j++){
		 	   				pg.drawRect(100 + j*100, 150, 100, 25);        // Printing Rectangular grid Headings
		 	   			}
	 	   				  pg.drawString("DATE", 130, 167);
	 	   				  pg.drawString("DAY", 230, 167);
	 	   			      pg.drawString("SUBJECT", 320, 167);
	 	   		          pg.drawString("TIME", 430, 167);                
	 	   		          
	 	   		          newFont = new Font("Times New Roman", Font.BOLD, 14);  
 	   				      pg.setFont(newFont);
	 	   				  pg.drawString("TIME  TABLE", 245, 125);
	 	   				  pg.drawLine(242, 128 , 345, 128);
	 	   				
//	 	   				  pg.drawLine(20, 100 , 600, 100);
/*	 	   				  newFont = new Font("Times New Roman", Font.PLAIN, 9);  
	 	   				  pg.setFont(newFont);	       
	 	   				        */				
	 	   				 int skip = 0; 
	 	   				 for(int i = 0; i < NOD; i++){
	 	   				   if (GetData(jTable, i, 3).trim().isEmpty()){skip--; }	
		   				    for(int j = 0; j < 4; j++){ 	 	   					  	   							
	 	   						 pg.drawRect(100 + j*100, 175 + (i+skip)*25, 100, 25);        // Printing Rectangular grid
	 	   					         LastY = 200 + (i+skip)*25;  
	 	   					  }	   					
	 	   				  }                      
	 	   				int skip1 = 0; 
 	   					  for(int i = 0; i < NOD; i++){ 	
	  	   					    newFont = new Font("Times New Roman", Font.PLAIN, 14);          
	 		   				    pg.setFont(newFont);	
	 		   				if (GetData(jTable, i, 3).trim().isEmpty()){skip1--; }
	 		   				  else{
	 		   				    pg.drawString((String)GetData(jTable, i, 0), 125, 195 + (i+skip1)*25);     // Printing Exam Dates
	 	   					    pg.drawString((String)GetData(jTable, i, 1), 235, 195 + (i+skip1)*25);     // Printing Exam Day
	 	   					    pg.drawString((String)GetData(jTable, i, 3), 330, 195 + (i+skip1)*25);     // Printing Subjects
	 	   					    pg.drawString((String)GetData(jTable, i, 2), 410, 195 + (i+skip1)*25);     // Printing Exam Timings
	 		   				  }
	   	
	 	   					  }                   
	 	   					newFont = new Font("Times New Roman", Font.PLAIN, 14);          
	 		   				pg.setFont(newFont);	
	 		   				pg.drawString("Exam - Chair Person", 72, LastY + 100);  
	 		   				pg.drawString("Co-ordinator", 275, LastY + 100);
	 		   				pg.drawString("Vice Principal", 435, LastY + 100);       

	 	   					  
	 	   				  return Printable.PAGE_EXISTS;                       // ie., end of job
	 	   				}
	 	   			 else
	 	   				{
	 	   				 return Printable.NO_SUCH_PAGE;
	 	   				}
	 	   			}
	 	   		});
	 	   				
	 	   		    if (pjob.printDialog() == false)                        // choose printer
	 	   			 return; 
	 	   			 pjob.print(); 
	 	   			} catch (PrinterException pe) { pe.printStackTrace(); }	

	 	            }	            	
	             
	        });
	        
	        JButton buttonSYTT = new JButton("SYJC TT");
	        buttonSYTT.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent arg0)
	            {
		              {   
		 	       		 try {
		 	   		      PrinterJob pjob = PrinterJob.getPrinterJob();
			   		      PageFormat pf = pjob.pageDialog(pjob.defaultPage());
		 	   		      pjob.setJobName("SYJC - TIME TABLE");
		 	   	//		      pjob.setCopies(1);
		 	   		      pjob.setPrintable(new Printable() {
			 	   	             public int print(Graphics pg, PageFormat pf, int pageNum)
				 	   		        {
				 	   		        if (pageNum < totalpages) { 
				 	   		          int NOD = NODOfExams();
//				 	   		          int PaperWidth = 595;   int LastX = 0 , LastY = 0;	 	   		          
				 	   		          Font newFont;    
				 	   		          int rows = jTable.getRowCount();	 	   		          	 	   		     
				   		              int StartX = 72, StartY = 82, EndX = 522, EndY = 770, HtDiff = 20 ;        // Border coordinates 	   		          
				   		              int StartPosX = EndX/2 + StartX/2;  int LastY = 0;
				   		          
				   				  newFont = new Font("Times New Roman", Font.PLAIN, 14);  
				   				  pg.setFont(newFont);	   				  
				   		          FontMetrics metrics = pg.getFontMetrics(newFont);
				   				  String CollName = textField3.getText().toUpperCase();
				   				  int  ColNameWidth = (int) (metrics.stringWidth(CollName));	   				  
				   				  pg.drawString(CollName, StartPosX-(ColNameWidth)/2, StartY);
				   				 pg.drawLine(StartX, (StartY+25), EndX, (StartY+25));
				 	   				  newFont = new Font("Times New Roman", Font.PLAIN, 12);  
				 	   				  pg.setFont(newFont);	   				  
				 	   				  String str0 = textField0.getText().toUpperCase();
				 	   				  pg.drawString("STREAM : "+ str0, 72, 105);
				 	   				  String str1 = textField1.getText().toUpperCase();
				 	   				  pg.drawString("EXAM  : " + str1, 240, 105);
				 	   				  pg.drawString("CLASS  :  SYJC", 440, 105);	 	   				  
				 	   				  newFont = new Font("Times New Roman", Font.BOLD, 12);  
				 	   				  pg.setFont(newFont);
				 	   				for(int j = 0; j < 4; j++){
					 	   				pg.drawRect(100 + j*100, 150, 100, 25);        // Printing Rectangular grid Headings
					 	   			}
				 	   				  pg.drawString("DATE", 130, 167);
				 	   				  pg.drawString("DAY", 230, 167);
				 	   			      pg.drawString("SUBJECT", 320, 167);
				 	   		          pg.drawString("TIME", 430, 167);                
				 	   		          
				 	   		          newFont = new Font("Times New Roman", Font.BOLD, 14);  
			 	   				      pg.setFont(newFont);
				 	   				  pg.drawString("TIME  TABLE", 245, 125);
				 	   				  pg.drawLine(242, 128 , 345, 128);
				 	   				
//				 	   				  pg.drawLine(20, 100 , 600, 100);
			/*	 	   				  newFont = new Font("Times New Roman", Font.PLAIN, 9);  
				 	   				  pg.setFont(newFont);	       
				 	   				        */				
				 	   				 int skip = 0; 
				 	   				 for(int i = 0; i < NOD; i++){
				 	   				   if (GetData(jTable, i, 4).trim().isEmpty()){skip--; }	
					   				    for(int j = 0; j < 4; j++){ 	 	   					  	   							
				 	   						 pg.drawRect(100 + j*100, 175 + (i+skip)*25, 100, 25);        // Printing Rectangular grid
				 	   					         LastY = 200 + (i+skip)*25;  
				 	   					  }	   					
				 	   				  }                      
				 	   				int skip1 = 0; 
			 	   					  for(int i = 0; i < NOD; i++){ 	
				  	   					    newFont = new Font("Times New Roman", Font.PLAIN, 14);          
				 		   				    pg.setFont(newFont);	
				 		   				if (GetData(jTable, i, 4).trim().isEmpty()){skip1--; }
				 		   				  else{
				 		   				    pg.drawString((String)GetData(jTable, i, 0), 125, 195 + (i+skip1)*25);     // Printing Exam Dates
				 	   					    pg.drawString((String)GetData(jTable, i, 1), 235, 195 + (i+skip1)*25);     // Printing Exam Day
				 	   					    pg.drawString((String)GetData(jTable, i, 4), 330, 195 + (i+skip1)*25);     // Printing Subjects
				 	   					    pg.drawString((String)GetData(jTable, i, 2), 410, 195 + (i+skip1)*25);     // Printing Exam Timings
				 		   				  }
				   	
				 	   					  }                   
				 	   					newFont = new Font("Times New Roman", Font.PLAIN, 14);          
				 		   				pg.setFont(newFont);	
				 		   				pg.drawString("Exam - Chair Person", 72, LastY + 100);  
				 		   				pg.drawString("Co-ordinator", 275, LastY + 100);
				 		   				pg.drawString("Vice Principal", 435, LastY + 100);       
		 	   				 	 	   				    
		 	   				  return Printable.PAGE_EXISTS;                     // ie., end of job
		 	   				}
		 	   			 else
		 	   				{
		 	   				 return Printable.NO_SUCH_PAGE;
		 	   				}
		 	   			}
		 	   		});
		 	   				
		 	   		    if (pjob.printDialog() == false)                        // choose printer
		 	   			 return; 
		 	   			 pjob.print(); 
		 	   			} catch (PrinterException pe) { pe.printStackTrace(); }	

		 	            }	            	
		             }

	        });
	        
	        JButton buttonTest = new JButton("EQUALISE");
	        buttonTest.addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent arg0)	            
	             {  
/*	            	Randomizer();
	            	show("PAUSE");                         
	            	int NOT =  NumberOfTeachers();      */
	            	 RemoveDuplicates();
	            //	else show("No Row of subject column is empty");
//	        		SwapDuties();	            		     
//	            	SumOfDuties();    
	             }
	        });
	        
	        southPanel.add(buttonDistri);
	        southPanel.add(buttonLoad);
	        southPanel.add(buttonSave);
	        southPanel.add(buttonUpdate);
	        southPanel.add(buttonMastChart);
	        southPanel.add(buttonIndTT);
	        southPanel.add(buttonFYTT);
	        southPanel.add(buttonSYTT);
	        southPanel.add(buttonTest);
	        
	        add(southPanel, BorderLayout.SOUTH);
	//     	String Test = GetData(jTable,6,1);
	//      	show(Test);
	//      	Show(headers.length);
	        
	    }
	 	
	  public  String DayOfWeek(String det)                                              //Format dd/MM/yy
	    	 {  SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yy");
		        Date MyDate = null;
		try {  MyDate = newDateFormat.parse(det); } catch (ParseException e) { e.printStackTrace(); }
		   newDateFormat.applyPattern("EEE");
		 String dow = newDateFormat.format(MyDate);
		 return dow;
		 }
	
	  public  String IncrementDate(String det)                                             //Format dd/MM/yyyy
			  {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
				Calendar c = Calendar.getInstance();
				try { c.setTime(sdf.parse(det)); } catch (ParseException e) { e.printStackTrace();	}
				c.add(Calendar.DATE, 1);                                                   // number of days to add
				det = sdf.format(c.getTime());                                            // det is now the new date
				return det;
			  }       
			  
	  public class TableHeaderMouseListener extends MouseAdapter {	       
			        private JTable jTable;
			         
		  public TableHeaderMouseListener(JTable table) {
			            this.jTable = table;
			        }  
		  public void mouseClicked(MouseEvent event) {
			         Point point = event.getPoint();
			         int column = jTable.columnAtPoint(point);
			         JTableHeader TablHdr = jTable.getTableHeader();
	//				 TableColumnModel tcm = TablHdr.getColumnModel(); 
			         String Name = JOptionPane.showInputDialog(jTable, "Column No." + (column+6) + " is clicked");
			         jTable.getColumnModel().getColumn(column).setHeaderValue(Name.toUpperCase()); 
			         TablHdr.repaint();		         
			     }
			  }    
	  
	  public int NumberOfBlocks(){
		  int NumOfBlocks = 0;
			int NOT =NumberOfTeachers();
			String CellValue = null;
			BloxArray.clear();
			for(int j = 5; j < NOT + 5; j++){    
				 CellValue = GetData(jTable, 0, j);
				 if(CellValue == null || CellValue == "" || CellValue.trim().length() == 0){ continue;}
				 else BloxArray.add(CellValue);
			     NumOfBlocks = BloxArray.size();
			 } 		  	  
		return NumOfBlocks;
	  }
	  
	  public int NODOfExams(){            // NODOfExams = Number OF Days Of Exam
		  int NumOfDays = 0;
		  int rows = jTable.getRowCount();
		  String CellData = null;
		  for(int i = rows-1; i >= 0; i--){
			  CellData = GetData(jTable, i, 0);
			  if(CellData == null || CellData == "" || CellData.trim().length() == 0){ continue;}
			  else NumOfDays++;
		  }		  
		return NumOfDays;
	  }
	  
	  public int NumberOfTeachers(){
		  int NOT = 0;
		  int cols = jTable.getColumnCount();
		  int NumOfTrs = 0, n = 0 ;
			for( n = cols-1; n > 0; n--){  
				JTableHeader TablHdr = jTable.getTableHeader();
				 TableColumnModel tcm = TablHdr.getColumnModel();
				 TableColumn tc = tcm.getColumn(n);
			    String TrName =  (String) tc.getHeaderValue();					
				if(TrName == null || TrName == "" || TrName.trim().length() == 0){ continue; }
				else NumOfTrs++;
			}     
			NOT = (NumOfTrs++) + 1;	  
		return NOT;	  
	  }
	  
		public int TotalOfIndividualDuties(int col){
			 String RowValue = null;
		     int rows = jTable.getRowCount(); 
	         int Total = 0;
	     	for(int i = 0; i < rows - 2; i++)   // NumofSubjects Equivalent to number of days of exam
	      	  {
	     	     RowValue =  GetData(jTable,i, col);              //  Total Number of Duties column     	   
				 if(RowValue == null || RowValue == "" || RowValue.trim().length() == 0){ continue; }
				 else Total++;
	     	  }
			return Total;
		}
		
		public void SumOfDuties(){
			   String ColValue = null;
			   int rows = jTable.getRowCount(); 
	           int counter = 0;
	           for(int j = 5; j < NumberOfTeachers() + 5 ; j++) 
	        	 {  counter = 0;
	        	 for (int i = 0; i <  NODOfExams(); i++)
	         	  {
	        	   ColValue = (String) GetData(jTable,i,j);             
	   	           String TrimedColValue = ColValue.trim();
	   	           int length = TrimedColValue.length();
	   	            if (length != 0)counter++;
	        	 }
	        	  String temptotal = String.valueOf(counter); 
	        	  SetData2( temptotal,rows - 2,j);
	       }    
		}
		
		public int LeastNumDuties(int NumOfTeachers){
			int Minimum = 5;
			for(int i = 5; i < NumOfTeachers + 5; i++){
				if(TotalOfIndividualDuties(Minimum) > TotalOfIndividualDuties(i)){
					Minimum = i;
				}
			}
			return Minimum;
		}
		
		public int LargestNumDuties(int NumOfTeachers){
			int Maximum = 5;
		   for(int j = 5; j < NumOfTeachers+5; j++)
		   {  
			  if(TotalOfIndividualDuties(Maximum) < TotalOfIndividualDuties(j)){
				 Maximum = j;
			     }  
		      }
		return Maximum;
		}
		
		public int recever (){
			int NOTs = NumberOfTeachers();
		    int NODE = NODOfExams();
		    int NOB = NumberOfBlocks();
			float NumofDutiesPerTeacher = (float)((NODE)*(NOB))/(float) NOTs ;
		    int MnNOD =  (int) Math.floor(NumofDutiesPerTeacher);                 //  Minimum No Of Duties
			  int rec ;
/*			  show(" The Number of Teachers = " + NOTs
			        + " \n The Number of Days of Exam = " + NODE
			        + " \n The Number of Blocks = " + NOB
			        + " \n The Total Number of Duties = " + (NODE*NumberOfBlocks())
			        + " \n The Number of Duties per Teacher = " + NumofDutiesPerTeacher
			        + " \n The Minimum number of Duties = " + MnNOD);                 */
			  for ( rec = 5; rec < NOTs + 5; rec++){
				if (TotalOfIndividualDuties(rec) < MnNOD) {
//					show("Total Number of duties for the " +rec +"th Person is = " +TotalOfIndividualDuties(rec)
//							+"\nSo, the " +rec +"th person is the Reciever");
					return rec;
				}
			}
			return -1; 
		}
			  
		public int donor(){
			int NOTs = NumberOfTeachers();
		    int NODE = NODOfExams();
		    int NOB = NumberOfBlocks();
		    float NumofDutiesPerTeacher = (float)((NODE)*(NOB))/(float) NOTs ;
			int MxNOD =   (int) Math.ceil(NumofDutiesPerTeacher);               //  Maximum No Of Duties
			int don ;
/*			 show(" The Number of Teachers = " + NOTs
				        + " \n The Number of Days of Exam = " + NODE
				        + " \n The Number of Blocks = " + NOB
				        + " \n The Total Number of Duties = " + (NODE*NumberOfBlocks())
				        + " \n The Number of Duties per Teacher = " + NumofDutiesPerTeacher
				        + " \n The Maximum number of Duties = " + MxNOD);                       */
	
			for ( don = 5; don < NOTs + 5; don++)
			{
				if (TotalOfIndividualDuties(don) > MxNOD){
//				show("Total Number of duties for the " +don +"th Person is = " +TotalOfIndividualDuties(don)
//						+"\nSo, the " +don +"th person is the Donor");
				return don;
				}
			}
			return -1; 
		}
		
	public void SwapDuties(){
		
		int NOT = NumberOfTeachers();
    	int NOD = NODOfExams();
    	int NOB = NumberOfBlocks();
    	int G = LargestNumDuties(NumberOfTeachers());
		int S = LeastNumDuties(NumberOfTeachers());
    	int R = recever(),  D = donor();             //  R = Reciever's Column value , D = Donor's Column value.
//    	show("Largest Number of duties is for " + G + "the person \nAnd the Least Number of duties is for " + S 
//    			+ "th person \nSo the Reciever is : " +R + "th Person " + " \n And Donor is : " + G + "th person");
		if(R == -1 && D ==-1){show("Process Over  !!!");return; }
    	int TotalNumOfSupervisions = NOD*NOB;
    	float NOfSup2eachTr = ((float) TotalNumOfSupervisions)/ (float) NOT;
		for(int i = 0; i< NODOfExams(); i++)
		 {		                       
		  if(GetData(jTable, i, S).length() == 0 && GetData(jTable, i, G).length() != 0)
		   {
			 SetData2(GetData(jTable, i, G),i, S);
			 SetData2(" ", i, G);
			 SumOfDuties();
//			 show("PAUSE");
			 return;
		   }
	     }    	
	}
	
	  public void RemoveDuplicates(){
      	int nod = NODOfExams();
//      	show(GetData(jTable, 0, 5));
      	for (int i = 0; i < nod; i++){
      		for (int j = 0; j < nod; j++){
      	      if (i != j && GetData(jTable, i, 5).trim().length() != 0 && GetData(jTable, j, 5).trim().length() != 0
      	    		&& GetData(jTable, i, 5) == GetData(jTable, j, 5)) 
      	        { show("Room No. : " + GetData(jTable, i, 5)+" in " + i +"th row is repeated i.e. " 
      	                + GetData(jTable, j, 5) +" in " +j + "th Row  ");}
      	      else show("No Rooms or Blocks are repeated");      	            	
      	    }
      	}                               
	  }


	public void distribute(){
		 //  NOB = Number Of Blocks , NOT = Number Of Teachers
		 int NOT = NumberOfTeachers();
		 int rows = jTable.getRowCount(); 
		 int NOB = NumberOfBlocks();
//		 Show(NOT);
//		 Show(NOB);
			 ClearjTable();
			 int c = 0;
			 for(int i = 0; i < NODOfExams(); i++){
				 c = i;
				 for(int j = 0; j < NOB ; j++){
					 SetData2(BloxArray.get(j), i, c%NOT +5);
					 c++;
				 }		 
			 }	 
			 SumOfDuties();
	  }			  
	 
	  public void SaveToFile(String fnem)
			   {
				 FileWriter fw=null;		
				 try {fw = new FileWriter(fnem); }    catch (IOException e1){e1.printStackTrace();}
				 String newLine = System.getProperty("line.separator");
				 int rows = jTable.getRowCount();
				 int cols = jTable.getColumnCount();
				 int freezcols = freezeTable.getColumnCount();
				 try { fw.write(textField3.getText()+newLine); }  catch (IOException e1) {e1.printStackTrace();}
				 try { fw.write(textField0.getText()+newLine); }  catch (IOException e1) {e1.printStackTrace();}
				 try { fw.write(textField1.getText()+newLine); }  catch (IOException e1) {e1.printStackTrace();}
				 try { fw.write(textField2.getText()+newLine); }  catch (IOException e1) {e1.printStackTrace();}
	                 
				 for(int i = 0; i < freezcols; i++)
				 { 
					 JTableHeader fTablHdr = freezeTable.getTableHeader();
					 TableColumnModel ftcm = fTablHdr.getColumnModel();
					 TableColumn ftc = ftcm.getColumn(i);
					 ftc.getHeaderValue();
//					 show((String) ftc.getHeaderValue());
					 try { fw.write(ftc.getHeaderValue() + newLine);} catch (IOException e1) {e1.printStackTrace(); }			 
				  } 
  				 for(int i = 0; i < freezcols +  NumberOfTeachers() ; i++)
  				 { 
					 JTableHeader jTablHdr = jTable.getTableHeader();
					 TableColumnModel jtcm = jTablHdr.getColumnModel();
					 TableColumn jtc = jtcm.getColumn(i);
					 jtc.getHeaderValue();
					 try { fw.write(jtc.getHeaderValue() + newLine);} catch (IOException e1) {e1.printStackTrace(); }			 
				  } 
//				 Show(freezcols +  NumberOfTeachers());
				   String freezTableContent;
				   for(int j = 0; j < freezcols ; j++){
				 	  for(int i = 0; i < rows; i++){
				    	 freezTableContent = ((String) GetData1(freezeTable,i,j)); 
						  try { fw.write(freezTableContent + newLine);} catch (IOException e1) {e1.printStackTrace(); }   				       
				      } 
				   }

				   String jTableContent;
				   for(int j = 5; j < NumberOfTeachers() + 5; j++){
				 	  for(int i = 0; i < rows; i++){
				    	 jTableContent =  GetData(jTable,i,j); 
						  try { fw.write(jTableContent + newLine);} catch (IOException e1) {e1.printStackTrace(); }   				       
				      } 
				   } 		                  
				   
				   try {fw.close();} catch (IOException e1) {e1.printStackTrace();}            
				       
	    }   
			  
	  public void LoadFile(String fnem){
		          ClearTable();
		       	  int rows = jTable.getRowCount();                   // To get the number of rows of jTable.
			      int cols = jTable.getColumnCount();                // To get the number of columns of jTable.	 
			      int freezcols = freezeTable.getColumnCount();
		          
			      for (int i = 0; i < rows; i++){
				      for (int j = 1; j < cols + freezcols; j++){
					    SetData1(" ",i,j);
			     		}
			         } 
				   	BufferedReader reader = null;
		 	        try { 	reader = new BufferedReader(new FileReader(fnem)); } 
		 	        catch (FileNotFoundException e) { e.printStackTrace(); }
		 	       
		 	        strArray.clear();
		 	        String line = null;                                                        
		 	        try { while ((line = reader.readLine()) != null) { strArray.add(line); } } // Collecting all data in to strArray 
		 	        catch (IOException e) {e.printStackTrace(); }
		         	int size = strArray.size(); 
				    
//		         	show("Size of strArray : " +size);
//				    show("Number of jTable Columns : " + cols);
//				    show("Number of Freezed Columns : " +freezcols);
				    
				    textField3.setText(strArray.get(0));
				    textField0.setText(strArray.get(1));
				    textField1.setText(strArray.get(2));
		            textField2.setText(strArray.get(3));
		            int count = 4;
		            for(int i = 0; i < 5; i++){
		              freezeTable.getColumnModel().getColumn(i).setHeaderValue(strArray.get(count));
		              if(count < 9) count++;
		              freezeTable.getTableHeader().repaint();
		            }
		            count = 9;
		//            show( (String) strArray.get(7));
		            
		            for(int i = 0; i < freezcols +  NumberOfTeachers() ; i++){
			              jTable.getColumnModel().getColumn(i).setHeaderValue(strArray.get(count));
			              if(count < freezcols+cols+2) count++;                     //  n(freezcols+cols+2)  =  19
			              jTable.getTableHeader().repaint();
			            }
		            
		            for(int i = 0; i < freezcols ; i++){
		            	for(int j = 0; j < rows; j++){
		            		if(strArray.get(count).equals("null")){SetData1(" ", j,i);}
		            		else SetData1(strArray.get(count), j,i);
		            		count++;
		            	}
		            }     

//		            Show(NOB);	
		            for(int i = 5; i < NumberOfTeachers() + 5; i++){
		            	for(int j = 0; j < rows; j++){
		            		if(strArray.get(count).equals("null")){SetData2(" ", j,i);}
		            	    else SetData2(strArray.get(count), j,i);
		            		count++;
		            	}
		            }   	                     
			  }
	   
	  public static int randInt(int min, int max) {
		    Random rand = new Random();
		    int randomNum = rand.nextInt((max - min) + 1) + min;   // nextInt is normally exclusive of the top value, so add 1 to make it inclusive
		    return randomNum;
		}
	  
	  public void ClearTable()
	  {  int rows = jTable.getRowCount();
		 int cols = jTable.getColumnCount();
			 for (int i = 0; i < rows ; i++)
			  {
			    for (int j = 0; j < cols; j++)
			      {
					SetData2("", i, j);
				  }
			  }        
	  }
	  
	  public void ClearjTable()
	  {  int rows = jTable.getRowCount();
		 int cols = jTable.getColumnCount();
			 for (int i = 0; i < rows ; i++)
			  {
			    for (int j = 5; j < cols; j++)
			      {
					SetData2("", i, j);
				  }
			  }        
	  }
	  
	  public int Randomizer1() {
		        int[] randomSequence = new int[NumOfTrs];
		        Random randomNumbers = new Random();
	            int r = 0;
		        for (int i = 0; i < randomSequence.length; i++ ) {
		            if (i == 0) { 
		                randomSequence[i] = 0; 
		            } else { 
		                int pointer = randomNumbers.nextInt(i + 1);
		                randomSequence[i] = randomSequence[pointer]; 
		                randomSequence[pointer] = i;
		             }
		        }
		        for (int i = 0; i < randomSequence.length; i++) {
		        	r = randomSequence[i];
		//        	Show(r);
		 //               System.out.printf("%2d ", number);
		        }
				return r;
		    }	  
	  
   	//     RANDOM NUMBER GENERATOR 
	 public void Randomizer(){
		 int NOT = NumberOfTeachers();
		 int NOD = NODOfExams();   int NOB = NumberOfBlocks();
		 ClearjTable();
	 for( int j = 0; j < NOT+1; j++) {
	      try
	      {   	    	  	    	  
	          myNumbers = new int[BloxArray.size()];
	          Random r = new Random();
	          int total_elements_cnt = 0;
	          boolean loop_status = true;
	          while(loop_status)                         
	          {
	              int next_num = r.nextInt(NOT)+1;           
	              if(!isCompleted()){
	                  if(!isDuplicate(next_num)){
	                      myNumbers[total_elements_cnt] = next_num;
	                      total_elements_cnt++;
	                  }else { continue; }
	              }else{ loop_status = false; }
	          }
	          for (int i = 0; i < NOB; i++) 
	          { 
	        	  if(j >= NOD){ break; }
	        	  else SetData2(BloxArray.get(i), j, (myNumbers[i]-1)+5); 	        	  
	        	  SumOfDuties();
	          }
	      } catch (Exception e) { e.printStackTrace(); }
	    }  
	  }
	
	  public static boolean isCompleted(){
	      boolean status = true;
	      for (int i = 0; i < myNumbers.length; i++){
	          if(myNumbers[i]==0){
	              status = false;
	              break;
	          }
	      }
	      return  status;
	  }
	  
	  public static boolean isDuplicate(int num){
	      boolean status = false;
	      for (int i = 0; i < myNumbers.length; i++){
	          if(myNumbers[i]== num){
	              status = true;
	              break;
	          }
	      }
	      return  status;           
	  }
	  
		} 
	  
