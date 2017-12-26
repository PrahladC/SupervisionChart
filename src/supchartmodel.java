import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


public class supchartmodel {
	
	
	private supchartview View ;
//	private supchartcontroller Control = new supchartcontroller(null, View);
	
	private String PrinterName, path ;
	
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

    public supchartmodel(){
        //  To Do - Constructor
     }    

   	
   	
	public String getJarPath()
    {
    	File f = new File(System.getProperty("java.class.path"));
     	File dir = f.getAbsoluteFile().getParentFile();
        path=dir.toString();
     	return  path;
    }

	
	  public void SaveToFile(String fnem)
	   {
		 FileWriter fw=null;		
		 try {fw = new FileWriter(fnem); }    catch (IOException e1){e1.printStackTrace();}
		 String newLine = System.getProperty("line.separator");
		 int rows = View.getTable().getRowCount();
		 int cols = View.getTable().getColumnCount();
		 int freezcols = View.getFreezeTable().getColumnCount();
		 try { fw.write(View.Textfield3().getText()+newLine); }  catch (IOException e1) {e1.printStackTrace();}
		 try { fw.write(View.Textfield0().getText()+newLine); }  catch (IOException e1) {e1.printStackTrace();}
		 try { fw.write(View.Textfield1().getText()+newLine); }  catch (IOException e1) {e1.printStackTrace();}
		 try { fw.write(View.Textfield2().getText()+newLine); }  catch (IOException e1) {e1.printStackTrace();}
            
		 for(int i = 0; i < freezcols; i++)
		 { 
			 JTableHeader fTablHdr = View.getFreezeTable().getTableHeader();
			 TableColumnModel ftcm = fTablHdr.getColumnModel();
			 TableColumn ftc = ftcm.getColumn(i);
			 ftc.getHeaderValue();
//			 show((String) ftc.getHeaderValue());
			 try { fw.write(ftc.getHeaderValue() + newLine);} catch (IOException e1) {e1.printStackTrace(); }			 
		  } 
			 for(int i = 0; i < freezcols + NumberOfTeachers() ; i++)
			 { 
			 JTableHeader jTablHdr = View.getTable().getTableHeader();
			 TableColumnModel jtcm = jTablHdr.getColumnModel();
			 TableColumn jtc = jtcm.getColumn(i);
			 jtc.getHeaderValue();
			 try { fw.write(jtc.getHeaderValue() + newLine);} catch (IOException e1) {e1.printStackTrace(); }			 
		  } 
//		 Show(freezcols +  NumberOfTeachers());
		   String freezTableContent;
		   for(int j = 0; j < freezcols ; j++){
		 	  for(int i = 0; i < rows; i++){
		    	 freezTableContent = ((String) View.GetData1(View.getFreezeTable(),i,j)); 
				  try { fw.write(freezTableContent + newLine);} catch (IOException e1) {e1.printStackTrace(); }   				       
		      } 
		   }

		   String jTableContent;
		   for(int j = 5; j < NumberOfTeachers() + 5; j++){
		 	  for(int i = 0; i < rows; i++){
		    	 jTableContent =  GetData(View.getTable(),i,j); 
				  try { fw.write(jTableContent + newLine);} catch (IOException e1) {e1.printStackTrace(); }   				       
		      } 
		   } 		                  
		   
		   try {fw.close();} catch (IOException e1) {e1.printStackTrace();}            
		       
}   
	  
public void LoadFile(String fnem){
          ClearTable();
      	  int rows = View.getTable().getRowCount();                   // To get the number of rows of jTable.
	      int cols = View.getTable().getColumnCount();                // To get the number of columns of jTable.	 
	      int freezcols = View.getFreezeTable().getColumnCount();
         
	      for (int i = 0; i < rows; i++){
		      for (int j = 1; j < cols + freezcols; j++){
			    SetData1(" ",i,j);
	     		}
	         } 
		   	BufferedReader reader = null;
	        try { 	reader = new BufferedReader(new FileReader(fnem)); } 
	        catch (FileNotFoundException e) { e.printStackTrace(); }
	       
	        View.strArray.clear();
	        String line = null;                                                        
	        try { while ((line = reader.readLine()) != null) { View.strArray.add(line); } } // Collecting all data in to strArray 
	        catch (IOException e) {e.printStackTrace(); }
        	int size = View.strArray.size(); 
		    
//        	show("Size of strArray : " +size);
//		    show("Number of jTable Columns : " + cols);
//		    show("Number of Freezed Columns : " +freezcols);
		    
        	View.Textfield3().setText(View.strArray.get(0));
        	View.Textfield0().setText(View.strArray.get(1));
        	View.Textfield1().setText(View.strArray.get(2));
        	View.Textfield2().setText(View.strArray.get(3));
           int count = 4;
           for(int i = 0; i < 5; i++){
        	   View.getFreezeTable().getColumnModel().getColumn(i).setHeaderValue(View.strArray.get(count));
             if(count < 9) count++;
             View.getFreezeTable().getTableHeader().repaint();
           }
           count = 9;
//            show( (String) strArray.get(7));
           
           for(int i = 0; i < freezcols +  NumberOfTeachers() ; i++){
	              View.getTable().getColumnModel().getColumn(i).setHeaderValue(View.strArray.get(count));
	              if(count < freezcols+cols+2) count++;                     //  n(freezcols+cols+2)  =  19
	              View.getTable().getTableHeader().repaint();
	            }
           
           for(int i = 0; i < freezcols ; i++){
           	for(int j = 0; j < rows; j++){
           		if(View.strArray.get(count).equals("null")){SetData1(" ", j,i);}
           		else SetData1(View.strArray.get(count), j,i);
           		count++;
           	}
           }     

//           Show(NOB);	
           for(int i = 5; i < NumberOfTeachers() + 5; i++){
           	for(int j = 0; j < rows; j++){
           		if(View.strArray.get(count).equals("null")){SetData2(" ", j,i);}
           	    else SetData2(View.strArray.get(count), j,i);
           		count++;
           	}
           }   	                     
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



}
