package main.java;
import java.util.*;

public class Command {

	/*TODO: @Tirth create a function of type
	 *  public PlayRisk.Phase parseCommand(PlayRisk.phase phaseValue, String cmd)()
	 *  This function will return appropriate phase value as per the command.
	 *  This value can be one of the following: {NULL, EDITMAP, STARTUP, REINFORCEMENT, FORTIFICATION, QUIT}
	 *  You will return it as, for example: return PlayRisk.Phase.EDITMAP;
	 */
	public boolean isAlpha(String s) {
		return s != null && s.matches("^[a-zA-Z]*$");
	}
	
	public static void main(String[] args) {
		
		String continentName = null,countryName = null,neighborCountryName = null,playerName = null,fromCountry = null, toCountry = null; 
		int continentValue = 0, numberOfArmies = 0, armiesToFortify = 0;
		
		Scanner sc = new Scanner(System.in);
		String cmd = sc.nextLine();
		String[] data = cmd.split(" ");
		
		Command ob1 = new Command();
		
		String commandName = data[0];
		
		switch(commandName){
		case "showmap":
			if(!(data[1] == ""))
			{
				LoadMap lm = new LoadMap();
			}
			else
			{
				System.out.println("Empty Name");
			}
			break;
		
		case "editmap":
			if(!(data[1] == ""))
			{
				if(ob1.isAlpha(data[1]))
				{
					//EditMap lm = new EditMap();
				}
				else
					System.out.println("invalid command");
			}
			else
			{
				System.out.println("Empty Name");
			}
			break;
			
		case "editcontinent":
			
			for(int i=1;i<data.length;i++)
			{
				if(data[i].equals("-add"))
				{
					if(ob1.isAlpha(data[i+1]))
						continentName = data[i+1];
					else
						System.out.println("invalid command");
					
					continentValue = Integer.parseInt(data[i+2]);
					
					System.out.println(continentName+"  "+continentValue);
				}
				else if(data[i].equals("-remove"))
				{
					if(ob1.isAlpha(data[i+1]))
						continentName = data[i+1];
					else
						System.out.println("invalid command");
					System.out.println(continentName);
				}		
			}
			break;
		
			case "editcountry":
			
				for(int i=1;i<data.length;i++)
				{
					if(data[i].equals("-add"))
					{
						if(ob1.isAlpha(data[i+1]) || ob1.isAlpha(data[i+2]))
						{	
							countryName = data[i+1];
							continentName = data[i+2];
						}
						else
							System.out.println("invalid command");	
					
						System.out.println(countryName+"  "+continentName);
					}
					else if(data[i].equals("-remove"))
					{
						if(ob1.isAlpha(data[i+1]))
							countryName = data[i+1];
						else
							System.out.println("invalid command");
						System.out.println(countryName);
					}		
				}
				break;
			
		case "editneighbor":
			
			for(int i=1;i<data.length;i++)
			{
				if(data[i].equals("-add"))
				{
					if(ob1.isAlpha(data[i+1]) || ob1.isAlpha(data[i+2]))
					{
						countryName = data[i+1];
						neighborCountryName = data[i+2];
					}
					else
						System.out.println("invalid command");
					System.out.println(countryName+"  "+neighborCountryName);
				}
				else if(data[i].equals("-remove"))
				{
					if(ob1.isAlpha(data[i+1]) || ob1.isAlpha(data[i+2]))
					{
						countryName = data[i+1];
						neighborCountryName = data[i+2];
					}
					else
						System.out.println("invalid command");
					System.out.println(countryName+"  "+neighborCountryName);
				}		
			}
			break;
			
		case "gameplayer":
			
			for(int i=1;i<data.length;i++)
			{
				if(data[i].equals("-add"))
				{
					if(data[i+1].matches("[a-zA-Z0-9]+"))
					{
						playerName = data[i+1];
					}
					else
						System.out.println("invalid command");
					System.out.println(playerName);
					
					// parse the playerName to class
				}
				else if(data[i].equals("-remove"))
				{
					if(data[i+1].matches("[a-zA-Z0-9]+"))
					{
						playerName = data[i+1];
					}
					else
						System.out.println("invalid command");
					System.out.println(playerName);
					
					// parse playerName to class
				}		
			}
			break;
			
		case "populatecountries":
			
			// call class method which will assign initial armies
			break;
			
		case "reinforce":
			
			if(!(data[1] == null) || !(data[2] == null))
			{
				if(ob1.isAlpha(data[1]) || data[2].matches("[0-9]+"))
				{
					countryName = data[1];
					numberOfArmies = Integer.parseInt(data[2]);
					System.out.println(countryName+"  "+numberOfArmies);
				}
				else
					System.out.println("invlid command");
				
				// parse countryName and numberOfArmies
			}
			break;
			
		case "fortify":
			
			if(!(data[1] == null) || !(data[2] == null) || !(data[3] == null))
			{
				if(ob1.isAlpha(data[1]) || ob1.isAlpha(data[2]) || data[3].matches("[0-9]+"))
				{
					countryName = data[1];
					numberOfArmies = Integer.parseInt(data[2]);
					System.out.println(countryName+"  "+numberOfArmies);
				}
				else
					System.out.println("invlid command");
				
				// parse countryName and numberOfArmies
			}
			
			break;
		}
		sc.close();
	}
}