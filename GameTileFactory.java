/*
 * Name: Bo Zhang; Yushan Liu 
 * Login: cs8bscu; cs8bsgr
 * Date: 05/20/2017
 * File: GameTileFactory.java
 * Sources of Help: textbook, tutors
 * 
 * Describe what the program does here:
 * This class combines multiple GameTiles and randomly 
 * use those GameTiles in 2048 game.
 */
import java.util.*;

public class GameTileFactory {
    private Random rand = new Random();
    
    // Modify this according to the number of custom tiles you wish to use
    private final int NUM_CUSTOM_TILES = 4;

    // use getTile method to get a random Tile
    public GameTile getTile(int value) {
        switch (rand.nextInt(NUM_CUSTOM_TILES)) {
            // Uses two students' custom tiles
            case 0: return new GameTileyokina(value);
            case 1: return new GameTiletnn050(value);
            case 2: return new GameTileafossier(value);
            case 3: return new GameTileajacosta(value);
            
            // You easily can add more tiles with an additional line of code
            // case 2: return new GameTileStudentThree(value);
            // case 3: return new GameTileStudentFour(value);
            default: return null;
        }
    }
}
