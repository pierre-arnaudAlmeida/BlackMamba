package analysis;

/**
 * 
 * @author Pierre-Arnaud
 *
 */
public class Employee {

	private int idEmployee = 0;
	private String lastnameEmployee = "";
	private String nameEmployee = "";
	private String password = "";
	private String poste = "";

	public Employee(int idEmployee, String lastnameEmployee, String nameEmployee, String password, String poste) {
		super();
		this.idEmployee = idEmployee;
		this.lastnameEmployee = lastnameEmployee;
		this.nameEmployee = nameEmployee;
		this.password = password;
		this.poste = poste;
	}

	public Employee() {
	}

	public int getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(int idEmployee) {
		this.idEmployee = idEmployee;
	}

	public String getLastnameEmployee() {
		return lastnameEmployee;
	}

	public void setLastnameEmployee(String lastnameEmployee) {
		this.lastnameEmployee = lastnameEmployee;
	}

	public String getNameEmployee() {
		return nameEmployee;
	}

	public void setNameEmployee(String nameEmployee) {
		this.nameEmployee = nameEmployee;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPoste() {
		return poste;
	}

	public void setPoste(String poste) {
		this.poste = poste;
	}

}
