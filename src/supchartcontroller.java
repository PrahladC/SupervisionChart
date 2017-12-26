import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


public class supchartcontroller {
	
	private supchartview View = new supchartview();
	private supchartmodel Model = new supchartmodel();
	
	ArrayList<String> strArray = new ArrayList<String>();
	ArrayList<String> BloxArray = new ArrayList<String>();
	
//	JButton buttonLoad;
//	JButton buttonSave;
	
	public static int[] myNumbers = null;
	public int  NumOfTrs = 0;


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
	
	 private ActionListener namesListener, blocksListener, distriListener, loadListener, saveListener,
	                        updateListener, masterprintListener, indprintListener;

	public supchartcontroller (supchartmodel model, supchartview view){
		  
		    this.Model = model;
	        this.View = view;   
		    System.out.println(model.getJarPath());       ///set JAR path in model variable path;

		
		JTableHeader jTableHeader = View.getTable().getTableHeader();
	    jTableHeader.addMouseListener(new TableHeaderMouseListener(View.getTable()));
	    JTableHeader TableHeader = View.getFreezeTable().getTableHeader();
	    TableHeader.addMouseListener(new TableHeaderMouseListener(View.getFreezeTable()));
		
	}
		
	public void control() {

		namesListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BtnAllot();			
			}
		};

		blocksListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BtnBlox();				
			}
		};

		distriListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BtnDistri();				
			}
		};
		
		loadListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BtnLoad();				
			}
		};
		
		saveListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BtnSave();				
			}
		};

		updateListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BtnUpdate();				
			}
		};
		
		masterprintListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BtnMasterprint();				
			}
		};
		
		indprintListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BtnIndprint();				
			}
		};
		
		
		View.getNamesButton().addActionListener(namesListener);
		View.getBlocksButton().addActionListener(blocksListener);
		View.getDistriButton().addActionListener(distriListener);
		View.getLoadButton().addActionListener(loadListener);
		View.getSaveButton().addActionListener(saveListener);
		View.getUpdateButton().addActionListener(updateListener);
		View.getMasterprintButton().addActionListener(masterprintListener);
		View.getIndprintButton().addActionListener(indprintListener);
		
	}

	protected void BtnIndprint() {
		try { 	    				 	    			
//			  int MidPageY = (EndY + StartY)/2;
			  final int NOTs =  NumberOfTeachers(); 	    			      
			  PrinterJob pjob = PrinterJob.getPrinterJob();
			  PageFormat pf = pjob.pageDialog(pjob.defaultPage());
		      pjob.setJobName("Individual Supervision Chart");
		      pjob.setPrintable(new Printable() {
		        public int print(Graphics pg, PageFormat pf, int pageNum)
		        {   
		        	int rows = View.getTable().getRowCount();
		        	int NOD = NODOfExams(); 
		        	int totalpages=0 , NumberOfPages = NOTs/4;
		        	int Remainder = NOTs % 4;
		        	if (Remainder == 0) 
				    	totalpages = NumberOfPages;
		        	else totalpages = NumberOfPages+1;
//		        	Show(NOD);
		        	
		     if (pageNum < totalpages) { 
		    	 int StartX = 72, StartY = 82, EndX = 522, EndY = 770, HtDiff = 20 ;        // Border coordinates	 
		    	 int MidPageX = 297;        //  (EndX + StartX)/2;
		        	Font newFont;
			        newFont = new Font("Times New Roman", Font.PLAIN, 14);
			        FontMetrics metrics = pg.getFontMetrics(newFont);
			     for(int p = 0; p < 4; p++){
			    	String CollName = View.Textfield3().getText().toUpperCase();
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
				           pg.drawString(GetData(View.getTable(),l,0), (StartX+1)+width*l, (StartY+60)+175*a);    // Printing Date
				           
				           newFont = new Font("Times New Roman", Font.PLAIN, 10);
   				         pg.setFont(newFont);
				           pg.drawString(GetData(View.getTable(),l,1), (StartX+5)+width*l, (StartY+75)+175*a);	// Printing Day  				          
			         }
		         }
			     
			     for(a = 0; a < 4; a++){
			         for(l = 0; l < NOD ; l++){	    				   	    				   
			           newFont = new Font("Times New Roman", Font.PLAIN, 7);
				       pg.setFont(newFont);
			           pg.drawString(GetData(View.getTable(),l,2), (StartX+1)+width*l, (StartY+90)+175*a);     // Printing Timings
			         }
			     }  
			     for(int i =0; i < NOTs; i++){
			         for(a = 0; a < 4; a++){	    				     
				    	 newFont = new Font("Times New Roman", Font.PLAIN, 10);       // Printing Supervisor's Codes
					   	 pg.setFont(newFont);	
				   		 JTableHeader jTablHdr = View.getTable().getTableHeader();
				   		 TableColumnModel jtcm = jTablHdr.getColumnModel();
				   		 TableColumn jtc = jtcm.getColumn(a+pageNum*4);
				   		 String SupNames = (String) jtc.getHeaderValue();
				   		  if (SupNames.length() > 10){ pg.drawString( SupNames.substring(0, 10), StartX + 2, (StartY+41)+175*a ); }
				   		  else pg.drawString( SupNames, StartX + 2, (StartY+41)+175*a );	
			         }
		         }   
			     
			     
			     for (a = 0; a < 4; a++){	    				         
			         for(l = 0; l < NOD; l++){
			    	   String temp = (String) GetData(View.getTable(), l, 5+pageNum*4+a);      // Duty of each teacher
				       pg.drawString(temp, (StartX+5)+(width*l), (StartY+105)+175*a);
			         }	    				     
			    }  
			     
			     for (a = 0; a < 4; a++){	    				         
			         for(l = 0; l < NOD; l++){
				       String temp2 = (String) GetData(View.getTable(), rows-2, 5+pageNum*4+a);  // Total Duty of each teacher
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

	protected void BtnMasterprint() {
		 final int totalpages  = 1;	
		 
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
		          int NOD = NODOfExams();
		          int StartX = 72, StartY = 82, EndX = 522, EndY = 770, HtDiff = 20 ;        // Border coordinates 
		          Font newFont;     int width = 35, ht = 15; 
		          int StartPosX = EndX/2 + StartX/2;
		          int rows = View.getTable().getRowCount();
				  newFont = new Font("Times New Roman", Font.PLAIN, 14);  
				  pg.setFont(newFont);	   				  
		          FontMetrics metrics = pg.getFontMetrics(newFont);
				  String CollName = View.Textfield3().getText().toUpperCase();
				  int  ColNameWidth = (int) (metrics.stringWidth(CollName));	   				  
				  pg.drawString(CollName, StartPosX-(ColNameWidth)/2, StartY);
				  String Title = "MASTER SUPERVISION CHART";
				  int TitleWidth = (int) (metrics.stringWidth(Title));				
				  pg.drawString(Title, StartPosX-(TitleWidth)/2, StartY+HtDiff);
				  
				  newFont = new Font("Times New Roman", Font.PLAIN, 11);  
				  pg.setFont(newFont);	
				  String str0 = View.Textfield0().getText().toUpperCase();
				  pg.drawString("STREAM : "+ str0, StartX, StartY+(2*HtDiff));
				  String str1 = View.Textfield1().getText().toUpperCase();
				  String str2 = View.Textfield2().getText().toUpperCase(); 
				  pg.drawString("EXAM  : " + str2 , StartX+150, StartY+(2*HtDiff));
//				  String str2 = textField2.getText().toUpperCase();
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
			   		 JTableHeader jTablHdr = View.getTable().getTableHeader();
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
					  pg.drawString((String)GetData(View.getTable(), i, 0), (StartX+66)+i*35,  StartY+(3*HtDiff)+1);  // Printing Exam Dates 
					  
					  newFont = new Font("Times New Roman", Font.PLAIN, 9);          // Printing Exam Dates and Subjects
   				  pg.setFont(newFont);	
					  pg.drawString((String)GetData(View.getTable(), i, 1), (StartX+66)+i*35, StartY+(3*HtDiff)+16);  // Printing Exam Days
					  pg.drawString((String)GetData(View.getTable(), i, 4), (StartX+66)+i*35, StartY+(3*HtDiff)+31);  // Printing Subjects
					  if(i == NOD){ pg.drawString("TOTAL", (StartX+66)+(i*35), StartY+(3*HtDiff)+46);}       // Printing TOTAL
					  else pg.drawString("----", (StartX+75)+(i*35), StartY+(3*HtDiff)+46);	                 // Printing ---- Dashes
					    for(int j = 0; j < NOTs; j++){	  
				        }	   				  
				    }	   	      			    

   			    for(int i = 0; i < NOD; i++){  
   			         for (int a = 0; a < 35; a++){	    				         
			           String temp = (String) GetData(View.getTable(), i, 5+pageNum*35+a);      
				       pg.drawString(temp, (StartX+70)+(width*i), (StartY+120)+15*a);           // Duty of each teacher
			         }	    				     
			    }                     
				       				    
			     for (int a = 0; a < 35; a++){	    				         
		         for(int i = 0; i < NOD; i++){
			       String temp2 = (String) GetData(View.getTable(), rows-2, 5+pageNum*35+a);  // Total Duty of each teacher
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

	protected void BtnUpdate() {
       	int rows = View.getTable().getRowCount();
    	int NOTs =  NumberOfTeachers();
        for(int j = 5; j < NOTs + 5; j++){
        	SetData2(TotalOfIndividualDuties(j), rows-2, j);
        }           	
        SumOfDuties();		
	}

	protected void BtnSave() {
		String fyle="";
		JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "SupervisionChart", "sup");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File("E:/SupervisionChart"));
//        int option = chooser.showSaveDialog(buttonSave);
        int option = chooser.showSaveDialog(View.getSaveButton());


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

  //          Model.SaveToFile(fyle);                             //Save to File is called here
         }  			 	              	  

		 FileWriter fw=null;		
		 try {fw = new FileWriter(fyle); }    catch (IOException e1){e1.printStackTrace();}
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

	protected void BtnLoad() {
		String fyle = "";
		JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "SupervisionChart", "sup");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File("E:/SupervisionChart"));
        chooser.setCurrentDirectory(new File("/home/prahallad/Test Entries"));
       
 //       int option = chooser.showOpenDialog(buttonLoad);
        int option = chooser.showOpenDialog(View.getLoadButton());
        if (option == JFileChooser.APPROVE_OPTION)
          {
            File[] sf = chooser.getSelectedFiles();
            String filelist = "nothing";
            if (sf.length > 0) filelist = sf[0].getName();
            for (int i = 1; i < sf.length; i++) 
            { filelist += ", " + sf[i].getName(); }
            fyle=sf[0].getPath();
            if (!fyle.endsWith(".sup")) fyle+= ".sup";
//		    Model.LoadFile(fyle);                                    //Load File is called here
//		    ReadFromDisk(fyle);
		 }  
                
        
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
	        try { 	reader = new BufferedReader(new FileReader(fyle)); } 
	        catch (FileNotFoundException e) { e.printStackTrace(); }
	       
	        View.strArray.clear();
	        String line = null;                                                        
	        try { while ((line = reader.readLine()) != null) { View.strArray.add(line); } } // Collecting all data in to strArray 
	        catch (IOException e) {e.printStackTrace(); }
      	int size = View.strArray.size(); 
		    
//      	show("Size of strArray : " +size);
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
//          show( (String) strArray.get(7));
         
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

//         Show(NOB);	
         for(int i = 5; i < NumberOfTeachers() + 5; i++){
         	for(int j = 0; j < rows; j++){
         		if(View.strArray.get(count).equals("null")){SetData2(" ", j,i);}
         	    else SetData2(View.strArray.get(count), j,i);
         		count++;
         	}
         }   	                     
	  }

        
        

	protected void BtnDistri() {

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

	private void SwapDuties() {
		int NOT = NumberOfTeachers();
    	int NOD = NODOfExams();
    	int NOB = NumberOfBlocks();
    	int G = LargestNumDuties(NumberOfTeachers());
		int S = LeastNumDuties(NumberOfTeachers());
    	int R = recever(),  D = donor();             //  R = Reciever's Column value , D = Donor's Column value.
		if(R == -1 && D ==-1){show("Process Over  !!!");return; }
    	int TotalNumOfSupervisions = NOD*NOB;
    	float NOfSup2eachTr = ((float) TotalNumOfSupervisions)/ (float) NOT;
		for(int i = 0; i< NODOfExams(); i++)
		 {		                       
		  if(GetData(View.getTable(), i, S).length() == 0 && GetData(View.getTable(), i, G).length() != 0)
		   {
			 SetData2(GetData(View.getTable(), i, G),i, S);
			 SetData2(" ", i, G);
			 SumOfDuties();
//			 show("PAUSE");
			 return;
		   }
	     }    	

		
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

	private void distribute() {
		 int NOT = NumberOfTeachers();
		 int rows = View.getTable().getRowCount(); 
		 int NOB = NumberOfBlocks();
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

	protected void BtnBlox() {
		
		  JOptionPane JOP = new JOptionPane();
		  String NemsOfBlox = JOptionPane.showInputDialog("Enter the Block Codes");	
		  JOptionPane.showMessageDialog(JOP, NemsOfBlox);
		  String[] splitBlox = NemsOfBlox.split(",");
		  NumOfTrs = NumberOfTeachers();
		  int start = supchartview.randInt(0, NumOfTrs);
		  int bloc = 0;
//			  Show(start);
		  for(int j = start ; j < splitBlox.length + start ; j++){
			  SetData2(splitBlox[bloc],0,(j % NumOfTrs + 5));
			  bloc++;
		  }

		
	}

	protected void BtnAllot() {

		JOptionPane JOP = new JOptionPane();
		  String NemsOfSups = JOptionPane.showInputDialog("Enter the CODE Names of Supervisors ( Up To 4 characters )");	
		  JOptionPane.showMessageDialog(JOP, NemsOfSups);
		  String[] splitNames = NemsOfSups.split(",");
		   for (int i = 0; i < splitNames.length; i++){
			 JTableHeader TablHdr = View.getTable().getTableHeader();
			 TableColumnModel tcm = TablHdr.getColumnModel();
			 TableColumn tc = tcm.getColumn(i);
			 tc.setHeaderValue(splitNames[i].toUpperCase());
			 TablHdr.repaint();			   
		   }
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

public  void ReadFromDisk(String fnem)
{   strArray.removeAll(strArray);
	BufferedReader reader	=null;
	try { 
		reader = new BufferedReader(new FileReader(fnem));
	} catch (FileNotFoundException e1) 
	{		
		e1.printStackTrace();
	}
		
	String line = null;
	try { while ((line = reader.readLine()) != null) 
		{
		 strArray.add(line);
		}
	} catch (IOException e) { e.printStackTrace(); } 
 }    	

		 

}