import java.sql.*;
import java.util.*;
public class JavaProject {
	String id,name,age,phone,question,opt1,opt2,opt3,opt4,answer,choice,ans;
	int score=0;
	Scanner sc = new Scanner(System.in);
	
	Connection con;
	String username = "root";
	String password = "";
	String url = "jdbc:mysql://localhost/javaproject";
	
	public void connection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			con = DriverManager.getConnection(url,username,password);
			//System.out.println("Connected!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void write() {
		
		System.out.println("WELCOME TO KBC");
		System.out.println();
		
		String que = "select max(score) as maxscore from userinfo";
		PreparedStatement p = null;
		try {
			p = con.prepareStatement(que);
			ResultSet result1;
			result1 = p.executeQuery();
			while(result1.next()) {
				System.out.println("High Score "+result1.getInt("maxscore"));
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Enter id, name, age, phone");
		id = sc.nextLine();
		name = sc.nextLine();
		age= sc.nextLine();
		phone = sc.nextLine();
	
		PreparedStatement ps = null;
		String query = "Insert into userinfo(id,name,age,phone,score) values(?,?,?,?,?)";
	
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, age);
			ps.setString(4, phone);
			ps.setInt(5, score);
			ps.execute();
				//System.out.println("Data inserted");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println();
		
		}
	
	public void read() {
			
		System.out.println("DEMO or QUIZ");
		choice = sc.nextLine();
		
		System.out.println();

		
		for(int i=1;i<=10;i++) {
			String query = "select question,opt1,opt2,opt3,opt4,answer from questions where qno="+i;
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(query);
				ResultSet result;
				result = ps.executeQuery();
				while(result.next()) {
					System.out.println("Question "+i+": "+result.getString("question"));
					System.out.println("Option 1: "+result.getString("opt1"));
					System.out.println("Option 2: "+result.getString("opt2"));
					System.out.println("Option 3: "+result.getString("opt3"));
					System.out.println("Option 4: "+result.getString("opt4"));
						
					System.out.println();
					
					System.out.println("Enter ans");
					ans = sc.nextLine();
					String a=result.getString("answer").toLowerCase();
				
					
					if(ans.toLowerCase().equals(a)) {
						System.out.println();
						System.out.println("Correct Answer");
						System.out.println();
							
					}
					else {
						System.out.println();
						System.out.println("Wrong Answer");
						System.out.println("Your Answer "+ans);
						System.out.println("Correct Answer "+result.getString("answer"));
						System.out.println();
					}
				
					
					if(choice.toLowerCase().equals("quiz")) {
						if(ans.toLowerCase().equals(a)) {
							score = score + 1;
						}
					}
					
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		System.out.println("GAME OVER!!");
		
		if(choice.toLowerCase().equals("quiz")) {
			System.out.println("TOTAL SCORE = "+score);
		}
		
		String query1 = "Update userinfo set score=? where id="+id;
		
		PreparedStatement ps1 = null;
		
		try {
			ps1 = con.prepareStatement(query1);
			ps1.setInt(1, score);
			ps1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
		
	public static void main(String[] args) {
		JavaProject jp = new JavaProject();
		jp.connection();
		jp.write();
		jp.read();

	}
	

}
