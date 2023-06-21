import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	static Scanner scanner=null;
	public static void main(String[] args) throws SQLException {
		
		Control c=new Control();
		scanner=new Scanner(System.in);
	
		System.out.println(" CREATE TABLE = 1 \n INSERT = 2 \n QUERY = 3\n UPDATE = 4 \n DELETE = 5 \n");
		String read= scanner.nextLine();
		
		String [] sa= {"1","2","3","4","5"};
		boolean found = false;

        for (String element : sa) {
            if (read.contains(element)) {
                found = true;
                break;
            }
        }

        if (read.isEmpty() || !found) {
            System.out.println("Enter 1, 2, 3, 4, or 5.\n");
            main(null); 
        }
		
		
		 switch(read){
		 case "1"://Create table.
			 System.out.println("Enter a name for a new table. \n");
			 String s1= scanner.nextLine();
			c.create(s1);
			 break;
		 case "2"://Add data to a table.
			 System.out.println("Enter table´s name wanted. \n");
			 String s2= scanner.nextLine();
			 System.out.println("Enter a name to add to this table. \n");
			 String s3= scanner.nextLine();
			 c.add(s2,s3);
			 break;
		 case "3"://Query table.
			 System.out.println("Enter the table´s name for a query. \n");
			 String s4= scanner.nextLine();
			 c.queryAll(s4);
			 break;
		 case "4"://Update data on a table.
			 System.out.println("Enter table´s name you want to modify.\n");
			 String s5= scanner.nextLine();
			 System.out.println("Enter the name you want to change.\n");
			 String s6= scanner.nextLine();
			 System.out.println("Enter the new name.\n");
			 String s7= scanner.nextLine();
			 c.update(s5,s6,s7);
			 break;
		 case "5"://Delete data from a table.
			 System.out.println("DELETE TABLE = 1.\nDELETE NAME IN A TABLE = 2. \n");
			 scanner=new Scanner(System.in);
			 String s= scanner.nextLine();
			 
			 if(s.contentEquals("1")){//Delete table.
				 System.out.println("Enter the name of the table you want to delete.\n");
				 String s8= scanner.nextLine();
				 c.deleteTable(s8);
				 
			 }else if(s.contentEquals("2")) {//Delete data from a mtable.
				 System.out.println("Enter the name of the table you want to change.\n");
				 String s9= scanner.nextLine();
				 System.out.println("Enter the name you want to delete in the table.\n");
				 String s10= scanner.nextLine();
				 c.delete(s9,s10);
			 }else {
				 System.out.println("Enter 1 or 2.\n");
				 Main.main(null);
			 }
			 break;
		 }

	}

}
