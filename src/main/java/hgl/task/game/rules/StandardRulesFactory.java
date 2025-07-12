package hgl.task.game.rules;

public class StandardRulesFactory implements RulesFactory {
    
    @Override
    public Rules getGameRules() {
        return new StandardRules();
    }
}
