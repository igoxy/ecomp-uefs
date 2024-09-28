module controlOutputAC( 
	input wire snA0, 
	input wire snA1,
	input wire snA2,
	input wire snA3,
	input wire snA4,
	output OUT_AC
);
	and And5 (OUT_AC, snA0, snA1, snA2, snA3, snA4);

endmodule