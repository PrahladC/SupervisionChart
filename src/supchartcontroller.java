import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


public class supchartcontroller {
	
	private supchartview View = new supchartview();
	private supchartmodel Model = new supchartmodel();
	
	ArrayList<String> strArray = new ArrayList<String>();
	ArrayList<String> BloxArray = new ArrayList<String>();
	
	public static int[] myNumbers = null;
	public int  NumOfTrs = 0;
	
//	private javax.swing.JTable jTable;
//    private javax.swing.JTable freezeTable;


	public String GetData (JTable table, int row_index, int col_index) {  
		return View.getTable().getModel().getValueAt(row_index, col_index).toString(); 
		}
	
	public String GetData1 (JTable table, int row_index, int col_index) {  
		return View.getFreezeTable().getModel().getValueAt(row_index, col_index).toString(); 
		}
	
	public void SetData1(Object obj, int row_index, int col_index) {  
		View.getFreezeTable().getModel().setValueAt(obj,row_index,col_index);   }
	
	public void SetData2(Object obj, int row_index, int col_index) {  
        View.getTable().getModel().setValueAt(obj,row_index,col_index);   }

	
	public static void show(String msg) {JOptionPane.showMessageDialog(null, msg);}
	public void Show(int msg) {JOptionPane.showMessageDialog(null, msg);}

	public supchartcontroller (supchartmodel model, supchartview view){
		  
		    this.Model = model;
	        this.View = view;   
		    System.out.println(model.getJarPath());       ///set JAR path in model variable path;

		
		JTableHeader jTableHeader = View.getTable().getTableHeader();
	    jTableHeader.addMouseListener(new TableHeaderMouseListener(View.getTable()));
	    JTableHeader TableHeader = View.getFreezeTable().getTableHeader();
	    TableHeader.addMouseListener(new TableHeaderMouseListener(View.getFreezeTable()));
		
	}
		
	public supchartcontroller() {
		// TODO Auto-generated constructor stub
	}

	public void ClearTable()
	{  int rows = View.getTable().getRowCount();
		 int cols = View.getTable().getColumnCount();
			 for (int i = 0; i < rows ; i++)
			  {
			    for (int j = 0; j < cols; j++)
			      {
					SetData2("", i, j);
				  }
			  }        
     }

	
	  public void ClearjTable()
	  {  int rows = View.getTable().getRowCount();
		 int cols = View.getTable().getColumnCount();
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
		     int rows = View.getTable().getRowCount(); 
	         int Total = 0;
	     	for(int i = 0; i < rows - 2; i++)   // NumofSubjects Equivalent to number of days of exam
	      	  {
	     	     RowValue =  GetData(View.getTable(),i, col);              //  Total Number of Duties column     	   
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
				 CellValue = GetData(View.getTable(), 0, j);
				 if(CellValue == null || CellValue == "" || CellValue.trim().length() == 0){ continue;}
				 else BloxArray.add(CellValue);
			     NumOfBlocks = BloxArray.size();
			 } 		  	  
		return NumOfBlocks;
	  }
	  
	  public int NODOfExams(){            // NODOfExams = Number OF Days Of Exam
		  int NumOfDays = 0;
		  int rows = View.getTable().getRowCount();
		  String CellData = null;
		  for(int i = rows-1; i >= 0; i--){
			  CellData = GetData(View.getTable(), i, 0);
			  if(CellData == null || CellData == "" || CellData.trim().length() == 0){ continue;}
			  else NumOfDays++;
		  }		  
		return NumOfDays;
	  }

	int NumberOfTeachers(){
		  int NOT = 0;
		  int cols = View.getTable().getColumnCount();
		  int NumOfTrs = 0, n = 0 ;
			for( n = cols-1; n > 0; n--){  
				JTableHeader TablHdr = View.getTable().getTableHeader();
				 TableColumnModel tcm = TablHdr.getColumnModel();
				 TableColumn tc = tcm.getColumn(n);
			    String TrName =  (String) tc.getHeaderValue();					
				if(TrName == null || TrName == "" || TrName.trim().length() == 0){ continue; }
				else NumOfTrs++;
			}     
			NOT = (NumOfTrs++) + 1;	  
		return NOT;	  
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
	
public void SumOfDuties(){
		   String ColValue = null;
		   int rows = View.getTable().getRowCount(); 
         int counter = 0;
         for(int j = 5; j < NumberOfTeachers() + 5 ; j++) 
      	 {  counter = 0;
      	 for (int i = 0; i <  NODOfExams(); i++)
       	  {
      	   ColValue = (String) GetData(View.getTable(),i,j);             
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
		  for ( rec = 5; rec < NOTs + 5; rec++){
			if (TotalOfIndividualDuties(rec) < MnNOD) {
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

		for ( don = 5; don < NOTs + 5; don++)
		{
			if (TotalOfIndividualDuties(don) > MxNOD){
			return don;
			}
		}
		return -1; 
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
		         String Name = JOptionPane.showInputDialog(jTable, "Column No." + (column+6) + " is clicked");
		         jTable.getColumnModel().getColumn(column).setHeaderValue(Name.toUpperCase()); 
		         TablHdr.repaint();		         
		     }
		  }    
		 

}