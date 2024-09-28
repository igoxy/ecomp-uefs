module controlOutputDis(
	input BUTTON,
	input ENTRADA_AC, 
	input ENTRADA_CO,  
	input ENTRADA_RE, 
	output SEG_A,
	output SEG_B,
	output SEG_C,
	output SEG_D,
	output SEG_E,
	output SEG_F,
	output SEG_G

);
	
	wire  w0, w1, w2, w3, w4,w5, w6;
	
	and And7 (w0, ENTRADA_AC, !ENTRADA_CO, !ENTRADA_RE);
	and And8 (w1, !ENTRADA_AC, ENTRADA_CO, !ENTRADA_RE);
	and And9 (w2, !ENTRADA_AC, !ENTRADA_CO, ENTRADA_RE);
	
	or Or1 (w3, w0, w1, w2);
	or Or2 (w4, w0, w2);
	
	and And10 (SEG_A, w3, BUTTON);
	and And11 (SEG_B, w4, BUTTON);
	and And12 (SEG_C, w0, BUTTON);
	and And13 (SEG_D, w1, BUTTON);
	and And14 (SEG_E, w3, BUTTON);
	and And15 (SEG_F, w3, BUTTON);
	and And16 (SEG_G, w0, BUTTON);

endmodule
