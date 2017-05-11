package md5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class Inicio {

	public static void main(String[] args) {
		ArrayList<Usuario> usuarios;
		ArrayList<String> palabras;
		ArrayList<String> palabrasMd5;
				
		palabras = toArrayList("spanish.lst");
		usuarios = dividePorCampos(toArrayList("passwd.1.txt"));
		palabrasMd5 = codificaPalabras(palabras);
				
		imprimeResultadosConsola(usuarios,palabras,palabrasMd5);
		imprimeResultadosFichero(usuarios,palabras,palabrasMd5);
	}
	
	public static ArrayList<String> toArrayList(String fileName){
		Scanner sc = null;
		File f = new File(fileName);
		ArrayList<String> temp = new ArrayList<String>();
		
		try{
			sc = new Scanner(f);
			while(sc.hasNextLine())
				temp.add(sc.nextLine());
			temp.trimToSize();
		}catch (FileNotFoundException e){	
			e.printStackTrace();
		}finally{
			sc.close();
		}
		
		return temp;
	}
	
	public static ArrayList<Usuario> dividePorCampos(ArrayList<String> infoUsuarios){
		ArrayList<Usuario> campos = new ArrayList<Usuario>();
		
		for(String u:infoUsuarios){
			campos.add(new Usuario(u.split(":"))); 
		}
		
		return campos;		
	}
	
	public static ArrayList<String> codificaPalabras(ArrayList<String> palabras){
		ArrayList<String> palabrasMd5 = new ArrayList<String>(palabras.size());

		for(String p:palabras){
			palabrasMd5.add(md5(p));
		}
		return palabrasMd5;
	}
	
	public static String md5(String palabra){
		String palabraMd5 = null;
		MessageDigest md;
		
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(palabra.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			palabraMd5 = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return palabraMd5;	
	}
	
	public static void imprimeResultadosConsola(ArrayList<Usuario> usuarios, ArrayList<String> palabras, ArrayList<String> palabrasMd5){
		for(Usuario u:usuarios){
			if(palabrasMd5.contains(u.getPassword())){
				System.out.println(u.getNombre().toUpperCase() +" -> " +palabras.get(palabrasMd5.indexOf(u.getPassword())));
			}else
				System.out.println("No ha sido posible descifrar la contraseña del usuario " +u.getNombre().toUpperCase());
		}
	}
	
	public static void imprimeResultadosFichero(ArrayList<Usuario> usuarios, ArrayList<String> palabras, ArrayList<String> palabrasMd5){
		FileWriter fichero = null;
        PrintWriter pw = null;
        try{
            fichero = new FileWriter("prueba.txt");
            pw = new PrintWriter(fichero);            
            for(Usuario u:usuarios){
    			if(palabrasMd5.contains(u.getPassword())){
    				pw.println(u.getNombre().toUpperCase() +" -> " +palabras.get(palabrasMd5.indexOf(u.getPassword())));
    			}else
    				pw.println("No ha sido posible descifrar la contraseña del usuario " +u.getNombre().toUpperCase());
    		}                
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}

}