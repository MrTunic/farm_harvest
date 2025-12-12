Notes about resources:
 * Place images in a resources/ directory that will be added to the classpath when running.
 * Example layout (relative to classpath root):
   -   tiles/grass.png
   -   tiles/dirt.png
   -   tiles/water.png
   -   tiles/wheat_stage0.png ... tiles/wheat_stage5.png
   -   player/player.png
 
 *  When running from the command line, include the resources directory on the classpath:
    - java --module-path $PATH_TO_FX --add-modules javafx.controls -cp out:resources io.github.game.controllers.App
 
