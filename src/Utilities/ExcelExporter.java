package Utilities;
import java.io.*;

import javax.swing.*;

import jxl.write.*;
import jxl.*;

public class ExcelExporter {
	


private File file;
private JTable table;
private String nombreTab;
private String[] colunmNames;
public ExcelExporter(JTable table,File file,String nombreTab, String[] colunmNames){
this.file=file;
this.table=table;
this.nombreTab=nombreTab;
this.colunmNames=colunmNames;
}



public boolean export(){
try
{
DataOutputStream out=new DataOutputStream(new FileOutputStream(file));

WritableWorkbook w = Workbook.createWorkbook(out);

WritableSheet s = w.createSheet(nombreTab, 0);
for(int i=0;i<colunmNames.length;i++)
{
	Object obj=colunmNames[i];
	s.addCell(new Label(i, 0, String.valueOf(obj))); 
}
for(int i=0;i< table.getColumnCount();i++){	
	for(int j=0;j<table.getRowCount();j++){
		Object obj=table.getValueAt(j,i);
		s.addCell(new Label(i, j+1, String.valueOf(obj))); 
}
}
w.write();
w.close();
out.close();



return true;

}catch(IOException ex){ex.printStackTrace();}
catch(WriteException ex){ex.printStackTrace();}
return false;
}

}



