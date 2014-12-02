/**
 * 
 */
package edu.csupomona.cs.cs141.classproject;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.InputMismatchException;
import java.util.Scanner;







import edu.csupomona.cs.cs141.classproject.GUIGame.MainMenuActionListener;

/**
 * @author Isa
 *
 */
public class UserInterface implements Serializable {


	private Taha player = new Taha();

	GameEngine gameEng = new GameEngine(player);

	Scanner kb = new Scanner(System.in);

	private boolean debug = false;

	private boolean lookAroundUsedOnce = false;

	// Menu has been updated
	// I've disabled it for now.
	//
	public void FirstMenu(){
		int userChoice = 0;


		kb = new Scanner(System.in);
		System.out.println("Welcome to Taha's Japanese Adventure in Japan!\n");
		System.out.println("1. Start Game");
		System.out.println("2. Load Game");
		System.out.println("3. About");
		System.out.println("4. Help");
		System.out.println("5. Quit");

		try{

			userChoice = kb.nextInt();
			kb.nextLine();
			FirstMenuRedirection(userChoice);



		} catch(InputMismatchException e){
			System.out.println("Bad Input, Try again");
			FirstMenu();

		}

	}



	public void FirstMenuRedirection(int userChoice) {
		// TODO Auto-generated method stub
		switch (userChoice){
		case 1: 
			options();
			break;
		case 2: //call load game method here..... NO
			printTheListofSaves();
			System.out.println("Enter your save Game filename, or press N to go back to Main Menu");
			String fileName = kb.nextLine().toLowerCase();
			if(fileName.equals("n")){
				FirstMenu();
				break;
			}
			loadGame(fileName+".taha");
			options();
			break;
		case 3: //call about method here..... NO
			System.out.println("Creators:\n Isaac (aka IsaacDG)\n Thomas (aka Butthole Ripper)\n Taha (aka Jericho)\n James (aka kor3a)\n Fraz (aka muffinbottoms)\n");
			FirstMenu();
			break;
		case 4: //call help method here.... NO
			if(Desktop.isDesktopSupported())
			{
				try {
					// go to the website where we'll have the help info.
					// will change it later, for now fill this out lol
					Desktop.getDesktop().browse(new URI("http://shrib.com/TahasJapAdv"));
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			FirstMenu();
			break;
		case 5: System.exit(0);
		}
	}

	public void options(){

		String playerChoice = "AWWWWWWWWWWW YEEEEEEEEEE, LET THE GAMES BEGIN";
		while(playerChoice != "0"){

			gameEng.callGridSeeReset();
			gameEng.callGridSeeAround();
			debugCheck();
			gameEng.printGrid();
			gameEng.gameOverCheck();
			if(gameEng.gameOverCheck()){
				player = new Taha();
				gameEng = new GameEngine(player);
				FirstMenu();
			}


			doIWinYet();
			System.out.println("W. Up, D. Right, S. Down, A. Left, 0 to quit, 1. Shoot, 2 Look Around, 3 to Save Game, 4 To go back to the Main Menu.");
			playerStatus();
			gameEng.playerTurnUsedWhileInvincible();

			playerChoice = kb.next().toLowerCase();
			kb.nextLine();
			switch(playerChoice){
			case "1":
				if(gameEng.ammoCheck()){
					playerShoot();
				}
				else{
					System.out.println("You are out of ammo.");
				}
				break;
			case "w":
			case "a":
			case "s":
			case "d":
				gameEng.move(playerChoice);
				resetLookAroundUsed();
				options();
				break;
			case "3":
				System.out.println("Enter the Save File name");
				String saveFileName = kb.nextLine();
				makeAListofSaves(saveFileName);
				saveGame(saveFileName + ".taha");
				options();
				break;
			case "4":
				System.out.println("Are you sure you want to the Main Menu, all unsaved progress will be lost!");
				System.out.println("Y/N");
				String saveGameYesNo = kb.next().toLowerCase();
				kb.nextLine();
				switch(saveGameYesNo){
				case "y":
					player = new Taha();
					gameEng = new GameEngine(player);
					FirstMenu();
					break;
				case "n":
					System.out.println("Press Enter to Continue.");
					kb.next();
					options();
					break;
				default:
					options();
					break;

				}
				break;
			case "2":
				checkLookAroundUsed();
				break;
			case "0":
				System.exit(0);
				break;
			case "8":
				System.out.println("Debug mode activated");
				if(debug == false){
					debug = true;	
				} else {
					debug = false;
				}

				break;

			default:

				System.out.println(playerChoice + " was not one of the choices, try again.");

				options();
			}

		}
	}

	// 	this was the old one, just in case someone wants to refer to this:	
	//	public void options(){
	//		int shootMove = 9;
	//		while(shootMove != 0){
	//		
	//		gameEng.callGridSeeReset();
	//		gameEng.callGridSeeAround();
	//		gameEng.printGrid();
	//		gameEng.gameOverCheck();
	//		doIWinYet();
	//		System.out.println("1 Shoot 2 Move");
	//		playerStatus();
	//		gameEng.playerTurnUsedWhileInvincible();
	//		shootMove = kb.nextInt();
	//		kb.nextLine();
	//		if (playerChoice == "1"){
	//			if(gameEng.ammoCheck()){
	//				playerShoot();
	//			}
	//			else{
	//				System.out.println("You are out of ammo.");
	//			}
	//		}
	//		else{
	//			theGameInterface();
	//		}
	//				
	//		}
	//		}


	// 	going to be removing this soon, implemented the functionality in options()
	//	public void theGameInterface() {
	//		
	//		String playerChoice = "Q";
	//		
	//			System.out
	//			try {
	//				playerChoice = kb.next();
	//				kb.nextLine();
	//					gameEng.resetSee();
	//					gameEng.move(playerChoice.toLowerCase());
	//					gameEng.see();
	//				
	//			} catch (InputMismatchException e) {
	//				System.out.println("Please enter correct input.");
	//				kb.next();
	//			}
	//		}
	//		public void doIWinYet(){
	//			if(gameEng.recieveWinFromGrid()){
	//				System.out.println("You have won the game");
	//				System.exit(0);
	//		}

	public void playerShoot(){
		System.out.println("Which direction to shoot? 1 up 2 left 3 down 4 right");
		int shootChoice = kb.nextInt();
		gameEng.shootDirection(shootChoice);
	}

	public void playerStatus(){
		System.out.println("Lives: " + gameEng.lives()+ " Ammo: "+ gameEng.ammo());
		System.out.println("Turns invincible: "+ gameEng.cantDie() );
	}

	public void doIWinYet(){
		if(gameEng.recieveWinFromGrid()){
			System.out.println("You have won the game");			
			System.out.println("Press 1 to continue.");
			kb.nextInt();
			kb.nextLine();
			player = new Taha();
			gameEng = new GameEngine(player);
			FirstMenu();
		}
	}
	public void lookAroundUsed(){
		lookAroundUsedOnce = true;
	}
	public void resetLookAroundUsed(){
		lookAroundUsedOnce = false;
	}
	public void checkLookAroundUsed(){
		if(lookAroundUsedOnce){
			System.out.println("You can only look ahead once per turn.");
		} else {
			wantedToSee();
			gameEng.printGrid();
			System.out.println("Press 1 to continue.");
			kb.next();
			kb.nextLine();
			lookAroundUsed();
			options();
		}
	}
	public void doYouWannaSee(){
		int lookFurther = 0;
		System.out.println("Do you want to look further in a direction? 1 yes 2 no");
		lookFurther = kb.nextInt();
		if(lookFurther == 1){
			wantedToSee();
		}
		else{
			System.out.println("Guess you wanna be blind");
		}
	}
	public void makeAListofSaves(String FileName){
		try {

			FileWriter ff = new FileWriter(".savedata", true);
			PrintWriter pw = new PrintWriter(ff);
			pw.println(FileName);
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void printTheListofSaves(){
		File fileReader = new File(".savedata");
		try {
			Scanner fileR = new Scanner(fileReader);
			System.out.println("Here are the List of current saved game files:");
			while(fileR.hasNextLine()){
				System.out.println(fileR.nextLine());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void wantedToSee(){
		String lookDirection = "0";
		System.out.println("Which direction do you wanna look at?");
		System.out.println("W up, D right, S down, A left" );
		lookDirection = kb.next();
		kb.nextLine();
		gameEng.playerLook(lookDirection);
	}

	public void debugCheck(){
		if(debug == true){
			debugActivate();
		}
	}

	public void debugActivate(){
		gameEng.debug();
	}
	public void saveGame(String fileName){

		try {

			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(gameEng);
			oos.close();
			System.out.println("Game Saved Successfully.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Save Game Unsuccessful.");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not save game.");
			e.printStackTrace();
		}
	}

	public void loadGame(String fileName){
		try {

			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			gameEng = (GameEngine) ois.readObject();
			Player testPlayer = gameEng.getPlayer();
			System.out.println(testPlayer.showLives()); // do these two match up?
			System.out.println(gameEng.lives());
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


}
