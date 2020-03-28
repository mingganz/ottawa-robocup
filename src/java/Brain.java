//
//	File:			Brain.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//
//    Modified by:	Paul Marlow

//    Modified by:      Edgar Acosta
//    Date:             March 4, 2008

import java.lang.Math;
import java.util.ArrayList; 
import java.util.List; 
import java.util.regex.*;

class Brain extends Thread implements SensorInput
{
    //---------------------------------------------------------------------------
    // This constructor:
    // - stores connection to krislet
    // - starts thread for this object
    public Brain(SendCommand krislet, 
		 String team, 
		 char side, 
		 int number, 
		 String playMode, int playerNum)
    {
	m_timeOver = false;
	m_krislet = krislet;
	m_memory = new Memory();
	//m_team = team;
	m_side = side;
	// m_number = number;
	m_playMode = playMode;
	m_playerNum = playerNum;
	start();
    }


    //---------------------------------------------------------------------------
    // This is main brain function used to make decision
    // In each cycle we decide which command to issue based on
    // current situation. the rules are:
    //
    //	1. If you don't know where is ball then turn right and wait for new info
    //
    //	2. If ball is too far to kick it then
    //		2.1. If we are directed towards the ball then go to the ball
    //		2.2. else turn to the ball
    //
    //	3. If we dont know where is opponent goal then turn wait 
    //				and wait for new info
    //
    //	4. Kick ball
    //
    //	To ensure that we don't send commands to often after each cycle
    //	we waits one simulator steps. (This of course should be done better)

    // ***************  Improvements ******************
    // Allways know where the goal is.
    // Move to a place on my side on a kick_off
    // ************************************************

    public void run()
    {
	ObjectInfo object;
	env = new ArrayList<List<String>>(); 
	List<String> objectData = new ArrayList<String>(); 

	// first put it somewhere on my side
	if(Pattern.matches("^before_kick_off.*",m_playMode))
	    m_krislet.move( -Math.random()*52.5 , 34 - Math.random()*68.0 );

	
	while( !m_timeOver )
	    {
		//Look for ball 
		object = m_memory.getObject("ball");
		if( object != null )
		    {
			objectData.add("ball"); 
			objectData.add(Float.toString(object.m_direction)); 
			objectData.add(Float.toString(object.m_distance)); 
			env.add(objectData);
			objectData.clear(); 
		    }
		//Look for Goal 
		if( m_side == 'l' )
		    object = m_memory.getObject("goal r");
		else
		    object = m_memory.getObject("goal l");
		if( object != null )
		    {
			objectData.add("goal"); 
			objectData.add(Float.toString(object.m_direction)); 
			objectData.add(Float.toString(object.m_distance)); 
			env.add(objectData); 
			objectData.clear(); 
		    }
	    }

		// sleep one step to ensure that we will not send
		// two commands in one cycle.
		try{
		    Thread.sleep(2*SoccerParams.simulator_step);
		}catch(Exception e){}

	m_krislet.bye();
		  
    }
    
    public List<List<String>> getENV() {
    	return this.env; 
    }
    
    public void performAction(String action, float amount){
    	if(action.equals("Turn")){
			m_krislet.turn(amount);
			m_memory.waitForNewInfo();
    	}
    	if(action.equals("Kick")){
    		m_krislet.kick(100, amount);
    	}
    	if(action.equals("Dash")){
			m_krislet.dash(10*amount);
    	}
    }


    //===========================================================================
    // Here are suporting functions for implement logic


    //===========================================================================
    // Implementation of SensorInput Interface

    //---------------------------------------------------------------------------
    // This function sends see information
    public void see(VisualInfo info)
    {
	m_memory.store(info);
    }


    //---------------------------------------------------------------------------
    // This function receives hear information from player
    public void hear(int time, int direction, String message)
    {
    }

    //---------------------------------------------------------------------------
    // This function receives hear information from referee
    public void hear(int time, String message)
    {						 
	if(message.compareTo("time_over") == 0)
	    m_timeOver = true;

    }


    //===========================================================================
    // Private members
    private SendCommand	                m_krislet;			// robot which is controled by this brain
    private Memory			m_memory;				// place where all information is stored
    private char			m_side;
    volatile private boolean		m_timeOver;
    private String                      m_playMode;
    private int 						m_playerNum;
    public List<List<String>> env; 
    
}
