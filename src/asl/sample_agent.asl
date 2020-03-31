// Agent sample_agent in project rideau_robocup

/* Initial beliefs and rules */

/* Initial goals */
!start.

/* Plans */
+!start : true <- look.
+beforeKickOff <- move;!look.
+!look <- looked;!look.
+kickOff <- dash;!look.