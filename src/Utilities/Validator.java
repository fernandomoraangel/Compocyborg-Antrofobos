package Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class Validator {
	public Validator(){
		
	}
	public boolean ValidateInt(String s, int minString, int maxString){
		int num=0;
		try {
			num=Integer.parseInt(s);
			if(num<minString|num>maxString)
			{
		JOptionPane.showMessageDialog(null, "El valor debe estar entre "+ minString + " y "+maxString);
return false;
			}
			else
			{
				return true;
			}
		} catch (NumberFormatException e1) {

			JOptionPane.showMessageDialog(null,"La entrada debe ser un número entero");
			return false;
		}
	
	
	}
	
public boolean ValidateDouble(String s, int minString, int maxString){
		
	double num=0;
	try {
		num=Double.parseDouble(s);
		if(num<minString|num>maxString)
		{
	JOptionPane.showMessageDialog(null, "El valor debe estar entre "+ minString + " y "+maxString);
return false;
		}
		else
		{
			return true;
		}
	} catch (NumberFormatException e1) {

		JOptionPane.showMessageDialog(null,"La entrada debe ser un número decimal");
		return false;
	}
		
	}
public boolean ValidateNote(String s){
	
	
	if(s.matches("[ABCDEFG]"))//arreglar
	{
		
		return true;	
	}
	else
	{
		JOptionPane.showMessageDialog(null,"La entrada debe ser una nota en cifrado");
		return false;
	}
	
}
public boolean ValidateTimeSignatureDenominator(int d)
{
	int dd=(int)(Math.log(d)/Math.log(2));
	if((Math.log(d)/Math.log(2))-dd!=0 | d>128)
	{
		JOptionPane.showMessageDialog(null,"La entrada debe ser una potencia de 2");
		return false;
	}
	else
	{
		return true;
	}
	
	
}
}
