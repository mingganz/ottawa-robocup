// Agent sample_agent in project rideau_robocup

/* Initial beliefs and rules */

/* Initial goals */
!start.

/* Plans */
+!start : true <- burn;look.
+fire <- run;look.
+look <- looked;look.
