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

	public void setPrinterName(String printername)
    {
       this.PrinterName = printername;
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
