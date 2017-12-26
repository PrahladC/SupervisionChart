import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFileChooser;
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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class supchartview extends javax.swing.JFrame {
	
	    /**
	 * 
	 */
	
	ArrayList<String> strArray = new ArrayList<String>();
	ArrayList<String> BloxArray = new ArrayList<String>();

	
//	private supchartcontroller control = new supchartcontroller();
	private supchartmodel Model = new supchartmodel();
	
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
  
public supchartview(){
	
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
//           SetData2(FYJCSubjects[i], i, 4);
	}
	
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
//    ClearTable();
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
//			System.exit(0);
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
		    Model.LoadFile(fyle);                                    //Load File is called here
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

	                Model.SaveToFile(fyle);                             //Save to File is called here
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

    
	
}

	public JTable getTable(){
	    return jTable;
	}

	public JTable getFreezeTable(){
		return freezeTable;
	}
	
	 public JTextField Textfield0(){
	        return textField0;
	    }

	
	 public JTextField Textfield1(){
	        return textField1;
	    }
	
	 public JTextField Textfield2(){
	        return textField2;
	    }

	 public JTextField Textfield3(){
	        return textField3;
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
		
		  public  String IncrementDate(String det)                                             //Format dd/MM/yyyy
		  {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			Calendar c = Calendar.getInstance();
			try { c.setTime(sdf.parse(det)); } catch (ParseException e) { e.printStackTrace();	}
			c.add(Calendar.DATE, 1);                                                   // number of days to add
			det = sdf.format(c.getTime());                                            // det is now the new date
			return det;
		  }       

		  public  String DayOfWeek(String det)                                              //Format dd/MM/yy
			 {  SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yy");
		        Date MyDate = null;
		       try {  MyDate = newDateFormat.parse(det); } catch (ParseException e) { e.printStackTrace(); }
		   newDateFormat.applyPattern("EEE");
		 String dow = newDateFormat.format(MyDate);
		 return dow;
		 }
	
		  
			int NumberOfTeachers(){
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

			  public static int randInt(int min, int max) {
				    Random rand = new Random();
				    int randomNum = rand.nextInt((max - min) + 1) + min;   // nextInt is normally exclusive of the top value, so add 1 to make it inclusive
				    return randomNum;
				}

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

				
				public void distribute(){
					 //  NOB = Number Of Blocks , NOT = Number Of Teachers
					 int NOT = NumberOfTeachers();
					 int rows = jTable.getRowCount(); 
					 int NOB = NumberOfBlocks();
//					 Show(NOT);
//					 Show(NOB);
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

			
			public void SwapDuties(){
				
				int NOT = NumberOfTeachers();
		    	int NOD = NODOfExams();
		    	int NOB = NumberOfBlocks();
		    	int G = LargestNumDuties(NumberOfTeachers());
				int S = LeastNumDuties(NumberOfTeachers());
		    	int R = recever(),  D = donor();             //  R = Reciever's Column value , D = Donor's Column value.
//		    	show("Largest Number of duties is for " + G + "the person \nAnd the Least Number of duties is for " + S 
//		    			+ "th person \nSo the Reciever is : " +R + "th Person " + " \n And Donor is : " + G + "th person");
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
//					 show("PAUSE");
					 return;
				   }
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
//						show("Total Number of duties for the " +rec +"th Person is = " +TotalOfIndividualDuties(rec)
//								+"\nSo, the " +rec +"th person is the Reciever");
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
//					show("Total Number of duties for the " +don +"th Person is = " +TotalOfIndividualDuties(don)
//							+"\nSo, the " +don +"th person is the Donor");
					return don;
					}
				}
				return -1; 
			}



	
}