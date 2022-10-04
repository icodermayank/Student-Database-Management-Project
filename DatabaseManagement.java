import java.util.Scanner;
import java.sql.*;

class DatabaseManagement{
	public static void main(String args[]){
		System.out.println("This is the Database Management command line program");

		Scanner s = new Scanner (System.in);

		try{
			// step 1 load the jdbc driver..
			Class.forName("com.mysql.cj.jdbc.Driver");

			// step 2 create Connection...
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root", "2244");

			System.out.println("Connection created Successfully....");


			// step 3 create Statement...
			Statement stmt = con.createStatement();

			// step 4 executeUpdate to create table.. [Warning: execute only one time for creation of table..]
			//stmt.executeUpdate("Create table StudentDatabase(roll int (10) primary key auto_increment, name varchar(20), city varchar (20))");
			//System.out.println("Table created Successfully..");

			// step 3.1 Prepare Statement for Dynamic query..
			PreparedStatement psmt = con.prepareStatement("insert into StudentDatabase (name, city) values(?,?)");

		while(true){
			System.out.println("How many student record you want to enter?");
			int t = s.nextInt();
			int count = 1;
			while(t-->0){
					if(count==1){
						String z = s.nextLine();
					}

				System.out.println("Enter the student " +count+" Name" );
				String s_name = s.nextLine();
				System.out.println("Enter the student "+ count + " City");
				String s_city = s.nextLine();

			psmt.setString(1,s_name);
			psmt.setString(2,s_city);

			// step 4.1 execute update for dynamic query..
			psmt.executeUpdate();

			System.out.println("Student "+ count + " record updated Successfully...");
			count ++;
			}

			System.out.println("Press 1 to display records.. \n press 2 to delete records \n press any key to exit");
			int d = s.nextInt();
			ResultSet rs = stmt.executeQuery("Select * from StudentDatabase");
			if(d==1){
				while (rs.next()){
					System.out.print("Roll No : "+ rs.getInt(1)+ ", ");
					System.out.print("Name : "+ rs.getString(2)+ ", ");
					System.out.print("City : " + rs.getString(3)+ ", ");
					System.out.println();
				}

			}
			else if(d==2){
				System.out.println("Enter Roll No to delete recored");
				int r_no = s.nextInt();
				PreparedStatement p = con.prepareStatement("delete from StudentDatabase where roll = (?)");
				p.setInt(1,r_no);
				p.executeUpdate();
				System.out.println(r_no+ " No record deleted Successfully");
				rs = stmt.executeQuery("Select * from StudentDatabase");
				System.out.println("After deleting your records....");

				while (rs.next()){
					System.out.print("Roll No : "+ rs.getInt(1)+ ", ");
					System.out.print("Name : "+ rs.getString(2)+ ", ");
					System.out.print("City : " + rs.getString(3)+ ", ");
					System.out.println();
				}

			}
			else{
				rs.close();
			    con.close();
				System.exit(0);
			}
			
			// step 5  close the connection ...
			
		}		
			
		}
		catch(Exception e ){
			e.printStackTrace();
		}
	} 
}