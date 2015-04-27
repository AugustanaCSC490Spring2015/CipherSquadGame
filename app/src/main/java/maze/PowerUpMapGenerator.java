package maze;

import game.PowerUpMap;

/**
 * Created by Matt on 4/26/2015.
 */
public class PowerUpMapGenerator {
    PowerUpMapGenerator(Maze maze, int screenWidth, int screenHeight, int width, int height) {
        generatePowerUpMap(maze, screenWidth, screenHeight, width, height);
    }

    public PowerUpMap generatePowerUpMap(Maze maze, int screenWidth, int screenHeight, int width, int height) {
        return new PowerUpMap(maze, screenWidth, screenHeight, width, height);
    }
}
