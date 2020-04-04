// Agent practiseagent in project rideau_robocup

/* Initial beliefs and rules */



/* Initial goals */


!start.

/* Plans */

+!start: true <- play.

+before_kick_off <- move;
                    .print("Moved player to the field");
                    !play.	//Places the agent on the field when the environment says 'before_kick_off'.

+ball_not_visible: true <- -ball_visible;
						   turn_fixed_angle; 
						   .abolish(ball_not_visible); !play.

+ball_visible: far_to_ball & not_face_to_ball <- -ball_not_visible; 
												 turnToBall;
												 .abolish(ball_visible); !play.

+ball_visible: far_to_ball & face_to_ball <- -ball_not_visible; 
											 dash;
											 .abolish(ball_visible); !play.

+ball_visible: ball_near & no_goal <- -ball_not_visible; 
									  turn_fixed_angle;
									  .abolish(ball_visible); !play.

+ball_visible: ball_near & have_goal <- -ball_not_visible; 
										kickToGoal;
										.abolish(ball_visible); !play. 
										
+ball_visible: true <- .abolish(ball_visible).     

+ball_visible: ball_near & have_goal <- -ball_not_visible;
											  turnToGoal;
											  .abolish(ball_visible);
											  !kick.  									
+!kick: playerVisible(X) <- kickToPlayer.

+!kick: playerNotVisible <- kickToGoal.

+!dash: playerNotVisible <- dash.

+!dash: playerVisible(X) <- .send(X,tell,requestDistanceFromBall(self));
							?distanceFromBall(Y, Z);
							!getDistanceFromBall;
							?distanceFromBall(L);
							if(L > Y){
							  	slowDash;	//Dash with power of just 10 or 5 (whichever makes the player seem casually running).
							  }
							  else{
							  	dash;	//dash with full power (attachking mode).
							  }.

+!play: true <- play.
				
//To maintain the Consistency of Belief Base

+ball_near <- -far_to_ball.
+far_to_ball <- -ball_near.

+face_to_ball <- -not_face_to_ball.
+not_face_to_ball <- -face_to_ball.

+have_goal <- -no_goal.
+no_goal <- -have_goal.




