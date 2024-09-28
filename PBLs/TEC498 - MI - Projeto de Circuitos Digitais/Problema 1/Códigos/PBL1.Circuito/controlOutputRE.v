module controlOutputRE( 
	input AC,
	input CO,
	output OUT_RE
);


	and And6 (OUT_RE, !AC, !CO);
	
endmodule