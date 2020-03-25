// Environment code for project rideau_robocup

import jason.asSyntax.*;
import jason.environment.*;
import java.util.logging.*;

public class Krislet extends Environment {

    private Logger logger = Logger.getLogger("rideau_robocup."+Krislet.class.getName());

    /** Called before the MAS execution with the args informed in .mas2j */
    @Override
    public void init(String[] args) {
    }

    @Override
    public boolean executeAction(String agName, Structure action) {
        logger.info("executing: "+action+", but not implemented!");
        if (true) { // you may improve this condition
             informAgsEnvironmentChanged();
        }
        return true; // the action was executed with success
    }

    /** Called before the end of MAS execution */
    @Override
    public void stop() {
        super.stop();
    }
}
