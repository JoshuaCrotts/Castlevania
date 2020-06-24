package castlevania;

/**
 * Alright, let's talk about this project. I've probably spent around 50 hours
 * on this thing alone, and it's been one heck of a job. There's still the bugs,
 * when the player attacks an enemy, sometimes multiple "particle" clouds come
 * out, and when he faces left and attacks, the sprite is screwed up. It's a
 * small game, only four levels and it randomly generates enemies, collision
 * detection is kind of whacked up, but all in all, I don't think it's an awful
 * game, and I definitely put in a lot of work into it. It was very hard.
 *
 * @author Joshua Crotts & Brandon Willis <--- Thanks to him that this thing
 * even has jumping physics... or spritesheets... or anything really.--->
 */
public class CastlevaniaRunner {

    public static void main(String[] args) {

        Game game = new Game();

        game.start();

    }

}
