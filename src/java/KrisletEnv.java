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
	
	public Krislet player1; 
	public Krislet player2; 
	public Krislet player3; 
	public Krislet player4; 
	public Krislet player5; 

    private Logger logger = Logger.getLogger("rideau_robocup."+KrisletEnv.class.getName());

    /** Called before the MAS execution with the args informed in .mas2j */
    @Override
    public void init(String[] args){
    	super.init(args);
    	
    	String hostName = new String("");
    	int port = 6000; 
    	String team = new String("Krislet-Rideau"); 
    	
    	/*try {
			//host = InetAddress.getByName("localhost");
    		hostName = new String("");
    		port = 6000;
    		team = new String("Krislet-Rideau");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
    	//mAgents = new HashMap<String, Krislet>();
    	try {
			player1 = new Krislet(InetAddress.getByName(hostName), port, team, 1);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
    		player2 = new Krislet(InetAddress.getByName(hostName), port, team, 2);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			player3 = new Krislet(InetAddress.getByName(hostName), port, team, 3);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			player4 = new Krislet(InetAddress.getByName(hostName), port, team, 4);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			player5 = new Krislet(InetAddress.getByName(hostName), port, team, 5);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			player1.mainLoop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    	try {
			player2.mainLoop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    	try {
			player3.mainLoop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    	try {
			player4.mainLoop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			player5.mainLoop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    }

    @Override
    public boolean executeAction(String agName, Structure action) {
        logger.info("executing: "+action);
        System.out.println("Agent： "+agName);
        
    	if (!mAgents.containsKey(agName)) {
    		
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
