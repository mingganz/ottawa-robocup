// Agent sample_agent in project rideau_robocup

/* Initial beliefs and rules */

/* Initial goals */
!start.

/* Plans */
+!start: true <- .print("start playing...");play.
+before_kick_off <- .print("move player to field...");move;!play.
+!play: kickOff <- .print("game is starting...");dash;!play.
+!play: no_ball | close_to_ball & no_goal <- .print("turn...");turn_fixed_angle;!play.
+!play: far_to_ball & not_face_to_ball <- .print("turn to ball...");turn;!play.
+!play: far_to_ball & face_to_ball <- .print("dash..."); dash;!play.
+!play: close_to_ball & have_goal <- .print("kick...");kick;!play.
+!play <- .wait(100);!play.