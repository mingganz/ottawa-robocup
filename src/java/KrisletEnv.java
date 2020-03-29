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
import java.util.regex.Pattern;

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
    		runAction(krislet);
    	}
    	
		if (action.getFunctor().equals("move")){
			logger.info("Kick off!");
			krislet.move( -Math.random()*52.5 , 34 - Math.random()*68.0 );
			return true;
		}
		else if (action.getFunctor().equals("look")){
			logger.info("Look!");
			addPercept(Literal.parseLiteral("looked"));
			return true;
		}
		else if (action.getFunctor().equals("looked")){
			logger.info("looked!");
			addPercept(Literal.parseLiteral("look"));
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
    
    
    public void runAction(Krislet m_krislet)
	{
		ObjectInfo object;
		Memory m_memory = m_krislet.getBrain().getMemory();
		char m_side = 'l';
	
		logger.info(m_krislet.getBrain().m_playMode);
		if (m_krislet.m_kicked_off == false) {
			// first put it somewhere on my side
			if(Pattern.matches("^before_kick_off.*",m_krislet.getBrain().m_playMode))
				//m_krislet.move( -Math.random()*52.5 , 34 - Math.random()*68.0 );
				addPercept(Literal.parseLiteral("fire"));
			m_krislet.m_kicked_off = true;
		}
	
		object = m_memory.getObject("ball");
		if( object == null )
		    {
			logger.info("turn"); 
			// If you don't know where is ball then find it
			m_krislet.turn(40);
			m_memory.waitForNewInfo();
		    }
		else if( object.m_distance > 1.0 )
		    {
			// If ball is too far then
			// turn to ball or 
			// if we have correct direction then go to ball
			if( object.m_direction != 0 )
			    m_krislet.turn(object.m_direction);
			else
			    m_krislet.dash(10*object.m_distance);
		    }
		else 
		    {
			// We know where is ball and we can kick it
			// so look for goal
			if( m_side == 'l' )
			    object = m_memory.getObject("goal r");
			else
			    object = m_memory.getObject("goal l");
	
			if( object == null )
			    {
				m_krislet.turn(40);
				m_memory.waitForNewInfo();
			    }
			else
			    m_krislet.kick(100, object.m_direction);
		    }
	
		// sleep one step to ensure that we will not send
		// two commands in one cycle.
		try{
		    Thread.sleep(2*SoccerParams.simulator_step);
		}catch(Exception e){} 	
	}
    
	private  InetAddress	host;
	private  int	port;
	private  String	team;
	Map<String, Krislet> mAgents;
}
