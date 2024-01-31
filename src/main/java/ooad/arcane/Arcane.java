package ooad.arcane;
import java.util.Random;

public class Arcane {
    public static void main(String[] args) {
        Game game = new Game();
        
        game.playGame();
    }
}


enum Location {
    NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST
}

class Utility {
    public static Location randomLocation() {
        Random random = new Random();
        int randomNumber = random.nextInt(4) + 1; 

        switch (randomNumber) {
            case 1:
                return Location.NORTHWEST;
            case 2:
                return Location.NORTHEAST;
            case 3:
                return Location.SOUTHWEST;
            case 4:
                return Location.SOUTHEAST;
            default:
                throw new IllegalStateException("Unexpected value: " + randomNumber);
    }
}}

class Entity {

    private String name;
    private int health;
    private Location location;

    public Entity(String entityName, Location entityLocation){
        this.name = entityName;
        this.health = 5;
        this.location = entityLocation;
    }

    public String displayEntityInfo(){
        String info = this.getName() + "(health: " + this.getHealth() + ")";
        return info;

    }
    
    public String getName() {
        return name;
    }

    
    public int getHealth() {
        return health;
    }

    
    public void setHealth(int health) {
        this.health = health;
    }

    
    public Location getLocation() {
        return location;
    }

    
    public void setLocation(Location location) {
        this.location = location;
    }

}


interface Movement {
    void MoveToNextRoom();
}


class Adventurer extends Entity implements Movement {

    public Adventurer(String adventurerName, Location adventurerLocation) {
        super(adventurerName, adventurerLocation);
    }

    @Override
    public void MoveToNextRoom(){

        Random random = new Random();
        boolean leftOrRightRoom = random.nextBoolean();


        switch (getLocation()) {
            case NORTHWEST:
                if (leftOrRightRoom) {
                    setLocation(Location.NORTHEAST);
                } else {
                    setLocation(Location.SOUTHWEST);
                }
                break;
                
            case NORTHEAST:
                if (leftOrRightRoom) {
                    setLocation(Location.SOUTHEAST);
                } else {
                    setLocation(Location.NORTHWEST);
                }
                break;
                
            case SOUTHWEST:
                if (leftOrRightRoom) {
                    setLocation(Location.NORTHWEST);
                } else {
                    setLocation(Location.SOUTHEAST);
                }
                break;
                
            case SOUTHEAST:
                if (leftOrRightRoom) {
                    setLocation(Location.SOUTHWEST);
                } else {
                    setLocation(Location.NORTHEAST);
                }
                break;
                
            default:
            
        }

        
    }
}


class EntityInteraction {
    public static void fight(Adventurer adventurer, Entity creature){
        Random random = new Random();
        int adventurerRoll = random.nextInt(6) + 1;
        int creatureRoll = random.nextInt(6) + 1;

        if (adventurerRoll != creatureRoll) {
        
            int difference = Math.abs(adventurerRoll - creatureRoll);
            if (adventurerRoll > creatureRoll) {
                creature.setHealth(creature.getHealth() - difference);
            } else {
                adventurer.setHealth(adventurer.getHealth() - difference);  
            }
        }

    }
}


class Game {
    private void displayAdventurerCreatureInfo(Adventurer adventurer, Entity creature, Location location) {
        if (adventurer.getLocation().equals(location)){
            System.out.println("Adventurer " + adventurer.displayEntityInfo() + " is here"); 
        }

        if (creature.getLocation().equals(location)){
            System.out.println("Creature " + creature.displayEntityInfo() + " is here");
        }

    }

    private void displayLocationInfo(Adventurer adventurer, Entity creature, int turn){
        System.out.println("ARCANE MAZE: turn " + Integer.toString(turn));
        System.out.println("Northwest:");
        displayAdventurerCreatureInfo(adventurer,creature, Location.NORTHWEST);

        System.out.println("Northeast:");
        displayAdventurerCreatureInfo(adventurer, creature, Location.NORTHEAST);

        System.out.println("Southwest:");
        displayAdventurerCreatureInfo(adventurer, creature, Location.SOUTHWEST);

        System.out.println("Southeast:");
        displayAdventurerCreatureInfo(adventurer, creature, Location.SOUTHEAST);

    }



    private boolean gameOver(Adventurer adventurer, Entity creature){
        if (creature.getHealth() <= 0){
            System.out.println("Yay, the Adventurer won!");
            return true;
        }else if (adventurer.getHealth() <= 0){
            System.out.println("Boo, the Creature won!");
            return true;
        }else{
            return false;
        }
    }

    public void playGame(){
        Adventurer adventurer = new Adventurer("Bill", Utility.randomLocation());
        Entity creature = new Entity("Ogre", Utility.randomLocation());

        int turn = 1;
        System.out.println("Starting play...");
        while(true){
            displayLocationInfo(adventurer, creature, turn);
            if (adventurer.getLocation().equals(creature.getLocation())){
                //fight
                EntityInteraction.fight(adventurer, creature);

                if (gameOver(adventurer, creature)){
                    break;
                }

            }else{
                adventurer.MoveToNextRoom();
            }

            turn += 1;
        }

    }
}




