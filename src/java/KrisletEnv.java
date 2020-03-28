// Environment code for project rideau_robocup

import jason.asSyntax.*;
import jason.environment.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

public class KrisletEnv extends Environment {

    private Logger logger = Logger.getLogger("rideau_robocup."+KrisletEnv.class.getName());

    /** Called before the MAS execution with the args informed in .mas2j */
    @Override
    public void init(String[] args) {
    	super.init(args);
    	
    	try {
			host = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	port = 6000;
    	team = new String("Krislet-Rideau");
    	
    	mAgents = new HashMap<String, Krislet>();
    }

    @Override
    public boolean executeAction(String agName, Structure action) {
    	Krislet krislet =null;
        logger.info("executing: "+action);
        System.out.println("Agent： "+agName);
        
    	if (!mAgents.containsKey(agName)) {
			try {
				krislet = new Krislet(host, port, team);
				mAgents.put(agName, krislet);
				System.out.println("bb： "+agName);
				krislet.start();
				System.out.println("gg： "+agName);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else
    	{
    		krislet = mAgents.get(agName);
    	}

    	Brain brain = krislet.getBrain();
    	if (brain != null) {
    		System.out.println("Run Agent： "+agName);
    		brain.runAgent();
    	}
    	
        if (action.getFunctor().equals("burn")) { // you may improve this condition
            //informAgsEnvironmentChanged();
			addPercept(Literal.parseLiteral("fire"));
			return true;

        }
		else if (action.getFunctor().equals("run")){
			logger.info("I am running away, follow me!");
			return true;
		}
		else if (action.getFunctor().equals("look")){
			logger.info("Look!");
			addPercept(Literal.parseLiteral("looked"));
			return true;
		}
		else if (action.getFunctor().equals("looked")){
			logger.info("Looked!");
			clearPercepts();
			addPercept(Literal.parseLiteral("fire"));
			return true;
		}
		else {
			logger.info("executing: " + action + ", but not implemented");
			return false;
		}
    }

    /** Called before the end of MAS execution */
    @Override
    public void stop() {
        super.stop();
    }
    
	private  InetAddress	host;
	private  int	port;
	private  String	team;
	Map<String, Krislet> mAgents;
}
