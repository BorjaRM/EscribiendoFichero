package md5;

public class Usuario {
	private String nombre;
	private String password;
	private String uid;
	private String gid;
	private String descripcion;
	private String home;
	private String shell;
	
	public Usuario(String[] datos){
		nombre=datos[0];
		password=eliminaSimbolo(datos[1]);
		uid=datos[2];
		gid=datos[3];
		descripcion=datos[4];
		home=datos[5];
		shell=datos[6];		
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}
	
	private static String eliminaSimbolo(String cadena){
		return cadena.replace("$", "");
	}
	
}
