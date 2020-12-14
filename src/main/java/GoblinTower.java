import java.util.*;
public class GoblinTower {
    public static void main(String[] args) {
        Scanner keys = new Scanner(System.in);
        String response;
        int oldGold = 0;
        int goblinCnt = 0;
        int lvl = 1;
        Random randy = new Random();
        do {
            Hero hero = new Hero(oldGold);
            boolean usedScanner = false;
            int steps=0;
            int maxHealth = hero.getHealth();
            while(!hero.isDead()){
                steps++;
                System.out.println("step...");
                if (steps % 10 == 0){
                    System.out.println("LEVEL UP!");
                    lvl++;
                    if(hero.getPotions() < 5) {
                        System.out.println("You have " + hero.getPotions() + " potions and " + hero.getGold()+ " gold.");
                        System.out.println("How many potions would you like to buy?");
                        // need to check contents of potion bag against health
                        int numPot = keys.nextInt();
                        hero.buyPotions(numPot);
                        usedScanner = true;

                    } else {
                        System.out.println("Your potions are full");
                    }
                }
                double gobEncounter= randy.nextDouble();
                if(gobEncounter < 0.25){
                    System.out.println("Time for battle!");
                    //System.out.println(gobEncounter);
                    Goblin goblin = new Goblin();
                    while(!goblin.isDead() && !hero.isDead()){
                        // 50% chance of hero defending against goblin attack
                        System.out.println("Hero: " + hero.health + " " + "Goblin: " + goblin.health);
                        if (randy.nextDouble() < 0.5){
                            hero.takeDmg(goblin.getAtkPwr()-hero.getDefense());
                        } else {
                            hero.takeDmg(goblin.getAtkPwr());
                        }

                        // 30% chance of goblin defending against hero attack
                        if (randy.nextDouble() < 0.3){
                            goblin.takeDmg(hero.getAtkPwr()-goblin.getDefense());
                        } else {
                            goblin.takeDmg(hero.getAtkPwr());
                        }
                    }
                    if(goblin.isDead()){
                        goblinCnt++;
                        hero.gainGold();
                        System.out.println("Goblin slain!");
                        if(hero.getHealth() < maxHealth){

                            // hero heals whenever he can
                            while(hero.getPotions() > 0 && hero.getHealth() < maxHealth){
                                hero.usePotion();
                            }
                        }
                    }
                }
            }
            if(hero.isDead()){
                oldGold = hero.getGold();
            }
            System.out.println("You died. Would you like to play again?");
            if(usedScanner) keys.nextLine(); // flush nextline if it took an int previously

            response = keys.nextLine();
        } while (response.equalsIgnoreCase("yes"));

        if(response.equalsIgnoreCase("no")){
            System.out.println("Thanks for playing!");
            System.out.println("Your most recent level " + lvl + " and slayed " + goblinCnt + " goblins!");

        }

    }

    public static class Hero{
        int potions;
        int atkPwr;
        int defense;
        int health;
        int gold;
        Hero(int oldGold){
            Random randy = new Random();
            health = randy.nextInt(31-20) +20;
            atkPwr = randy.nextInt(4-1) + 1;
            defense = randy.nextInt(6-1) + 1;
            gold = oldGold;
            potions = 5;
        }
        public boolean isDead(){
            return health <= 0;
        }

        public int getHealth() {
            return health;
        }

        public int getAtkPwr() {
            return atkPwr;
        }

        public int getDefense() {
            return defense;
        }

        public int getPotions() {
            return potions;
        }

        public void takeDmg(int dmg){
            if (dmg > 0) health = health - dmg;
        }
        public int getGold(){
            return gold;
        }
        public void gainGold(){
            gold += 2;
        }

        public void buyPotions(int i){
            int cost = i * 4;
            if(gold>= cost && (potions+i)<=5){
                potions += i;
            } else {
                System.out.println("You don't have enough money sorry.");
            }
        }
        public void usePotion(){
            if(potions> 0) {
                potions--;
                health += 2;
            }
        }
    }
    public static class Goblin{
        int atkPwr;
        int health;
        int defense;

        Goblin(){
            Random rand = new Random();
            health = rand.nextInt(11-5) + 5;
            atkPwr = rand.nextInt(4-2)+ 2;
            defense = rand.nextInt(3-1) + 1;
        }
        public boolean isDead(){
            return health <= 0;
        }

        public int getAtkPwr() {
            return atkPwr;
        }

        public int getDefense() {
            return defense;
        }

        public void takeDmg(int dmg){
            if (dmg > 0) health = health - dmg;
        }

    }
}
