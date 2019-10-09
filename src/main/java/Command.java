package main.java;
import java.util.*;

public class Command {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String cmd = sc.nextLine();
		String[] alt = cmd.split(" ");
		
		String day = alt[0];
		
		switch(day){
		
		case "showmap":
			if(!(alt[1] == ""))
			{
				LoadMap lm = new LoadMap();
			}
			else
			{
				System.out.println("Empty Name");
			}
			break;
		
		case "editmap":
			if(!(alt[1] == ""))
			{
				//EditMap lm = new EditMap();
			}
			else
			{
				System.out.println("Empty Name");
			}
			break;
		
		case "editcontinent":
			if(!(alt[1] == null))
			{
				int i;
				
				for(i=1;i<alt.length;i+=3)
				{
					if(alt[i].equals("-add"))
					{
						if(!(alt[i+1] == null))
						{
							String continent_name = alt[i+1];
							//System.out.println(continent_name);
						}
						else
							System.out.println("Invalid Command");
					
						if(!(alt[i+2] == null))
						{
							int continent_value = Integer.parseInt(alt[i+2]);
							//System.out.println(continent_value);
						}
						else
							System.out.println("Invalid Command");
					}
					//NewMap nm = new NewMap();
					//nm.addContinent(continent_name, continent_value);
					
					for(i=1;i<alt.length;i+=2)
					{
						if(alt[i].equals("-remove"))
						{
							if(!(alt[i+1] == null))
							{
								String continent_name = alt[i+1];
							}
							else
								System.out.println("Invalid Command");
						
							//NewMap nm = new NewMap();
							//nm.removeContinent(continent_name);
						}
					}
				}	
			}
			else
				System.out.println("Invalid Command");
			
			break;
		
		case "editcountry":
			if(!(alt[1] == null))
			{
				int i;
				
				for(i=1;i<alt.length;i+=3)
				{
					if(alt[i].equals("-add"))
					{
						if(!(alt[i+1] == null))
						{
							String country_name = alt[i+1];
							//System.out.println(continent_name);
						}
						else
							System.out.println("Invalid Command");
					
						if(!(alt[i+2] == null))
						{
							String continent_name = alt[i+2];
							//System.out.println(continent_value);
						}
						else
							System.out.println("Invalid Command");
					}
					//NewMap nm = new NewMap();
					//nm.addCountry(country_name, continent_name);
					
					for(i=1;i<alt.length;i+=2)
					{
						if(alt[i].equals("-remove"))
						{
							if(!(alt[i+1] == null))
							{
								String country_name = alt[i+1];
							}
							else
								System.out.println("Invalid Command");
						
							//NewMap nm = new NewMap();
							//nm.removeContinent(continent_name);
						}
					}
				}	
			}
			else
				System.out.println("Invalid Command");
			
			break;
			
		case "editneighbor":
			if(!(alt[1] == null))
			{
				int i;
				
				for(i=1;i<alt.length;i+=3)
				{
					if(alt[i].equals("-add"))
					{
						if(!(alt[i+1] == null))
						{
							String country_name = alt[i+1];
							//System.out.println(continent_name);
						}
						else
							System.out.println("Invalid Command");
					
						if(!(alt[i+2] == null))
						{
							String neighbor_country_name = alt[i+2];
							//System.out.println(continent_value);
						}
						else
							System.out.println("Invalid Command");
					}
					//NewMap nm = new NewMap();
					//nm.addCountry(country_name, continent_name);
					
					for(i=1;i<alt.length;i+=2)
					{
						if(alt[i].equals("-remove"))
						{
							if(!(alt[i+1] == null))
							{
								String country_name = alt[i+1];
							}
							else
								System.out.println("Invalid Command");
							
							if(!(alt[i+2] == null))
							{
								String neighbor_country_name = alt[i+2];
							}
							else
								System.out.println("Invalid Command");
						
							//NewMap nm = new NewMap();
							//nm.removeContinent(continent_name);
						}
					}
				}	
			}
			else
				System.out.println("Invalid Command");
			
			break;
			
		}

	}

}
